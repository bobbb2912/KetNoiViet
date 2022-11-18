<?php 
	include "connect.php";
	$id = $_POST['idprovince'];
	// $id = '25';
	$query = "SELECT * from province where province.id = '$id'";
	$data = mysqli_query($conn, $query);
	$mangprovince = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangprovince, new Province(
			$row['id'],
			$row['name'],
			$row['code'],
			$row['imageprovince'],
			$row['location'],
			$row['area'],
			$row['population'],
			$row['numbervehicle'],
			$row['danhlam'],
			$row['dacsan']));
	}
	echo json_encode($mangprovince);
	class Province{
		 function __construct ($id, $name, $code, $imageprovince, $location, $area, $population, $numbervehicle, $danhlam, $dacsan){
			$this->id 				= $id;
			$this->name 		= $name;
			$this->code		= $code;
			$this->imageprovince 		= $imageprovince;
			$this->location 		= $location;
			$this->area 		= $area;
			$this->population 		= $population;
			$this->numbervehicle 	= $numbervehicle;
			$this->danhlam 	= $danhlam;
			$this->dacsan 	= $dacsan;
		}
	}
?>

