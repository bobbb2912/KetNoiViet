<?php 
	include "connect.php";
	// $idpost = $_POST['idpost'];
	// $phoneuser	= $_POST['phoneuser'];
	$idpost = '3';
	$phoneuser	= '0902784151';
	//    $status = "1"; 

	$chon = "SELECT * FROM react WHERE react.idpost = $idpost AND react.phoneuser = $phoneuser";

	$datachon = mysqli_query($conn, $chon);

	if(mysqli_num_rows($datachon) == 0){
		echo "0";
	}
	if(mysqli_num_rows($datachon) == 1){
		echo "1";
	}
?>