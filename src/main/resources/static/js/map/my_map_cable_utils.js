//纜線資料及功能
var cableVectorLayers = []; //read from json file
var cableVectorLayersId = []; //圖層Id
var cableShowIdTag = []; //顯示ID
var cableShowNameTag = []; //顯示名稱
var cableShowMapIndex = []; //顯示地圖編號(暫時沒用到)

var cableLandShowIdTag = [];
var clickScable = "";
var nowCableInfoBox;
//var nowCableLandInfoBox;
var start_count = 0 ;
//纜線登陸點資料
var cableMapIndex = 0; //要顯示的地圖索引

//纜線資料

function removeACable(x){
	//cableShowIdTag[cableVectorLayers.length] = idTag;
    //cableShowNameTag[cableVectorLayers.length] = nameTag;
    //cableShowMapIndex[cableVectorLayers.length] = mapindex;
    //cableVectorLayers[cableVectorLayers.length] = getCableVectorLayer(jsonsource,dataarrayindex,vectorStyles[styleindex],cableVectorLayers.length);
	for (var i=0 ; i<x ; i++){
	mapArray[0].removeLayer(cableVectorLayers[cableVectorLayers.length-1]);
	cableVectorLayers.pop();
	}
	//setTimeout("oneCable(4,3);",2000);
}

function oneCableName(a,b,c,q){
	addCableToVectorArray9(b,0,2,q,a,'Name');
    preCreateCableInfoBoxDiv(2,a);
    setInfoBox(a,c);
	var new_i = cableVectorLayers.length ;
	addCableVectorLayer(0,cableVectorLayers[new_i-1]);
}

function addCableToVectorArray9(jsonsource,mapindex,dataarrayindex,styleindex,idTag,nameTag){
    cableShowIdTag[cableVectorLayers.length] = idTag;
    cableShowNameTag[cableVectorLayers.length] = nameTag;
    cableShowMapIndex[cableVectorLayers.length] = mapindex;
    cableVectorLayers[cableVectorLayers.length] = getCableVectorLayer(jsonsource,dataarrayindex,vectorStyles[styleindex],cableVectorLayers.length);
}

function oneCable(i,q){
	addCableToVectorArray(cableJsonFiles[i].split(",")[0],0,2,q,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0],'Name');
    preCreateCableInfoBoxDiv(2,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0]);
    setInfoBox(cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0],cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0]);
	var new_i = cableVectorLayers.length ;
	addCableVectorLayer(0,cableVectorLayers[new_i-1]);
}

function prepareCableVectorArray_Color(q){
    for(i=0;i<cableJsonFiles.length;i++) {
        //addToVectorArray(cableJsonFiles[i].split(",")[0],0,2,0,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0],'Name');
        addCableToVectorArray(cableJsonFiles[i].split(",")[0],0,2,q,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0],'Name');
        //preCreateCountyInfoBoxDiv(2,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0]);
        preCreateCableInfoBoxDiv(2,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0]);
        setInfoBox(cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0],cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0]);
    }

    //加入到地圖
    for(i=0;i<cableVectorLayers.length;i++){
        try {
            addCableVectorLayer(cableMapIndex,cableVectorLayers[i]);
        } catch (error) {

        }
    }

    //加入登陸點
    for(i=0;i<cableLandGeometry.length;i++){
        try {
            preCreateCableLandInfoBoxDiv(2,cableLandGeometry[i].split(",")[3],cableLandGeometry[i].split(",")[0],cableLandGeometry[i].split(",")[1]);
            cableLandShowIdTag[cableLandShowIdTag.length] = cableLandGeometry[i].split(",")[3];
            setInfoBox(cableLandGeometry[i].split(",")[3],cableLandGeometry[i].split(",")[2]);
        } catch (error) {

        }
    }

    //sample
    //showCableByArray(['TPE','C2C']);
    //showCableLandByArray(['cl00','cl01']);
}

function prepareCableVectorArray(){
    for(i=0;i<cableJsonFiles.length;i++) {
        //addToVectorArray(cableJsonFiles[i].split(",")[0],0,2,0,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0],'Name');
        addCableToVectorArray(cableJsonFiles[i].split(",")[0],0,2,0,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0],'Name');
        //preCreateCountyInfoBoxDiv(2,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0]);
        preCreateCableInfoBoxDiv(2,cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0]);
        setInfoBox(cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0],cableJsonFiles[i].split(",")[0].split("/")[1].split(".")[0]);
    }

    //加入到地圖
    for(i=0;i<cableVectorLayers.length;i++){
        try {
            addCableVectorLayer(cableMapIndex,cableVectorLayers[i]);
        } catch (error) {

        }
    }

    //加入登陸點
    for(i=0;i<cableLandGeometry.length;i++){
        try {
            preCreateCableLandInfoBoxDiv(2,cableLandGeometry[i].split(",")[3],cableLandGeometry[i].split(",")[0],cableLandGeometry[i].split(",")[1]);
            cableLandShowIdTag[cableLandShowIdTag.length] = cableLandGeometry[i].split(",")[3];
            setInfoBox(cableLandGeometry[i].split(",")[3],cableLandGeometry[i].split(",")[2]);
        } catch (error) {

        }
    }

    //sample
    //showCableByArray(['TPE','C2C']);
    //showCableLandByArray(['cl00','cl01']);
}

function prepareLandingOnlyVectorArray(){
    for(i=0;i<cableLandGeometry.length;i++){
        try {
            preCreateCableLandInfoBoxDiv(2,cableLandGeometry[i].split(",")[3],cableLandGeometry[i].split(",")[0],cableLandGeometry[i].split(",")[1]);
            cableLandShowIdTag[cableLandShowIdTag.length] = cableLandGeometry[i].split(",")[3];
            setInfoBox(cableLandGeometry[i].split(",")[3],cableLandGeometry[i].split(",")[2]);
        } catch (error) {

        }
    }
}

function ShowLandingOnlyVectorArray(){
    for(i=0;i<cableLandGeometry.length;i++){
        try {
            preCreateCableLandInfoBoxDiv1(2,cableLandGeometry[i].split(",")[3],cableLandGeometry[i].split(",")[0],cableLandGeometry[i].split(",")[1]);
            cableLandShowIdTag[cableLandShowIdTag.length] = cableLandGeometry[i].split(",")[3];
            setInfoBox(cableLandGeometry[i].split(",")[3],cableLandGeometry[i].split(",")[2]);
        } catch (error) {

        }
    }
}

function addCableToVectorArray(jsonsource,mapindex,dataarrayindex,styleindex,idTag,nameTag){
    cableShowIdTag[cableVectorLayers.length] = idTag;
    cableShowNameTag[cableVectorLayers.length] = nameTag;
    cableShowMapIndex[cableVectorLayers.length] = mapindex;
    cableVectorLayers[cableVectorLayers.length] = getCableVectorLayer(jsonsource,dataarrayindex,vectorStyles[styleindex],cableVectorLayers.length);
}

function getCableOverlayDivObject(popid){
    var resultString = '';
    resultString += '<div id="' + popid + '-popup" class="ol-popup">';
    resultString += '<div id="' + popid + '-content" style="height:30px;"></div>';
    resultString += '</div>';

    return resultString;
}

function getCableLandOverlayDivObject(popid){
    var resultString = '';
    resultString += '<div id="' + popid + '-popup">';
    resultString += '<div id="' + popid + '-content"></div>';
    resultString += '</div>';

    return resultString;
}

function cableMoveTopDiv(mId,tId){
    //alert('0:' + tId);
    for(i=0;i<cableLandShowIdTag.length;i++){
        if(cableLandShowIdTag[i] === tId){
          $('#' + cableLandShowIdTag[i] + '-popup').css("z-index", 100);
          //alert('1:' + tId);
        }else{
          $('#' + cableLandShowIdTag[i] + '-popup').css("z-index", 0);
        }
    }
}

function preCreateCableLandInfoBoxDiv(mId,tId,tLon,tLat){
    $('#map').append(getCableLandOverlayDivObject(tId));
    $('#' + tId + '-popup').click(function(){
        cableMoveTopDiv(0,tId);
    });
    document.getElementById(tId + '-popup').style.display = 'none';
    mapArray[0].addOverlay(getCableOverlayObject(tId,tLon,tLat));
}

function preCreateCableLandInfoBoxDiv1(mId,tId,tLon,tLat){
    $('#map').append(getCableLandOverlayDivObject(tId));
    $('#' + tId + '-popup').click(function(){
        cableMoveTopDiv(0,tId);
    });
    document.getElementById(tId + '-popup').style.display = 'none';
    mapArray[0].addOverlay(getCableOverlayObject(tId,tLon,tLat));
}

function preCreateCableInfoBoxDiv(mId,tId){
    $('#map').append(getCableOverlayDivObject(tId));
    $('#' + tId + '-popup').click(function(){
        cableMoveTopDiv(0,tId);
    });
    document.getElementById(tId + '-popup').style.display = 'none';
    //預設擺在一個固定位置
    mapArray[0].addOverlay(getCableOverlayObject(tId,123.0,23.05));
}

function getCableOverlayObject(popid,poplon,poplat){
    //alert(poplon + "+" + poplat);
    var resultOverlay;

    resultOverlay = new ol.Overlay(/** @type {olx.OverlayOptions} */ ({
      id: popid + '-popup',
      element: document.getElementById(popid + '-popup'),
      autoPan: false,
      autoPanAnimation: {
        duration: 250
      }
    }));

    /*
    closer.onclick = function() {
      popOverlay.setPosition(undefined);
      closer.blur();
      return false;
    };

	if(poplon < 0){ 
		console.log(parseFloat(poplon)+"/"+parseFloat(poplat));
		console.log(ol.proj.transform([parseFloat(poplon), parseFloat(poplat)], 'EPSG:4326','EPSG:3857'));
		console.log(parseFloat(-180)+"/"+parseFloat(poplat));
		console.log(ol.proj.transform([parseFloat(-180), parseFloat(poplat)], 'EPSG:4326','EPSG:3857'));
	}
	    */
    //resultOverlay.setPosition(ol.proj.fromLonLat([parseFloat(poplon), parseFloat(poplat)]));
    var newPosition = ol.proj.transform([parseFloat(poplon), parseFloat(poplat)], 'EPSG:4326','EPSG:3857');
	if(poplon < -15){ 
		newPosition[0] = newPosition[0]+Allnums;
	}
	resultOverlay.setPosition(newPosition);
	//resultOverlay.setPosition(ol.proj.transform([parseFloat(poplon), parseFloat(poplat)], 'EPSG:4326','EPSG:3857'));

    return resultOverlay;
  }

function getCableVectorLayer(jsonFile,dataarrayindex,vStyle,idPosition){
    var resultVectorLayer = new ol.layer.Vector({ //初始化向量圖層
        source: new ol.source.Vector({
        format: new ol.format.GeoJSON(),
        url: jsonFile //讀取json檔案
        }),
        style: function(feature, resolution) {
        //vStyle.getText().setText(resolution < 5000 ? feature.get('countyname') : '');  //放大到1:5000
        //vStyle.getText().setText("高鐵軌道");
        return [vStyle];
        }
    });

    var resultVectorSource = resultVectorLayer.getSource();
    resultVectorSource.on('change', function(e) {
        if (resultVectorSource.getState() == 'ready') {
            if(isNaN(resultVectorSource.getFeatures()[0].get(cableShowNameTag[idPosition]))){
                //cableVectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(cableShowNameTag[idPosition]);
                cableVectorLayersId[idPosition] = cableShowIdTag[idPosition];
            }else{
                //cableVectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(cableShowNameTag[idPosition]).toString();
                cableVectorLayersId[idPosition] = "-1";
            }
            /*
            if(isNaN(resultVectorSource.getFeatures()[0].get(showIdTag[idPosition]))){
                cableVectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(cableShowIdTag[idPosition]);
            }else{
                cableVectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(cableShowIdTag[idPosition]).toString();
            }
            */
        }
    });

    return resultVectorLayer;
}

function addCableVectorLayer(tIndex,tLayer){
    mapArray[tIndex].addLayer(tLayer);
	
    var inVectorSource = tLayer.getSource();
    inVectorSource.on('change', function(e) {
        if (inVectorSource.getState() == 'ready') {
			//console.log(tLayer);
			start_count += 1 ;
			//console.log(start_count);
			//console.log(tLayer.getId());
            /*
            if(debugMode){
                if(getVectorLayerPosition(tLayer) != -1){
                    //加入到顯示選項(測試時)
                    //var checkBtn1 = $('<input type="checkbox" name="countyselect[]" value="' + showIdTag[getVectorLayerPosition(tLayer)] + '">'  + inVectorSource.getFeatures()[0].get(showNameTag[getVectorLayerPosition(tLayer)]) + ":" + showIdTag[getVectorLayerPosition(tLayer)] + '</input>');
                    //checkBtn1.appendTo('#selectoptions');
                }
            }
            */
        }
    });
}

function clearCableVecLayersData(tIndex){
    cableVectorLayers.length = 0;
    cableVectorLayersId.length = 0;
    cableShowIdTag.length = 0;
    cableShowNameTag.length = 0;
    cableShowMapIndex.length = 0;
}

function getCableVectorLayerPosition(targetLayer){
    for(var i = 0;i<cableVectorLayers.length;i++){
        if( targetLayer == cableVectorLayers[i]) return i;
    }
    return -1;
}

var displayCableFeatureInfo = function(pixel,tIndex,mCoordinate) {
    var myfeature;
    var mylayer;
    var myFeaturePosition = -1;
    mapArray[tIndex].forEachFeatureAtPixel(pixel, function(feature, layer) {
        myfeature = feature;
        mylayer = layer;
    });

    if(getCableVectorLayerPosition(mylayer) !== -1){
        myFeaturePosition = getCableVectorLayerPosition(mylayer);
    }

    if (myfeature) {
        if(myFeaturePosition != -1){
            try{
                if(nowCableInfoBox.length > 0){
                    document.getElementById(nowCableInfoBox).style.display = 'none';
                    nowCableInfoBox = '';
                }
            } catch (error) {
                //alert(error);
            }
            //info.innerHTML = cableShowIdTag[myFeaturePosition]  + '-popup' + ':' + myfeature.get(cableShowNameTag[myFeaturePosition]);
            try{
                mapArray[tIndex].getOverlayById(cableShowIdTag[myFeaturePosition] + '-popup').setPosition(mCoordinate);
                document.getElementById(cableShowIdTag[myFeaturePosition] + '-popup').style.display = 'block';
                nowCableInfoBox = cableShowIdTag[myFeaturePosition] + '-popup';
				clickScable = document.getElementById(cableShowIdTag[myFeaturePosition] + '-content').innerHTML;
            } catch (error) {
            }
        }else{
            try{
                if(nowCableInfoBox.length > 0){
                    document.getElementById(nowCableInfoBox).style.display = 'none';
                    nowCableInfoBox = '';
                }
            } catch (error) {
                //alert(error);
            }
        }
    } else {
        try{
            document.getElementById(nowCableInfoBox).style.display = 'none';
            nowCableInfoBox = '';
        } catch (error) {

        }
    }
};

function getCableIdPosition(tId){
    for(var i = 0;i<cableVectorLayers.length;i++){
        //if( targetLayer === cableVectorLayers[i]) return i;
        if(cableJsonFiles[i].split(",")[0].split("/")[1] === tId + ".json") return i;
    }
    return -1;
}

function getCableLandIdPosition(tId){
    for(var i = 0;i<cableLandGeometry.length;i++){
        //if( targetLayer === cableVectorLayers[i]) return i;
        if(cableLandGeometry[i].split(",")[3] === tId) return i;
    }
    return -1;
}

//顯示控制功能
//隱藏所有Cable圖層
function hideAllCableLayers(mId){
    for(i = 0;i < cableVectorLayers.length;i++) {
        cableVectorLayers[i].setVisible(false);
    }
}

//隱藏所有cable顯示內容
function hideAllCableInfoBox(){
    for(i=0;i<cableShowIdTag.length;i++){
        try{
            document.getElementById(cableShowIdTag[i] + '-popup').style.display = 'none';
        } catch (error) {
        }
    }
}

function showCableByArray(sArray){
    hideAllCableLayers(0);
    try{
        if(sArray.length>0){
            for(i = 0;i < sArray.length;i++) {
                if(getCableIdPosition(sArray[i]) != -1){
                    cableVectorLayers[getCableIdPosition(sArray[i])].setVisible(true);
                }
            }
        }
    } catch (error) {
    }
}

//隱藏所有CableLand顯示內容
function hideAllCableLandInfoBox(){
    cableLandShowIdTag.length = 0;
    for(i=0;i<cableLandGeometry.length;i++){
        try{
            document.getElementById(cableLandGeometry[i].split(",")[3] + '-popup').style.display = 'none';
        } catch (error) {
        }
    }
}

function showCableLandByArray(sArray){
    hideAllCableLandInfoBox();
    try{
        if(sArray.length>0){
            for(i = 0;i < sArray.length;i++) {
                //if(getCableLandIdPosition(sArray[i]) != -1){
                    document.getElementById(sArray[i] + '-popup').style.display = 'block';
                    cableLandShowIdTag[cableLandShowIdTag.length] = sArray[i];
               // }
			   //console.log($("#img_"+sArray[i]).height());
            }
        }
    } catch (error) {
    }
}

var port=1,item=1;
function showLandingContents_new(x,y,z,colors){
	var pic_font = "12" ;
	var pic_size = "60" ;
	//var my_infoContents = "<div style='border-color:#aaaaee;border-width:3px;border-style:solid;margin-top:-24px;margin-left:-12px;font-size:"+pic_font+"px;'>";
	//var my_infoContents = "<div style='margin-top:-8px;margin-left:-8px;border-color:#aaaaee;border-width:3px;border-style:solid;font-size:"+pic_font+"px;'>";
	var my_infoContents = showLandingContents_size(x,y,z,colors,14);
	return my_infoContents ;
}

function showLandingContents(x,y,z,colors){
	var pic_font = "12" ;
	var pic_size = "60" ;
	//var my_infoContents = "<div style='border-color:#aaaaee;border-width:3px;border-style:solid;margin-top:-24px;margin-left:-12px;font-size:"+pic_font+"px;'>";
	//var my_infoContents = "<div style='margin-top:-8px;margin-left:-8px;border-color:#aaaaee;border-width:3px;border-style:solid;font-size:"+pic_font+"px;'>";
	var my_infoContents = "<div id='area_"+x+"' style='margin-top:-18px;margin-left:-18px;font-size:"+pic_font+"px;'>";
	my_infoContents += "<span style='"+showTextColor(colors)+"'";
	my_infoContents += "onclick=\"showIbox_full('station/info.jsp?id="+x+"','','');\"";
	//my_infoContents += "><img src='images/"+y+".png' width='"+pic_size+"px;' height='"+pic_size+"px;' style='margin-left:-18px;margin-top:-30px;'>"+adjust_location(x,z);
	//my_infoContents += "><img src='images/"+y+"' width='"+pic_size+"px;' height='"+pic_size+"px;'>"+adjust_location(x,z);
	//my_infoContents += ">"+adjust_location(x,z);
	my_infoContents += "><img id='img_"+x+"' src='images/"+y+".png' onclick=\"showIbox_full('station/info.jsp?port="+port+"&item="+item+"&station="+z+"','',''); \">"+adjust_location(x,z);
	my_infoContents += "</span></div>";
	return my_infoContents ;
}

function showLandingContents_size(x,y,z,colors,a){
	var pic_font = "12" ;
	var pic_size = "60" ;
	//var my_infoContents = "<div style='border-color:#aaaaee;border-width:3px;border-style:solid;margin-top:-24px;margin-left:-12px;font-size:"+pic_font+"px;'>";
	//var my_infoContents = "<div style='margin-top:-8px;margin-left:-8px;border-color:#aaaaee;border-width:3px;border-style:solid;font-size:"+pic_font+"px;'>";
	var my_infoContents = "<div id='area_"+x+"' style='margin-top:-"+(a/2)+"px;margin-left:-"+(a/2)+"px;font-size:"+pic_font+"px;'>";
	my_infoContents += "<span style='"+showTextColor(colors)+"'";
	my_infoContents += "onclick=\"showIbox_full('station/info.jsp?id="+x+"','','');\"";
	//my_infoContents += "><img src='images/"+y+".png' width='"+pic_size+"px;' height='"+pic_size+"px;' style='margin-left:-18px;margin-top:-30px;'>"+adjust_location(x,z);
	//my_infoContents += "><img src='images/"+y+"' width='"+pic_size+"px;' height='"+pic_size+"px;'>"+adjust_location(x,z);
	//my_infoContents += ">"+adjust_location(x,z);
	my_infoContents += "><img id='img_"+x+"' src='images/"+y+".png' onclick=\"showIbox_full('station/info.jsp?port="+port+"&item="+item+"&station="+z+"','',''); \">"+adjust_location(x,z);
	my_infoContents += "</span></div>";
	//adjust_position(x);
	return my_infoContents ;
}


function showTextColor(y){
	var ret_color = "color:"+y;
	if(y == 'red') ret_color = "color:red;"
	if(y == 'yl') ret_color = "color:yellow;"
	if(y == 'og') ret_color = "color:orange;"
	return ret_color ;
}

function adjust_location(x,z){
	/*var ret_adjust = "";
	if(x == 'cl06' || x == 'cl13' || x == 'cl11'){
		ret_adjust = "<div style='margin-top:-50px;margin-left:20px;'><b>"+z+"</b></div>";
	}else if(x == 'cl05'){
		ret_adjust = "<div style='margin-top:-10px;margin-left:20px;'><b>"+z+"</b></div>";
	}else if(x == 'cl08' || x == 'cl17'){
		ret_adjust = "<div style='margin-top:-35px;margin-left:-45px;'><b>"+z+"</b></div>";
	}else if(x == 'cl10'){
		ret_adjust = "<div style='margin-top:-15px;margin-left:-100px;'><b>"+z+"</b></div>";
	}else if(x == 'cl09'){
		ret_adjust = "<div style='margin-top:-10px;margin-left:20px;'><b>"+z+"</b></div>";
	}else{
		*/
		ret_adjust = "<div><b>"+z+"</b></div>";
	//}
	return ret_adjust;
}

function prepareVectorArray1(){
  //移除所有marker,以地圖為單位
  clearAllInfoBox(0);
  clearAllInfoBox(1);
}

function show_Marker(){
		//prepareVectorArray1();
		clear_Marker();
		var stationlist = [];
		var getLandingNOw = [];
		var showLandingInfo = [];
		// var getLandingNOw = showLandingStation.split("<w>");
        var getLandingNOw = showLandingStation;
		cableLandShowIdTag = [];
		//alert(getLandingNOw.length);
		for (var i=0;i < getLandingNOw.length ; i++){
		preCreateCableLandInfoBoxDiv1(2,getLandingNOw[i].split(",")[0],getLandingNOw[i].split(",")[3],getLandingNOw[i].split(",")[4]);
        cableLandShowIdTag[i] = getLandingNOw[i].split(",")[0];
        //setInfoBox(getLandingNOw[i].split(",")[0],getLandingNOw[i].split(",")[0]);
		stationlist[i]=getLandingNOw[i].split(",")[0];
		showLandingInfo[i]=showLandingContents(getLandingNOw[i].split(",")[0],getLandingNOw[i].split(",")[1],getLandingNOw[i].split(",")[2],getLandingNOw[i].split(",")[5]);
		setInfoBox_popup(stationlist[i],showLandingInfo[i]);
		// console.log(stationlist[i]);
		}
		showCableLandByArray(stationlist);
}

function clear_Marker(){
		
		var stationlist = [];
		var getLandingNOw = [];
		var showLandingInfo = [];
		//alert("*"+showLandingStation+"*");
		if(showLandingStation == ""){
		}else{
		// var getLandingNOw = showLandingStation.split("<w>");
        var getLandingNOw = showLandingStation;
		//alert(getLandingNOw.length);
		for (var i=0;i < getLandingNOw.length ; i++){
			stationlist[i]=getLandingNOw[i].split(",")[0];
			showOrHideInfoBox(-1,stationlist[i],false);
			$("#"+stationlist[i]).remove();
			//console.log(stationlist[i]);
		}
		//showCableLandByArray(stationlist);
		}
}

function OneMarker(a,b,c,d,e){
	preCreateCableLandInfoBoxDiv1(2,a,b,c);
	setInfoBox_popup(a,showLandingContents_size(a,e,d,'red',0));
	ShowHide_OneMarker(a,true);
}

function clearOneMarker(a){
	$("#"+a+"-popup").remove();
}

function checkOneMarker(a){
	return document.getElementById(a+"-popup");
}

function Toggle_OneMarker(a){
	$("#"+a+"-popup").toggle();
}

function ShowHide_OneMarker(a,b){
	if(b){
		//adjust_position(a);
		$("#"+a+"-popup").show();
		setTimeout("adjust_position('"+a+"');",50);
	}else{
		$("#"+a+"-popup").hide();
	}
}

function adjust_position(a){
	var h = $("#img_"+a).height()/2;
	var w = $("#img_"+a).width()/2;
	var ah = "-"+h.toString()+"px";
	var aw = "-"+w.toString()+"px";
	$("#area_"+a).css('margin-top',ah);
	$("#area_"+a).css('margin-left',aw);
}

