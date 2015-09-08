<?php
/**
 * Created by PhpStorm.
 * User: Gustavo
 * Date: 08/09/2015
 * Time: 09:34
 */

require_once "../includes/config.php";
require_once "../includes/db_connect.php";

function db_get() {
    $numArgs=func_num_args();
    if($numArgs < 1)
        return null;

    // Get arguments
    $type = '';
    $argList=func_get_args();
    $query = (string) $argList[0];

    global $db_connection;

    $prepStatement = $db_connection->prepare($query);

    for($i=1;$i<$numArgs;$i++) {
        $prepStatement->bind_param("s", $argList[$i]);
    }

    // database query
    $prepStatement->execute();

    // Throw an exception if the result metadata cannot be retrieved
    if (!$meta = $prepStatement->result_metadata())
    {
        throw new Exception($prepStatement->error);
    }

    // The data array
    $data = array();

    // The references array
    $refs = array();

    // Iterate over the fields and set a reference
    while ($name = $meta->fetch_field())
    {
        $refs[] =& $data[$name->name];
    }

    // Free the metadata result
    $meta->free_result();

    // Throw an exception if the result cannot be bound
    if (!call_user_func_array(array($prepStatement, 'bind_result'), $refs))
    {
        throw new Exception($prepStatement->error);
    }

    return array_map("utf8_encode", $data);
}

function get_PoI_info($id)
{
    $sql = "SELECT typeId, regionId, name, description, latitude, longitude FROM PointsOfInterest WHERE id = ?";
    return db_get_test($sql, $id);
}

function get_reviews($id)
{
    $sql = "SELECT userId, poiId, comment, like FROM Reviews WHERE poiId = ?";
    return db_get($sql, $id);
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