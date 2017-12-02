<?php
include('connect.php');
if(	isset($_REQUEST['business_id']) && 
	isset($_REQUEST['business_name']) && 
	isset($_REQUEST['business_description']) && 
	isset($_REQUEST['location_lat']) && 
	isset($_REQUEST['location_lon'])&& 
	isset($_REQUEST['total_avalibility'])&& 
	isset($_REQUEST['avg_rating'])&& 
	isset($_REQUEST['total_reviews'])&& 
	isset($_REQUEST['type'])&& 
	isset($_REQUEST['image_url'])
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

		$qry="INSERT INTO `users` (`username`, `password`, `name`, `sex`, `nationalitiy`,`age`) VALUES ('$username', '$password', '$name', '$sex', '$nationality','$age')";
		//echo $qry;
		if(mysqli_query($dbconnect,$qry))
		{
	            //echo 'Successfully Signed up. Login to proceed.';
		}	
		else echo "eroor";

	}
	else echo'Fill all fields.';
}
?>
