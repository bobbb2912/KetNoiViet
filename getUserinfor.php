<?php 
    $host   = "localhost";
    $username = "root";
    $password = "";
    $datebase = "ketnoiviet";
    $connect = mysqli_connect($host, $username, $password, $datebase);
    // mysqli_query($connect, "SET NAME 'utf8'");

    if($_SERVER['REQUEST_METHOD'] == 'POST')
    {
        $result = array();
        $result['data'] = array();
        $select = "SELECT * FROM users WHERE phonenumber = $phone_number";
        $responce = mysqli_query($connect, $select);

        while ($row = mysqli_fetch_array($responce)) 
        {
            $index['idusers'] = $row['0'];
            $index['nameuser'] = $row['1'];
            $index['birthday'] = $row['2'];
            $index['gender'] = $row['3'];
            $index['imageuser'] = $row['4'];
            $index['email'] = $row['5'];
            $index['cover'] = $row['6'];
            $index['phonenumber'] = $row['7'];
            $index['status'] = $row['8'];
            $index['hometown'] = $row['9'];
            $index['money'] = $row['10'];
            $index['datecreate'] = $row['11'];
            $index['password'] = $row['12'];

            array_push($result['data'], $index);
            
        }
        $result["success"] = "1";
        echo json_encode($result);
        mysqli_close($connect);
    }
?>