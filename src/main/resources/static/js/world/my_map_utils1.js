//var map,vectorLayer,vectorLayer1;
var map;
var highlightStyleCache = {},highlight;
var vectorLayers = []; //read from json file
var vectorLayersId = [];
var vectorStyles = [];
var styleColors = [];

var showIdTag = [];
var showNameTag = [];

//debug info
var info = document.getElementById('info');

//popup overlay == start
var container = document.getElementById('popup');
var content = document.getElementById('popup-content');
var closer = document.getElementById('popup-closer');

var overlay = new ol.Overlay(/** @type {olx.OverlayOptions} */ ({
  element: container,
  autoPan: true,
  autoPanAnimation: {
    duration: 250
  }
}));

closer.onclick = function() {
  overlay.setPosition(undefined);
  closer.blur();
  return false;
};
//popup overlay == end

function setVecLayersLength(vLength){
  vectorLayers.length = vLength;
  vectorLayersId.length = vectorLayers.length;
  vectorStyles.length = vectorLayers.length;
  showIdTag.length = vectorLayers.length;
  showNameTag.length = vectorLayers.length;
}

function getVectorStyle(rColor,gColor,bColor,aColor,sColor){
  var vectorStyle = new ol.style.Style({
    fill: new ol.style.Fill({ //填充色和透明度
      //color: 'rgba(164, 212, 166, 0.6)'
      //color: [rColor,gColor,bColor,aColor]
	  color: [rColor,gColor,bColor,0.9]
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

function getVectorLayer(jsonFile,vStyle,idPosition){
  var vectorLayer = new ol.layer.Vector({ //初始化向量圖曾
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

  var vectorSource = vectorLayer.getSource();
  vectorSource.on('change', function(e) {
    if (vectorSource.getState() == 'ready') {
      vectorLayersId[idPosition] = vectorSource.getFeatures()[0].get(showIdTag[idPosition]);
    }
  });
  return vectorLayer;
}

map = new ol.Map({
  layers: [
    new ol.layer.Tile({
      //source: new ol.source.OSM()
    })
  ],
  target: 'map',
  view: new ol.View({
    //center: ol.proj.transform([37.41, 8.82], 'EPSG:4326', 'EPSG:3857'),
    center: ol.proj.fromLonLat([120.3, 23.8]),
    zoom: 7
  })
});

function addVectoryLayer(){
  for(i = 0;i < vectorLayers.length;i++){
    map.addLayer(vectorLayers[i]);
  }

  map.getLayers().forEach(function(layer){
    try {
      if(layer.getSource() instanceof ol.source.Vector){
        var vectorSource = layer.getSource();
        vectorSource.on('change', function(e) {
          if (vectorSource.getState() == 'ready') {
/*          
		  if(getVectorLayerPosition(layer) != -1){
              var checkBtn1 = $('<input type="checkbox" name="countyselect[]" value="' + vectorSource.getFeatures()[0].get(showIdTag[getVectorLayerPosition(layer)]) + '">'  + vectorSource.getFeatures()[0].get(showNameTag[getVectorLayerPosition(layer)]) + '</input>');
              checkBtn1.appendTo('#selectoptions');
              var checkBtn2 = $('<input type="checkbox" name="alarmselect[]" value="' + vectorSource.getFeatures()[0].get(showIdTag[getVectorLayerPosition(layer)]) + '">'  + vectorSource.getFeatures()[0].get(showNameTag[getVectorLayerPosition(layer)]) + '</input>');
              checkBtn2.appendTo('#alarmoptions');
            }
*/			
          }
        });
      }
    } catch(exception){
      //info.innerHTML += exception;
    } finally {

    }
  });
}

function addOverlay(){
  map.addOverlay(overlay);
}

addOverlay();

//移動上去的樣式
var featureOverlay = new ol.layer.Vector({
  source: new ol.source.Vector(),
  map: map,
  style: function(feature, resolution) {
    //var text = resolution < 5000 ? feature.get('countyname') : '';
    //var text =  feature.get('countyname');
    var text = "";
    if (!highlightStyleCache[text]) {
      highlightStyleCache[text] = [new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: [255,255,255,1],
          width: 2
        }),
        fill: new ol.style.Fill({
          color: [0,0,255,0.9]
        }),
        text: new ol.style.Text({
          font: '12px Calibri,sans-serif',
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

var displayFeatureInfo = function(pixel) {
  var myfeature;
  var mylayer;
  var myFeaturePosition = -1;
  map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    myfeature = feature;
    mylayer = layer;
  });

  if(getVectorLayerPosition(mylayer) !== -1){
    myFeaturePosition = getVectorLayerPosition(mylayer);
  }

  
  var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    if(getVectorLayerPosition(layer) !== -1){
      myFeaturePosition = getVectorLayerPosition(layer);
    }
    return feature;
  });
  

  //debug info
  if (myfeature) {
    if(myFeaturePosition != -1){
      info.innerHTML = myfeature.get(showIdTag[myFeaturePosition]) + ': ' + myfeature.get(showNameTag[myFeaturePosition]);
    }
  } else {
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
};

//地圖功能
/*
map.on('pointermove', function(evt) {
  if (evt.dragging) { //判斷是否為拖動地圖
    return;
  }
  var pixel = map.getEventPixel(evt.originalEvent);
  //設定移動上去的樣式
  displayFeatureInfo(pixel);
});
*/
map.on('pointermove', function(evt) {
  var pixel = map.getEventPixel(evt.originalEvent);
  var myfeature;
  var mylayer;
  map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    myfeature = feature;
    mylayer = layer;
  });

  var coordinate = evt.coordinate;
  var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326'));

  if(myfeature!==undefined){
    content.innerHTML = '<p>座標位置<code>' + hdms + '</code><p>屬於'+ myfeature.get(showNameTag[getVectorLayerPosition(mylayer)]) + '</p>';
  }else{
    content.innerHTML = '<p>座標位置<code>' + hdms + '</code><p>不再範圍內</p>';
  }

  overlay.setPosition(coordinate);
  map.getView().setCenter(coordinate);
});
