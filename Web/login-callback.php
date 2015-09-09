<?php
require_once "includes/config.php";
require_once "includes/utils.php";

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
    login($accessToken);
    $_SESSION['facebook_access_token'] = (string) $accessToken;

    $userNode = getFacebookGraphUser($fb, $accessToken);

    $url = "http://graph.facebook.com/".$userNode->getId()."/picture?type=large";

    $headers = get_headers($url, 1);

    if( isset($headers['Location']) )
        echo '<img src="'.$headers['Location'].'" alt="Profile picture"/>'; // string
    echo "<br />";
    echo "Hello " . $userNode->getId() . " you've been successfully logged in!";
    header("Location: account/");
} else {
    echo "Failed to login...";
}

?>