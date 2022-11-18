<?php 
	include "connect.php";
	$phone_number = $_POST['phone_number'];
	$name_user = $_POST['nameuser'];
	$birth_day = $_POST['birthday'];
	$gen_der = $_POST['gender'];
	$e_mail = $_POST['email'];
	$sta_tus = $_POST['status'];
	$home_town =$_POST['hometown'];

	$manguser = array();
	$query = "UPDATE users 
				SET nameuser = '$name_user', 
				birthday = '$birth_day',  
				gender = '$gen_der', 
				email = '$e_mail', 
				status = '$sta_tus', 
				hometown = '$home_town'
				WHERE phonenumber = '$phone_number' ";
	$data 			= mysqli_query($conn, $query);
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>
