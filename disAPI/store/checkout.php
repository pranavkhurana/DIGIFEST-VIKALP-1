<?php
if(isset($_REQUEST['pid']))
{
	$pid = $_REQUEST['pid'];
	include('../connect.php');
	include('../classes.php');
	$sql ="SELECT * FROM products where pid=".$pid;
	$qry = mysqli_query($dbconnect,$sql);
			
	if($rs=mysqli_fetch_assoc($qry))
	{	
		$product = new product($rs['name'],$rs['pid'],$rs['cost'],$rs['rating'],$rs['business_id'],$rs['image_url'],$rs['description']);
	}
	
	$sql2="select * from businesses where business_id=".$product->business_id;
	$sql3="select * from registered_business where business_id=".$product->business_id;
	$qry2= mysqli_query($dbconnect,$sql2);
	$qry3= mysqli_query($dbconnect,$sql3);	
	
	if($rs2=mysqli_fetch_assoc($qry2))
	{	
		$business_name = $rs2['business_name'];
		$business_description = $rs2['business_description'];
		
	}
	$business_holder;
	if($rs3=mysqli_fetch_assoc($qry3))
	{	
		$bhamashah_id = $rs3['bhamashah_id'];
		$aadhar_id = $rs3['aadhar_id'];
		$business_holder = $rs3['business_holder'];
	}
	$product_temp= $product;
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
	<script>
		.card-img-top{
			height:300px;
		}	
	</script>
	<style>
		.card-img-top{
			height:330px;
		}	
	</style>
  </head>

  <body>

    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
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
    </nav>

    <!-- Page Content -->
    <div class="container">

      <!-- Jumbotron Header -->
      <!--header class="jumbotron my-4">
        <h1 class="display-3"><?=$business_name?>!</h1>
        <p class="lead"><?=$business_description?></p>
        <a href="#" class="btn btn-primary btn-lg">Call to action!</a>
      </header-->

      <!-- Page Features -->
      <div class="jumbotron row text-center">
	<div class="col-lg-6 col-md-6 mb-6">
          <div class="card">
<br><br><br>
            <div class="card-body">
              <h4 class="card-title">
		Your order is fulfilled by :
		<p>Vikalp</p>
		
		<p>Your are about to make payment to:</p>
		<p><?=$business_holder?></p>

		<small class="float-right">Powered by Bhamashah</small>
       	      </h4>
              
            </div>
            <div class="card-footer">
              
            </div>
          </div>
        </div>
	<div class="col-lg-6 col-md-6 mb-6">
          <div class="card">
            <img class="card-img-top" src="../img/<?=$product_temp->image_url?>" alt="">
            <div class="card-body">
              <h4 class="card-title"><?=$product_temp->name ?> <span class="float-right">Rs.<?=$product_temp->cost ?></span></h4>
              <p class="card-text"><?=$product_temp->description ?></p>
            </div>
            <div class="card-footer">
              <a href="payment-gateway.php?business_id=<?=$product->business_id?>" class="btn btn-primary">Pay directly to Bhamashah account</a>
            </div>
          </div>
        </div>

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
