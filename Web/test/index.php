<?php
require_once "includes/utils.php";
$helper = $fb->getRedirectLoginHelper();
$permissions = ['email', 'user_friends']; // optional
$loginUrl = $helper->getLoginUrl('https://'.$_SERVER['SERVER_NAME'] . dirname($_SERVER['REQUEST_URI']).'login-callback.php', $permissions);

header('Content-Type: text/html; charset=utf-8');
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>iSee Porto</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/onepage.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body id="page-top" class="index">

<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header page-scroll">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Abrir menu</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand page-scroll" href="#page-top">iSeePorto</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="hidden">
                    <a href="#page-top"></a>
                </li>
                <li>
                    <a class="page-scroll" href="#features">Funcionalidades</a>
                </li>
                <li>
                    <a class="page-scroll" href="#contact">Contacto</a>
                </li>
                <li>
                    <?php
                    if(!isset($_SESSION["facebook_access_token"])){
                        echo "<a class=\"page-scroll\" href=\"$loginUrl\">Login</a>";
                    } else {
                        global $fb;
                        $user = getFacebookGraphUser($fb, $_SESSION["facebook_access_token"]);
                        $name = $user->getName();
                        $picUrl = "http://graph.facebook.com/".$userNode->getId()."/picture?type=large";

                        echo "<a class=\"page-scroll\" href=\"http://iseeporto.revtut.net/account/\">$name</a>";
                    }
                    ?>

                </li>
                <li>
                    <ul class="list-inline">
                        <li>
                            <a href="http://www.android.com"><img width="50px" src="assets/img/android_white.png" onmouseover="src='assets/img/android.png'" onmouseout="src='assets/img/android_white.png'"/></a>
                        </li>
                        <li>
                            <a href="http://www.facebook.com"><img width="50px" src="assets/img/facebook_white.png" onmouseover="src='assets/img/facebook.png'" onmouseout="src='assets/img/facebook_white.png'"/></a>
                        </li>
                        <li>
                            <a href="http://www.twitter.com"><img width="50px" src="assets/img/twitter_white.png" onmouseover="src='assets/img/twitter.png'" onmouseout="src='assets/img/twitter_white.png'"/></a>
                        </li>
                    </ul>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

<!-- Header -->
<header>
    <div class="container">
        <div class="intro-text">
            <div class="intro-lead-in">Bem-vindo!</div>
            <div class="intro-heading">Vem conhecer o Porto!</div>
            <a href="#" class="page-scroll btn btn-xl">Android</a> <!-- TODO add android download link -->
        </div>
    </div>
</header>

<!-- Features Section -->
<section id="features">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <h2 class="section-heading">Funcionalidades</h2>
                <h3 class="section-subheading text-muted">(Re)Descobrir novos locais fascinantes na Invicta.</h3>
            </div>
        </div>
        <div class="row text-center">
            <div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-shopping-cart fa-stack-1x fa-inverse"></i>
                    </span>
                <h4 class="service-heading">Social</h4>
                <p class="text-muted">Visita os locais que os teus amigos mais gostaram! Partilha a tua apreciação de novos locais socialmente.</p>
            </div>
            <div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-laptop fa-stack-1x fa-inverse"></i>
                    </span>
                <h4 class="service-heading">Íntimo</h4>
                <p class="text-muted">Os pontos de interesse existentes na aplicação foram adicionados com a melhor intenção, por pessoas como tu!</p>
            </div>
            <div class="col-md-4">
                    <span class="fa-stack fa-4x">
                        <i class="fa fa-circle fa-stack-2x text-primary"></i>
                        <i class="fa fa-lock fa-stack-1x fa-inverse"></i>
                    </span>
                <h4 class="service-heading">Simples</h4>
                <p class="text-muted">Nada de menus confusos, nem opções escondidas. Tudo o que é preciso está bem à vista de uma forma excelentemente organizada.</p>
            </div>
        </div>
    </div>
</section>

<!-- Contact Section -->
<section id="contact">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <h2 class="section-heading">Contacte-nos</h2>
                <h3 class="section-subheading text-muted">Expõe as tuas dúvidas e/ou sugestões.</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <form name="sentMessage" id="contactForm" novalidate>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Nome *" id="name" required data-validation-required-message="Por favor insira o seu nome.">
                                <p class="help-block text-danger"></p>
                            </div>
                            <div class="form-group">
                                <input type="email" class="form-control" placeholder="Email *" id="email" required data-validation-required-message="Por favor insira o seu endereço de email.">
                                <p class="help-block text-danger"></p>
                            </div>
                            <div class="form-group">
                                <input type="tel" class="form-control" placeholder="Telemóvel *" id="phone" required data-validation-required-message="Por favor insira o seu número de telemóvel.">
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <textarea class="form-control" placeholder="Mensagem *" id="message" required data-validation-required-message="Por favor escreva a sua mensagem."></textarea>
                                <p class="help-block text-danger"></p>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="col-lg-12 text-center">
                            <div id="success"></div>
                            <button type="submit" class="btn btn-xl">Enviar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<!-- Footer Section -->
<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <span class="copyright">Copyright &copy; iSeePorto 2015</span>
            </div>
            <div class="col-md-4">
                <ul class="list-inline social-buttons">
                    <li>
                        <a class="page-scroll" href="#page-top"><i class="fa fa-arrow-up"></i></a>
                    </li>
                </ul>
            </div>
            <div class="col-md-4">
                <ul class="list-inline quicklinks">
                    <li>
                        <a href="http://portosummerofcode.com/" target="_blank">Porto Summer of Code</a>
                    </li>
                    <li>
                        <a href="https://github.com/portosummerofcode/iseeporto/" target="_blank">Github</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</footer>

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
<script src="js/classie.js"></script>
<script src="js/cbpAnimatedHeader.js"></script>

<!-- Contact Form JavaScript -->
<script src="js/jqBootstrapValidation.js"></script>
<script src="js/contact_me.js"></script>

<!-- Custom Theme JavaScript -->
<script src="js/onepage.js"></script>

</body>

</html>