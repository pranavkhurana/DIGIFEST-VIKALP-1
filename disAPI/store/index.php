<?php
if(isset($_REQUEST['business_id']))
{
	$business_id = $_REQUEST['business_id'];
	include('../connect.php');
	include('../classes.php');
	$sql ="SELECT * FROM products where business_id=".$business_id;
	$qry = mysqli_query($dbconnect,$sql);
	
	$products=array();
			
	while($rs=mysqli_fetch_assoc($qry))
	{	
		$product = new product($rs['name'],$rs['pid'],$rs['cost'],$rs['rating'],$rs['business_id'],$rs['image_url'],$rs['description']);
		array_push($products,$product);			
	}
	//echo json_encode($reviews,JSON_PRETTY_PRINT);

	$sql2="select * from businesses where business_id=".$business_id;
	$qry2= mysqli_query($dbconnect,$sql2);
	while($rs2=mysqli_fetch_assoc($qry2))
	{	
		$business_name = $rs2['business_name'];
		$business_description = $rs2['business_description'];
	}
}

else{
	echo "enter a valid business_id";
}

?>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><?=$business_name?></title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/heroic-features.css" rel="stylesheet">
    <style>
	.card-img-top{
		height:200px
	}
    </style>
  </head>

  <body>

    <!-- Navigation -->
    <!--nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#">Store</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home
                <span class="sr-only">(current)</span>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">About</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Services</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Contact</a>
            </li>
          </ul>
        </div>
      </div>
    </nav-->

    <!-- Page Content -->
    <div class="container">

      <!-- Jumbotron Header -->
      <header class="my-4">
        <h2><?=$business_name?></h2>
        <p class="lead"><?=$business_description?></p>
      </header>
	<hr>
      <!-- Page Features -->
      <div class="row text-center">
	
	<?php 
		foreach ($products as $product_temp){

	?>
        <div class="col-lg-3 col-md-4 mb-4">
          <div class="card">
            <img class="card-img-top" src="../img/<?=$product_temp->image_url?>" alt="">
            <div class="card-body">
              <h4 class="card-title"><?=$product_temp->name ?> <span class="float-right">Rs.<?=$product_temp->cost ?></span></h4>
              <p class="card-text"><?=$product_temp->description ?></p>
            </div>
            <div class="card-footer">
              <a href="<?php echo 'checkout.php?pid='.$product_temp->pid.'&uid=1'?>" class="btn btn-primary">Buy Now</a>
            </div>
          </div>
        </div>
	<?php
		}	
	?>
      </div>
      <!-- /.row -->

    </div>
    <!-- /.container -->

    <!-- Footer -->
    <footer class="py-5 bg-dark">
      <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Your Website 2017</p>
      </div>
      <!-- /.container -->
    </footer>

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  </body>

</html>
