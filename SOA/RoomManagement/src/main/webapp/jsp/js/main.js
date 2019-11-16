function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

var getCurrentTimeDate = function() {
	let today = new Date();
	
	let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
	let time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
	let dateTime = date+' '+time;
	return dateTime;
}

var updateTime = function (){
	var element = document.getElementById("date");
	element.textContent = getCurrentTimeDate();
}

var getOutsideTemp = function (){
	var element = document.getElementById("outTemp");
	let res = httpGet("http://localhost:8484/RoomManagement/temperature/out");
	res = parseInt(res);
	res = res.toFixed(1);
	element.textContent = res + "°C";
}

window.onload = function(){
	var intervalID = setInterval(updateTime, 1000);
	getOutsideTemp();
}


