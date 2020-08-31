    <?php   
      require_once 'connection.php';  
      $response = array();  
      if(isset($_GET['apicall'])){  
      switch($_GET['apicall']){  
      case 'signup':  
        if(isTheseParametersAvailable(array('username','email','password','gender'))){  
        $username = $_POST['username'];   
        $email = $_POST['email'];   
        $password = md5($_POST['password']);  
        $gender = $_POST['gender'];   
       
        $stmt = $conn->prepare("SELECT id FROM ms_user WHERE usr_name = ? OR usr_email = ?");  
        $stmt->bind_param("ss", $username, $email);  
        $stmt->execute();  
        $stmt->store_result();  
       
        if($stmt->num_rows > 0){  
            $response['error'] = true;  
            $response['message'] = 'User already registered';  
            $stmt->close();  
        }  
        else{  
            $teq = $conn->query("SELECT MAX(RIGHT(id_usr,3)) AS kol FROM ms_user ORDER BY id_usr");
            $row = $teq->fetch_assoc();
            $varauto = autonumber($row['kol'], 0, 3);
            $stmt = $conn->prepare("INSERT INTO ms_user (id_usr,usr_name, usr_email, usr_password, gender) VALUES ('IU".$varauto."',?, ?, ?, ?)");  
            $stmt->bind_param("ssss", $username, $email, $password, $gender);  
       
            if($stmt->execute()){  
                $stmt = $conn->prepare("SELECT id, id, usr_name, usr_email, gender FROM ms_user WHERE usr_name = ?");   
                $stmt->bind_param("s",$username);  
                $stmt->execute();  
                $stmt->bind_result($userid, $id, $username, $email, $gender);  
                $stmt->fetch();  
       
                $user = array(  
                'id'=>$id,   
                'username'=>$username,   
                'email'=>$email,  
                'gender'=>$gender  
                );  
       
                $stmt->close();  
       
                $response['error'] = false;   
                $response['message'] = 'User registered successfully';   
                $response['user'] = $user;   
            }  
        }  
       
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break;   
    case 'login':  
      if(isTheseParametersAvailable(array('username', 'password'))){  
        $username = $_POST['username'];  
        $password = md5($_POST['password']);   
       
        $stmt = $conn->prepare("SELECT id, id_usr, usr_name, usr_email, gender FROM ms_user WHERE usr_name = ? AND usr_password = ?");  
        $stmt->bind_param("ss",$username, $password);  
        $stmt->execute();  
        $stmt->store_result();  
        if($stmt->num_rows > 0){  
        $stmt->bind_result($id, $idusr, $username, $email, $gender);  
        $stmt->fetch();  
        $user = array(  
        'id'=>$id,   
        'idusr'=>$idusr,
        'username'=>$username,   
        'email'=>$email,  
        'gender'=>$gender  
        );  
       
        $response['error'] = false;   
        $response['message'] = 'Login successfull';   
        $response['user'] = $user;   
     }  
     else{  
        $response['error'] = false;   
        $response['message'] = 'Invalid username or password';  
     }  
    }  
    break;   
    default:   
     $response['error'] = true;   
     $response['message'] = 'Invalid Operation Called';  
    }  
    }  
    else{  
     $response['error'] = true;   
     $response['message'] = 'Invalid API Call';  
    }  
    echo json_encode($response);  
    function autonumber($id_terakhir, $panjang_kode, $panjang_angka) {
    $kode = substr($id_terakhir, 0, $panjang_kode);
    $angka = substr($id_terakhir, $panjang_kode, $panjang_angka);
    $angka_baru = str_repeat("0", $panjang_angka - strlen($angka+1)).($angka+1);
    $id_baru = $kode.$angka_baru;
    return $id_baru;
}
    function isTheseParametersAvailable($params){  
    foreach($params as $param){  
     if(!isset($_POST[$param])){  
         return false;   
      }  
    }  
    return true;   
    }  
    ?>  