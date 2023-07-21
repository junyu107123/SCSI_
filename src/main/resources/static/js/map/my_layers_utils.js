//縣市界線
var vectorLayers = []; //read from json file
var vectorLayersId = []; //圖層Id
var showIdTag = []; //顯示ID
var showNameTag = []; //顯示名稱
var showMapIndex = []; //顯示地圖編號(暫時沒用到)


var KMLvectorLayers = []; //read from json file
var KMLvectorLayersId = []; //圖層Id
var KMLshowIdTag = []; //顯示ID
var KMLshowNameTag = []; //顯示名稱
var KMLshowMapIndex = []; //顯示地圖編號(暫時沒用到)

//鄉鎮界線
var townVectorLayers = []; //read from json file
var townVectorLayersId = []; //圖層Id
var townShowIdTag = []; //顯示ID
var townShowNameTag = []; //顯示名稱
var townShowMapIndex = []; //顯示地圖編號(暫時沒用到)

var detailInMapIdTag = []; //目前在鄉鎮市地圖地圖顯示的id
var detailInMapNameTag = []; //目前在鄉鎮市地圖地圖顯示的名字

var vectorStyles = []; //圖層樣式
//var styleColors = [];

//清空所有layer資料
function clearVecLayersData(tIndex){
    switch (tIndex){
        case 0:
            vectorLayers.length = 0;
            vectorLayersId.length = 0;    
            showIdTag.length = 0;
            showNameTag.length = 0;
            showMapIndex.length = 0;
            break;
        case 1:
            townVectorLayers.length = 0;
            townVectorLayersId.length = 0;    
            townShowIdTag.length = 0;
            townShowNameTag.length = 0;
            townShowMapIndex.length = 0;
            break;
    }   

    //vectorStyles.length = 0;
}

function clearDetailId(){
    detailInMapIdTag.length = 0;
    detailInMapNameTag.length = 0;
}

//加入一個layer樣式到最後一個位置
function addToVectorStylesArray(rColor,gColor,bColor,aColor,sColor){
    vectorStyles[vectorStyles.length] = getVectorStyle(rColor,gColor,bColor,aColor,sColor);
}

function addToVectorStylesArray1(rColor,gColor,bColor,aColor,sColor,bdColor,bdwidth){
    vectorStyles[vectorStyles.length] = getVectorStyle1(rColor,gColor,bColor,aColor,sColor,bdColor,bdwidth);
}

//移除所有layer樣式
function clearAllVectorStyles(){
    vectorStyles.length = 0;
}

//加入一個layer到最後一個位置
function addToVectorArray(jsonsource,mapindex,dataarrayindex,styleindex,idTag,nameTag){
    switch (dataarrayindex){
        case 0:
            //showIdTag[vectorLayers.length] = idTag;
            showIdTag[vectorLayers.length] = idTag;
            showNameTag[vectorLayers.length] = nameTag;
            showMapIndex[vectorLayers.length] = mapindex;
            vectorLayers[vectorLayers.length] = getVectorLayer(jsonsource,dataarrayindex,vectorStyles[styleindex],vectorLayers.length);
            break;
        case 1:
            townShowIdTag[townVectorLayers.length] = idTag;
            townShowNameTag[townVectorLayers.length] = nameTag;
            townShowMapIndex[townVectorLayers.length] = mapindex;
            townVectorLayers[townVectorLayers.length] = getVectorLayer(jsonsource,dataarrayindex,vectorStyles[styleindex],townVectorLayers.length);
            break;
    }
}

function addKMLToVectorArray(kmlFile,mapindex,dataarrayindex,styleindex,idTag,nameTag){
			KMLshowIdTag[KMLvectorLayers.length] = idTag;
            KMLshowNameTag[KMLvectorLayers.length] = nameTag;
            KMLshowMapIndex[KMLvectorLayers.length] = mapindex;
            KMLvectorLayers[KMLvectorLayers.length] = getVectorLayerKML(kmlFile,vectorLayers.length);
}


//取得targetLayer在vectorLayers的位置
function getVectorLayerPosition(targetLayer){
    for(var i = 0;i<vectorLayers.length;i++){
      if( targetLayer === vectorLayers[i]) return i;
    }
    return -1;
}

function getTownVectorLayerPosition(targetLayer){
    for(var i = 0;i<townVectorLayers.length;i++){
      if( targetLayer === townVectorLayers[i]) return i;
    }
    return -1;
}

//遮罩層樣式
/*
var clistyle = new ol.style.Style({
    fill: new ol.style.Fill({
        color: [255,255,255,1]
    })
});
*/

//取得遮罩層
function getCoverLayer(jsonFile){
    var resultCoverLayer = new ol.layer.Image({
        source: new ol.source.ImageVector({
            source: new ol.source.Vector({
                url: jsonFile
                ,format: new ol.format.GeoJSON()
            })                      
            ,style: new ol.style.Style({
                fill: new ol.style.Fill({
                  color: [224,224,0,0.7]
                })
            })                     
        })
        //,zIndex: 999
        //,opacity: 0.1 //透明度
    });

    var minVectorSource = resultCoverLayer.getSource();
    minVectorSource.on('change', function(e) {
      if (minVectorSource.getState() == 'ready') {
        //info.innerHTML += "resultCoverLayer圖片轉換完成";
      }
    });

    resultCoverLayer.on('postcompose', function(e) {
        e.context.globalCompositeOperation = 'source-over';
        
        //e.context.globalCompositeOperation = 'source-over';
        //e.context.globalAlpha = 0.1;
        //info.innerHTML += "postcompose";
    });
    
    
    resultCoverLayer.on('precompose', function(e) {
        e.context.globalCompositeOperation = 'destination-in';

        //e.context.globalAlpha = 0.1;
        //e.context.fillStyle = "blue";
        //e.context.globalCompositeOperation = 'destination-in';
        //info.innerHTML += "precompose";
    });
    
    
    return resultCoverLayer;
}

function getVectorLayerKML(kmlFile,idPosition){
    var resultVectorLayer = new ol.layer.Vector({ //初始化向量圖曾
        source: new ol.source.Vector({
        format: new ol.format.KML(),
        url: kmlFile //讀取json檔案
        })
    });
	var resultVectorSource = resultVectorLayer.getSource();
	 resultVectorSource.on('change', function(e) {
                if (resultVectorSource.getState() == 'ready') {
                    if(isNaN(resultVectorSource.getFeatures()[0].get(showNameTag[idPosition]))){
						KMLvectorLayersId[idPosition] = KMLshowIdTag[idPosition];
                    }else{
                        KMLvectorLayersId[idPosition] = "-1";
                    }
				}
      });
	 return resultVectorLayer;
}

//從JsonFile取得VectorLayer
function getVectorLayer(jsonFile,dataarrayindex,vStyle,idPosition){
    var resultVectorLayer = new ol.layer.Vector({ //初始化向量圖曾
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
    switch (dataarrayindex){
        case 0:
            resultVectorSource.on('change', function(e) {
                if (resultVectorSource.getState() == 'ready') {
                    if(isNaN(resultVectorSource.getFeatures()[0].get(showNameTag[idPosition]))){
                        //vectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(showNameTag[idPosition]);
                        vectorLayersId[idPosition] = showIdTag[idPosition];
                    }else{
                        //vectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(showNameTag[idPosition]).toString();
                        vectorLayersId[idPosition] = "-1";
                    }
                    /*
                    if(isNaN(resultVectorSource.getFeatures()[0].get(showIdTag[idPosition]))){
                        vectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(showIdTag[idPosition]);
                    }else{
                        vectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(showIdTag[idPosition]).toString();
                    }
                    */  
                }
            });
            break;
        case 1:
            resultVectorSource.on('change', function(e) {
                if (resultVectorSource.getState() == 'ready') {
                    if(isNaN(resultVectorSource.getFeatures()[0].get(townShowNameTag[idPosition]))){
                        //townVectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(townShowIdTag[idPosition]);
                        townVectorLayersId[idPosition] = townShowIdTag[idPosition];
                    }else{
                        //townVectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(townShowIdTag[idPosition]).toString();
                        townVectorLayersId[idPosition] = "-1";
                    }
                    /*
                    if(isNaN(resultVectorSource.getFeatures()[0].get(townShowIdTag[idPosition]))){
                        townVectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(townShowIdTag[idPosition]);
                    }else{
                        townVectorLayersId[idPosition] = resultVectorSource.getFeatures()[0].get(townShowIdTag[idPosition]).toString();
                    }
                    */ 
                }
            });
            break;        
    }
    
    return resultVectorLayer;
}

//隱藏圖層
function hideAllLayers(mId){
    switch(mId){
        case 0:
            for(i = 0;i < vectorLayers.length;i++) {
                vectorLayers[i].setVisible(false);
            }
            break;
        case 1:
            for(i = 0;i < townVectorLayers.length;i++) {
                townVectorLayers[i].setVisible(false);
            }
            break;
    }  
    //info.innerHTML += ":" + mapArray[0].getLayers().getLength();
}