<?php 
	include "connect.php";
	$users = $_POST['users'];
	$userfollow	= $_POST['userfollow'];

	$chon = "SELECT * FROM followers WHERE followers.users = $users AND followers.userfollow = $userfollow";

	$datachon = mysqli_query($conn, $chon);

	if(mysqli_num_rows($datachon) == 0){
		$them = "INSERT INTO followers(id, users, userfollow) VALUES (null,'$users',$userfollow)";
		$data = mysqli_query($conn, $them);
		if($data){
			echo "1";
		} 
		else{
			echo "loi khi them";
		}
	}
	if(mysqli_num_rows($datachon) == 1){
		$xoa = "DELETE FROM followers WHERE followers.users = $users AND followers.userfollow = $userfollow";
		$data1 = mysqli_query($conn, $xoa);
		if($data1){
			echo "0";
		} 
		else{
			echo "loi khi xoa";
		}
	}

	
	
	
?>