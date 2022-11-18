<?php 
	include "connect.php";
	//$idpost = $_POST['idpost'];

   	//$idpost = '5';
	$query = "SELECT COUNT(r.id) FROM react r, post p WHERE r.idpost = p.id AND r.idpost =5";
	
	$data = mysqli_query($conn, $query);

	// $mang = array();
	// while ($row = mysqli_fetch_assoc($data)) {
	// 	array_push($mang, new cart(
	// 		$row['id'],
	// 		$row['idpost'],
	// 		$row['phoneuser'],
	// 		$row['status']
 //        ));
	// }
	// echo json_encode($mang);
	
	// class cart{
	// 	 function __construct ($id, $idpost, $phoneuser, $status){
	// 		$this->id 				= $id;
	// 		$this->idpost 		= $idpost;
	// 		$this->phoneuser 		= $phoneuser;
	// 		$this->status 		= $status;
	// 	}
	// }


	//$data=mysql_fetch_assoc($query);
	echo "$data";

?>
