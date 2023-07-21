var nowCountyInfoBox;
var nowTownInfoBox;
var nowAllInfoBox = [];

//(縣市)
function getCenterGeometryPosition(targetIdString){
  for(i = 0;i < countyJsonFiles.length;i++){
    if(countyJsonFiles[i].indexOf(targetIdString) != -1){
      return i;
    }
  }
  return -1;
}

//鄉鎮市
function getTownCenterGeometryPosition(targetIdString){
  for(i = 0;i < townJsonFiles.length;i++){
    if(townJsonFiles[i].indexOf(targetIdString) != -1){
      return i;
    }
  }
  return -1;
}

function getOverlayDivObject(popid){
  var resultString = '';
  resultString += '<div id="' + popid + '-popup" class="ol-popup">';
  //resultString += '<a href="#" id="popup-closer" class="ol-popup-closer"></a>';
  resultString += '<div id="' + popid + '-content" style="height:50px;font-size:small"></div>';
  resultString += '</div>';

  return resultString;
}

function getOverlayObject(popid,poplon,poplat){
  //alert(poplon + "+" + poplat);
  var resultOverlay;

  resultOverlay = new ol.Overlay(/** @type {olx.OverlayOptions} */ ({
    id: popid + '-popup',
    element: document.getElementById(popid + '-popup'),
    autoPan: true,
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
  */

  //resultOverlay.setPosition(ol.proj.fromLonLat([parseFloat(poplon), parseFloat(poplat)]));
  resultOverlay.setPosition(ol.proj.transform([parseFloat(poplon), parseFloat(poplat)], 'EPSG:4326','EPSG:3857'));

  return resultOverlay;
}

//清空地圖中所有的infobox的div
function clearAllInfoBox(mId){
  switch(mId){
    case 0:
      $('#map div .ol-popup').remove();
      mapArray[0].getOverlays().clear();
      break;
    case 1:
      $('#map1 div .ol-popup').remove();
      mapArray[1].getOverlays().clear();
      break;
  }  
}

function moveTopDiv(mId,tId){
  //switch(mId){
   // case 0:
      for(i=0;i<showIdTag.length;i++){
        if(showIdTag[i] === tId){
          $('#' + showIdTag[i] + '-popup').css("z-index", 100);
        }else{
          $('#' + showIdTag[i] + '-popup').css("z-index", 0);
        }
      }
    //  break;
   // case 1:
      for(i=0;i<detailInMapIdTag.length;i++){
        if(detailInMapIdTag[i] === tId){
          $('#' + detailInMapIdTag[i] + '-popup').css("z-index", 100);
        }else{
          $('#' + detailInMapIdTag[i] + '-popup').css("z-index", 0);
        }
      }
  //    break;
 // }  
}

//(縣市)預先建立所有div物件
function preCreateCountyInfoBoxDiv(mId,tId){  
  //document.getElementById(tId + '-content').innerHTML = tInfoString;
  //mapArray[mIndex].addOverlay(getOverlayObject(tId,countyCenterGeometry[getCenterGeometryPosition(tId)].split(",")[0],countyCenterGeometry[getCenterGeometryPosition(tId)].split(",")[1]));
  //if(getCenterGeometryPosition(tId) == -1){
	//  mapArray[0].addOverlay(getOverlayObject(tId,townJsonFiles[getTownCenterGeometryPosition(tId)].split(",")[1],townJsonFiles[getTownCenterGeometryPosition(tId)].split(",")[2]));
  //}else{
//	  mapArray[0].addOverlay(getOverlayObject(tId,countyCenterGeometry[getCenterGeometryPosition(tId)].split(",")[0],countyCenterGeometry[getCenterGeometryPosition(tId)].split(",")[1]));
 // }
  //if(getCenterGeometryPosition(tId) == -1){
  //getTownCenterGeometryPosition
  if(getCenterGeometryPosition(tId) == -1){ mId= 1;}
  switch(mId){
    case 0:
      if(countyCenterGeometry[getCenterGeometryPosition(tId)].length > 0){
        $('#map').append(getOverlayDivObject(tId));
        $('#' + tId + '-popup').click(function(){
          moveTopDiv(0,tId);
        });
        document.getElementById(tId + '-popup').style.display = 'none';
        mapArray[0].addOverlay(getOverlayObject(tId,countyCenterGeometry[getCenterGeometryPosition(tId)].split(",")[0],countyCenterGeometry[getCenterGeometryPosition(tId)].split(",")[1]));
      }
      break;
    case 1:
      $('#map').append(getOverlayDivObject(tId));
      $('#' + tId + '-popup').click(function(){
        moveTopDiv(0,tId);
      });
      document.getElementById(tId + '-popup').style.display = 'none';
      mapArray[0].addOverlay(getOverlayObject(tId,townJsonFiles[getTownCenterGeometryPosition(tId)].split(",")[1],townJsonFiles[getTownCenterGeometryPosition(tId)].split(",")[2]));
      break;    
  }
  
}

//(縣市)設定infobox內容並顯示
function setInfoBox(tId,tInfoString){
	//console.log(tInfoString);
  if(tInfoString.length == 0){
    //沒有資料內容,隱藏資訊框
    try{
      document.getElementById(tId + '-content').innerHTML = tInfoString;
    } catch (error) {
      //console.log("div塞入資料錯誤:" + error);   
    }
    showOrHideInfoBox(-1,tId,false);
  }else{
    //有資料內容
    try{
      document.getElementById(tId + '-content').innerHTML = tInfoString;
    } catch (error) {
      console.log("div塞入資料錯誤:" + error);   
    }    
    showOrHideInfoBox(-1,tId,true);
	//showOrHideInfoBox(-1,tId,false);
  }
}

function setInfoBox_popup(tId,tInfoString){
	
  if(tInfoString.length == 0){
    //沒有資料內容,隱藏資訊框
    try{
      document.getElementById(tId + '-popup').innerHTML = tInfoString;
    } catch (error) {
      //console.log("div塞入資料錯誤:" + error);   
    }
    showOrHideInfoBox(-1,tId,false);
  }else{
    //有資料內容
    try{
      document.getElementById(tId + '-popup').innerHTML = tInfoString;
	  document.getElementById(tId + '-popup').style.display = 'none';
    } catch (error) {
      console.log("div塞入資料錯誤:" + error);   
    }    
    showOrHideInfoBox(-1,tId,true);
  }
}

//(縣市)設定顯示不顯示
function showOrHideInfoBox(mId,tId,sBool){
  if(document.getElementById(tId + '-popup') !== undefined){
    if(sBool){
      //要求要顯示
      //1.縣市地圖直接顯示
      //2.鄉鎮市地圖目前的才顯示,其他不顯示
      if($.inArray(tId, showIdTag) != -1){
        //屬於縣市地圖
        try{
          document.getElementById(tId + '-popup').style.display = 'block';
        } catch (error) {
          //console.log("鄉鎮市不在顯示範圍:" + error);   
        }        
      }else{
        //屬於鄉鎮市地圖
        if($.inArray(tId, detailInMapIdTag) != -1){
          try{
            document.getElementById(tId + '-popup').style.display = 'block';
          } catch (error) {
            //console.log("鄉鎮市不在顯示範圍:" + error);   
          }          
          //console.log("在顯示範圍");
          //alert("在顯示範圍");
        }else{
          try{
            document.getElementById(tId + '-popup').style.display = 'none';
          } catch (error) {
            console.log("鄉鎮市不在顯示範圍:" + error);   
          }          
          //console.log("不在顯示範圍");
          //alert("不在顯示範圍");
        }
      }      
    }else{
      //如果是不顯示的直接不顯示
      try{
        document.getElementById(tId + '-popup').style.display = 'none';
      } catch (error) {
      }     
    }
  }else{
    //alert("找不到div:" + tId + "-popup");
    console.log("找不到div:" + tId + "-popup");
  }
}

//(縣市)隱藏所有顯示
function hideAllCountyInfoBox(){
  for(i=0;i<showIdTag.length;i++){
    if(countyCenterGeometry[getCenterGeometryPosition(showIdTag[i])].length > 0){
      try{
        document.getElementById(showIdTag[i] + '-popup').style.display = 'none';
      } catch (error) {  
      }      
    }
  }
}

//(鄉鎮市)隱藏所有顯示
function hideAllTownInfoBox(){
  for(i=0;i<townShowIdTag.length;i++){
    try{
      document.getElementById(townShowIdTag[i] + '-popup').style.display = 'none';
    } catch (error) {
    }    
  }
}

function receiveInfoBoxArray(iArray,splitString){
  console.log("收到InfoBoxArray:" + iArray);
  if(nowAllInfoBox.length > 0){
    //有資料時
    console.log("nowAllInfoBox有資料");
    var tmpAllInfoBox = [];
    for(i=0;i<nowAllInfoBox.length;i++){
      tmpAllInfoBox[i] = nowAllInfoBox[i];
    }

    nowAllInfoBox.length = 0;

    for(i=0;i<iArray.length;i++){
      nowAllInfoBox[nowAllInfoBox.length] = iArray[i].split(splitString)[0];
      if(debugMode) 
        setInfoBox(iArray[i].split(splitString)[0],iArray[i].split(splitString)[1]);
      showOrHideInfoBox(-1,iArray[i].split(splitString)[0],true);
    }

    for(i=0;i<nowAllInfoBox.length;i++){
      if($.inArray(nowAllInfoBox[i], tmpAllInfoBox) != -1){
        delete tmpAllInfoBox[$.inArray(nowAllInfoBox[i], tmpAllInfoBox)];
      }
    }
    
    for(i=0;i<tmpAllInfoBox.length;i++){
      //console.log("tmpAllInfoBox:" + tmpAllInfoBox + ":" + tmpAllInfoBox.length + ":" + tmpAllInfoBox[i]);
      
      if(tmpAllInfoBox[i] !== undefined){
        setInfoBox(tmpAllInfoBox[i],"");
        showOrHideInfoBox(-1,tmpAllInfoBox[i],false);
      } 
           
    }

    tmpAllInfoBox.length = 0;
    tmpAllInfoBox = undefined;

  }else{
    //沒有資料時塞入資料
    for(i=0;i<iArray.length;i++){
      nowAllInfoBox[nowAllInfoBox.length] = iArray[i].split(splitString)[0];
      if(debugMode)
        setInfoBox(iArray[i].split(splitString)[0],iArray[i].split(splitString)[1]);
      showOrHideInfoBox(-1,iArray[i].split(splitString)[0],true);
    }
  }  
}

//測試區
//在網頁中加入div元素
//$('#map').append(getOverlayDivObject('popup'));
//$('#map').append(getOverlayDivObject('popup1'));
//在地圖上加入Overlay
//addOverlay(getOverlayObject('popup',countyCenterGeometry[23].split(",")[0],countyCenterGeometry[23].split(",")[1]));
//addOverlay(getOverlayObject('popup',120.32369613464408,23.131734430368596));
//addOverlay(getOverlayObject('popup1',121.01,22.51));
//document.getElementById('popup-content').innerHTML = "測試訊息1";
//document.getElementById('popup1-content').innerHTML = "測試訊息2";
//info.innerHTML = mapArray[0].getLayers().getLength();
//document.getElementById('popup').style.zIndex = "50";