<!DOCTYPE html>
<html>
<body onload="getLocation()">

<p>Click the button to get your coordinates.</p>

<!--<button onclick="getLocation()">Try It</button>-->

<p id="demo"></p>
<form method="post" action="submit.php" id="myForm">
<input type="hidden" name="lat" id="lat" visibility="hidden"/>
<input type="hidden" name="lon" id="lon" visibility="hidden"/>
</form>
<script>
var x = document.getElementById("demo");

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.watchPosition(showPosition);
    } else { 
        x.innerHTML = "Geolocation is not supported by this browser.";}
    }
    
function showPosition(position) {
    document.getElementById("lat").value=position.coords.latitude;
    document.getElementById("lon").value=position.coords.longitude;
    document.getElementById("myForm").submit();
}
</script>

</body>
</html>
