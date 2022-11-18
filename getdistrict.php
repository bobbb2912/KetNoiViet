<?php 
	include "connect.php";
	$idtinh = $_POST['idtinh'];
	$manghuyen = array();
	$query = "SELECT * FROM district WHERE provinceid = $idtinh ";
	$data 			= mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($manghuyen, new District(
			$row['id'],
			$row['name'],
			$row['prefix'],
			$row['provinceid']
		));
	}
	echo json_encode($manghuyen);
	class District{
		function __construct ($id, $name, $prefix, $provinceid){
			$this->id 			=$id;
			$this->name 		=$name;
			$this->prefix 		=$prefix;
			$this->provinceid	=$provinceid;
		}
	}
?>