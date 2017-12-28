<?php
	require "connection.php";
	
	$email = $_POST['email'];
	$password = $_POST['password'];
	
	/* $email = "amanshrm8@gmail.com";
	$password = "amit@1234"; */
	
	$login_query = "SELECT * FROM user WHERE email='$email' AND password='$password'";
	
	if(mysqli_num_rows(mysqli_query($conn,$login_query)) > 0){
		echo "Sucessfully logged in";
	}else
	{
		echo "Invalid Credentials";
	}
?>
