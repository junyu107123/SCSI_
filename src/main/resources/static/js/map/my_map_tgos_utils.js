//var map,vectorLayer,vectorLayer1;
var map,map1,map2,map3,mapdetail;
var highlightStyleCache = {},highlight;
var mapArray = [map,map1,map2,map3,mapdetail];
var clipLayer;

//debug info
var info = document.getElementById('info');

var cableJsonFiles = [];

cableJsonFiles =["taiwanSMC/APCN2.txt","taiwanSMC/APG.txt","taiwanSMC/EAC-C2C.txt","taiwanSMC/FASTER.txt","taiwanSMC/FNAL-RNAL.txt","taiwanSMC/NCP.txt","taiwanSMC/SMW3.txt","taiwanSMC/TPE.txt","taiwanSMC/TSE1.txt","taiwanSMC/PLCN.txt", "used_scable/TDM2.json","used_scable/TM3-A.json", "used_scable/TK2.json","used_scable/TP2.json","used_scable/TP3.json","used_scable/PK1-B.json","used_scable/PK3-B.json","used_scable/TM1-D.json","used_scable/TM3-B.json","used_scable/TM3-C.json","used_scable/TM3-D.json","used_scable/NB2.json","used_scable/DX.json","used_scable/PK1-A.json","used_scable/PK3-A.json","used_scable/TL1.json","used_scable/TL2.json"];
/*
cableJsonFiles[0] ="taiwanSMC/APCN2.txt";
cableJsonFiles[1] ="taiwanSMC/APG.txt";
cableJsonFiles[2] ="taiwanSMC/EAC-C2C.txt";
cableJsonFiles[3] ="taiwanSMC/FASTER.txt";
cableJsonFiles[4] ="taiwanSMC/FNAL-RNAL.txt";
cableJsonFiles[5] ="taiwanSMC/NCP.txt";
cableJsonFiles[6] ="taiwanSMC/SMW3.txt";
cableJsonFiles[7] ="taiwanSMC/TPE.txt";
cableJsonFiles[8] ="taiwanSMC/TSE1.txt";
cableJsonFiles[9] ="taiwanSMC/plcn.txt";
cableJsonFiles[10] ="taiwanSMC/plcn1.txt";
cableJsonFiles[11] = "used_scable/TDM2.json";
cableJsonFiles[12] = "used_scable/TM3-A.json";
cableJsonFiles[13] = "used_scable/TK2.json";
cableJsonFiles[14] = "used_scable/TP2.json";
cableJsonFiles[15] = "used_scable/TP3.json";
cableJsonFiles[16] = "used_scable/PK1-B.json";
cableJsonFiles[17] = "used_scable/PK3-B.json";
cableJsonFiles[18] = "used_scable/TM1-D.json";
cableJsonFiles[19] = "used_scable/TM3-B.json";
cableJsonFiles[20] = "used_scable/TM3-C.json";
cableJsonFiles[21] = "used_scable/TM3-D.json";
cableJsonFiles[22] = "used_scable/NB2.json";
cableJsonFiles[23] = "used_scable/DX.json";
cableJsonFiles[24] = "used_scable/PK1-A.json";
cableJsonFiles[25] = "used_scable/PK3-A.json";
*/
var cableLandGeometry = [];
//TGOS==Start

var appID = "/jFmH8iF14UgHch1oi/7pzUbOUUuk4XVOsQ9XfTRmF3UGi7xmGBufQ==";
var apiKey = "cGEErDNy5yN/1fQ0vyTOZrghjE+jIU6uN4BQcvGbbxrigHgzfsiCvsDM6EWeO5jtZB1FeI4Sgn/mYkV42gxngnr/wYCdOnwPSZnsOYA+WRp5J0Gp2DVvdASutkDsL8m6eerCSBM10mkl1REoi5SD7aO4EZrjRXhKYeyKFibAPZ2zimOPvlXg1X9zjbdR2CvcaIuyy/xD0kVzVKxbgvysM1rdeV4M07JLLJrOUC4qbOLppT8xfD8JsWLF9FoFgV6Bz4U5YRjJKHopLmc2mtm67NjQ2vh+x8LPvBdn/2gaGVrm3Q9i/wVvnDqIXuJ53xRyFDMnN0cS+eaR75N3bPtGr6fVwMEdnzIX0j8HG6rfEtU=";
var key_ = new TGOS.TGKey(appID, apiKey);

var url_ = TGOS.getTileAgentPath(TGOS.TGMapTileId.TGOSMAP, TGOS.CoordSys.EPSG3857);
//var url_ = TGOS.getTileAgentPath(TGOS.TGMapTileId.F2IMAGE, TGOS.CoordSys.EPSG3857);
//var url_ = TGOS.getTileAgentPath(TGOS.TGMapTileId.ROADMAP, TGOS.CoordSys.EPSG3857);
//var url_ = TGOS.getTileAgentPath(TGOS.TGMapTileId.HILLSHADE, TGOS.CoordSys.EPSG3857);

var tileUrl = url_ + '/GetCacheImage' + 
    '?APPID=' + key_.getAppID() + '&APIKEY=' + key_.getApiKey() +
    '&L=0&S={z}&X={x}&Y={y}';

var extent_ = [];
var resolution_length_;

function createTileUrl(tileCoord) {
	var z = resolution_length_ - tileCoord[0] - 1; // 要換算 Level (z 的順序是相反的)
	var x = tileCoord[1];
	var y = tileCoord[2];
	var s = tileUrl.replace('{z}', z.toString())
		.replace('{x}', x.toString())
		.replace('{y}', y.toString());
	return s;
}

function loadLayerDef(url) {
	try{
	fetch(url).then(function(response) {
		return response.text();
	}).then(function(text) {
		// 剔除字串中, 多餘的內容
		var temp = text.replace('var result =', '');
		if (temp[temp.length - 1] == ';') {
			temp = temp.substring(0, temp.length - 1);
		}
		var tileGrid;
		var resolutions = [];
		//var result = JSON.parse(temp);    // 無法解譯 key:'value' 的字串, 掛 JSON5 來處理
		var tileDef = JSON5.parse(temp);
		var pNodeRes = tileDef.Infomation;				//tileDef 是解出來的物件, 取得其設定內容
		if (pNodeRes) {
			//var _resource = pNodeRes.ResourceName;		//取得TGOS圖磚服務名稱
			var dCLeft = parseFloat(pNodeRes.CornerLeft);	//取得服務的的原點(CornerLeft,CornerLower)坐標值
			var dCLower = parseFloat(pNodeRes.CornerLower);
			var ImgWidth = parseInt(pNodeRes.TileWidth);	//取得單張圖磚的寬度(TileWidth)和高度(TileHeight)
            var ImgHeight = parseInt(pNodeRes.TileHeight);
			var pEnv = pNodeRes.Envelope;					//取得圖磚的上下左右邊界值
			var dCacheLeft = parseFloat(pEnv.Left);
			var dCacheTop = parseFloat(pEnv.Top);
			var dCacheRight = parseFloat(pEnv.Right);
			var dCacheBottom = parseFloat(pEnv.Bottom);
			var pSclss = pNodeRes.Scales;	
			var pScls = pSclss.Scale;				//取得服務內的所有縮放層級
			if (pScls) {
				if (pScls.length > 0) {
					for (var i = 0 ; i < pScls.length ; i++) {
						var pScl = pScls[i];
						var fac = parseFloat(pScl.Factor);	//依序取出各個縮放層級, 並存入陣列 resolutions
						resolutions.push(fac);
					}
				}
				resolution_length_ = resolutions.length; // 計算 z 時需要反過來算索引
			}
			extent_ = [dCacheLeft, dCacheBottom, dCacheRight, dCacheTop];
			tileGrid = new ol.tilegrid.createXYZ({
				tileSize: [ImgWidth, ImgHeight],
				origin: [dCLeft, dCLower],
				resolutions: resolutions
			});
		}
		return tileGrid;
	}).then(function (tileGrid) {
		createMap(tileGrid); // 完成 tileGrid 之後, 再建立地圖.
	});
	}catch{
		location.reload();
	}
}
	  
//loadLayerDef(url_ +  '/GetCacheConfig?FORMAT=JSON');
//TGOS==End

createMap();

//取得圖層樣式..線條...
function getVectorStyle(rColor,gColor,bColor,aColor,sColor){
  var vectorStyle = new ol.style.Style({
    fill: new ol.style.Fill({ //填充色和透明度
      //color: 'rgba(164, 212, 166, 0.6)'
      color: [rColor,gColor,bColor,aColor]
    }),
    stroke: new ol.style.Stroke({ //邊界樣式
      //color: '#319FD3',
      color: sColor,
      width: 2
    }),
    text: new ol.style.Text({ //文字樣式
      font: '12px Calibri,sans-serif',
      fill: new ol.style.Fill({
        color: '#000'
      }),
      stroke: new ol.style.Stroke({
        color: '#fff',
        width: 3
      })
    })
  });
  return vectorStyle;
}


function getVectorStyle2(rColor,gColor,bColor,aColor,sColor,bdColor,bdwidth){
  var vectorStyle = new ol.style.Style({
    fill: new ol.style.Fill({ //填充色和透明度
      //color: 'rgba(164, 212, 166, 0.6)'
      color: [rColor,gColor,bColor,aColor]
    }),
    stroke: new ol.style.Stroke({ //邊界樣式
      color: bdColor,
      //color: sColor,
      width: bdwidth,
    }),
    text: new ol.style.Text({ //文字樣式
      font: '12px Calibri,sans-serif',
      fill: new ol.style.Fill({
        color: '#000'
      }),
      stroke: new ol.style.Stroke({
        color: '#fff',
        width: 3
      })
    })
  });
  return vectorStyle;
}

function getVectorStyle1(rColor,gColor,bColor,aColor,sColor,bdColor,bdwidth){
  var vectorStyle = new ol.style.Style({
    fill: new ol.style.Fill({ //填充色和透明度
      //color: 'rgba(164, 212, 166, 0.6)'
      color: [rColor,gColor,bColor,aColor]
    }),
    stroke: new ol.style.Stroke({ //邊界樣式
      color: bdColor,
      //color: sColor,
      width: bdwidth,
	  //lineDash: [1, 2, 3, 4, 5, 6 ],
	  lineDash: [10, 5 ],
    }),
    text: new ol.style.Text({ //文字樣式
      font: '12px Calibri,sans-serif',
      fill: new ol.style.Fill({
        color: '#000'
      }),
      stroke: new ol.style.Stroke({
        color: '#fff',
        width: 3
      })
    })
  });
  return vectorStyle;
}


function getVectorStyle3(rColor,gColor,bColor,aColor,sColor,bdColor,bdwidth){
  var vectorStyle = new ol.style.Style({
    fill: new ol.style.Fill({ //填充色和透明度
      //color: 'rgba(164, 212, 166, 0.6)'
      color: [rColor,gColor,bColor,aColor]
    }),
    stroke: new ol.style.Stroke({ //邊界樣式
      color: bdColor,
      //color: sColor,
      width: bdwidth,
	  lineDash: [1, 2, 3, 4, 5, 6 ],
	  //lineDash: [10, 5 ],
    }),
    text: new ol.style.Text({ //文字樣式
      font: '12px Calibri,sans-serif',
      fill: new ol.style.Fill({
        color: '#000'
      }),
      stroke: new ol.style.Stroke({
        color: '#fff',
        width: 3
      })
    })
  });
  return vectorStyle;
}

function changeLayers(){
	var xq = mapArray[0].getLayers().item(0);
	xq.setSource(xyz1);
	setTimeout("changeLayers1();",5000);
}

function changeLayers1(){
	var xq = mapArray[0].getLayers().item(0);
	xq.setSource(xyz0);
	setTimeout("changeLayers2();",5000);
}


var maptile = ["https://stamen-tiles.a.ssl.fastly.net/toner/{z}/{x}/{y}.png",
"https://stamen-tiles.a.ssl.fastly.net/watercolor/{z}/{x}/{y}.jpg",
"http://services.arcgisonline.com/arcgis/rest/services/Ocean/World_Ocean_Base/MapServer/tile/{z}/{y}/{x}",
"http://services.arcgisonline.com/arcgis/rest/services/Specialty/DeLorme_World_Base_Map/MapServer/tile/{z}/{y}/{x}",
"http://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png",
"http://services.arcgisonline.com/arcgis/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}",
"http://services.arcgisonline.com/arcgis/rest/services/Canvas/World_Light_Gray_Base/MapServer/tile/{z}/{y}/{x}",
"http://services.arcgisonline.com/arcgis/rest/services/NatGeo_World_Map/MapServer/tile/{z}/{y}/{x}",
"http://services.arcgisonline.com/arcgis/rest/services/World_Physical_Map/MapServer/tile/{z}/{y}/{x}",
"http://{s}.basemaps.cartocdn.com/dark_nolabels/{z}/{x}/{y}.png"
];
//http://alexurquhart.github.io/free-tiles/
var maptile_no = 0 ;


function changeLayers2(){
	var xq = mapArray[0].getLayers().item(0);
	var xyz2 = new ol.source.XYZ({
		url:maptile[maptile_no]
    });
	xq.setSource(xyz2);
	maptile_no++ ;
	if(maptile_no >= maptile.length ){
		maptile_no = 0 ;
		setTimeout("changeLayers();",5000);
	}else{
		setTimeout("changeLayers2();",5000);
	}
}

//var xyz2 = new ol.source.XYZ({
//		url:"https://stamen-tiles.a.ssl.fastly.net/toner/{z}/{x}/{y}.png"
//    });
 //https://stamen-tiles.a.ssl.fastly.net/watercolor/{z}/{x}/{y}.jpg
 //https://tile.thunderforest.com/transport/${z}/${x}/${y}.png
 //https://tile.thunderforest.com/landscape/${z}/${x}/${y}.png
 //https://tile.thunderforest.com/outdoors/${z}/${x}/${y}.png
 //https://maptiles.p.rapidapi.com/en/map/v1/{z}/{x}/{y}.png
	
var xyz1 = new ol.source.XYZ({
       url:"https://server.arcgisonline.com/arcgis/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
    });
	
var xyz0 = 	new ol.source.OSM();

var layerx = new ol.layer.Tile({
        source: xyz0
    });

function createMap() {
//function createMap(tileGrid) {
	mapArray[0] = new ol.Map({
		layers: [
			
			/*
			new ol.layer.Tile({
				source: ,
				opacity: 1
            }),
			*/
			
            new ol.layer.Tile({
			source: new ol.source.OSM(),
			opacity: 1
			}),
			/*
				source : new ol.source.XYZ({
					url:"https://server.arcgisonline.com/arcgis/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
				}),
				
			opacity: 0.9
			*/
            //}),
			
			/*
			http://tiles.wmflabs.org/hillshading/${z}/${x}/${y}.png
			*/
			//new ol.layer.Tile({
				//extent: extent_,
			//	source: new ol.source.XYZ({
					//tileGrid: tileGrid,
			//		tileUrl : "http://tiles.wmflabs.org/hillshading/${z}/${x}/${y}.png",
					//tileUrlFunction: createTileUrl
			//	}),
			//	opacity: 0.7
            //}),
			
			//,clipLayer
		],
		logo: false,
		target: 'map',
		view: new ol.View({
			center: ol.proj.fromLonLat([120.70, 23.58]),
		zoom: 7,
		//extent:ol.proj.transformExtent([ 0, 0, 180, 26], 'EPSG:4326', 'EPSG:3857'),
		minZoom:2.5,
		//maxZoom:10
		}),
		controls: [new ol.control.Zoom(),
        new ol.control.ScaleLine()]
	});

	mapArray[1] = new ol.Map({
		/*
		layers: [
			new ol.layer.Tile({
				extent: extent_,
				source: new ol.source.XYZ({
					tileGrid: tileGrid,
					tileUrlFunction: createTileUrl
				}),
				opacity: 0.7     
			})
			//,clipLayer
		],
		*/
		layers: [

            new ol.layer.Tile({
				source: new ol.source.OSM(),

				opacity: 1
            }),
		
		],
		logo: false,
		target: 'map1',
		view: new ol.View({
			//center: ol.proj.transform([120.3, 23.8], 'EPSG:4326', 'EPSG:3857'),
			center: ol.proj.fromLonLat([119.90, 26.18]),
			zoom: 8
		}),
		controls: []
	});

	prepareMapFunction();
}


function addVectorLayer(tIndex,tLayer){

  mapArray[tIndex].addLayer(tLayer);

  //info.innerHTML += ":" + mapArray[tIndex].getLayers().getLength();
  
  var inVectorSource = tLayer.getSource();
  inVectorSource.on('change', function(e) {
    if (inVectorSource.getState() == 'ready') {
      if(debugMode){
        if(getVectorLayerPosition(tLayer) != -1){
          //加入到顯示選項(測試時)
          //var checkBtn1 = $('<input type="checkbox" name="countyselect[]" value="' + showIdTag[getVectorLayerPosition(tLayer)] + '">'  + inVectorSource.getFeatures()[0].get(showNameTag[getVectorLayerPosition(tLayer)]) + ":" + showIdTag[getVectorLayerPosition(tLayer)] + '</input>');
          //checkBtn1.appendTo('#selectoptions');          
        }
      }            
    }
  });  
}

//移動上去的樣式
var featureOverlay = new ol.layer.Vector({
  source: new ol.source.Vector(),
  map: mapArray[0],
  style: function(feature, resolution) {
    //var text = resolution < 5000 ? feature.get('countyname') : '';
    //var text =  feature.get('countyname');
    var text = "";
    if (!highlightStyleCache[text]) {
      highlightStyleCache[text] = [new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: [0,0,255,1],
          width: 2
        }),
        fill: new ol.style.Fill({
          color: [0,0,255,1]
        }),
        text: new ol.style.Text({
          font: '18px Calibri,sans-serif',
          text: text,
          fill: new ol.style.Fill({
            color: '#000'
          }),
          stroke: new ol.style.Stroke({
            color: '#f00',
            width: 3
          })
        })
      })];
    }
    return highlightStyleCache[text];
  }
});

var othermouseover = null ;

var displayFeatureInfo = function(pixel,tIndex) {
  var myfeature;
  var mylayer;
  var myFeaturePosition = -1;
  mapArray[0].forEachFeatureAtPixel(pixel, function(feature, layer) {
    myfeature = feature;
    mylayer = layer;
	//console.log(mylayer);
  });

if(OtherVectorLayers.indexOf(mylayer)>-1){
	myFeaturePosition = OtherVectorLayers.indexOf(mylayer);
	//console.log("OV:"+myFeaturePosition);
}else{
  if(getCableVectorLayerPosition(mylayer) !== -1){
    myFeaturePosition = getVectorLayerPosition(mylayer);
    //myFeaturePosition = getCableVectorLayerPosition(mylayer);
    //console.log(myFeaturePosition);
	//alert(OtherVectorLayers.indexOf(mylayer));
  }
}
  //console.log(myfeature.getId());
  var feature = mapArray[0].forEachFeatureAtPixel(pixel, function(feature, layer) {
    //if(getVectorLayerPosition(layer) !== -1){
      myFeaturePosition = getVectorLayerPosition(layer);
    //}
    return feature;
  });
  

  //debug info
  if (myfeature) {
	  //console.log(myFeaturePosition);
    //alert("in:" + myfeature.get(showIdTag[myFeaturePosition]));
    if(myFeaturePosition != -1){
      //info.innerHTML = myfeature.get(showIdTag[myFeaturePosition]) + ': ' + myfeature.get(showNameTag[myFeaturePosition]);
      //info.innerHTML = showIdTag[myFeaturePosition] + ': ' + myfeature.get(showNameTag[myFeaturePosition]);
      //info.innerHTML = cableShowIdTag[myFeaturePosition] + ': ' + myfeature.get(cableShowNameTag[myFeaturePosition]);
    }else{
		
	}
  } else {
    //alert("out");
    //info.innerHTML = '移出範圍';
    //info.innerHTML = '';
    //overlay.setPosition(undefined);
  }

  if (myfeature !== highlight) {
    if (highlight) {
      featureOverlay.getSource().removeFeature(highlight);
    }
    if (myfeature) {
      featureOverlay.getSource().addFeature(myfeature);
    }
    highlight = myfeature;
  }

  //info.innerHTML += ":" + mapArray[tIndex].getLayers().getLength();
};

//地圖功能
function prepareMapFunction(){

	mapArray[0].on('pointermove', function(evt) {
	  if (evt.dragging) { //判斷是否為拖動地圖
		return;
	  }
	  var pixel = mapArray[0].getEventPixel(evt.originalEvent);
	  //設定移動上去的樣式
	  displayCableFeatureInfo(pixel,0,mapArray[0].getEventCoordinate(event));
	  displayFeatureInfo(pixel,0,mapArray[0].getEventCoordinate(event));
	  changeCSSCurve(Number(mapArray[0].getView().getZoom()));
	});
/*
mapArray[0].on('pointermove', function(evt) {
	//var coordinate = browserEvent.coordinate;
       // var pixel = mapArray[0].getPixelFromCoordinate(coordinate);
         var pixel = mapArray[0].getEventPixel(evt.originalEvent);
        mapArray[0].forEachFeatureAtPixel(pixel, function(feature) {
          alert(feature.get('name'));
        });
});
*/

	mapArray[0].on('singleclick', function(evt) {
	  var getFeatures = "";
	  var coordinate = evt.coordinate;
	  //var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326'));
	  var hdms = ol.coordinate.toStringXY(ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326'),10);
	  var pixel = mapArray[0].getEventPixel(evt.originalEvent);
	  var getThisFeature = null ;
	  mapArray[0].forEachFeatureAtPixel(pixel, function(feature) {
		 //console.log(feature);
		 if(feature.id_ != undefined){
			mapCurveClick(feature.id_);
		 }else{
		  if(getFeatures != "") getFeatures += "<w>";
		  var xxx = feature.get('name') ;
		  if(xxx == undefined) xxx = feature.get('Name') ;
          getFeatures += xxx ;
//		  + feature.get('feature_id')+feature.get('id') ;
		  getThisFeature = feature ;
		  if(getFeatures != "") showInfoWindows(getFeatures+"/"+hdms+"/");
		 }
        });
		//if(getFeatures != ""){
		//	doNextFunc(hdms,getFeatures,getThisFeature);
		//}
		
	});

	mapArray[1].on('Xsingleclick', function(evt) {
	  var pixel = mapArray[1].getEventPixel(evt.originalEvent);
	  var myfeature;
	  var mylayer;
	  mapArray[1].forEachFeatureAtPixel(pixel, function(feature, layer) {
		myfeature = feature;
		mylayer = layer;
	  });

	  var coordinate = evt.coordinate;
	  var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326'));
	  
	  if(myfeature!==undefined){
		//info.innerHTML = '座標位置<code>' + hdms + '</code><br>屬於'+ myfeature.get(showNameTag[getVectorLayerPosition(mylayer)]) + ":" + showIdTag[getVectorLayerPosition(mylayer)];
		//alert(myfeature.get('name'));
		//顯示需要的資訊,可能會被遮罩層檔住    
		info.innerHTML = ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326') + "," + myfeature.get(townShowNameTag[getTownVectorLayerPosition(mylayer)]) + ",x" + myfeature.get('towncode');
	  }else{
		//alert("out");
		//content.innerHTML = '<p>座標位置<code>' + hdms + '</code><p>不在範圍內</p>';
		info.innerHTML = '座標位置<code>' + hdms + '</code><br>不在範圍內';
	  }  
	});
}
	
function showDetailMap(mydcoordinate,myId,myIndex){
  //removeAllVectorLayers(0);
  //map1Show();
  //alert(myIndex);

  //mapArray[1].removeLayer(clipLayer);
  //移動後添加遮罩
  //sample:ol.proj.transform([23.4, 42.5], 'EPSG:4326','EPSG:3857');
  //臺南市(海)沒有資料
  if(countyCenterGeometry[myIndex].length > 0){
    mapArray[0].getView().setCenter(ol.proj.transform([parseFloat(countyCenterGeometry[myIndex].split(",")[0]), parseFloat(countyCenterGeometry[myIndex].split(",")[1])], 'EPSG:4326','EPSG:3857'));
    mapArray[0].getView().setZoom(parseFloat(countyCenterGeometry[myIndex].split(",")[2]));
  }  

  if(myId !== undefined){
    //clipLayer = getCoverLayer('jsondata/county/' + myId + '.json');
    //mapArray[1].addLayer(clipLayer);

    info.innerHTML += "<br>收到ID:" + myId.substring(0,5);
    //info.innerHTML += "地圖大小:" + mapArray[0].getView().getZoom();

    //清除(鄉鎮市)已記錄下來的資訊盒
/*   
   if(detailInMapIdTag.length > 0){
      for(i=0;i<detailInMapIdTag.length;i++){
        showOrHideInfoBox(0,detailInMapIdTag[i],false);
      }
      detailInMapIdTag.length = 0;
      detailInMapNameTag.length = 0;
    }else{
    }
  */  
    var myCountyId = myId.substring(0,5);
    for(i = 0;i < townJsonFiles.length;i++){
      //info.innerHTML += "<br>" + townJsonFiles.indexOf(myCountyId);
      if(townJsonFiles[i].indexOf(myCountyId) != -1){
        //info.innerHTML += "<br>" + townShowIdTag[i];
        //info.innerHTML += "<br>" + townJsonFiles[i] + ":" + townJsonFiles.indexOf(townJsonFiles[i]);
        //alert(townJsonFiles[i].indexOf(myCountyId) + ":" + myCountyId + ":" + townJsonFiles[i]);
        try {
          addVectorLayer(0,townVectorLayers[townJsonFiles.indexOf(townJsonFiles[i])]);
          detailInMapIdTag[detailInMapIdTag.length] = townJsonFiles[i].split(",")[0].split("/")[2].split(".")[0];
          detailInMapNameTag[detailInMapNameTag.length] = townJsonFiles[i].split(",")[3];
          //alert(townJsonFiles[i].split(",")[3]);
          //這個縣市中,有內容的鄉鎮市進行顯示
          //alert($('#' + townJsonFiles[i].split(",")[0].split("/")[2].split(".")[0] + '-content').html());
          if($('#' + townJsonFiles[i].split(",")[0].split("/")[2].split(".")[0] + '-content').html().length > 0){
            showOrHideInfoBox(0,townJsonFiles[i].split(",")[0].split("/")[2].split(".")[0],true);            
          }
        } catch (error) {
          
        }
      }
    }

    clipLayer = getCoverLayer('jsondata/county/' + myId + '.json');
    mapArray[0].addLayer(clipLayer);
  }
}

//地圖互動部分
function setAltShiftDragRotateActive(mIndex,activeFlag){
  mapArray[mIndex].getInteractions().forEach(function (interaction) {
    if(interaction instanceof ol.interaction.DragRotate) {
      interaction.setActive(activeFlag);
    }
  });
}

function setDragPanActive(mIndex,activeFlag){
  mapArray[mIndex].getInteractions().forEach(function (interaction) {
    if(interaction instanceof ol.interaction.DragPan) {
      interaction.setActive(activeFlag);
    }
  });
}

function setDragZoomActive(mIndex,activeFlag){
  mapArray[mIndex].getInteractions().forEach(function (interaction) {
    if(interaction instanceof ol.interaction.DragZoom) {
      interaction.setActive(activeFlag);
    }
  });
}

function setMouseWheelZoomActive(mIndex,activeFlag){
  mapArray[mIndex].getInteractions().forEach(function (interaction) {
    if(interaction instanceof ol.interaction.MouseWheelZoom) {
      interaction.setActive(activeFlag);
    }
  });
}

function setDoubleClickZoomActive(mIndex,activeFlag){
  mapArray[mIndex].getInteractions().forEach(function (interaction) {
    if(interaction instanceof ol.interaction.DoubleClickZoom) {
      interaction.setActive(activeFlag);
    }
  });
}
/*
var currZoom = map.getView().getZoom();
mapArray[0].on('moveend', function(e) {
  var newZoom = mapArray[0].getView().getZoom();
  if (currZoom != newZoom) {
    console.log('zoom end, new zoom: ' + newZoom);
    currZoom = newZoom;
  }
});

mapArray[0].on('singleclick', function(evt) {
	  mapArray[0].forEachFeatureAtPixel(evt.pixel, function(feature) {
		  console.log(feature.id_);
        });
	});
*/

changeLayers();