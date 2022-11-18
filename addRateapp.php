<?php 
	include "connect.php";
	$phoneuser	= $_POST['phoneuser'];
	$star	= $_POST['star'];
	$content	= $_POST['content'];
	// $phoneuser	= '0902784151';
	// $star	= '5';
	// $content	= 'App xịn quá ạ';

	$chon = "SELECT * FROM rateapp WHERE rateapp.phoneuser = $phoneuser";

	$datachon = mysqli_query($conn, $chon);

	if(mysqli_num_rows($datachon) == 0){
		$them = "INSERT INTO rateapp(id, phoneuser, star, content) VALUES (null,'$phoneuser','$star','$content')";
		$data = mysqli_query($conn, $them);
		if($data){
			echo "1";
		} 
		else{
			echo "loi khi up";
		}
	}
	if(mysqli_num_rows($datachon) == 1){
		$xoa = "UPDATE rateapp SET rateapp.star = '$star', rateapp.content = '$content' WHERE phoneuser = '$phoneuser' ";
		$data1 = mysqli_query($conn, $xoa);
		if($data1){
			echo "0";
		} 
		else{
			echo "loi khi cap nhat";
		}
	}

	
	
	
?>