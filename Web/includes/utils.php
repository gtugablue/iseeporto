<?php
/**
 * Created by PhpStorm.
 * User: Gustavo
 * Date: 08/09/2015
 * Time: 16:45
 */

require_once "config.php";

/**
 * Calculates the great-circle distance between two points, with
 * the Haversine formula.
 * @param float $latitudeFrom Latitude of start point in [deg decimal]
 * @param float $longitudeFrom Longitude of start point in [deg decimal]
 * @param float $latitudeTo Latitude of target point in [deg decimal]
 * @param float $longitudeTo Longitude of target point in [deg decimal]
 * @param float $earthRadius Mean earth radius in [m]
 * @return float Distance between points in [m] (same as earthRadius)
 */
function haversineGreatCircleDistance($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo, $earthRadius = 6371000)
{
    // convert from degrees to radians
    $latFrom = deg2rad($latitudeFrom);
    $lonFrom = deg2rad($longitudeFrom);
    $latTo = deg2rad($latitudeTo);
    $lonTo = deg2rad($longitudeTo);

    $latDelta = $latTo - $latFrom;
    $lonDelta = $lonTo - $lonFrom;

    $angle = 2 * asin(sqrt(pow(sin($latDelta / 2), 2) +
            cos($latFrom) * cos($latTo) * pow(sin($lonDelta / 2), 2)));
    return $angle * $earthRadius;
}

function utf8_encode_array($array) {
    return array_map('utf8_encode', $array);
}

function db_query($query, $parameters, $typeParameters) {
    // Create prepared statement
    global $db_connection;

    $prepStatement = $db_connection->stmt_init();
    if(!$prepStatement->prepare($query))
    {
        print "Failed to prepare statement\n" . $db_connection->error;
    }

    if(count($parameters) > 0) {
        $queryParams[] = $typeParameters;
        foreach ($parameters as $id => $term)
            $queryParams[] = &$parameters[$id];

        call_user_func_array(array($prepStatement, 'bind_param'), $queryParams);
    }

    // database query
    $prepStatement->execute();

    // In case the query was for example in an insertion
    if($prepStatement->affected_rows != -1)
        return true;

    return $prepStatement->get_result();
}

function validate_access_token($fb, $accessToken)
{
    try {
        $result = getFacebookGraphUser($fb, $accessToken);
        return true;
    }
    catch (Facebook\Exceptions\FacebookResponseException $e)
    {
        return false;
    }
}

function login($accessToken)
{
    global $fb;
    $userNode = getFacebookGraphUser($fb, $accessToken);
    $sql = "SELECT idFacebook FROM User WHERE idFacebook = ?";
    $parameters = array();
    $parameters[0] = $userNode->getID();
    $typeParameters = "i";
    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result) return false;
    if ($result->num_rows > 0)
        return true;

    // Create account
    $sql = "INSERT INTO User (idFacebook, points) VALUES (?, 0)";
    $parameters = array();
    $parameters[0] = $userNode->getID();
    $typeParameters = "i";
    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result) return false;
}

function getFacebookGraphUser($fb, $accessToken)
{
    $response = $fb->get("/me", $accessToken);
    return $response->getGraphUser();
}

function getFacebookFriends($fb, $accessToken)
{
    $friends = $fb->get("/me/friends", $accessToken);
    return $friends->getGraphEdge();
}

?>