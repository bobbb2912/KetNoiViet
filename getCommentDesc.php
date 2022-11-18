<?php 
	include "connect.php";
	$idpost = $_POST['idpost'];
	// $idpost = '3';
	$mangcomment = array();
	$query = "SELECT users.nameuser, users.phonenumber, users.imageuser, comment.content FROM comment,users WHERE comment.phoneuser = users.phonenumber and comment.idpost = '$idpost' ORDER by comment.id Desc";
	$data 			= mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangcomment, new Comment(
			$row['nameuser'],
			$row['phonenumber'],
			$row['imageuser'],
			$row['content']
		));
	}
	echo json_encode($mangcomment);
	class Comment{
		function __construct ($nameuser, $phonenumber, $imageuser, $content){
			$this->nameuser 			=$nameuser;
			$this->phonenumber 		=$phonenumber;
			$this->imageuser 		=$imageuser;
			$this->content	=$content;
		}
	}
?>