function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

let days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

var getCurrentTimeDate = function() {
	let today = new Date();
	
	let date = days[today.getDay()] + ", " + months[today.getMonth()] +' '+ today.getDate();
	let time = ("0" + today.getHours()).slice(-2) + ":" + ("0" + today.getMinutes()).slice(-2) + ":" + ("0" + today.getSeconds()).slice(-2);
	let dateTime = date+' - '+time;
	return dateTime;
}

var updateTime = function (){
	var element = document.getElementById("date");
	element.textContent = getCurrentTimeDate();
}

var updateData = function (){
	let data = httpGet("http://localhost:8484/RoomManagement/controller/all");
	data = JSON.parse(data);
	
	let tempData = data["temp"];
	let nbRooms = document.getElementById("nbRooms");
	nbRooms.textContent = tempData.length;

	for(let i=0; i < tempData.length; i++){
		let tempRes = document.getElementById("temp" + (i+1));
		tempRes.textContent = tempData[i][2] + "°C";
	}
	
	let winData = data["window"];
	let winRes = document.getElementById("win1-1");
	winRes.textContent = winData[0][2]
	winRes = document.getElementById("win1-2");
	winRes.textContent = winData[1][2]
	winRes = document.getElementById("win2-1");
	winRes.textContent = winData[2][2]
	winRes = document.getElementById("win2-2");
	winRes.textContent = winData[3][2]
}

var getOutsideTemp = function (){
	var element = document.getElementById("outTemp");
	let res = httpGet("http://localhost:8484/RoomManagement/temperature/out");
	res = parseFloat(res);
	res = res.toFixed(1);
	element.textContent = res + "°C";
}

window.onload = function(){
	var intervalID = setInterval(updateTime, 1000);
	var intervalIData = setInterval(updateData, 1000);
	getOutsideTemp();
}


