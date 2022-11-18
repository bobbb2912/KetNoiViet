<?php 
	include "connect.php";
	$idpost = $_POST['idpost'];
	$phoneuser	= $_POST['phoneuser'];
    $status = "1"; 

	$chon = "SELECT * FROM react WHERE react.idpost = $idpost AND react.phoneuser = $phoneuser";

	$datachon = mysqli_query($conn, $chon);

	if(mysqli_num_rows($datachon) == 0){
		$them = "INSERT INTO react(id, idpost, phoneuser, status) VALUES (null,$idpost,'$phoneuser',$status)";
		$data = mysqli_query($conn, $them);
		if($data){
			echo "0";
		} 
		else{
			echo "loi khi them";
		}
	}
	if(mysqli_num_rows($datachon) == 1){
		$xoa = "DELETE FROM react WHERE idpost = $idpost and phoneuser = $phoneuser";
		$data1 = mysqli_query($conn, $xoa);
		if($data1){
			echo "Hủy thích bài viết !";
		} 
		else{
			echo "loi khi xoa";
		}
	}

	
	
	
?>