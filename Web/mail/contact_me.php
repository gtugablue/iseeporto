<?php
// Check for empty fields
if(empty($_POST['name'])  		||
   empty($_POST['email']) 		||
   empty($_POST['phone']) 		||
   empty($_POST['message'])	||
   !filter_var($_POST['email'],FILTER_VALIDATE_EMAIL))
   {
	echo "No arguments Provided!";
	return false;
   }
	
$name = $_POST['name'];
$email_address = $_POST['email'];
$phone = $_POST['phone'];
$message = $_POST['message'];
	
$to = 'webforms@iseeporto.revtut.net';
$email_subject = "Nova mensagem para iSeePorto:  $name";
$email_body = "Recebida nova mensagem enviada através do formulário online.\n\n"."Detalhes:\n\nNome: $name\n\nEmail: $email_address\n\nTelemóvel: $phone\n\nMensagem:\n$message";
$headers = "From: noreply@iseeporto.revtut.net\n";
$headers .= "Reply-To: $email_address";
mail($to,$email_subject,$email_body,$headers);
return true;			
?>