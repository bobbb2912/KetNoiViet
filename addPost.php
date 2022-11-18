<?php 
	include "connect.php";
	$nameplace = $_POST['nameplace'];
	$province	= $_POST['province'];
	$district	= $_POST['district'];
	$ward	= $_POST['ward'];
	$address	= $_POST['address'];
	$description	= $_POST['description'];
	$content	= $_POST['content'];


    $target_dir = "images/";
	$image1	= $_POST['image1'];
    $image2	= $_POST['image2'];
    $image3	= $_POST['image3'];
    $image4	= $_POST['image4'];

    $phoneuser	= $_POST['phoneuser'];
	$datepost	= $_POST['datepost'];
	$status	= $_POST['status'];

    $imageStore1 = rand()."_".time().".jpeg";
    $target_dir1 = $target_dir."/".$imageStore1;
    file_put_contents($target_dir1, base64_decode($image1));

    $imageStore2 = rand()."_".time().".jpeg";
    $target_dir2 = $target_dir."/".$imageStore2;
    file_put_contents($target_dir2, base64_decode($image2));

    $imageStore3 = rand()."_".time().".jpeg";
    $target_dir3 = $target_dir."/".$imageStore3;
    file_put_contents($target_dir3, base64_decode($image3));

    $imageStore4 = rand()."_".time().".jpeg";
    $target_dir4 = $target_dir."/".$imageStore4;
    file_put_contents($target_dir4, base64_decode($image4));
	
    $result = array();
	$query = "INSERT INTO post(id, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status) VALUES (null,'$nameplace','$province','$district','$ward','$address','$description', '$content', '$imageStore1', '$imageStore2', '$imageStore3', '$imageStore4','$phoneuser','$datepost','$status')";
	
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>