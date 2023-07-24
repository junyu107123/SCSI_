var OtherVectorLayers = []; //read from json file
var OtherVectorLayersId = []; //圖層Id
var OtherShowIdTag = []; //顯示ID
var OtherShowNameTag = []; //顯示名稱
var OtherShowMapIndex = []; //顯示地圖編號(暫時沒用到)
//var styleColors = [];
var source_x = [[7.199969135414307, 55.58982013593325], [7.650068816559269, 55.58970711313168], [8.329168335478954, 55.75165023178093]];

function drawMultiLine2(line_id,points,colors,_width){

var sources =  points;

var polygon = new ol.geom.LineString(sources);
//var polygon = new ol.geom.Polygon(sources);
var feature = new ol.Feature({
	geometry : polygon.transform('EPSG:4326', 'EPSG:3857'),
	name: line_id
	}
	);
//var feature = new ol.Feature(polygon.transform('EPSG:4326', 'EPSG:3857'));
var style = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: colors,
            width: _width,
            //lineDash: [1, 2, 3, 4, 5, 6],
        })
    })
    feature.setStyle(style);
	feature.setId(line_id);
// Create vector source and the feature to it.
var vectorSource = new ol.source.Vector();
vectorSource.addFeature(feature);

// Create vector layer attached to the vector source.
var vectorLayer = new ol.layer.Vector({
  source: vectorSource
});
var c = OtherVectorLayers.length;
OtherVectorLayers[c] = vectorLayer;
OtherVectorLayersId[c] = line_id ;
// Add the vector layer to the map.
mapArray[0].addLayer(vectorLayer);

}

function drawMultiLine1(line_id,points,colors,_width){

var sources =  points;

var polygon = new ol.geom.LineString(sources);
//var polygon = new ol.geom.Polygon(sources);

var feature = new ol.Feature({
	geometry : polygon.transform('EPSG:4326', 'EPSG:3857'),
	name: line_id
	}
	);
var style = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: colors,
            width: _width,
            lineDash: [1, 2, 3, 4, 5, 6],
        })
    })
    feature.setStyle(style);
	feature.setId(line_id);
// Create vector source and the feature to it.
var vectorSource = new ol.source.Vector();
vectorSource.addFeature(feature);

// Create vector layer attached to the vector source.
var vectorLayer = new ol.layer.Vector({
  source: vectorSource
});
var c = OtherVectorLayers.length;
OtherVectorLayers[c] = vectorLayer;
OtherVectorLayersId[c] = line_id ;
// Add the vector layer to the map.
mapArray[0].addLayer(vectorLayer);

}

function drawMultiLine(line_id,points,colors){

var sources =  points;

var polygon = new ol.geom.LineString(sources);
//var polygon = new ol.geom.Polygon(sources);

var feature = new ol.Feature({
	geometry : polygon.transform('EPSG:4326', 'EPSG:3857'),
	name: line_id
	}
	);
var style = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: colors,
            width: 5,
            lineDash: [1, 2, 3, 4, 5, 6],
        })
    })
    feature.setStyle(style);
	feature.setId(line_id);
// Create vector source and the feature to it.
var vectorSource = new ol.source.Vector();
vectorSource.addFeature(feature);

// Create vector layer attached to the vector source.
var vectorLayer = new ol.layer.Vector({
  source: vectorSource
});
var c = OtherVectorLayers.length;
OtherVectorLayers[c] = vectorLayer;
OtherVectorLayersId[c] = line_id ;
// Add the vector layer to the map.
mapArray[0].addLayer(vectorLayer);
console.log(vectorLayer);
}

function oneJson2(_id,x,_color,_width){
	//var a = getVectorStyle1(141,235,0,0.6,_color,_color,_width);
	var a = getVectorStyle2(255,91,51,0.6,_color,_color,_width);
	var resultVectorLayer = new ol.layer.Vector({ //初始化向量圖曾
        source: new ol.source.Vector({
        format: new ol.format.GeoJSON(),
        url: x //讀取json檔案
        })
    });
	var c = OtherVectorLayers.length;
	//console.log(c);
	//console.log(a);
	//console.log(resultVectorLayer);
	OtherVectorLayers[c]=resultVectorLayer;
	OtherVectorLayersId[c] = _id ;
	OtherVectorLayers[c].setStyle(a);
}

function oneJson3(_id,x,_color,_width){
	//var a = getVectorStyle1(141,235,0,0.6,_color,_color,_width);
	var a = getVectorStyle3(255,91,51,0.6,_color,_color,_width);
	var resultVectorLayer = new ol.layer.Vector({ //初始化向量圖曾
        source: new ol.source.Vector({
        format: new ol.format.GeoJSON(),
        url: x //讀取json檔案
        })
    });
	var c = OtherVectorLayers.length;
	//console.log(c);
	//console.log(a);
	//console.log(resultVectorLayer);
	OtherVectorLayers[c]=resultVectorLayer;
	OtherVectorLayersId[c] = _id ;
	OtherVectorLayers[c].setStyle(a);
}

function oneJson4(_id,x,_color,_width){
	//var a = getVectorStyle1(141,235,0,0.6,_color,_color,_width);
	var a = getVectorStyle3(255,91,51,0.6,_color,_color,_width);
	var resultVectorLayer = new ol.layer.Vector({ //初始化向量圖曾
        source: new ol.source.Vector({
        format: new ol.format.GeoJSON(),
        url: x //讀取json檔案
        })
    });
	resultVectorLayer.setStyle(a);
	return resultVectorLayer;
}

function oneJson1(_id,x,_color,_width){
	//var a = getVectorStyle1(141,235,0,0.6,_color,_color,_width);
	var a = getVectorStyle1(255,91,51,0.6,_color,_color,_width);
	var resultVectorLayer = new ol.layer.Vector({ //初始化向量圖曾
        source: new ol.source.Vector({
        format: new ol.format.GeoJSON(),
        url: x //讀取json檔案
        })
    });
	var c = OtherVectorLayers.length;
	//console.log(c);
	//console.log(a);
	OtherVectorLayers[c]=resultVectorLayer;
	OtherVectorLayersId[c] = _id ;
	OtherVectorLayers[c].setStyle(a);
}

function oneJson(_id,x){
	var a = getVectorStyle1(141,235,0,0.6,'#8cea00','#33FF35',5);
	var resultVectorLayer = new ol.layer.Vector({ //初始化向量圖曾
        source: new ol.source.Vector({
        format: new ol.format.GeoJSON(),
        url: x //讀取json檔案
        })
    });
	var c = OtherVectorLayers.length;
	//console.log(c);
	OtherVectorLayers[c]=resultVectorLayer;
	OtherVectorLayersId[c] = _id ;
	OtherVectorLayers[c].setStyle(a);
}

function showOrnoshow(x,q){
	var tmpList = q.split("<w>");
	for(var y=0 ; y< tmpList.length-1 ; y++){
		var i = OtherVectorLayersId.indexOf(tmpList[y]);
		if(x == 0) OtherVectorLayers[i].setVisible(false);
		if(x == 1) OtherVectorLayers[i].setVisible(true);
	}
}

function showOthers(x){
	var i = OtherVectorLayersId.indexOf(x);
	if (i > -1) {
		addVectorLayer(0,OtherVectorLayers[i]);
	}
}

function unshowOthers(x){
	var i = OtherVectorLayersId.indexOf(x);
	if (i > -1) {
		mapArray[0].removeLayer(OtherVectorLayers[i]);
	}
}

function removeJson(x){
	var i = OtherVectorLayersId.indexOf(x);
	if (i > -1) {
		mapArray[0].removeLayer(OtherVectorLayers[i]);
		OtherVectorLayersId.splice(i, 1);
		OtherVectorLayers.splice(i, 1);
	}
}

function removeAllOthers(){
	for (var i=0 ; i< OtherVectorLayers.length ; i++){
		mapArray[0].removeLayer(OtherVectorLayers[i]);
	}
	OtherVectorLayersId=[];
	OtherVectorLayers=[];
}

function findOthersLayers(targetLayer){
    for(var i = 0;i<OtherVectorLayers.length;i++){
        if( targetLayer == OtherVectorLayers[i]) return i;
    }
    return -1;
}

//console.log(OtherVectorLayers);