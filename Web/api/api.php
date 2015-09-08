<?php
/**
 * Created by PhpStorm.
 * User: Gustavo
 * Date: 08/09/2015
 * Time: 09:34
 */

require_once "../includes/config.php";
require_once "../includes/db_connect.php";

function db_get($query)
{
    global $db_connection;

    $PoI_info = array();

    $result = mysqli_query($db_connection, $query);

    if (!$result || mysqli_num_rows($result) == 0) return null;

    $row = mysqli_fetch_array($result, MYSQLI_ASSOC);

    return array_map("utf8_encode", $row);
}

function get_PoI_info($id)
{
    $sql = "SELECT typeId, regionId, name, description, latitude, longitude FROM PointsOfInterest WHERE id = ".$id;
    return db_get($sql);
}

function get_reviews($id)
{
    $sql = "SELECT userId, poiId, comment, like FROM Reviews WHERE poiId = ".$id;
    return db_get($sql);
}

$value = "An error has occurred";

if (isset($_GET["action"]))
{
    switch (strtolower($_GET["action"]))
    {
        case "get_reviews":
            if (isset($_GET["id"]))
                $value = get_reviews(mysqli_real_escape_string($db_connection, $_GET["id"]));
            else
                $value = "Missing argument";
            break;
        case "get_poi_info":
            if (isset($_GET["id"]))
                $value = get_PoI_info(mysqli_real_escape_string($db_connection, $_GET["id"]));
            else
                $value = "Missing argument";
            break;
        default:
            $value = "Unknown request.";
    }
}

//return JSON array
header('Content-Type: application/json; charset=utf-8');
echo json_encode($value, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);

require_once "../includes/db_disconnect.php";
?>