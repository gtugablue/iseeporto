<?php
require_once "includes/config.php";

$helper = $fb->getRedirectLoginHelper();
$permissions = ['email']; // optional
$loginUrl = $helper->getLoginUrl('http://'.$_SERVER['SERVER_NAME'] . $_SERVER['REQUEST_URI'].'login-callback.php', $permissions);
echo '<a href="' . $loginUrl . '">Log in with Facebook!</a>';
?>