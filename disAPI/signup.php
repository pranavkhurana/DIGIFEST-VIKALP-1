<?php
include('connect.php');
if(	isset($_REQUEST['username']) && 
	isset($_REQUEST['password']) && 
	isset($_REQUEST['name']) && 
	isset($_REQUEST['sex']) && 
	isset($_REQUEST['nationality'])&& 
	isset($_REQUEST['age'])
){
	if(	!empty($_REQUEST['username']) && 
		!empty($_REQUEST['password']) && 
		!empty($_REQUEST['name']) && 
		!empty($_REQUEST['nationality']) && 
		!empty($_REQUEST['age'])&& 
		!empty($_REQUEST['sex'])
	){
		function test_input($data) {
			$data = trim($data);
			$data = stripslashes($data);
			$data = htmlspecialchars($data);
			return $data;
		}
		$name=test_input($_REQUEST['name']);
		$username=test_input($_REQUEST['username']);
		$password=test_input($_REQUEST['password']);
		$sex=test_input($_REQUEST['sex']);
		$age=test_input($_REQUEST['age']);
		$nationality=test_input($_REQUEST['nationality']);

		$qry= "INSERT INTO `users` (`username`, `password`, `name`, `sex`, `nationalitiy`, `age`) VALUES ('$username', '$password', '$name', '$sex', '$nationality','$age')";
		if(mysqli_query($dbconnect,$qry))
		{
	            //echo 'Successfully Signed up. Login to proceed.';
		}	
		else echo "error";

	}
	else echo'Fill all fields.';
}
?>
