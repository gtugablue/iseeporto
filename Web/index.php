<?php
require_once "includes/config.php";

$fb = new Facebook\Facebook([
  'app_id' => '1657545704515352',
  'app_secret' => 'a495d66defd01e9c0ffe786cefb3aa08',
  'default_graph_version' => 'v2.4',
  ]);

$helper = $fb->getRedirectLoginHelper();
$permissions = ['email']; // optional
$loginUrl = $helper->getLoginUrl('http://paginas.fe.up.pt/~up201304143/iSee%20Porto/login-callback.php', $permissions);

echo '<a href="' . $loginUrl . '">Log in with Facebook!</a>';
?>