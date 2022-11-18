<?php 
	include "connect.php";
	$query = "SELECT post.nameplace FROM post";
	$data = mysqli_query($conn, $query);
	$mangprovince = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangprovince, new Nameplace(
			$row['nameplace']));
	}
	echo json_encode($mangprovince);
	class Nameplace{
		 function __construct ($nameplace){
			$this->nameplace 				= $nameplace;
		}
	}
?>