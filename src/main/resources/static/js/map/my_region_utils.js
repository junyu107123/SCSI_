//兩點座標畫線和功能(測試)
var regionVectorLayers = [];
var regionVectorLayersId = []; //圖層Id
var regionShowIdTag = []; //顯示ID
var regionShowNameTag = []; //顯示名稱
var regionShowMapIndex = []; //顯示地圖編號(暫時沒用到)

var nowRegionInfoBox;

var regionTopIndex = 0;

var regionVectorStyles = []; //圖層樣式

var regionlayer = new ol.layer.Vector({
	source: new ol.source.Vector(), 
	style: function(feature) { 
		var color = feature.get("__color");
		return new ol.style.Style({ 
			fill: new ol.style.Fill({
				color: color
				//color: [rColor,gColor,bColor,aColor]
			}),
		}); 
    }
});

function toRegionSectorGeometry(mypoints){
	
	for(var i=0;i<mypoints.length;i++){ 
		var newCoord= ol.proj.transform(mypoints[i],'EPSG:4326','EPSG:3857');       
            mypoints[i] = newCoord; 
    } 
	
	var polygon = new ol.geom.Polygon([mypoints]);
	return polygon;	
}

function calRegion(mypoints){
	//console.log(mypoints);
	var geom = toRegionSectorGeometry(mypoints);
	if (!geom) return feature;
	//console.log(geom.getArea().toString());
	return (Math.round(geom.getArea() /1.18 * 100) / 100);
}

function addRegionFunction(myfid,mypoints,mycolor){
	
	//console.log();
	
	var geom = toRegionSectorGeometry(mypoints);
	if (!geom) return feature;
	//console.log(geom.getArea().toString());
	//console.log("1->"+(Math.round(geom.getArea() /1.18 * 100) / 100) + ' ' + '㎡');
	//area = Math.abs(wgs84Sphere.geodesicArea(mypoints));
	//console.log("2->"+(Math.round(area /1.18 * 100) / 100) + ' ' + '㎡');
	feature = new ol.Feature();
	feature.setId(myfid); 
	feature.set("__color", mycolor, true); 
    feature.setGeometry(geom); 
    regionlayer.getSource().addFeature(feature); 
}

/**
* @params {number} ID
*/
function removeRegionFunction(myfid){
	try {
		regionlayer.getSource().removeFeature(regionlayer.getSource().getFeatureById(myfid));
	}catch (e) {
	}
}

function clearRegionFunction(){
	try {
		regionlayer.getSource().clear();
	}catch (e) {
	}
}

function initRegionLayer(){
	try {
		mapArray[0].addLayer(regionlayer);
	}catch (e) {
	}
}

function getRegionOverlayDivObject(popid){
    var resultString = '';
    resultString += '<div id="' + popid + '-popup" class="ol-popup">';
    resultString += '<a href="#" id="' + popid + '-popup-closer" class="ol-popup-closer"></a>';
    resultString += '<div id="' + popid + '-content" style="height:30px;"></div>';
    resultString += '</div>';
  
    return resultString;
}

function preCreateRegionInfoBoxDiv(mId,tId,myLongitude,myLatitude){
	//tId = myId.toString();
    $('#map').append(getRegionOverlayDivObject(tId));
    
    $('#' + tId + '-popup').click(function(){
        console.log("被點擊:" + tId);
		regionMoveTopDiv(0,tId);
    });
    
    $('#' + tId + '-popup-closer').click(function(){
        document.getElementById(tId + '-popup').style.display = 'none';
    });
	
    //document.getElementById(tId + '-popup').style.display = 'none';
    //預設擺在一個固定位置
    mapArray[0].addOverlay(getRegionOverlayObject(tId,myLongitude,myLatitude));
}

function getRegionOverlayObject(popid,poplon,poplat){
    //console.log("取得Overlay:" + popid);
    var resultOverlay;
  
    resultOverlay = new ol.Overlay(/** @type {olx.OverlayOptions} */ ({
      id: popid + '-popup',
      element: document.getElementById(popid + '-popup'),
      autoPan: false,
      autoPanAnimation: {
        duration: 250
      }
    }));
  
    //resultOverlay.setPosition(ol.proj.fromLonLat([parseFloat(poplon), parseFloat(poplat)]));
    resultOverlay.setPosition(ol.proj.transform([parseFloat(poplon), parseFloat(poplat)], 'EPSG:4326','EPSG:3857'));
  
    return resultOverlay;
}

var displayRegionFeatureInfo = function(pixel,tIndex,mCoordinate) {
    var myfeature;
    var mylayer;
    var myFeaturePosition = -1;
	
	mapArray[tIndex].forEachFeatureAtPixel(pixel, function(feature, layer) {
        myfeature = feature;
        mylayer = layer;
    });

    if (myfeature) {
        console.log("移動到了:" + myfeature.getId());
		//regionMoveTopDiv(0,myfeature.getId().toString());
    }else{
		//onsole.log("no:" + myfeature.getId());
		//document.getElementById(myfeature.getId().toString() + '-popup').style.display = 'none';
    }
};

function regionMoveTopDiv(mId,tId){
    console.log('click:0:' + tId);
    console.log('top now:' + regionTopIndex);
    regionTopIndex++;
	$('#' + tId + '-popup').css("z-index", regionTopIndex);
}

//測試====
$('#draregion').click(function() {
	mapArray[0].getView().setZoom(14);
    mapArray[0].getView().setCenter(ol.proj.transform([121.543767,25.041629], 'EPSG:4326','EPSG:3857'));
	
	initRegionLayer();	
});

//添加測試
$('#addregions').click(function() {

	var points = new Array();
	points.push([121.549643,25.063718]);
	points.push([121.5448626,25.0417843]);
	points.push([121.5579885,25.0410987]);
	points.push([121.5439248,25.0607843]);
	points.push([121.549643,25.063718]);

	var points2 = new Array();
	points2.push([121.5651802,25.0409916]);
	points2.push([121.576419,25.041007]);
	points2.push([121.582961,25.045248]);
	points2.push([121.549643,25.063718]);
	points2.push([121.5651802,25.0409916]);
	
	addRegionFunction('1234567890',points,'#C14242B0');
	preCreateRegionInfoBoxDiv(0,'1234567890',121.5579885,25.0410987);
	setInfoBox('1234567890','測試訊息1');
	
	addRegionFunction('2345678901',points2,'#C1424250');
	preCreateRegionInfoBoxDiv(0,'2345678901',121.5651802,25.0409916);
	setInfoBox('2345678901','測試訊息2');
});
var wgs84Sphere = new ol.Sphere(6378137);
function showregion(){
	//mapArray[0].getView().setZoom(12);
   // mapArray[0].getView().setCenter(ol.proj.transform([121.543767,25.041629], 'EPSG:4326','EPSG:3857'));
   //var testLng = "[121.549643,25.063718],[121.5439248,25.0607843],[121.540767,25.050629],[121.5448626,25.0417843],[121.5579885,25.0410987],[121.549643,25.063718]";
   var testLng =[[[121.549643,25.063718],[121.5439248,25.0607843],[121.540767,25.050629],[121.5448626,25.0417843],[121.5579885,25.0410987],[121.549643,25.063718]],[[121.514688,25.027221],[121.514693,25.027212],[121.514711,25.027225],[121.514686,25.027258],[121.514673,25.027246],[121.514688,25.027221],[121.514688,25.027221]]];
	//alert(testLng.length);
	//var points = new Array();
	for (var j=0 ; j< testLng.length ; j++){
		//points.push(testLng[j]);
		draw_region("xyz"+j,testLng[j],"#C14242B0");
	}

	/*
	points.push([121.549643,25.063718]);
	points.push([121.5439248,25.0607843]);
		points.push([121.540767,25.050629]);
	points.push([121.5448626,25.0417843]);
		points.push([121.5579885,25.0410987]);
	points.push([121.549643,25.063718]);
	*/
	//alert(testLng);
	//var points = testLng.split(",");
	//draw_region("xyz",points,"#C14242B0");
}

function draw_region(x,y,z){
	//id : x ,,y: points ,, z:color
	// x=1234 , points is array , z = #C14242B0 
	addRegionFunction(x,y,z);
}

//移除測試
$('#removeregion').click(function() {
	removeRegionFunction($("#regionid").val());
});

//清除測試
$('#clearregion').click(function() {
	clearRegionFunction();
});