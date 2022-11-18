<?php 
	include "connect.php";
	$phonenumber = $_POST['phonenumber'];
	$password	= $_POST['password'];

	$query = "UPDATE users SET password = '$password' WHERE phonenumber = '$phonenumber'";
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>