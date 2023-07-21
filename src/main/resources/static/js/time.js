var fail_sc , fail_loc , fail_locname;
var scablecolor = ["dotgreen","dotblue","dotpurple","dotyellow","dotlight","dotpink"];
var scableColorCode = ["#33FF35","#3383FF","#6F29F1","#DDFF33","#FFC433","#FF33E9","#F51A08"];
var checknum = 0 ;
var showFlashTimer ;
var debugMode = true;
var mytimeout = 0 ;
var t ;
function CurentTime(){
	clearTimeout(t);
    // const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    const weekNames = ["星期日", "星期一", "星期二", "星期三","星期四", "星期五", "星期六"];
    var today = new Date();
    var y = today.getFullYear();
    var h = today.getHours();
    var m = today.getMinutes();
    var d = today.getDate();
    var s = today.getSeconds();
    // var mmn = monthNames[today.getMonth()];
    var mm = today.getMonth()+1;    
    var w = weekNames[today.getDay()];
    // var ampm = h >= 12 ? 'pm' : 'am';
    h = checkTime(h);
    m = checkTime(m);
    mm = checkTime(mm);
    d = checkTime(d);
    //s = checkTime(s) + " "+mytimeout;
	s = checkTime(s);
    document.getElementById('time').innerText =  h + ":" + m + ":" + s ;
    document.getElementById('date').innerText = y + "/" + mm + "/" + d ;
    //document.getElementById('day').innerText = "/ " + w  + " / " ;
	document.getElementById('time1').innerText = h + ":" + m + ":" + s ;
    document.getElementById('date1').innerText = y + "/" + mm + "/" + d ;
	document.getElementById('time2').innerText = h + ":" + m + ":" + s ;
    document.getElementById('date2').innerText = y + "/" + mm + "/" + d ;
	mytimeout = mytimeout + 1 ;
	if(mytimeout > 1000) location.reload();
    t = setTimeout(CurentTime, 1000);
}

function checkTime(i) {
    if (i < 10) {i = "0" + i};
    return i;
}

window.onload=CurentTime();

var backup_route = "";
var backup_route_text = "";
var cnt = 0 ;
var tmpdata = [];

function SelectAdd(){
	for(var i=0 ; i< tmpdata.length-1 ; i++){
		addScable(tmpdata[i]);
	}
}

function addScable(x){
	var cc = $("#scablelist").html();
	cc += "<div class='radio'><div class='radio_b'><input class='rdobutton_radio' onchange='sentBackup(this.value,this.checked);' type='checkbox' id='scable_"+x.split("/")[0]+"' name='scable_"+x.split("/")[0]+"' value='scable_"+x.split("/")[0]+"' />";
    cc += "<label class='rdobutton_label' for='scable_"+x.split("/")[0]+"'></label></div><div id='scable_"+x.split("/")[0]+"_text' class='radio_text'><p>"+x+"</p></div></div>";
	$("#scablelist").html(cc);
}

function sentBackup(x,y){
	if(y) checknum++;
	if(checknum > 3) {
		checknum--;
		$("#"+x).prop('checked', false);
		alert("僅可選擇三條替代海纜");
	}else{
		var mytext = $("#"+x+"_text").text();
		var mypos = backup_route.indexOf(x) ;
		var mypos_text = backup_route_text.indexOf(mytext) ;
		var mylength = x.length+1;
		var mylength_text = mytext.length+1;
		if(mypos < 0 && y ){
			backup_route += x+";";
			backup_route_text += mytext+";";
		}else{
			if(mypos >= 0 && !y){
				backup_route = backup_route.substring(0,mypos)+backup_route.substring(mypos+mylength,backup_route.length);
				backup_route_text = backup_route_text.substring(0,mypos_text)+backup_route_text.substring(mypos_text+mylength_text,backup_route_text.length);
			}
		}
		if(!y) checknum--;
	}
}

function getScableList(){
	backup_route="";
	backup_route_text="";
	fail_sc="";
	fail_loc = "";
	checknum = 0 ;
	//getPostData("DataAPI/scableList.jsp","scablealllist");
	getPostData("../DataAPI","scablealllist");
}

function getScableNations(x){
	//getScableNation("DataAPI/scableNations.jsp",x,"faillocation");
	getScableNation("../DataAPI",x,"faillocation");
}

function getScableNation(x,y,z){
$("#scablelist").html("");
checknum = 0 ;
fail_sc = TrimMe(y) ;
$.post( x, {scable:y,func:"2"})
  .done(function( data ) {
	//console.log("2:"+TrimMe(data));
    showSelect(TrimMe(data),z);
  });
}

function getFailLoacation(x){
	fail_loc = TrimMe(x) ;
	fail_locname = $("#faillocation").find("option:selected").text();
	//alert(fail_sc + ";"+fail_loc);
//	$.post( "DataAPI/scableBackup.jsp", {id:fail_sc, loc : fail_loc, func:"3"})
	$.post( "../DataAPI", {scable:fail_sc, loc : fail_loc, func:"3"})	
	.done(function( data ) {
		//console.log("3:"+TrimMe(data));
		$("#scablelist").html("");
		checknum = 0 ;
		tmpdata = TrimMe(data).split("<w>");
		cnt = 0 ;
		SelectAdd();
	});
}

function getPostData(x,y){
	var xyz ;
  $.post(x, {func:"1"})
  .done(function( data ) {
	 //console.log("1:"+TrimMe(data));
	 showSelect(TrimMe(data),y);
  });
}

function showSelect(x,showid){
	$('#'+showid).find('option').remove().end();
	$('#'+showid).append($('<option>', {
    value: "0",
    text: "請選擇"
	}));
	var tmp = x.split("<w>");
	for(var i=0 ; i< tmp.length-1 ; i++){
	$('#'+showid).append($('<option>', {
    value: tmp[i].split("<i>")[0],
    text: tmp[i].split("<i>")[1]
	}));
	}
}

function TrimMe(x){
	return decodeURI(x).replace(new RegExp("%2F","gm"),"/").replace(new RegExp("%2C","gm"),",").replace(new RegExp("%3B","gm"),";").replace(/(^[\s]*)|([\s]*$)/g, "");
}

var route_cnt = 0 ;
var broute , broute_text ;
function showScableInMap(){
	$("#fail_scable").html(fail_sc);
	$("#fail_location").html(fail_locname);
	$("#fail_bw").html($("#failbw").val());
	$("#fail_desc").html($("#faildesc").val());
	broute = backup_route.split(";") ;
	broute_text = backup_route_text.split(";") ;
	$("#fail_sol").html("");
	route_cnt = 0 ;
	getRoute(fail_sc,fail_loc,scableColorCode[scableColorCode.length-1],0);
}

function againRoute(){
	if(route_cnt < broute.length-1 ){
		var tmpstr = $("#fail_sol").html();
		var tmpinfo = broute_text[route_cnt];
		tmpstr += "<p><span class='"+scablecolor[route_cnt]+"'></span>利用"+tmpinfo.split("/")[0]+"海纜，尚餘"+tmpinfo.split("/")[1]+"容量，以解決"+fail_sc+fail_locname+"段斷纜之狀況。</p>";
		$("#fail_sol").html(tmpstr);
		getRoute(tmpinfo.split("/")[0],fail_loc,scableColorCode[route_cnt],2);
	}
	route_cnt++;
}

var FlashList = "";
var thisFlash = "";
function getRoute(x,y,z,a){
	//$.post( "DataAPI/scableroute.jsp", {id:x, loc : y, func:"4"})
	$.post( "../DataAPI", {scable:x, loc : y, func:"4"})
	.done(function( data ) {
		//console.log("4:"+TrimMe(data));
		var tmpData = TrimMe(data);
		var mypoint =[];
		var myline =[];
		tmpData = tmpData.substring(3,tmpData.length-3);
		var mypoint0 = [] ;
		mypoint0 = tmpData.split("]],[[");
		
		for (var j=0 ; j< mypoint0.length ; j++){
			myline =[];
			mypoint = mypoint0[j].split("],[");
			
			for (var i=0 ; i< mypoint.length ; i++){
				myline.push([Number(mypoint[i].split(",")[0]),Number(mypoint[i].split(",")[1])]);
			}
			
			if(a ==0){ 
				drawMultiLine(x+"_"+y+"_"+j,myline,z);
				FlashList += x+"_"+y+"_"+j+"<w>";
							
			}
			if(a ==1) drawMultiLine1(x+"_"+y+"_"+j,myline,z,5);
			if(a ==2) drawMultiLine2(x+"_"+y+"_"+j,myline,z,5);
			
		}
		
		if(a ==0){ showFlashTimer = setTimeout(" showUp() ;", 1000);}
		againRoute();
	});
}

var myshowup = 0 ;
function showUp(){
	clearTimeout(showFlashTimer);
	myshowup = myshowup + 1 ;
	myshowup = (myshowup%2);
	showOrnoshow(myshowup,FlashList);
	showFlashTimer = setTimeout(" showUp() ;", 1000);
}

function showInfoWindows(x){
	$.colorbox({
				href:'world/myiframe.jsp?name='+clickScable.split("-")[0]+'&zoom=4',
                width:window.innerWidth*0.9,
                height:window.innerHeight,
                iframe:true
            });
}

function showOneCable(x){
	
}
var gocaseflag = 0 ;
function goCase(x)
{
	gocaseflag = 1 ;
	removeAllOthers();
	/*
	if(x == 3){
		thisFlash = "3";
		//oneCableName("TK2-1","used_scable/TK2.json","TK2",1);
		oneJson3("TK2","used_scable/TK2.json",scableColorCode[scableColorCode.length-1],5);
		oneJson2("PK1B","used_scable/PK1-B.json",scableColorCode[0],5);
		oneJson2("PK3B","used_scable/PK3-B.json",scableColorCode[1],5);
		oneJson2("PK1A","used_scable/PK1-A.json",scableColorCode[2],5);
		oneJson2("PK3A","used_scable/PK3-A.json",scableColorCode[3],5);
		oneJson2("TP2","used_scable/TP2.json",scableColorCode[4],5);
		oneJson2("TP3","used_scable/TP3.json",scableColorCode[5],5);
		showOthers("TK2");
		showOthers("TP2");
		showOthers("PK1B");
		showOthers("PK1A");
		showOthers("TP3");
		showOthers("PK3B");
		showOthers("PK3A");
		FlashList="TK2<w>";
		showFlashTimer = setTimeout(" showUp() ;", 1000);
	}
	*/
	//$.post( "DataAPI/scableScenario.jsp", {id:x,func:"5"})
	$.post( "../DataAPI", {scable:x,func:"5"})
	.done(function( data ) {
		//console.log("5:"+TrimMe(data));
		var tmpdata = TrimMe(data).split("<w>");
		 $("#failbw").val(tmpdata[3]);
		$("#faildesc").val(tmpdata[4]);
		fail_sc = tmpdata[0];
		fail_loc = tmpdata[1];
		fail_locname = tmpdata[2];
		backup_route = tmpdata[5];
		backup_route_text = tmpdata[6];
		if(x == 3){
			thisFlash = "3";
			oneJson3(fail_sc,"used_scable/"+fail_sc+".json",scableColorCode[scableColorCode.length-1],5);
			showOthers(fail_sc);
			for (var i=0 ; i < tmpdata[6].split(";").length ; i++){
				if(tmpdata[6].split(";")[i] != ""){
				oneJson2(tmpdata[6].split(";")[i].split("/")[0],"used_scable/"+tmpdata[6].split(";")[i].split("/")[0]+".json",scableColorCode[i],5);
				showOthers(tmpdata[6].split(";")[i].split("/")[0]);	
				}
			}
			FlashList="TK2<w>";
			showFlashTimer = setTimeout(" showUp() ;", 1000);
		}
		showResult();
	});
}

 function first_ready(x) {
    prepareVectorStylesArray(); //添加圖層樣式陣列
    prepareVectorArray();
	preparePlugin_Color(1);
	getScableList();
	switchScreen(x);
	}

function switchScreen(x){
	if(x==1) switchInfra();
	if(x==2) switchMap();
}
	
function showResult(){
    if(backup_route=="" || 	fail_sc == "" || fail_loc == ""){
		alert("資料輸入內容不足, 請重新輸入..謝謝!!");
	}else{
		if(gocaseflag == 0){
			removeAllOthers();
		}
		$("#myinput").hide();
		$("#myresult").show();
		showScableInMap();
	}
	gocaseflag = 0;
}

function showInput(){
	$("#myinput").show();
	$("#myresult").hide();
	$("#scablelist").html("");
	$("#failbw").val("");
	$("#faildesc").val("");
	getScableList();
	getScableNations("begin");
	cnt = 0 ;
	removeAllOthers();
	if(thisFlash == "3"){
		removeACable(1);
	}
	clearTimeout(showFlashTimer);
	FlashList = "";
	thisFlash = "";
	mapArray[0].getView().setCenter(ol.proj.fromLonLat([120.70, 23.58]));
    mapArray[0].getView().setZoom(7);
}

function showThisCable(){
	removeAllOthers();
	if(fail_sc == ""){
		alert("請選擇欲查看之障礙海纜..謝謝!!");
	}else{
		var showName = fail_sc ;
		if(showName == "C2C") showName = "EAC-C2C";
		if(showName == "EAC") showName = "EAC-C2C";
		if(showName == "TSE-1") showName = "TSE1";
		if(showName == "RNAL") showName = "FNAL-RNAL";
		if(showName == "FNAL") showName = "FNAL-RNAL";
		oneJson3(fail_sc,"taiwanSMC/"+showName+".txt",scableColorCode[scableColorCode.length-1],5);
		showOthers(fail_sc);
	}
}

//`var checkS = setTimeout("checkSession();",5000);
function checkSession(){
	 clearTimeout(checkS);
	 $.post("../DataAPI", {func:"1"})
		.done(function( data ) {
		//console.log("0:"+TrimMe(data));
		checkS = setTimeout("checkSession();",5000);
  });
}
var mapElement ;
function testLine(){
	//alert("testline");
	var myline =[];
	myline.push([121.4560743,24.9948708]);
	myline.push([120.6549408,22.2744088]);
	drawMultiLine1("test",myline,"#ffff66",5);

}

function doNextFunc(a,b,c){
	//console.log("point_"+b,Number(a.split(",")[0]),Number(a.split(",")[1]),1,1);
	var d = (b.indexOf("<BR>") >=0 ? "<BR>":" ");
	OneMarker("point_"+b.split(d)[0],Number(a.split(",")[0]),Number(a.split(",")[1]),b,1);
	console.log(a);
	console.log(b);
	console.log(c);
	console.log(c.values_.id);
	console.log(ol.proj.transform([parseFloat(a.split(",")[0]), parseFloat(a.split(",")[1])], 'EPSG:4326','EPSG:3857'));
}
