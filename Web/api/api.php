<?php
/**
 * Created by PhpStorm.
 * User: Gustavo
 * Date: 08/09/2015
 * Time: 09:34
 */

require_once "../includes/config.php";
require_once "../includes/utils.php";

function get_PoI_info($id)
{
    $sql = "SELECT typeId, regionId, name, description, address, latitude, longitude, numLikes, numDislikes, numVisits FROM PointsOfInterest WHERE id = ?";
    $parameters = array();
    $parameters[0] = $id;
    $typeParameters = "i";

    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    if ($result->num_rows == 0)
    {
        http_response_code(404);
        return null;
    }
    $data = $result->fetch_array(MYSQLI_ASSOC);
    return array_map("utf8_encode", $data);
}

function get_reviews($id)
{
    $sql = "SELECT userId, poiId, comment, like FROM Reviews WHERE poiId = ?";
    $parameters = array();
    $parameters[0] = $id;
    $typeParameters = "i";

    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    $data = $result->fetch_array(MYSQLI_ASSOC);
    return array_map("utf8_encode", $data);
}

function get_achievements()
{
    $sql = "SELECT id, name, description FROM Achievement";

    $result = db_query($sql);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    $data = $result->fetch_array(MYSQLI_ASSOC);
    return array_map("utf8_encode", $data);
}

function get_suggestions($currLat, $currLon, $minDist, $maxDist)
{
    $sql = "SELECT typeId, regionId, name, description, address, latitude, longitude,
            (POW(69.1 * (latitude - ?), 2) +
            POW(69.1 * (? - longitude) * COS(latitude / 57.3), 2)) AS distance, rating
            FROM PointsOfInterest HAVING distance BETWEEN ? AND ? ORDER BY rating DESC";

    $parameters = array();
    $parameters[0] = $currLat;
    $parameters[1] = $currLon;
    $parameters[2] = pow($minDist, 2);
    $parameters[3] = pow($maxDist, 2);
    $typeParameters = "dddd";

    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    $data = $result->fetch_all(MYSQLI_ASSOC);
    return array_map("utf8_encode_array", $data);
}

function get_visited($accessToken)
{
    global $fb;
    if (!validate_access_token($fb, $accessToken))
    {
        http_response_code(401);
        return null;
    }
    $friends = getFacebookFriends($fb, $accessToken);
    $list = "?";
    $parameters = array();
    $userNode = getFacebookGraphUser($fb, $accessToken);
    $parameters[0] = $userNode->getID();
    $typeParameters = "s";
    foreach ($friends as $friend) {
        $list .= ", ?";
        array_push($parameters, $friend["id"]);
        $typeParameters .= "s";
    }
    $sql = "SELECT PointsOfInterest.id, PointsOfInterest.userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes
            FROM PointsOfInterest INNER JOIN PoIVisits ON PoIVisits.poiId = PointsOfInterest.id WHERE PoIVisits.userId IN (" . $list .")";

    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    $data = $result->fetch_all(MYSQLI_ASSOC);
    return array_map('utf8_encode_array', $data);
}

function set_visited($accessToken, $id)
{
    global $fb;
    if (!validate_access_token($fb, $accessToken))
    {
        http_response_code(401);
        return null;
    }
    $sql = "INSERT INTO PoIVisits (userId, poiId, visitDate) VALUES (?, ?, CURRENT_DATE)";
    $parameters = array();
    global $fb;
    $userNode = getFacebookGraphUser($fb, $accessToken);
    $parameters[0] = $userNode->getID();
    $parameters[1] = $id;
    $typeParameters = "si";

    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    return true;
}

function set_not_visited($accessToken, $id)
{
    global $fb;
    if (!validate_access_token($fb, $accessToken))
    {
        http_response_code(401);
        return null;
    }
    $sql = "DELETE FROM PoIVisits WHERE userId = ? AND poiId = ?";
    $parameters = array();
    $userNode = getFacebookGraphUser($fb, $accessToken);
    $parameters[0] = $userNode->getID();
    $parameters[1] = $id;
    $typeParameters = "si";

    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    return true;
}

function make_review($accessToken, $id, $comment, $like)
{
    global $fb;
    if (!validate_access_token($fb, $accessToken))
    {
        http_response_code(401);
        return null;
    }
    $sql = "INSERT INTO Reviews (userId, poiId, comment, `like`) VALUES (?, ?, ?, ?)";
    $parameters = array();
    global $fb;
    $userNode = getFacebookGraphUser($fb, $accessToken);
    $parameters[0] = $userNode->getID();
    $parameters[1] = $id;
    $parameters[2] = $comment;
    $parameters[3] = $like;
    $typeParameters = "sisi";

    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    return true;
}

function delete_review($accessToken, $id)
{
    global $fb;
    if (!validate_access_token($fb, $accessToken))
    {
        http_response_code(401);
        return null;
    }

    $sql = "DELETE FROM Reviews WHERE userId = ? AND poiId = ?";
    $parameters = array();
    global $fb;
    $userNode = getFacebookGraphUser($fb, $accessToken);
    $parameters[0] = $userNode->getID();
    $parameters[1] = $id;
    $typeParameters = "si";

    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result)
    {
        http_response_code(500);
        return null;
    }
    return true;
}

$value = "An error has occurred";

if (isset($_GET["action"]))
{
    if (isset($_SESSION["facebook_access_token"])) echo "Access Token: ".$_SESSION["facebook_access_token"];
    switch (strtolower($_GET["action"]))
    {
        case "get_reviews":
            if (isset($_GET["id"]))
                $value = get_reviews($_GET["id"]);
            else
                $value = "Missing argument";
            break;
        case "get_poi_info":
            if (isset($_GET["id"]))
                $value = get_PoI_info($_GET["id"]);
            else
                $value = "Missing argument";
            break;
        case "get_achievements":
            $value = get_achievements();
            break;
        case "get_suggested_pois":
            if (isset($_GET["currLat"]) && isset($_GET["currLon"]) && isset($_GET["minDist"]) && isset($_GET["maxDist"]))
                $value = get_suggestions($_GET["currLat"], $_GET["currLon"], $_GET["minDist"], $_GET["maxDist"]);
            else
                $value = "Missing argument";
            break;
        case "get_visited":
            if (isset($_GET["accessToken"]))
                $value = get_visited($_GET["accessToken"]);
            else
                $value = "Missing argument";
            break;
        case "set_visited":
            if (isset($_GET["accessToken"]) && isset($_GET["id"]))
                $value = set_visited($_GET["accessToken"], $_GET["id"]);
            else
                $value = "Missing argument";
            break;
        case "set_not_visited":
            if (isset($_GET["accessToken"]) && isset($_GET["id"]))
                $value = set_visited($_GET["accessToken"], $_GET["id"]);
            else
                $value = "Missing argument";
            break;
        case "make_review":
            if (isset($_GET["id"]) && isset($_GET["accessToken"]) && isset($_GET["comment"]) && isset($_GET["like"]))
                $value = make_review($_GET["accessToken"], $_GET["id"], $_GET["comment"], $_GET["like"]);
            else
                $value = "Missing argument";
        case "delete_review":
            if (isset($_GET["id"]) && isset($_GET["accessToken"]))
                $value = make_review($_GET["accessToken"], $_GET["id"]);
            else
                $value = "Missing argument";
        default:
            $value = "Unknown request.";
    }
}

//return JSON array
header('Content-Type: application/json; charset=utf-8');
echo json_encode($value, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);

require_once "../includes/db_disconnect.php";
?>