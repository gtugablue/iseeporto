<?php
require_once "../includes/config.php";

if (!isset($_SESSION['facebook_access_token'])) {
    header("Location: ../index.php");
    die();
}
// Logged in!
require_once "../includes/utils.php";
$userNode = getFacebookGraphUser($fb, $_SESSION['facebook_access_token']);
$formError = "";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (!empty($_POST["type"]) && !empty($_POST["name"]) && !empty($_POST["description"]) && !empty($_POST["address"]) && !empty($_POST["latitude"]) && !empty($_POST["longitude"])) {
        $sql = "INSERT INTO PointsOfInterest (userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, active) VALUES (?, ?, 1, ?, ?, ?, ?, ?, CURRENT_DATE, true)";
        $parameters = array();
        $userNode = getFacebookGraphUser($fb, $_SESSION["facebook_access_token"]);
        $parameters[0] = $userNode->getID();
        $parameters[2] = $_POST["type"];
        $parameters[3] = $_POST["name"];
        $parameters[4] = $_POST["description"];
        $parameters[5] = $_POST["address"];
        $parameters[6] = $_POST["latitude"];
        $parameters[7] = $_POST["longitude"];
        $typeParameters = "sisssdd";

        $result = db_query($sql, $parameters, $typeParameters);
        if ($result) {
            $allowedExts = array("jpg", "jpeg", "gif", "png");
            $photoName = explode(".", $_FILES["photo"]["name"]);
            $extension = end($photoName);

            if ($_FILES["photo"]["type"] == "image/gif" || $_FILES["photo"]["type"] == "image/jpg" || $_FILES["photo"]["type"] == "image/jpeg" || $_FILES["photo"]["type"] == "image/png" && $_FILES["photo"]["size"] < 2500000 && in_array($extension, $allowedExts)) {
                if ($_FILES["photo"]["error"] != UPLOAD_ERR_OK) {
                    $formError = "Erro: " . $_FILES["photo"]["error"] . "<br />";
                } else {
                    global $db_connection;
                    $fname = '../uploads/PoI_photos/' . mysqli_insert_id($db_connection) . '.jpg';
                    move_uploaded_file($_FILES["photo"]["tmp_name"], $fname);
                    $image = '';

                    switch ($_FILES["photo"]["type"]) {
                        case "image/gif":
                            $image = imagecreatefromgif($fname);
                            break;
                        case "image/jpg":
                        case "image/jpeg":
                            $image = imagecreatefromjpeg($fname);
                            break;
                        case "image/png":
                            $image = imagecreatefrompng($fname);
                            break;
                    }
                    imagejpeg($image, $fname);
                }
            } else {
                $formError = "Erro: ocorreu um problema ao enviar a fotografia.";
            }
        }
    } else {
        $formError = "Ocorreu um erro. Por favor certifique-se que preencheu corretamente todos os campos.";
    }
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>iSee Porto - Painel de Controlo</title>

    <!-- Bootstrap Core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../css/sb-admin.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="../">iSee Porto</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li>
                    <img class="circularImage" src="https://graph.facebook.com/<?php echo $userNode->getId(); ?>/picture?type=large"/>
                </li>
                <li>
                    <a href="#"><?php echo $userNode->getName(); ?></a>
                </li>
                <li>
                    <a href="logout.php"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li>
                        <a href="myPoIs.php"><i class="fa fa-fw fa-table"></i> Os meus PoIs</a>
                    </li>
                    <li class="active">
                        <a href="createPoI.php"><i class="fa fa-fw fa-edit"></i> Criar PoI</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Criar PoI
                        </h1>
                        <ol class="breadcrumb">
                            <li>
                                <i class="fa fa-dashboard"></i>  <a href="index.php">Painel de Controlo</a>
                            </li>
                            <li class="active">
                                <i class="fa fa-edit"></i> Criar PoI
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-6">

                        <form role="multipart/form-data" action="createPoI.php" method="post" enctype="multipart/form-data">

                            <?php
                            if ($formError != "") {
                                ?>
                                <div class="form-group">
                                    <label><?php echo $formError; ?></label>
                                </div>
                                <?php
                            }
                            ?>

                            <div class="form-group">
                                <label>Nome</label>
                                <input name="name" class="form-control" <?php if (isset($_POST["name"])) echo 'value='.$_POST["name"]; ?>>
                            </div>

                            <div class="form-group">
                                <label>Descrição</label>
                                <textarea name="description" class="form-control" rows="5"><?php if (isset($_POST["description"])) echo $_POST["description"]; ?></textarea>
                            </div>

                            <div class="form-group">
                                <label>Endereço</label>
                                <input name="address" class="form-control" <?php if (isset($_POST["address"])) echo 'value='.$_POST["address"]; ?>>
                            </div>

                            <div class="form-group">
                                <label>Tipo</label>
                                <select name="type" class="form-control">
                                    <?php
                                    $sql = "SELECT id, type FROM TypeOfPoI";
                                    $parameters = array();
                                    $typeParameters = "";
                                    $result = db_query($sql, $parameters, $typeParameters);
                                    if ($result && $result->num_rows > 0) {
                                        $rows = $result->fetch_all();
                                        foreach($rows as $row) {
                                            echo "<option value=$row[0]> $row[1]</option>";
                                        }
                                    }
                                    ?>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Latitude</label>
                                <input name="latitude" class="form-control" placeholder="41.1579407" <?php if (isset($_POST["latitude"])) echo 'value='.$_POST["latitude"]; ?>>
                                <label>Longitude</label>
                                <input name="longitude" class="form-control" placeholder="-8.6291025" <?php if (isset($_POST["longitude"])) echo 'value='.$_POST["longitude"]; ?>>
                            </div>

                            <div class="form-group">
                                <label>Fotografia</label>
                                <input id="photo" name="photo" type="file">
                            </div>

                            <button type="submit" class="btn btn-default">Submit</button>

                        </form>

                    </div>
                </div>
                <!-- /.row -->

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../js/bootstrap.min.js"></script>

</body>

</html>
