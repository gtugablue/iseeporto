<?php
/**
 * Created by PhpStorm.
 * User: Gustavo
 * Date: 08/09/2015
 * Time: 09:34
 */

require_once "../includes/config.php";
require_once "../includes/db_connect.php";

function get_PoI_info_by_ID($id)
{
    $PoI_info = array();

    // normally this info would be pulled from a database.
    // build JSON array.
    switch ($id){
        case 1:
            $PoI_info = array("app_name" => "Web Demo", "app_price" => "Free", "app_version" => "2.0");
            break;
        case 2:
            $PoI_info = array("app_name" => "Audio Countdown", "app_price" => "Free", "app_version" => "1.1");
            break;
        case 3:
            $PoI_info = array("app_name" => "The Tab Key", "app_price" => "Free", "app_version" => "1.2");
            break;
        case 4:
            $PoI_info = array("app_name" => "Music Sleep Timer", "app_price" => "Free", "app_version" => "1.9");
            break;
    }

    return $PoI_info;
}

function get_app_list()
{
    //normally this info would be pulled from a database.
    //build JSON array
    $app_list = array(array("id" => 1, "name" => "Web Demo"), array("id" => 2, "name" => "Audio Countdown"), array("id" => 3, "name" => "The Tab Key"), array("id" => 4, "name" => "Music Sleep Timer"));

    return $app_list;
}

$value = "An error has occurred";

if (isset($_GET["action"]))
{
    switch (strtolower($_GET["action"]))
    {
        case "get_app_list":
            $value = get_app_list();
            break;
        case "get_poi_info_by_id":
            if (isset($_GET["id"]))
                $value = get_PoI_info_by_ID($_GET["id"]);
            else
                $value = "Missing argument";
            break;
        default:
            $value = "Unknown request.";
    }
}

//return JSON array
echo json_encode($value);

require_once "../includes/db_disconnect.php";
?>