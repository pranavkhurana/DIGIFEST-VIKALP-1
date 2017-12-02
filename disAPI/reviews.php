<?php
if(isset($_REQUEST['business_id']))
{
	$business_id = $_REQUEST['business_id'];
	include('connect.php');
	include('classes.php');
	$sql ="SELECT * FROM reviews where business_id=".$business_id;
	//echo $sql;
	$qry = mysqli_query($dbconnect,$sql);
	
	$reviews=array();
			
	while($rs=mysqli_fetch_assoc($qry))
	{
		$review = new review($rs['review_id'],$rs['user_id'],$rs['business_id'],$rs['rating'],$rs['review_heading'],$rs['review_description'],$rs['time']);
		array_push($reviews,$review);			
		//echo $review->review_description;	
	}
	echo json_encode($reviews,JSON_PRETTY_PRINT);
}

else{
	echo "enter a valid business_id";
}

?>
