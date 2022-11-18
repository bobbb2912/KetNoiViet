<?php 
	include "connect.php";
	// $idpost = ;
	$idpost = $_POST['idpost'];
	$query = "SELECT react.idpost,react.phoneuser,users.nameuser FROM react,users WHERE react.idpost = '$idpost' and react.phoneuser = users.phonenumber";
	
	$data = mysqli_query($conn, $query);
	$mangLike = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangLike, new like(
			$row['idpost'],
            $row['phoneuser'],
            $row['nameuser']
			));
	}
	echo json_encode($mangLike);
	
	class like{
		 function __construct ($idpost, $phoneuser, $nameuser){
			$this->idpost 				= $idpost;
			$this->phoneuser 		= $phoneuser;
			$this->nameuser 		= $nameuser;
		}
	}
?>