<?php
/**
 * Created by PhpStorm.
 * User: Gustavo
 * Date: 08/09/2015
 * Time: 21:18
 */

require_once "../includes/config.php";

session_destroy();
header('Location: ../index.php');
?>