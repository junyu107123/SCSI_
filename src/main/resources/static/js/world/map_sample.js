var map,vectorLayer,vectorLayer1;
var highlightStyleCache = {},highlight;

/*
function randomRgbaColor() {
 var r = Math.floor(Math.random() * 256);
 var g = Math.floor(Math.random() * 256);
 var b = Math.floor(Math.random() * 256);
 //var alpha = Math.random();
 var alpha = 0.6;
 return `rgba(${r},${g},${b},${alpha})`;
}
*/

//popup overlay ==start
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
//popup overlay ==end

var style = new ol.style.Style({
  fill: new ol.style.Fill({ //填充色和透明度
    //color: 'rgba(164, 212, 166, 0.6)'
    color: [164,212,166,0.6]
  }),
  stroke: new ol.style.Stroke({ //邊界樣式
    color: '#319FD3',
    width: 3
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

vectorLayer = new ol.layer.Vector({ //初始化向量圖曾
  source: new ol.source.Vector({
    format: new ol.format.GeoJSON(),
    url: 'jsondata/t1_10002001.json' //讀取json檔案
  }),
  style: function(feature, resolution) {
    //style.getText().setText(resolution < 5000 ? feature.get('countyname') : '');  //放大到1:5000
    //style.getText().setText("高鐵軌道");
    return [style];
  }
});

vectorLayer1 = new ol.layer.Vector({ //初始化向量圖曾
  source: new ol.source.Vector({
    format: new ol.format.GeoJSON(),
    url: 'jsondata/3_1.geojson' //讀取json檔案
  }),
  style: function(feature, resolution) {
    //style.getText().setText(resolution < 5000 ? feature.get('countyname') : '');  //放大到1:5000
    //style.getText().setText("高鐵軌道");
    return [style];
  }
});

map = new ol.Map({
  layers: [
    new ol.layer.Tile({
      source: new ol.source.OSM()
    }),
    vectorLayer,
    vectorLayer1
  ],
  overlays:[overlay],
  target: 'map',
  view: new ol.View({
    //center: ol.proj.transform([37.41, 8.82], 'EPSG:4326', 'EPSG:3857'),
    center: ol.proj.fromLonLat([120.3, 23.8]),
    zoom: 8
  })
});

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
          color: '#f00',
          width: 1
        }),
        fill: new ol.style.Fill({
          color: 'rgba(255,0,0,0.1)'
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
  //var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
  var feature = map.forEachFeatureAtPixel(pixel, function(feature) {
    return feature;
  });

  //debug info
  var info = document.getElementById('info');
  if (feature) {
    info.innerHTML = feature.get('countyid') + ': ' + feature.get('countyname');
  } else {
    info.innerHTML = '移出範圍';
    overlay.setPosition(undefined);
    //closer.blur();
  }

  if (feature !== highlight) {
    if (highlight) {
      featureOverlay.getSource().removeFeature(highlight);
    }
    if (feature) {
      featureOverlay.getSource().addFeature(feature);
    }
    highlight = feature;
  }
};

map.on('pointermove', function(evt) {
  if (evt.dragging) { //判斷是否為拖動地圖
    return;
  }
  var pixel = map.getEventPixel(evt.originalEvent);
  displayFeatureInfo(pixel);
});

map.on('singleclick', function(evt) {
  var pixel = map.getEventPixel(evt.originalEvent);
  var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    return feature;
  });

  var coordinate = evt.coordinate;
  var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326'));

  if(feature!==undefined){
    content.innerHTML = '<p>座標位置<code>' + hdms + '</code><p>屬於'+ feature.get('countyname') + '</p>';
  }else{
    content.innerHTML = '<p>座標位置<code>' + hdms + '</code><p>不再範圍內</p>';
  }

  overlay.setPosition(coordinate);
  map.getView().setCenter(coordinate);
});

//vectorLayer.setVisible(false);
vectorLayer1.setZIndex(100);
