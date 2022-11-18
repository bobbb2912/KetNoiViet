<?php 
	include "connect.php";
	$query = "SELECT province.id, province.name, province.code FROM province ORDER BY name ASC";
	$data = mysqli_query($conn, $query);
	$mangprovince = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangprovince, new Province(
			$row['id'],
			$row['name'],
			$row['code']));
	}
	echo json_encode($mangprovince);
	class Province{
		 function __construct ($id, $name, $code){
			$this->id 				= $id;
			$this->name 		= $name;
			$this->code 	= $code;
		}
	}
?>