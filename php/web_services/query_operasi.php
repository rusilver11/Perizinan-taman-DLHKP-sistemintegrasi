<?php
	$DB_NAME = "db_perijinan";
	$DB_USER = "root";
	$DB_PASS = "";
	$DB_SERVER_LOC = "localhost";

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		$conn = mysqli_connect($DB_SERVER_LOC, $DB_USER, $DB_PASS, $DB_NAME);
		$mode = $_POST['mode'];
		$respon = array(); $respon['kode'] = '000';
		switch (($mode)) {
			case "insert":
				$id_usr = $_POST['id_usr'];
				$judul = $_POST['judul'];
				$lembaga = $_POST['lembaga'];
				$tgl_mulai = $_POST['tgl_mulai'];
				$tgl_akhir = $_POST['tgl_akhir'];
				$wkt_mulai = $_POST['wkt_mulai'];
				$wkt_akhir = $_POST['wkt_akhir'];
				$jml_peserta = $_POST['jml_peserta'];
				$jns_peserta = $_POST['jns_peserta'];
				$lokasi = $_POST['lokasi'];
				$kegiatan = $_POST['kegiatan'];
				$imstr = $_POST['image'];
				$file = $_POST['file'];
				$path = "../images/";

				$sql = "select id_lokasi from ms_lokasi where nm_lokasi='$lokasi'";
				// $arr = [];
				// 	$q1 = mysqli_query($conn,"SELECT id_mp FROM ms_pemohon");
				// 	while (list($id_mp) = mysqli_fetch_array($q1)){
				// 		$arr[] = $id_mp;
				// 	}
				// 	$q2 = mysqli_query($conn,"SELECT MAX(id_mp) FROM ms_pemohon");
				// 	list($largest_id) = mysqli_fetch_array($q2);
				$result = mysqli_query($conn,$sql);

				if(mysqli_num_rows($result)>0){
					$data = mysqli_fetch_assoc($result);
					$id_lokasi = $data['id_lokasi'];
					
					// for ($j = 1; $j <= $largest_id + 1; $j += 1){
					// 	if(!in_array($j, $arr) && mysqli_num_rows($result)>0){
					// 	mysqli_query($conn,"INSERT INTO ms_pemohon(jdl_kegiatan, nm_lembaga, tglm_kegiatan, tgla_kegiatan, wktm_kegiatan, wkta_kegiatan, tmpt_kegiatan, jml_peserta, jenis_peserta, ket_kegiatan, file_upload ) VALUES('$judul','$lembaga','$tgl_mulai','$tgl_akhir', '$wkt_mulai', '$wkt_akhir', '$lokasi', '$jml_peserta', '$jns_peserta', '$kegiatan', '$file')");
					// 	$result = mysqli_query($conn,$sql);
					// 	}
					// }

					$teq = $conn->query("SELECT MAX(RIGHT(id_mp,3)) AS kol FROM ms_pemohon ORDER BY id_mp");
            		$row = $teq->fetch_assoc();
           			 $varauto = autonumber($row['kol'], 0, 3);

					$sql = "INSERT  INTO ms_pemohon(id_mp, id_usr,jdl_kegiatan, nm_lembaga, tgl_ajuan,tglm_kegiatan, tgla_kegiatan, wktm_kegiatan, wkta_kegiatan, tmpt_kegiatan, jml_peserta, jenis_peserta, ket_kegiatan, file_upload )
					 VALUES('IM".$varauto."', '$id_usr','$judul','$lembaga', CURDATE(), '$tgl_mulai','$tgl_akhir', '$wkt_mulai', '$wkt_akhir', '$lokasi', '$jml_peserta', '$jns_peserta', '$kegiatan', '$file')";
					 $sql1 = "INSERT INTO tr_detailmp (id_mp,id_usr,td_status) VALUES ('IM".$varauto."','$id_usr','Belum')";

					// $sql = "INSERT INTO ms_pemohon(jdl_kegiatan, nm_lembaga, tglm_kegiatan, tgla_kegiatan, tmpt_kegiatan ) VALUES('$judul','$lembaga','$tgl_mulai','$tgl_akhir','$lokasi')";

					$result = mysqli_query($conn,$sql);
					$result1 = mysqli_query($conn,$sql1);
					if($result && $result1){
						if(file_put_contents($path.$file, base64_decode($imstr)) == false){
							// $sql = "DELETE FROM ms_pemohon WHERE id_mp='$id_mp'";
							// mysqli_query($conn,$sql);
							$respon['kode'] = "111";
							echo json_encode($respon);
							exit();
						}else{
							echo json_encode($respon);
							exit(); //insert data sukses semua
						}
					}else{
						$respon['kode'] = "111";
						echo json_encode($respon);
						exit();
					}
				}
				break;
			
			case "update":
				$nim = $_POST['nim'];
				$nama = $_POST['nama'];
				$nama_prodi = $_POST['nama_prodi'];
				$imstr = $_POST['image'];
				$file = $_POST['file'];
				$path = "images/";

				$sql = "select id_prodi from prodi where nama_prodi='$nama_prodi'";
				$result = mysqli_query($conn,$sql);
				if(mysqli_num_rows($result)>0){
					$data = mysqli_fetch_assoc($result);
					$id_prodi = $data['id_prodi'];

					$sql = "";
					if($imstr == ""){
						$sql = "update mahasiswa set nama='$nama',id_prodi=$id_prodi where nim='$nim'";
						$result = mysqli_query($conn,$sql);
						if($result){
							echo json_encode($respon);
							exit(); //insert data sukses semua
						}else{
							$respon['kode'] = "111";
							echo json_encode($respon);
							exit();
						}
					}else{
						if(file_put_contents($path.$file, base64_decode($imstr)) == false){
							$respon['kode'] = "111";
							echo json_encode($respon);
							exit();
						}else{
							$sql = "update mahasiswa set nama='$nama',id_prodi=$id_prodi,photos='$file' where nim='$nim'";
							$result = mysqli_query($conn,$sql);
							if($result){
								echo json_encode($respon);
								exit(); //insert data sukses semua
							}else{
								$respon['kode'] = "111";
								echo json_encode($respon);
								exit();
							}
						}
					}			
				}
				break;

			case "delete":
				$nim = $_POST['nim'];
				$sql = "select photos from mahasiswa where nim='$nim'";
				$result = mysqli_query($conn,$sql);
				if($result){
					if(mysqli_num_rows($result)>0){
						$data = mysqli_fetch_assoc($result);
						$photos = $data['photos'];
						$path = "images/";
						unlink($path.$photos);
					}
					$sql = "delete from mahasiswa where nim='$nim'";
					$result = mysqli_query($conn,$sql);
					if($result){
						echo json_encode($respon);
						exit();
					}else{
						$respon['kode'] = "111";
						echo json_encode($result);
						exit();
					}
				}
				break;
		}
	}
	function autonumber($id_terakhir, $panjang_kode, $panjang_angka) {
    $kode = substr($id_terakhir, 0, $panjang_kode);
    $angka = substr($id_terakhir, $panjang_kode, $panjang_angka);
    $angka_baru = str_repeat("0", $panjang_angka - strlen($angka+1)).($angka+1);
    $id_baru = $kode.$angka_baru;
    return $id_baru;
}
?>
