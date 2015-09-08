<?php
/**
 * Created by PhpStorm.
 * User: Gustavo
 * Date: 08/09/2015
 * Time: 09:34
 */

require_once "../includes/config.php";
require_once "../includes/db_connect.php";

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

    $result = $prepStatement->get_result();

    if (!$result || $result->num_rows == 0) return null;

    $row = $result->fetch_array(MYSQLI_ASSOC);

    return array_map("utf8_encode", $row);
}

function get_PoI_info($id)
{
    $sql = "SELECT typeId, regionId, name, description, latitude, longitude FROM PointsOfInterest WHERE id = ?";
    return db_query($sql, $id);
}

function get_reviews($id)
{
    $sql = "SELECT userId, poiId, comment, like FROM Reviews WHERE poiId = ?";
    return db_query($sql, $id);
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
        default:
            $value = "Unknown request.";
    }
}

//return JSON array
header('Content-Type: application/json; charset=utf-8');
echo json_encode($value, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);

require_once "../includes/db_disconnect.php";
?>