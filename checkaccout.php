<?php 
	include "connect.php";
	$phonenumber = $_POST['phonenumber'];
	$manguserinfo = array();

	$query = "SELECT * FROM users WHERE phonenumber = $phonenumber";
	$data = mysqli_query($conn, $query);
	$count = mysqli_num_rows($data);
	if($count>0){
		echo "Tontai";
	} 
	else{
		echo "Chuatontai";
	}
?>