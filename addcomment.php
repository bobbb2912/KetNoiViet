<?php 
	include "connect.php";
	$idpost = $_POST['idpost'];
	$phoneuser	= $_POST['phoneuser'];
    $content = $_POST['content']; 
 //    $idpost = '3';
	// $phoneuser	= '0376701749';
 //    $content = 'Tuyệt vời luôn'; 

	$query = "INSERT INTO comment (id,idpost,phoneuser,content) VALUES (null,'$idpost','$phoneuser','$content')";
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>