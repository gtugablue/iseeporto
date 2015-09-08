<?php
/**
 * Created by PhpStorm.
 * User: Gustavo
 * Date: 08/09/2015
 * Time: 09:34
 */

require_once "../includes/config.php";
require_once "../includes/db_connect.php";
require_once "../includes/utils.php";

function db_query() {
    $numArgs=func_num_args();
    if($numArgs < 1)
        return null;

    // Get arguments
    $argList=func_get_args();
    $query = (string) $argList[0];

    global $db_connection;

    $prepStatement = $db_connection->stmt_init();
    if(!$prepStatement->prepare($query))
    {
        print "Failed to prepare statement\n";
    }

    for($i=1;$i<$numArgs;$i++) {
        $prepStatement->bind_param("s", $argList[$i]);
    }

    // database query
    $prepStatement->execute();

    return $prepStatement->get_result();
}

function get_PoI_info($id)
{
    $sql = "SELECT typeId, regionId, name, description, latitude, longitude FROM PointsOfInterest WHERE id = ?";
    $result = db_query($sql, $id);
    if (!$result || $result->num_rows == 0) return null;
    $data = $result->fetch_array(MYSQLI_ASSOC);
    return array_map("utf8_encode", $data);
}

function get_reviews($id)
{
    $sql = "SELECT userId, poiId, comment, like FROM Reviews WHERE poiId = ?";
    $result = db_query($sql, $id);
    if (!$result || $result->num_rows == 0) return null;
    $data = $result->fetch_array(MYSQLI_ASSOC);
    return array_map("utf8_encode", $data);
}

function get_achievements()
{
    $sql = "SELECT id, name, description FROM Achievement";
    $result = db_query($sql);
    if (!$result || $result->num_rows == 0) return null;
    $data = $result->fetch_array(MYSQLI_ASSOC);
    return array_map("utf8_encode", $data);
}

function get_suggestions($currLat, $currLon, $minDist, $maxDist)
{
    $sql = "SELECT typeId, regionId, name, description, address, latitude, longitude,
            (POW(69.1 * (latitude - ?), 2) +
            POW(69.1 * (? - longitude) * COS(latitude / 57.3), 2)) AS distance
            FROM PointsOfInterest WHERE distance BETWEEN ? AND ? ORDER BY ranking DESC)";
    $result = db_query($sql, $currLat, $currLon, pow($minDist, 2), pow($maxDist, 2));
    if (!$result || $result->num_rows == 0) return null;
    $data = $result->fetch_array(MYSQLI_ASSOC);
    return array_map("utf8_encode", $data);
}

$value = "An error has occurred";

if (isset($_GET["action"]))
{
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
            if (isset($_GET["currLat"]) && iseet($_GET["currLon"]) && isset($_GET["minDist"]) && isset($_GET["maxDist"]))
                $value = get_suggestions($_GET["currLat"], $_GET["currLong"], $_GET["minDist"], $_GET["maxDist"]);
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