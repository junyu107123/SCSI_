//jQuery 3.2.1
var urgentIndex = [];

var urgentId = 0;
var twinkleFlag = true;
var tFlag = true;

function removeAllVectorLayers(){
  //map.removeOverlay();
  //map.removeLayer(vectorLayers[0]);
  for(i = 0;i < vectorLayers.length;i++) {
    map.removeLayer(vectorLayers[i]);
  }
  vectorLayers.length = 0;
  vectorLayersId.length = 0;
  vectorStyles.length = 0;
  showIdTag.length = 0;
  showNameTag.length = 0;

  urgentIndex.length = 0;

}

function removeAllVectorStyles(){
  vectorStyles.length = 0;
}

function addToVectorArray(jsonsource,styleindex,idTag,nameTag){
  showIdTag[vectorLayers.length] = idTag;
  showNameTag[vectorLayers.length] = nameTag;
  vectorLayers[vectorLayers.length] = getVectorLayer(jsonsource,vectorStyles[styleindex],vectorLayers.length);
}

function addToVectorArray1(jsonsource,styleindex,idTag,nameTag){
  showIdTag[vectorLayers.length] = idTag;
  showNameTag[vectorLayers.length] = nameTag;
  vectorLayers[vectorLayers.length] = getVectorLayer(jsonsource,vectorStyles[styleindex],vectorLayers.length);
}

function addAllVectoryLayer() {
  addVectoryLayer();
}

function addToVectorStylesArray(rColor,gColor,bColor,aColor,sColor){
  vectorStyles[vectorStyles.length] = getVectorStyle(rColor,gColor,bColor,aColor,sColor);
}

function addToVectorStylesArray1(rColor,gColor,bColor,aColor,sColor){
  vectorStyles[vectorStyles.length] = getVectorStyle1(rColor,gColor,bColor,aColor,sColor);
}

function getVectorLayerPosition(targetLayer){
  for(var i = 0;i<vectorLayers.length;i++){
    if( targetLayer === vectorLayers[i]) return i;
  }
  return -1;
}



