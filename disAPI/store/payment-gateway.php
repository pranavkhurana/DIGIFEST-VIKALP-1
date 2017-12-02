<!DOCTYPE html>
<html>
<head>
	<title></title>
	<script>		
	//window.setTimeout(function(){ window.location="payment-done.php"; },3000);	
	</script>
</head>
<body>
	<center>
		<br><br>
		<img width="30%" src="../img/loading.gif">
		<h2><p>Redirecting to payment gateway</p></h2>	
	</center>
	<a href="payment-done.php?business_id=<?=$_REQUEST['business_id']?>"><button>x</button></a>
</body>
</html>
