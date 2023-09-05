var Allnums = 20037508.342789244*2;
var showStep = 0 ;
var curveInfo = new Array();
var curveDesc = new Array();
var curvelevel = new Array();
var curvelineArray = new Array();
var curvelineBottomArray = new Array();
var curveIDArray = new Array();
var curveColorArray = new Array();
var curveTimesArray = new Array();
var curveMergeArray = new Array();
var lwidth = 3 ;
var curveNode = null ;
var fromCNOC = 0 ;
var LocationInfo = null ;
var curvesource = new ol.source.Vector({
	features: []
});

var curvelayer = new ol.layer.Vector({
	source: curvesource // layer-source  1:1
});


function curveCollectInfo(c){
	//console.log(c);
	//console.log("*"+showLandingStation+"*");
	if(c!="{}"){
		var newJson = JSON.parse(c).node_info ;
		//console.log(newJson);
		for (var u=0 ; u < newJson.length  ; u++){
			//var detailInfo = "<BR>國際語音:&nbsp;"+newJson[u].InterVoice[0]+"路"+(newJson[u].level[0]=='1'?"，障礙申告:"+newJson[u].failvoice[0]+"路":"")+"<BR>";
			//detailInfo += "國際網際網路:&nbsp;"+newJson[u].Internet[0]+"Gbps"+(newJson[u].level[0]=='1'?"，障礙申告:"+newJson[u].failinternet[0]+"Gbps":"")+"<BR>";
			//detailInfo += "國際出租電路:&nbsp;"+newJson[u].Leasing_circuit_line[0]+"路"+(newJson[u].level[0]=='1'?"，障礙申告:"+newJson[u].failleasing_circuit_line[0]+"路":"")+"<BR>";
			//detailInfo += "國際出租電路(頻寬):&nbsp;"+newJson[u].Leasing_circuit_bw[0]+"Gbps"+(newJson[u].level[0]=='1'?"，障礙申告:"+newJson[u].failleasing_circuit_bw[0]+"Gbps":"")+"<BR>";
			//var detailInfo = "<style> tr {border-bottom: 1pt solid black;}</style>";
			
			//var detailInfo = "<style>table, td, th { border: 1px solid;} table { width: 100%; border-collapse: collapse;}</style>";
			//detailInfo += "<BR><table><tr><td align='center'>國際語音:</td><td align='right'>"+newJson[u].InterVoice[0]+"</td><td>路</td>"+(newJson[u].level[0]=='1'?"<td>&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failvoice[0]+"</td><td>路</td>":"")+"</tr>";
			//detailInfo += "<tr ><td align='center'>國際網際網路:</td><td align='right'>"+newJson[u].Internet[0]+"</td><td>Gbps</td>"+(newJson[u].level[0]=='1'?"<td>&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failinternet[0]+"</td><td>Gbps</td>":"")+"</tr>";
			//detailInfo += "<tr ><td align='center'>國際出租電路:</td><td align='right'>"+newJson[u].Leasing_circuit_line[0]+"</td><td>路</td>"+(newJson[u].level[0]=='1'?"<td>&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failleasing_circuit_line[0]+"</td><td>路</td>":"")+"</tr>";
			//detailInfo += "<tr ><td align='center'>國際出租電路(頻寬):</td><td align='right'>"+newJson[u].Leasing_circuit_bw[0]+"</td><td>Gbps</td>"+(newJson[u].level[0]=='1'?"<td>&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failleasing_circuit_bw[0]+"</td><td>Gbps</td>":"")+"</tr></table>";
			
			var detailInfo = "<BR><table><tr style='border-bottom: 1px solid black;'><td align='center'>國際語音:</td><td align='right'>"+newJson[u].InterVoice[0]+"</td><td>路</td><td>"+(newJson[u].level[0]=='1'?"&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failvoice[0]+"</td><td>路":"")+"</td></tr>";
			detailInfo += "<tr style='border-bottom: 1px solid black;'><td align='center'>國際網際網路:</td><td align='right'>"+newJson[u].Internet[0]+"</td><td>Gbps</td><td>"+(newJson[u].level[0]=='1'?"&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failinternet[0]+"</td><td>Gbps":"")+"</td></tr>";
			detailInfo += "<tr style='border-bottom: 1px solid black;'><td align='center'>國際出租電路:</td><td align='right'>"+newJson[u].Leasing_circuit_line[0]+"</td><td>路</td><td>"+(newJson[u].level[0]=='1'?"&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failleasing_circuit_line[0]+"</td><td>路":"")+"</td></tr>";
			detailInfo += "<tr style='border-bottom: 1px solid black;'><td align='center'>國際出租電路(頻寬):</td><td align='right'>"+newJson[u].Leasing_circuit_bw[0]+"</td><td>Gbps</td><td>"+(newJson[u].level[0]=='1'?"&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failleasing_circuit_bw[0]+"</td><td>Gbps":"")+"</td></tr></table>";
			
			//detailInfo += "國際出租電路:&nbsp;"+newJson[u].InterLeasing[0]+"路(每路64Kbps)";
			try{
				//if((newJson[u].node_lat != "") && (newJson[u].node_lon != "")){
				//	var wordcolor = (newJson[u].node_failure == "0"?"#000000":(newJson[u].node_failure == "1"?"gray":"red"));
				//	showLandingStation += (showLandingStation==""?"":"<w>")+"marker_"+newJson[u].node_id+",gn,"+newJson[u].node_name+","+newJson[u].node_lon+","+newJson[u].node_lat+","+wordcolor;
				//}
				//console.log(newJson[u].linecolor[0]);
			if(newJson[u].nidB_lon[0] < -100){
				addCurveFunction([Number(newJson[u].nidB_lon[0]),Number(newJson[u].nidB_lat[0])],[Number(newJson[u].nidA_lon[0]),Number(newJson[u].nidA_lat[0])],1,newJson[u].connection_id[0],newJson[u].linecolor[0]+"B0",newJson[u].connection_A[0]+"~"+newJson[u].connection_B[0],"<font color='black'>國際電路服務及障礙資訊</font><BR>"+newJson[u].connection_A[0]+"~"+newJson[u].connection_B[0]+"<BR>"+(newJson[u].notes[0]==null?"":newJson[u].notes[0])+detailInfo,newJson[u].level[0],lwidth);
			}else{
				addCurveFunction([Number(newJson[u].nidA_lon[0]),Number(newJson[u].nidA_lat[0])],[Number(newJson[u].nidB_lon[0]),Number(newJson[u].nidB_lat[0])],1,newJson[u].connection_id[0],newJson[u].linecolor[0]+"B0",newJson[u].connection_A[0]+"~"+newJson[u].connection_B[0],"<font color='black'>國際電路服務及障礙資訊</font><BR>"+newJson[u].connection_A[0]+"~"+newJson[u].connection_B[0]+"<BR>"+(newJson[u].notes[0]==null?"":newJson[u].notes[0])+detailInfo,newJson[u].level[0],lwidth);
			}
			//console.log(Number(1+(u/newJson.length)*2));
			}catch{}
		}
	}
}

function curveCollectInfo1(c){
	//console.log(c);
	//console.log("*"+showLandingStation+"*");
	if(c!="{}"){
		showLandingStation = "";
		var newJson = JSON.parse(c).node_info ;
		console.log(newJson);
		for (var u=0 ; u < newJson.length  ; u++){
			//var detailInfo = "<BR>國際語音:&nbsp;"+newJson[u].InterVoice[0]+"路"+(newJson[u].level[0]=='1'?"，障礙申告:"+newJson[u].failvoice[0]+"路":"")+"<BR>";
			//detailInfo += "國際網際網路:&nbsp;"+newJson[u].Internet[0]+"Gbps"+(newJson[u].level[0]=='1'?"，障礙申告:"+newJson[u].failinternet[0]+"Gbps":"")+"<BR>";
			//detailInfo += "國際出租電路:&nbsp;"+newJson[u].Leasing_circuit_line[0]+"路"+(newJson[u].level[0]=='1'?"，障礙申告:"+newJson[u].failleasing_circuit_line[0]+"路":"")+"<BR>";
			//detailInfo += "國際出租電路(頻寬):&nbsp;"+newJson[u].Leasing_circuit_bw[0]+"Gbps"+(newJson[u].level[0]=='1'?"，障礙申告:"+newJson[u].failleasing_circuit_bw[0]+"Gbps":"")+"<BR>";
			//var detailInfo = "<style> tr {border-bottom: 1pt solid black;}</style>";
			
			//var detailInfo = "<style>table, td, th { border: 1px solid;} table { width: 100%; border-collapse: collapse;}</style>";
			//detailInfo += "<BR><table><tr><td align='center'>國際語音:</td><td align='right'>"+newJson[u].InterVoice[0]+"</td><td>路</td>"+(newJson[u].level[0]=='1'?"<td>&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failvoice[0]+"</td><td>路</td>":"")+"</tr>";
			//detailInfo += "<tr ><td align='center'>國際網際網路:</td><td align='right'>"+newJson[u].Internet[0]+"</td><td>Gbps</td>"+(newJson[u].level[0]=='1'?"<td>&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failinternet[0]+"</td><td>Gbps</td>":"")+"</tr>";
			//detailInfo += "<tr ><td align='center'>國際出租電路:</td><td align='right'>"+newJson[u].Leasing_circuit_line[0]+"</td><td>路</td>"+(newJson[u].level[0]=='1'?"<td>&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failleasing_circuit_line[0]+"</td><td>路</td>":"")+"</tr>";
			//detailInfo += "<tr ><td align='center'>國際出租電路(頻寬):</td><td align='right'>"+newJson[u].Leasing_circuit_bw[0]+"</td><td>Gbps</td>"+(newJson[u].level[0]=='1'?"<td>&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failleasing_circuit_bw[0]+"</td><td>Gbps</td>":"")+"</tr></table>";
			
			var detailInfo = "<BR><table><tr style='border-bottom: 1px solid black;'><td align='center'>國際語音:</td><td align='right'>"+newJson[u].InterVoice+"</td><td>路</td><td>"+(newJson[u].level!='0'?"&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failvoice+"</td><td>路":"")+"</td></tr>";
			detailInfo += "<tr style='border-bottom: 1px solid black;'><td align='center'>國際網際網路:</td><td align='right'>"+newJson[u].Internet+"</td><td>Gbps</td><td>"+(newJson[u].level!='0'?"&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failinternet+"</td><td>Gbps":"")+"</td></tr>";
			detailInfo += "<tr style='border-bottom: 1px solid black;'><td align='center'>國際出租電路:</td><td align='right'>"+newJson[u].Leasing_circuit_line+"</td><td>路</td><td>"+(newJson[u].level!='0'?"&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failleasing_circuit_line+"</td><td>路":"")+"</td></tr>";
			detailInfo += "<tr style='border-bottom: 1px solid black;'><td align='center'>國際出租電路(頻寬):</td><td align='right'>"+newJson[u].Leasing_circuit_bw+"</td><td>Gbps</td><td>"+(newJson[u].level!='0'?"&nbsp;&nbsp;</td><td>障礙申告:</td><td align='right'>"+newJson[u].failleasing_ethernet_bw+"</td><td>Gbps":"")+"</td></tr></table>";
			
			//detailInfo += "國際出租電路:&nbsp;"+newJson[u].InterLeasing[0]+"路(每路64Kbps)";
			try{
				//if((newJson[u].node_lat != "") && (newJson[u].node_lon != "")){
				//	var wordcolor = (newJson[u].node_failure == "0"?"#000000":(newJson[u].node_failure == "1"?"gray":"red"));
				//	showLandingStation += (showLandingStation==""?"":"<w>")+"marker_"+newJson[u].node_id+",gn,"+newJson[u].node_name+","+newJson[u].node_lon+","+newJson[u].node_lat+","+wordcolor;
				//}
				newJson[u].connection_id = "T"+u;
				if(newJson[u].nidB_lon == "0"){
					var matchok = 0 ;
					for(var v=0 ; v<LocationInfo.length ; v++){
						if(LocationInfo[v].node_country[0] == newJson[u].connection_B){
							newJson[u].nidB_lon = LocationInfo[v].node_lon[0] ;
							newJson[u].nidB_lat = LocationInfo[v].node_lat[0] ;
							//newJson[u].connection_id = "T"+u;
							matchok = 1 ;
							break;
						}
					}
					if(matchok == 0){
						newJson[u].nidB_lon = "120.000";
						newJson[u].nidB_lat = "24.000";
						//newJson[u].connection_id = "T"+u;
					}
				}
				
				if(showLandingStation == ""){
					showLandingStation += "markerA_"+newJson[u].connection_id+",gn,"+newJson[u].connection_A+","+newJson[u].nidA_lon+","+newJson[u].nidA_lat+",#000000";
					showLandingStation += "<w>";
					showLandingStation += "markerB_"+newJson[u].connection_id+",gn,"+newJson[u].connection_B+","+newJson[u].nidB_lon+","+newJson[u].nidB_lat+",#000000";
				}else{
					var tmpStation = showLandingStation.split("<w>");
					var checkdup = 0 ;
					for (var r=0 ; r < tmpStation.length ; r++){
						if(tmpStation[r].indexOf(","+newJson[u].connection_A)>= 0 ){
							if(tmpStation[r].indexOf(newJson[u].nidA_lon)>=0 && tmpStation[r].indexOf(newJson[u].nidA_lat)>=0){
								checkdup = 1;
								break;
							}
						}
					}
					if(checkdup == 0){
						showLandingStation += (showLandingStation == ""?"":"<w>");
						showLandingStation += "markerA_"+newJson[u].connection_id+",gn,"+newJson[u].connection_A+","+newJson[u].nidA_lon+","+newJson[u].nidA_lat+",#000000";
					}
					checkdup = 0 ;
					for (var r=0 ; r < tmpStation.length ; r++){
						if(tmpStation[r].indexOf(","+newJson[u].connection_B)>= 0 ){
							if(tmpStation[r].indexOf(newJson[u].nidB_lon)>=0 && tmpStation[r].indexOf(newJson[u].nidB_lat)>=0){
								checkdup = 1;
								break;
							}
						}
					}
					if(checkdup == 0){
						showLandingStation += (showLandingStation == ""?"":"<w>");
						showLandingStation += "markerB_"+newJson[u].connection_id+",gn,"+newJson[u].connection_B+","+newJson[u].nidB_lon+","+newJson[u].nidB_lat+",#000000";
					}				
				}
			if(newJson[u].nidB_lon < -100){
				addCurveFunction([Number(newJson[u].nidB_lon),Number(newJson[u].nidB_lat)],[Number(newJson[u].nidA_lon),Number(newJson[u].nidA_lat)],1,newJson[u].connection_id,newJson[u].linecolor+"B0",newJson[u].connection_A+"~"+newJson[u].connection_B,"<font color='black'>國際電路服務及障礙資訊</font><BR>"+newJson[u].connection_A+"~"+newJson[u].connection_B+"<BR>"+(newJson[u].notes==null?"":newJson[u].notes)+detailInfo,newJson[u].level,lwidth);
			}else{
				addCurveFunction([Number(newJson[u].nidA_lon),Number(newJson[u].nidA_lat)],[Number(newJson[u].nidB_lon),Number(newJson[u].nidB_lat)],1,newJson[u].connection_id,newJson[u].linecolor+"B0",newJson[u].connection_A+"~"+newJson[u].connection_B,"<font color='black'>國際電路服務及障礙資訊</font><BR>"+newJson[u].connection_A+"~"+newJson[u].connection_B+"<BR>"+(newJson[u].notes==null?"":newJson[u].notes)+detailInfo,newJson[u].level,lwidth);
			}
			//console.log(Number(1+(u/newJson.length)*2));
			}catch{}
		}
		console.log(showLandingStation);
		show_Marker();
	}
}

function catchInfo_CNOC(){
	//CNOCAPI
	colorMessage("串接資料中...");
	$.post("/scsi/DataAPI", {scable:"h",func:"18",loc:"111"})
	.done(function( data , statusnow , finalcode) {
		//console.log(finalcode);
		if(data==""){
			//alert("無串接資料..."+statusnow);
		}else{	
		curveNode = '{"node_info":'+ decodeURIComponent(data)+'}';
		//curveNode = decodeURIComponent(data);
		console.log(curveNode);
		curveCollectInfo1(curveNode);
		//compareNode();
		//drawTest();
		}
	}
	)
	.always(function(data){
		//alert("Complete無串接資料...");
	}
	)
}

function catchInfo(){
	$.post("/scsi/DataAPI", {scable:"",func:"16",loc:"111"})
	.done(function( data ) {
		curveNode = decodeURIComponent(data);
		curveCollectInfo(decodeURIComponent(data));

		//compareNode();
	})
}

function compareNode(){
	var newJson = JSON.parse(curveNode).node_info ;
	var newStation = showLandingStation.split("<w>");
	var newLandingStation = "";
		for (var u=0 ; u < newJson.length  ; u++){
			try{
				//if((newJson[u].node_lat != "") && (newJson[u].node_lon != "")){
				//	var wordcolor = (newJson[u].node_failure == "0"?"#000000":(newJson[u].node_failure == "1"?"gray":"red"));
				//	showLandingStation += (showLandingStation==""?"":"<w>")+"marker_"+newJson[u].node_id+",gn,"+newJson[u].node_name+","+newJson[u].node_lon+","+newJson[u].node_lat+","+wordcolor;
				//}
				var n1 = newJson[u].connection_A[0];
				var n2 = newJson[u].connection_B[0];
				if(newLandingStation.indexOf(n1) < 0){
					for(var cc=0 ; cc < newStation.length ; cc++){
						if(newStation[cc].split(",")[2] == n1){
							newLandingStation += (newLandingStation==""?"":"<w>") + newStation[cc];
							break;
						}
					}
				}
				if(newLandingStation.indexOf(n2) < 0){
					for(var cc=0 ; cc < newStation.length ; cc++){
						if(newStation[cc].split(",")[2] == n2){
							newLandingStation += (newLandingStation==""?"":"<w>") + newStation[cc];
							break;
						}
					}
				}
				showLandingStation = newLandingStation;
				
			}catch{}
		}
		
		showLineSwitch(0,true);
		//showLineSwitch(0);
}

function initCurveLayer(){
	try {
		mapArray[0].addLayer(curvelayer);
		//showLineSwitch(0,true);
		if(confirm("要串接CNOC資料嗎??")){
			getNodeDBInfo();
			fromCNOC = 1;
			catchInfo_CNOC();
		}else{
			fromCNOC = 0 ;
			catchInfo();
		}
	}catch (e) {
	}
}

function factorial(num) {
    if (num <= 1) {
        return 1;
    } else {
        return num * factorial(num - 1);
    }
}

function createBezierCurvePointsP(n, arrPoints) {
	var Ptx = 0;
    var Pty = 0;
    var LineString = [];
	
    for (var t = 0; t < 1; t = t + 0.01) {
        Ptx = 0;
        Pty = 0;
        for (var i = 0; i <= n; i++) {
			if(arrPoints[i][0] < 0){
				Ptx += (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t,i) * (Allnums + arrPoints[i][0]);
			}else{
				Ptx += (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t,i) * arrPoints[i][0];
			}
			Pty += (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t,i) * arrPoints[i][1];
		}
		LineString.push([Ptx, Pty]);
	}
	return LineString;
}

function createBezierCurvePointsPS(n, arrPoints) {
	var Ptx = 0;
    var Pty = 0;
    var LineString = [];

    for (var t = 0; t < 1; t = t + 0.01) {
        Ptx = 0;
        Pty = 0;
        for (var i = 0; i <= n; i++) {
			if(arrPoints[i][0] > 0){

				Ptx += (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t,i) * (arrPoints[i][0]-Allnums);
			}else{
				Ptx += (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t,i) * arrPoints[i][0];
			}
			Pty += (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t,i) * arrPoints[i][1];
		}
		LineString.push([Ptx, Pty]);
	}
	return LineString;
}

function createBezierCurvePoints(n, arrPoints) {
	var Ptx = 0;
    var Pty = 0;
    var LineString = [];
    for (var t = 0; t < 1; t = t + 0.01) {
        Ptx = 0;
        Pty = 0;
        for (var i = 0; i <= n; i++) {
            Ptx += (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t,i) * arrPoints[i][0];
			Pty += (factorial(n) / (factorial(i) * factorial(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t,i) * arrPoints[i][1];
		}
		//console.log(Ptx+"/"+Pty);
		LineString.push([Ptx, Pty]);
	}
	return LineString;
}

function createLine(result,myfid,mycolor,lw,words) {
	var feature = new ol.Feature({
		id: myfid,
        geometry: new ol.geom.LineString(result)
    });
    var style = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: mycolor,
            width: lw,
			lineDash: [10,10],
			//lineDash: [1,2,3,4,5,6],
            //lineDash: [ .1, 5],
			//lineDash: [ 1, 2],
			//lineDash: [ 3,5],
        }),
		 text: new ol.style.Text({
			text: words,
			scale: 1.1,
			fill: new ol.style.Fill({
				color: '#000000'
			}),
			stroke: new ol.style.Stroke({
				color: '#FFFFFF',
				width: 3.5
			})
		 }),
		zIndex: 100
    })
	
    feature.setStyle(style);
	feature.setId(myfid);
	feature.set("__color", mycolor, true);
    curvesource.addFeature(feature);
}

var r1,r2,r3,r4,r5;


function addCurveFunction(mypoints,mypoints1,x,myfid,mycolor,mySimple,myNotes,myLevel,lineWidth){
	var myPointsResult = [];
	myPointsResult.push(ol.proj.transform(mypoints, 'EPSG:4326','EPSG:3857'));
	var result ;
	try{
	if(mypoints[0]<0 && mypoints1[0]>0){
		//console.log(((180+mypoints[0][0]) +(180-mypoints[2][0])));
		if(((180+mypoints[0]) +(180-mypoints1[0])) > 180){
			myPointsResult.push(ol.proj.transform(findmymiddle(mypoints,mypoints1,x), 'EPSG:4326','EPSG:3857'));
			myPointsResult.push(ol.proj.transform(mypoints1, 'EPSG:4326','EPSG:3857'));
			result = createBezierCurvePoints(myPointsResult.length - 1, myPointsResult);
		}else{
			myPointsResult.push(ol.proj.transform(findmymiddle180(mypoints,mypoints1,x), 'EPSG:4326','EPSG:3857'));
			myPointsResult.push(ol.proj.transform(mypoints1, 'EPSG:4326','EPSG:3857'));
			result = createBezierCurvePointsP(myPointsResult.length - 1, myPointsResult);
		}
	}else{
		//console.log(((180+mypoints[0][0]) +(180-mypoints[2][0])));
		if(((180-mypoints[0]) +(180+mypoints1[0])) > 180){
			myPointsResult.push(ol.proj.transform(findmymiddle(mypoints,mypoints1,x), 'EPSG:4326','EPSG:3857'));
			myPointsResult.push(ol.proj.transform(mypoints1, 'EPSG:4326','EPSG:3857'));
			result = createBezierCurvePoints(myPointsResult.length - 1, myPointsResult);
		}else{
			myPointsResult.push(ol.proj.transform(findmymiddle180(mypoints,mypoints1,x), 'EPSG:4326','EPSG:3857'));
			myPointsResult.push(ol.proj.transform(mypoints1, 'EPSG:4326','EPSG:3857'));
			result = createBezierCurvePointsPS(myPointsResult.length - 1, myPointsResult);
		}
		//result = createBezierCurvePoints(myPointsResult.length - 1, myPointsResult);
	}

	//console.log(myPointsResult[1]);
	//result = createBezierCurvePoints(myPointsResult.length - 1, myPointsResult);
	curvelineArray.push(result);
	curvelineBottomArray.push(myPointsResult[myPointsResult.length - 1]);
	curveIDArray.push(myfid);
	curveDesc.push(myNotes);
	curvelevel.push(myLevel);
	curveColorArray.push(mycolor);
	curveTimesArray.push(1);
	//console.log(result);
	if(showStep == 0){
		result.push(myPointsResult[myPointsResult.length - 1]);
		createLine(result,myfid,mycolor,lineWidth,mySimple);
		preCreateCableInfoBoxDiv(0,myfid);
		//setInfoBox(myfid,myfid+"/"+mySimple);
		setInfoBox(myfid,mySimple);
	}
	}catch{}
}

var stages = 10 ;
var drawTimer ;
var step_time = 1000 ;
function showLine(){
	clearTimeout(drawTimer);
	//console.log(curveIDArray.length);
	for (var cu=0 ; cu < curveIDArray.length ; cu++){
		removeCurveFunction(curveIDArray[cu]);
		var r1 = new Array();
		var upto = curveTimesArray[cu] * stages ;
		//if (cu == 3){
		//console.log(cu+"/"+upto+"/"+curvelineArray[cu].length+"/"+curveTimesArray[cu]);	
		//}
		if (upto > curvelineArray[cu].length){
			upto = stages;
			curveTimesArray[cu] = 2;
		}else{
			curveTimesArray[cu] = curveTimesArray[cu]+1;
		}
		for (var rr=0 ; rr < upto ; rr++){
			r1.push(curvelineArray[cu][rr]);
			if(rr == 0){
				// console.log(rr+"="+curvelineArray[cu][rr][0]+", "+upto+"="+curvelineArray[cu][rr][1]);
				// showLandingStation.push("A1, red, ,"+curvelineArray[cu][rr][0]+","+curvelineArray[cu][rr][1]);
			}
		}
		if (r1.length == curvelineArray[cu].length){
			r1.push(curvelineBottomArray[cu]);
		}
		createLine(r1,curveIDArray[cu],curveColorArray[cu]);
		//createLine(curvelineArray[cu],curveIDArray[cu]+"_1",curveColorArray[cu]);
	}
	var len = (curvelineArray[0].length-1);
	var startPoint = curvelineArray[0][0];
	var endPoint = curvelineArray[0][len];

	drawTimer = setTimeout("showLine();",step_time);
}

function removeCurveFunction(myfid){
	try {
		curvelayer.getSource().removeFeature(curvelayer.getSource().getFeatureById(myfid));
	}catch (e) {
	}
}


function clearCurveFunction(){
	try {
		curvelayer.getSource().clear();
	}catch (e) {
	}
}


function findmymiddle(a,b,c){
	var d=c ;
	if (((a[0]-b[0])>10) || ((a[0]-b[0])<-10)){
		c = Number(c)*(Math.abs(a[0]-b[0])*0.1);
	}else{
		c = Number(c)*0.3;
	}
	
	if ( ((a[1]-b[1])>10) || ((a[1]-b[1])<-10)){
		d = Number(c)*1.5;
	}else{
		d = Number(c)*0.3;
	}
	var get_A = (a[0] + b[0])/2 + (Number(c)*2);
	var get_B = (a[1] + b[1])/2 + (Number(d)*2);
	return [get_A,get_B];
}


function findmymiddle180(a,b,c){
	var get_A , get_B ;
	if (((a[0]-b[0])>10) || ((a[0]-b[0])<-10) || ((a[1]-b[1])>10) || ((a[1]-b[1])<-10)){
	//if (((Math.abs(a[0])-Math.abs(b[0]))>10) || ((Math.abs(a[0])-Math.abs(b[0]))<-10) || ((Math.abs(a[1])-Math.abs(b[1]))>10) || ((Math.abs(a[1])-Math.abs(b[1]))<-10)){
		c = c+10;	
	}else{
		c = c*0.1;
	}
	get_A = 180;
	get_B = (a[1] + b[1])/2 + (c*2);
	//get_B = (Math.abs(a[1]) + Math.abs(b[1]))/2 + (c*2);
	return [get_A,get_B];
}

function curveToggle1(x){
	if(x){
		if(fromCNOC == 1){
			setTimeout("catchInfo_CNOC();",500);
		}else{
			setTimeout("catchInfo();",500);
		}
		//catchInfo();
	}else{
		for(var i=0 ; i< curveIDArray.length ; i++){
			$("#"+curveIDArray[i]+"-popup").remove();
		}
		clearCurveFunction();
		curvelineArray = new Array();
		curvelineBottomArray = new Array();
		curveIDArray = new Array();
		curveColorArray = new Array();
		curveTimesArray = new Array();
		curveMergeArray = new Array();
		curveInfo = new Array();
		curveDesc = new Array();
		curvelevel = new Array();
	}
	
}

function curveToggle(){
	if(curveIDArray.length == 0){
		if(fromCNOC == 1){
			setTimeout("catchInfo_CNOC();",500);
		}else{
			setTimeout("catchInfo();",500);
		}
		//catchInfo();
	}else{
		for(var i=0 ; i< curveIDArray.length ; i++){
			$("#"+curveIDArray[i]+"-popup").remove();
		}
		clearCurveFunction();
		curvelineArray = new Array();
		curvelineBottomArray = new Array();
		curveIDArray = new Array();
		curveColorArray = new Array();
		curveTimesArray = new Array();
		curveMergeArray = new Array();
		curveInfo = new Array();
		curveDesc = new Array();
		curvelevel = new Array();
	}
	
}

function reLoadCurve(){
		for(var i=0 ; i< curveIDArray.length ; i++){
			$("#"+curveIDArray[i]+"-popup").remove();
		}
		clearCurveFunction();
		curvelineArray = new Array();
		curvelineBottomArray = new Array();
		curveIDArray = new Array();
		curveColorArray = new Array();
		curveTimesArray = new Array();
		curveMergeArray = new Array();
		curveInfo = new Array();
		curveDesc = new Array();
		curvelevel = new Array();
		if(fromCNOC == 1){
			setTimeout("catchInfo_CNOC();",500);
		}else{
			setTimeout("catchInfo();",500);
		}
}
var smallone = 0 ;
var limitzoom = 9 ;
var maxwidths = 8 ;
function changeCSSCurve(x){
	if(x < limitzoom && smallone == 0){
	}else{
		if(x>limitzoom && smallone == 0){
			lwidth = maxwidths ;
			smallone = 1;
			reLoadCurve();
		}else{
			if(x<limitzoom && smallone == 1){
				lwidth = 3 ;
				smallone = 0;
				reLoadCurve();
			}
		}
	}
}

function getNodeDBInfo(){
	$.post("/scsi/DataAPI", {scable:"",func:"13",loc:"111"})
	//$.post("../DataAPI", {scable:PostDataEnc(testjson,passKey),func:"6",loc:"111"})
	.done(function( data ) {
		//console.log(decodeURIComponent(data));
		LocationInfo = JSON.parse(decodeURIComponent(data)).node_info ;
	});
}