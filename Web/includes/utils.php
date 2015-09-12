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
        return $result;
    }
    catch (Facebook\Exceptions\FacebookResponseException $e)
    {
        return false;
    }
}

function login($accessToken)
{
    global $fb;
    $userNode = validate_access_token($fb, $accessToken);
    if (!$userNode) return false;
    $sql = "SELECT idFacebook FROM User WHERE idFacebook = ?";
    $parameters = array();
    $parameters[0] = $userNode->getID();
    $parameters[1] = $userNode->getName();
    $typeParameters = "is";
    $result = db_query($sql, $parameters, $typeParameters);
    if (!$result) return false;
    if ($result->num_rows > 0)
        return true;

    // Create account
    $sql = "INSERT INTO User (idFacebook, name, points) VALUES (?, ?, 0)";
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

/**
 * Resize image - preserve ratio of width and height.
 * Made by: https://gist.github.com/kallak/2994977
 * @param string $sourceImage path to source JPEG image
 * @param string $targetImage path to final JPEG image file
 * @param int $maxWidth maximum width of final image (value 0 - width is optional)
 * @param int $maxHeight maximum height of final image (value 0 - height is optional)
 * @param int $quality quality of final image (0-100)
 * @return bool
 */
function resizeImage($sourceImage, $targetImage, $maxWidth, $maxHeight, $quality = 80)
{
    // Obtain image from given source file.
    if (!$image = @imagecreatefromjpeg($sourceImage))
    {
        return false;
    }

    // Get dimensions of source image.
    list($origWidth, $origHeight) = getimagesize($sourceImage);

    if ($maxWidth == 0)
    {
        $maxWidth  = $origWidth;
    }

    if ($maxHeight == 0)
    {
        $maxHeight = $origHeight;
    }

    // Calculate ratio of desired maximum sizes and original sizes.
    $widthRatio = $maxWidth / $origWidth;
    $heightRatio = $maxHeight / $origHeight;

    // Ratio used for calculating new image dimensions.
    $ratio = min($widthRatio, $heightRatio);

    // Calculate new image dimensions.
    $newWidth  = (int)$origWidth  * $ratio;
    $newHeight = (int)$origHeight * $ratio;

    // Create final image with new dimensions.
    $newImage = imagecreatetruecolor($newWidth, $newHeight);
    imagecopyresampled($newImage, $image, 0, 0, 0, 0, $newWidth, $newHeight, $origWidth, $origHeight);
    imagejpeg($newImage, $targetImage, $quality);

    // Free up the memory.
    imagedestroy($image);
    imagedestroy($newImage);

    return true;
}

/**
 * Example
 * resizeImage('image.jpg', 'resized.jpg', 200, 200);
 */


?>