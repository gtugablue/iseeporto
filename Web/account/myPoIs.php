<?php
require_once "../includes/config.php";

if (!isset($_SESSION['facebook_access_token'])) {
    header("Location: ../index.php");
    die();
}
// Logged in!
require_once "../includes/utils.php";
$userNode = getFacebookGraphUser($fb, $_SESSION['facebook_access_token']);
?>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
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
            <a class="navbar-brand" href="index.php">iSee Porto</a>
        </div>
        <!-- Top Menu Items -->
        <ul class="nav navbar-right top-nav">
            <li>
                <a href="#"><i class="fa fa-user"></i> <?php echo $userNode->getName(); ?></a>
            </li>
            <li>
                <a href="logout.php"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
            </li>
        </ul>
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav">
                <li class="active">
                    <a href="myPoIs.php"><i class="fa fa-fw fa-table"></i> Os meus PoIs</a>
                </li>
                <li>
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
                        Os meus PoIs
                    </h1>
                    <ol class="breadcrumb">
                        <li>
                            <i class="fa fa-dashboard"></i>  <a href="index.php">Painel de Controlo</a>
                        </li>
                        <li class="active">
                            <i class="fa fa-table"></i> Os meus PoIs
                        </li>
                    </ol>
                </div>
            </div>
            <!-- /.row -->

            <div class="row">
                <div class="col-lg-6">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Descrição</th>
                                <th>Endereço</th>
                                <th>Coordenadas</th>
                                <th>Tipo</th>
                            </tr>
                            </thead>
                            <tbody>
                            <?php
                            $sql = "SELECT name, description, address, latitude, longitude, type FROM PointsOfInterest INNER JOIN TypeOfPoI WHERE typeId = TypeOfPoI.id AND userId = ?";
                            $parameters = array();
                            $parameters[0] = getFacebookGraphUser($fb, $_SESSION["facebook_access_token"])->getID();
                            $typeParameters = "s";

                            $result = db_query($sql, $parameters, $typeParameters);
                            if (!$result || $result->num_rows == 0) {
                                echo "<tr>";
                                echo "<td>----</td>";
                                echo "<td>----</td>";
                                echo "<td>----</td>";
                                echo "<td>----</td>";
                                echo "<td>----</td>";
                                echo "</tr>";
                            } else {
                                while ($row = $result->fetch_row()) {
                                    echo "<tr>";
                                    echo "<td>" . $row[0] . "</td>";
                                    echo "<td>" . $row[1] . "</td>";
                                    echo "<td>" . $row[2] . "</td>";
                                    echo "<td>" . $row[3] . " " . $row[4] . "</td>";
                                    echo "<td>" . $row[5] . "</td>";
                                    echo "</tr>";
                                }
                            }
                            ?>
                            </tbody>
                        </table>
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
