<?php 
	$host	= "localhost";
	$username = "root";
	$password = "";
	$datebase = "ketnoiviet";

	$conn = mysqli_connect($host, $username, $password, $datebase);
	mysqli_query($conn, "SET NAME 'utf8'");
	// if($conn){
	// 	echo "1";
	// }
	// else {
	// 	echo "0";
	// }
?>