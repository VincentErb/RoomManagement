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
	var promiseData = new Promise(function(resolve, reject) {
		let data = httpGet("http://localhost:8484/RoomManagement/controller/all");
		resolve(data);
	});
	  
	promiseData.then(function(value) {
		data = JSON.parse(value);
	
		let tempData = data["temp"];
		let nbRooms = document.getElementById("nbRooms");
		let numberRooms = tempData.length;
		nbRooms.textContent = numberRooms;

		for(let i=0; i < tempData.length; i++){
			let tempRes = document.getElementById("temp" + tempData[i][1]);
			tempRes.textContent = tempData[i][2] + "°C";
		}
		
		let winData = data["window"];
		let nbOpen = 0;
		
		for(let j=0; j < winData.length; j++){
			let winRes = document.getElementById("win" + winData[j][0] + winData[j][1]);
			if(winData[j][2] === "1"){
				winRes.textContent = "OPEN";
				nbOpen++;
			} else {
				winRes.textContent = "CLOSED";
			}	
		}	
		
		let elemOpen = document.getElementById("nbWinOpen");
		elemOpen.textContent = nbOpen;
		
		elemOpen = document.getElementById("nbWinTotal");
		elemOpen.textContent = winData.length;
		
		let gasData = data["gas"];
		
		for(let i=0; i < gasData.length; i++){
			let tempRes = document.getElementById("gas" + gasData[i][1]);
			tempRes.textContent = gasData[i][2] + "%";
		}
		
		let lightData = data["light"];
		
		for(let i=0; i < lightData.length; i++){
			let tempRes = document.getElementById("light" + lightData[i][1]);
			tempRes.textContent = lightData[i][2];
		}
		
		let lampData = data["lamp"];
		let lightab = new Array(numberRooms);
		lightab.fill(0, 0, numberRooms);
		
		for(let i=0; i < lampData.length; i++){
			if(lampData[i][2] === "1"){
				for(let j=0; j < numberRooms; j++){
					let str = "room" + (j+1);
					if(lampData[i][1] === str){
						lightab[j]++;
					}
				}
			}
		}
		
		for (let k=0; k<numberRooms;k++){
			let l = document.getElementById("lampsroom" + (k+1));
			l.textContent = lightab[k] + "/" + "3";
		}
		
	});
}

var manageRooms = function (){
	var promiseData = new Promise(function(resolve, reject) {
		let res = httpGet("http://localhost:8484/RoomManagement/controller/manage");
		resolve(res);
	});

	promiseData.then(function(value) {
		console.log("fecthing ..." + res);
	});
}

var getOutsideTemp = function (){
	var element = document.getElementById("outTemp");
	let res = httpGet("http://localhost:8484/RoomManagement/temperature/out");
	res = parseFloat(res);
	res = res.toFixed(1);
	element.textContent = res + "°C";
}

let buttonSetTempe = document.getElementById("btnSetTempe");
buttonSetTempe.onclick = function() {
	let roomId = document.getElementById("roomSett");
	let tempe = document.getElementById("tempeSet");
	console.log("setting temperature to " + tempe.value + "°C in room " + roomId.value);
	let res = httpGet("http://localhost:8484/RoomManagement/controller/setTempe/" + roomId.value + "/" + tempe.value);
	console.log(res);
}

let buttonSetGas = document.getElementById("btnSetGas");
buttonSetGas.onclick = function() {
	let roomId = document.getElementById("roomSetg");
	let gas = document.getElementById("gasSet");
	console.log("setting gas to " + gas.value + "/ in room " + roomId.value);
	let res = httpGet("http://localhost:8484/RoomManagement/controller/setGas/" + roomId.value + "/" + gas.value);
	console.log(res);
}

let buttonSetLight = document.getElementById("btnSetLight");
buttonSetLight.onclick = function() {
	let roomId = document.getElementById("roomSetl");
	let light = document.getElementById("lightSet");
	console.log("setting light to " + light.value + " in room " + light.value);
	let res = httpGet("http://localhost:8484/RoomManagement/controller/setLight/" + roomId.value + "/" + light.value);
	console.log(res);
}

window.onload = function(){
	this.updateData();
	var intervalID = setInterval(updateTime, 1000);
	var intervalIData = setInterval(updateData, 3000);
	// var intervalManage = setInterval(manageRooms, 10000);
	getOutsideTemp();
	
}




