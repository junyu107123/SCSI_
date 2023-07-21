//jQuery 3.2.1
//(縣市)告警強度陣列
var countyUrgentLevelArray = [];//藍
var countyUrgentLevelArray1 = [];//黃
var countyUrgentLevelArray2 = [];//橘
var countyUrgentLevelArray3 = [];//紅
var countyUrgentLevelArray4 = [];//黑

//(鄉鎮區)告警強度陣列
var townUrgentLevelArray = [];//藍
var townUrgentLevelArray1 = [];//黃
var townUrgentLevelArray2 = [];//橘
var townUrgentLevelArray3 = [];//紅
var townUrgentLevelArray4 = [];//黑

//(纜線)告警強度陣列
var cableUrgentLevelArray = [];//藍
var cableUrgentLevelArray1 = [];//黃
var cableUrgentLevelArray2 = [];//橘
var cableUrgentLevelArray3 = [];//紅
var cableUrgentLevelArray4 = [];//黑

//纜線登陸點
/*
var cableLandUrgentLevelArray = [];//藍
var cableLandUrgentLevelArray1 = [];//黃
var cableLandUrgentLevelArray2 = [];//橘
var cableLandUrgentLevelArray3 = [];//紅
var cableLandUrgentLevelArray4 = [];//黑
*/

var twinkleFlag = true; //是否閃爍
var urgentId = 0; //閃爍的Inteval-Id
var tFlag = true; //標示目前顯示狀態

var countyUrgentTemp = [];
var townUrgentTemp = [];

//重設所有Urgent資料
function clearAllUrgentLevelArray(){
    clearUrgentLevelArray(0);
    clearUrgentLevelArray(1);
    clearUrgentLevelArray(2);
    //clearUrgentLevelArray(3);
    
    resetAllMapLayerStyle();
	//removeAllVectorLayers(-1);
	//prepareVectorArray();
}

//清除Urgent資料
function clearUrgentLevelArray(mId){
    switch(mId){
        case 0:
            countyUrgentLevelArray.length = 0;
            countyUrgentLevelArray1.length = 0;
            countyUrgentLevelArray2.length = 0;
            countyUrgentLevelArray3.length = 0;
            countyUrgentLevelArray4.length = 0;
            break;
        case 1:
            townUrgentLevelArray.length = 0;
            townUrgentLevelArray1.length = 0;
            townUrgentLevelArray2.length = 0;
            townUrgentLevelArray3.length = 0;
            townUrgentLevelArray4.length = 0;
            break;
        case 2:
            cableUrgentLevelArray.length = 0;
            cableUrgentLevelArray1.length = 0;
            cableUrgentLevelArray2.length = 0;
            cableUrgentLevelArray3.length = 0;
            cableUrgentLevelArray4.length = 0;
            break;
            /*
        case 3:
            cableLandUrgentLevelArray.length = 0;
            cableLandUrgentLevelArray1.length = 0;
            cableLandUrgentLevelArray2.length = 0;
            cableLandUrgentLevelArray3.length = 0;
            cableLandUrgentLevelArray4.length = 0;
            break;
            */
    }
}

function resetAllMapLayerStyle(){
    resetMapLayerStyle(1);
    resetMapLayerStyle(0);
    resetMapLayerStyle(2);
    //resetMapLayerStyle(3);
}

function resetMapLayerStyle(mId){
    switch(mId){
        case 0:
            for(i = 0;i<vectorLayers.length;i++){
                try{
                    vectorLayers[i].setStyle(vectorStyles[9]);
                    vectorLayers[i].setZIndex(0);
                }catch(err){}
            }
            break;
        case 1:
            for(i = 0;i<townVectorLayers.length;i++){
                try{
                    townVectorLayers[i].setStyle(vectorStyles[8]);
                    townVectorLayers[i].setZIndex(0);
                }catch(err){}        
            }
            break;
        case 2:
            for(i = 0;i<cableVectorLayers.length;i++){
                try{
                    cableVectorLayers[i].setStyle(vectorStyles[1]);
                    cableVectorLayers[i].setZIndex(0);
                }catch(err){}        
            }
            break;
            /*
        case 3:
            for(i = 0;i<cableLandVectorLayers.length;i++){
                try{
                    cableLandVectorLayers[i].setStyle(vectorStyles[1]);
                    cableLandVectorLayers[i].setZIndex(0);
                }catch(err){}        
            }
            break;
            */
    }
}

//檢查並修改所有
function checkAllUrgentLevelArray(){
    if(vectorLayers.length>0){
        if(countyUrgentLevelArray.length > 0){
            for(i = 0;i<countyUrgentLevelArray.length;i++){
                try{
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray[i])].setStyle(vectorStyles[6]);
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray[i])].setZIndex(10);
                    checkTempArray(countyUrgentLevelArray1[i]);
                }catch(err){}                
            }        
        }
		//console.log(countyUrgentLevelArray1.length);
        if(countyUrgentLevelArray1.length > 0){
            for(i = 0;i<countyUrgentLevelArray1.length;i++){
                try{
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray1[i])].setStyle(vectorStyles[2]);
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray1[i])].setZIndex(10);
                    checkTempArray(countyUrgentLevelArray1[i]);
                }catch(err){}                
            }        
        }
        if(countyUrgentLevelArray2.length > 0){
            for(i = 0;i<countyUrgentLevelArray2.length;i++){
                try{
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray2[i])].setStyle(vectorStyles[3]);
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray2[i])].setZIndex(11);
                }catch(err){
                    //alert(err);
                }                
            }        
        }
        if(countyUrgentLevelArray3.length > 0){
            for(i = 0;i<countyUrgentLevelArray3.length;i++){
                try{
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray3[i])].setStyle(vectorStyles[4]);
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray3[i])].setZIndex(12);
                }catch(err){}                
            }        
        }
        if(countyUrgentLevelArray4.length > 0){
            for(i = 0;i<countyUrgentLevelArray4.length;i++){
                try{
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray4[i])].setStyle(vectorStyles[5]);
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray4[i])].setZIndex(13);
                }catch(err){}                
            }        
        }
    }
    
    if(townVectorLayers.length>0){
        if(townUrgentLevelArray.length > 0){
            for(i = 0;i<townUrgentLevelArray.length;i++){
                try{
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray[i])].setStyle(vectorStyles[6]);
                    //townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray2[i])].setZIndex(10);
                }catch(err){}                
            }        
        }

        if(townUrgentLevelArray1.length > 0){
            for(i = 0;i<townUrgentLevelArray1.length;i++){
                try{
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray1[i])].setStyle(vectorStyles[2]);
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray1[i])].setZIndex(10);
                }catch(err){}                
            }        
        }
        if(townUrgentLevelArray2.length > 0){
            for(i = 0;i<townUrgentLevelArray2.length;i++){
                try{
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray2[i])].setStyle(vectorStyles[3]);
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray2[i])].setZIndex(11);
                }catch(err){}
            }        
        }
        if(townUrgentLevelArray3.length > 0){
            for(i = 0;i<townUrgentLevelArray3.length;i++){
                try{
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray3[i])].setStyle(vectorStyles[4]);
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray3[i])].setZIndex(12);
                }catch(err){}                 
            }
        }
        if(townUrgentLevelArray4.length > 0){
            for(i = 0;i<townUrgentLevelArray4.length;i++){
                try{
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray4[i])].setStyle(vectorStyles[5]);
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray4[i])].setZIndex(13);
                }catch(err){}                
            }        
        }
    }

    if(cableVectorLayers.length>0){        
        if(cableUrgentLevelArray.length > 0){
            for(i = 0;i<cableUrgentLevelArray.length;i++){
                try{
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray[i])].setStyle(vectorStyles[6]);
                }catch(err){}                
            }        
        }

        if(cableUrgentLevelArray1.length > 0){
            for(i = 0;i<cableUrgentLevelArray1.length;i++){
                try{
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray1[i])].setStyle(vectorStyles[2]);
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray1[i])].setZIndex(10);
                }catch(err){}                
            }        
        }
        if(cableUrgentLevelArray2.length > 0){
            for(i = 0;i<cableUrgentLevelArray2.length;i++){
                try{
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray2[i])].setStyle(vectorStyles[3]);
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray2[i])].setZIndex(11);
                }catch(err){}
            }
        }
        if(cableUrgentLevelArray3.length > 0){
            for(i = 0;i<cableUrgentLevelArray3.length;i++){
                try{
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray3[i])].setStyle(vectorStyles[4]);
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray3[i])].setZIndex(12);
                }catch(err){}                 
            }
        }
        if(cableUrgentLevelArray4.length > 0){
            for(i = 0;i<cableUrgentLevelArray4.length;i++){
                try{
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray4[i])].setStyle(vectorStyles[5]);
                    cableVectorLayers[cableVectorLayersId.indexOf(cableUrgentLevelArray4[i])].setZIndex(13);
                }catch(err){}
            }        
        }
    }
}

//可用度檢查(忘了這是甚麼)
/*
function checkUrgentLevelArray(){
    if(urgentLevelArray.length > 0){
        return true;
    }else{
        return false;
    }
}
*/

//檢查並顯示強度
/*
function toggleUrgentLevel(uLevel){
    //uLevel:要閃爍第幾個強度,必須小於總長度
    if(checkUrgentLevelArray()){
        if(urgentLevelArray > uLevel){
            if(twinkleFlag){
                //閃爍告警
                if(tFlag){
                    /*
                    for(i=0;i<urgentIndex.length;i++){
                        vectorLayers[urgentIndex[i]].setStyle(vectorStyles[1]);
                        vectorLayers[urgentIndex[i]].setZIndex(50);
                    }
                    *
                    tFlag = false;
                }else{
                    /*
                    for(i=0;i<urgentIndex.length;i++){
                        vectorLayers[urgentIndex[i]].setStyle(vectorStyles[0]);
                        vectorLayers[urgentIndex[i]].setZIndex(0);
                    }
                    *
                    tFlag = true;
                }
            }else{
                //不閃爍告警
            }
        }
    }
}
*/