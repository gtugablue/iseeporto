<?php
$db_connection = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

if($db_connection->connect_error)
{
    die("$db_connection->connect_errno: $db_connection->connect_error");
}
?>