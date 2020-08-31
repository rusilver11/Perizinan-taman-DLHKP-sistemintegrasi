<?php

	$DB_NAME = "db_perijinan";
	$DB_USER = "root";
	$DB_PASS = "";
	$DB_SERVER_LOC = "localhost";

if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$conn = mysqli_connect($DB_SERVER_LOC,$DB_USER,$DB_PASS,$DB_NAME);
	$id = $_POST['id'];

	$sql = "SELECT ms_pemohon.id_mp,ms_pemohon.tgl_ajuan,ms_pemohon.jdl_kegiatan,tr_detailmp.td_status
			FROM ms_pemohon JOIN tr_detailmp
			ON ms_pemohon.id_mp = tr_detailmp.id_mp
			WHERE tr_detailmp.id_usr = '$id' ";

	$result = mysqli_query($conn, $sql);

	if(mysqli_num_rows($result) > 0 ){
		header("Acces-Control-Allow-Origin: *");
		header("Content-type: application/json; charset=UTF-8");

		$result = array();
		while($ijn = mysqli_fetch_assoc($result)){
			$result[] = $ijn;
		}
		echo json_encode(array('result'=>$result));
	}
}
?>