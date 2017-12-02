<?php

require ('connect.php');

if(isset($_REQUEST['username'])&&isset($_REQUEST['password']))
{
	$name=$_REQUEST['username'];
	$password=$_REQUEST['password'];
	$sql = "SELECT * FROM `users` WHERE `username` LIKE '$name' AND `password` LIKE '$password'";

	$qry = mysqli_query($dbconnect,$sql);
	$logged=false;
	while($rs=mysqli_fetch_assoc($qry)){
		$logged=true;	
	}
	if ($logged){
		echo 'login:true';	
	}
	else
	{
		echo 'login:false';
	}
}
else
{
	echo 'login:false';
}

?>
