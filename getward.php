<?php 
	include "connect.php";
	$idhuyen = $_POST['idhuyen'];
	$mangphuong = array();
	$query = "SELECT * FROM ward WHERE districtid = $idhuyen ";
	$data 			= mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangphuong, new Ward(
			$row['id'],
			$row['name'],
			$row['prefix'],
			$row['provinceid'],
			$row['districtid']
		));
	}
	echo json_encode($mangphuong);
	class Ward{
		function __construct ($id, $name, $prefix, $provinceid, $districtid){
			$this->id 			=$id;
			$this->name 		=$name;
			$this->prefix 		=$prefix;
			$this->provinceid	=$provinceid;
			$this->districtid	=$districtid;
		}
	}
?>