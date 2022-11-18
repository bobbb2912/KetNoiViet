<?php 
	include "connect.php";
	$phonenumber = $_POST['phonenumber'];
	$nameuser	= $_POST['nameuser'];
	$datecreate = $_POST['datecreate'];
	$password	= $_POST['password'];

	$query = "INSERT INTO users (idusers,nameuser, birthday, gender, imageuser, cover, email, phonenumber, status, hometown, money, datecreate, password) VALUES (null,'$nameuser','','','831407957_1636091120.jpeg','1206001153_1636091120.jpeg','','$phonenumber','','',0,'$datecreate','$password')";
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>