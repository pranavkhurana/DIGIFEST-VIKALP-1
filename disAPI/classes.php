<?php
/**
* 
*/

class business
{
	public $business_id;
	public $business_name;
	public $business_description;
	public $lat;
	public $lon;
	public $total_avalibility;
	public $avg_rating;
	public $type;
	public $image_url;
	public $dist_from_user;
	
	function __construct($business_id_arg,$business_name_arg,$business_description_arg,$lat_arg,$lon_arg,$total_avalibility_arg,$avg_rating_arg,$type_arg,$image_url)
	{
		$this->business_id = $business_id_arg;
		$this->business_name = $business_name_arg;
		$this->business_description = $business_description_arg;
		$this->lat = $lat_arg;
		$this->lon = $lon_arg;
		$this->total_avalibility = $total_avalibility_arg;
		$this->avg_rating = $avg_rating_arg;
		$this->type = $type_arg;
		$this->image_url=$image_url;
	}
}
class review{
	public $review_id;
	public $user_id;
	public $business_id;
	public $rating;
	public $review_heading;
	public $review_description;
	public $time;
	
	function __construct($review_id, $user_id, $business_id, $rating, $review_heading, $review_description, $time){
		$this -> review_id = $review_id;
		$this -> user_id = $user_id;
		$this -> business_id = $business_id;
		$this -> rating = $rating;
		$this -> review_heading = $review_heading;
		$this -> review_description = $review_description;
		$this -> time = $time;

	} 
}
class product{
	public $pid;
	public $name;
	public $cost;
	public $business_id;
	public $rating;
	public $image_url;
	public $description;
	
	function __construct($name, $pid, $cost, $rating, $business_id, $image_url, $description){
		$this -> pid = $pid;
		$this -> cost = $cost;
		$this -> business_id = $business_id;
		$this -> rating = $rating;
		$this -> image_url = $image_url;
		$this -> description = $description;
		$this -> name = $name;
	} 
}
?>
