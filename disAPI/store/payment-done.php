<!DOCTYPE html>
<html>
<head>
	<title></title>
<META HTTP-EQUIV="REFRESH" CONTENT="5">
	<script>
	//setTimeout(function () {    
	//window.location.href = "index.php?business_id="<?=$_REQUEST['business_id']?>; 
	//},1000);
	</script>
</head>
<body>
	<center>
		<br><br>
		<h3>Order was successfully placed.</h3>
		<img width="30%" src="../img/loading.gif">
		<h2><p>Redirecting back</p></h2>	
	</center>
	</center>
<a href="index.php?business_id=<?=$_REQUEST['business_id']?>"><button>x</button></a>
</body>
</html>
