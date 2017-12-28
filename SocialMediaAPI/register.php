<?php
include "connection.php";

/* $name = "Shubham Sharma";
$email = "shubham5964@gmail.com";
$password = "amit@1234"; */


$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];


$insert_query = "INSERT INTO user(name,email,password) VALUES('$name','$email','$password')";
$verify_query = "SELECT * FROM user WHERE email='$email'";

if (mysqli_num_rows ( mysqli_query ( $conn, $verify_query ) ) > 0) {
	echo "User with '" . $email. "' Already Exists";
} else {
		
	$res = mysqli_query ( $conn, $insert_query ) or die ( "ERROR CODE: " . mysqli_errno ( $conn ) );
	
	if (res)
		echo "Registered Sucessfully";
}

?>
