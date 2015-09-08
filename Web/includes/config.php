<?php

// Error reporting
ini_set('display_errors',1);
ini_set('display_startup_errors',1);
error_reporting(-1);

// PHP session
session_start();

// Facebook
define('FACEBOOK_SDK_SRC_DIR', __DIR__ . '/..' . '/libs/facebook/');
require_once FACEBOOK_SDK_SRC_DIR . 'autoload.php';
$fb = new Facebook\Facebook([
    'app_id' => '1657545704515352',
    'app_secret' => 'a495d66defd01e9c0ffe786cefb3aa08',
    'default_graph_version' => 'v2.4',
]);

// Database
define ('DB_USER', "adm51187_isee");
define ('DB_PASSWORD', "iseeporto2015");
define ('DB_DATABASE', "adm51187_iseeporto");
define ('DB_HOST', "revtut.net");
?>