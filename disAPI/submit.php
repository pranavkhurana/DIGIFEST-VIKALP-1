	<?php

if(isset($_REQUEST['lat'])&&isset($_REQUEST['lon']))
{
	include('connect.php');
	include('classes.php');
	$business_id_single= "";
	if(isset($_REQUEST['business_id'])) { 
		$business_id_single = " where business_id = ".$_REQUEST['business_id']; }
		//echo $business_id_single;
	$sql ="SELECT * FROM businesses".$business_id_single;
	//echo $sql;
	$qry = mysqli_query($dbconnect,$sql);

$lat = $_REQUEST['lat'];
$lon = $_REQUEST['lon'];
$x = 0;
$des="";
while($rs=mysqli_fetch_assoc($qry))
{
	if($x){$des=$des."%7C"; }
	$businesses[$rs['business_id']] = 0;
	
    $des=$des.$rs['location_lat']."%2C".$rs['location_lon'];
    $x++;
}
$x = 0;
//echo $des;

//echo "$lat \n $lon";
$string = file_get_contents("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=".$lat.",".$lon."&destinations=".$des."&units=metric&key=AIzaSyC5Y5Poy0Xi0Rs3LrXP6u8JwZxnSoE4JYQ");
//echo "$string";
$json_d = json_decode($string,true);
foreach ($json_d['rows'] as $row) {
	foreach ($row['elements'] as $value) {
		$res = $value['distance']['value'];
		$dis[$x] = $res;
        $x++;
		//echo "$res \n";
	}

	}

$x = 0;
foreach ($businesses as $key => $value) {
	$businesses[$key] = $dis[$x];
	$x++;
	//echo $key." ->".$businesses[$key]." \n";
	
}
asort($businesses);
$x =0;
$all_business = array();
//echo gettype($all_business);
foreach ($businesses as $key => $value) {
	$query = mysqli_query($dbconnect,"SELECT * FROM businesses where business_id = ".$key);
	$res = mysqli_fetch_assoc($query);
	$business_obj = new business($key,$res['business_name'],$res['business_description'],$res['location_lat'],$res['location_lon'],$res['total_avalibility'],$res
		['avg_rating'],$res['type'],$res['image_url']);
	$business_obj->dist_from_user = $value;

	if(!$x){$all_business = array($business_obj);}
		else{array_push($all_business, $business_obj);}
		//echo gettype($all_business);
		$x++;




	//echo $key." ->".$value." \n";
}
$businesses_json = json_encode($all_business,JSON_PRETTY_PRINT);
echo $businesses_json;


//echo $businesses['person4@gmail.com'];
}

else{
	echo "location not set";
}




?>
