<?php
	$DB_Name = "id1432739_test";
	$DB_User = "id1432739_roots";
	$DB_password = "frank@1234";
	$server = "localhost";
	
	$conn = mysqli_connect($server,$DB_User,$DB_password,$DB_Name) or die("Failed to Connect to Database<br>".mysql_error());
	
	 if($conn)
		echo "Connection Established"; 

?>
