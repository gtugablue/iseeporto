<?php
require_once "includes/config.php";

$helper = $fb->getRedirectLoginHelper();
try {
    $accessToken = $helper->getAccessToken();
} catch(Facebook\Exceptions\FacebookResponseException $e) {
    // When Graph returns an error
    echo 'Graph returned an error: ' . $e->getMessage();
    exit;
} catch(Facebook\Exceptions\FacebookSDKException $e) {
    // When validation fails or other local issues
    echo 'Facebook SDK returned an error: ' . $e->getMessage();
    exit;
}

if (isset($accessToken)) {
    // Logged in!
    $_SESSION['facebook_access_token'] = (string) $accessToken;

    $response = $fb->get("/me", $accessToken);
    $userNode = $response->getGraphUser();

    echo "Hello " . $userNode->getId() . " you've been successfully logged in!";
} else {
    echo "Failed to login...";
}

?>