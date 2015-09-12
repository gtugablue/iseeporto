<?php
require_once "../includes/config.php";

if (!isset($_SESSION['facebook_access_token'])) {
    header("Location: ../index.php");
    die();
}
// Logged in!
require_once "../includes/utils.php";
header("Content-Type: text/html; charset=UTF-8");
$userNode = getFacebookGraphUser($fb, $_SESSION['facebook_access_token']);
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
                <a><?php echo $userNode->getName(); ?></a>
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
                <div class="col-lg-12">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Descrição</th>
                                <th>Endereço</th>
                                <th>Coordenadas</th>
                                <th>Tipo</th>
                                <th>Ação</th>
                            </tr>
                            </thead>
                            <tbody>
                            <?php
                            $sql = "SELECT PointsOfInterest.id, name, description, address, latitude, longitude, type FROM PointsOfInterest INNER JOIN TypeOfPoI WHERE active = true AND typeId = TypeOfPoI.id AND userId = ?";
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
                                    echo "<td>" . htmlspecialchars(iconv('ISO-8859-1', 'UTF-8//IGNORE', $row[1])) . "</td>";
                                    echo "<td>" . htmlspecialchars(iconv('ISO-8859-1', 'UTF-8//IGNORE', $row[2])) . "</td>";
                                    echo "<td>" . htmlspecialchars(iconv('ISO-8859-1', 'UTF-8//IGNORE', $row[3])) . "</td>";
                                    echo "<td>" . htmlspecialchars(iconv('ISO-8859-1', 'UTF-8//IGNORE', $row[4])) . " " . htmlspecialchars(iconv('ISO-8859-1', 'UTF-8//IGNORE', $row[5])) . "</td>";
                                    echo "<td>" . htmlspecialchars(iconv('ISO-8859-1', 'UTF-8//IGNORE', $row[6])) . "</td>";
                                    echo "<td><a href='../api/api.php?action=delete_poi&id=$row[0]&accessToken=" . $_SESSION['facebook_access_token'] . "'<i class='fa fa-remove' title='Remover PoI'></i></a></td>";
                                    echo "<td><a data-toggle='modal' data-target='#qrCode' data-id='$row[0]'<i class='fa fa-qrcode' title='Gerar QR Code'></i></a></td>";
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

    <!-- Modal -->
    <div class="modal fade" id="qrCode" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">QR Code</h4>
                </div>
                <div class="modal-body" id="qr_code_div" >
                    <?php
                    echo "<img src='' id='qr_code_pic' class='img-responsive'/>";
                    ?>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="print_qr">Print</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Replace QR Code Image -->
    <script type="application/javascript">
        $('#qrCode').on('show.bs.modal', function(e) {
            var poiId = $(e.relatedTarget).data('id');
            var token = '<?php echo $_SESSION['facebook_access_token']; ?>';
            $("#qr_code_pic").attr("src", "../api/api.php?action=generate_qr&id=" + poiId + "&accessToken=" + token);
        });
    </script>

    <!-- Print QR Code -->

    <script type="application/javascript">
        $('#print_qr').click(function() {
            w=window.open();
            w.document.write($('#qr_code_div').html());
            w.print();
            w.close();

            $('#qrCode').modal('hide');
        });
    </script>

</body>

</html>
