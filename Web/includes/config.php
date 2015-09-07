<?php

ini_set('display_errors',1);
ini_set('display_startup_errors',1);
error_reporting(-1);

session_start();

define('FACEBOOK_SDK_SRC_DIR', './libs/facebook/');
require_once FACEBOOK_SDK_SRC_DIR . 'autoload.php';

$fb = new Facebook\Facebook([
    'app_id' => '1657545704515352',
    'app_secret' => 'a495d66defd01e9c0ffe786cefb3aa08',
    'default_graph_version' => 'v2.4',
]);
?>