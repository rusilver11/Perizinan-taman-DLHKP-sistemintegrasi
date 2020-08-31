<?php
	$DB_NAME = "db_perijinan";
	$DB_USER = "root";
	$DB_PASS = "";
	$DB_SERVER_LOC = "localhost";

if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$conn = mysqli_connect($DB_SERVER_LOC,$DB_USER,$DB_PASS,$DB_NAME);
	$sql = "select nm_lokasi from ms_lokasi order by nm_lokasi asc";
	$result = mysqli_query($conn,$sql);

	if(mysqli_num_rows($result)>0){
		header("Acces-Control-Allow-Origin: *");
		header("Content-type: application/json; charset=UTF-8");

		$nm_lokasi = array();
		while($nm_cat = mysqli_fetch_assoc($result)){
			array_push($nm_lokasi, $nm_cat);

		}
		echo json_encode($nm_lokasi);
	}
}

?>