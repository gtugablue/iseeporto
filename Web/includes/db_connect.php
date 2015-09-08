<?php
require_once "config.php";
$db_connection = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD) or print (mysqli_error());
mysqli_select_db($db_connection, DB_DATABASE) or print(mysqli_error());
?>