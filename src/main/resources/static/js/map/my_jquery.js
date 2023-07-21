//jQuery 3.2.1
//移除地圖上所有圖層,並清空圖層資料陣列
function removeAllVectorLayers(tIndex){
  if(tIndex != -1 ){
    switch (tIndex) {
      case 0:
        for(i = 0;i < vectorLayers.length;i++) {
          mapArray[tIndex].removeLayer(vectorLayers[i]);
        }
		for(i = 0;i < townVectorLayers.length;i++) {
          mapArray[tIndex].removeLayer(townVectorLayers[i]);
        }
        clearVecLayersData(0);
        break;
      case 1:
        for(i = 0;i < townVectorLayers.length;i++) {
          mapArray[tIndex].removeLayer(townVectorLayers[i]);
        }
        //clearVecLayersData(1);
        break;
    }    
  }else{
    //移除全部
    for(i = 0;i < vectorLayers.length;i++) {
      mapArray[0].removeLayer(vectorLayers[i]);
    }
    for(i = 0;i < townVectorLayers.length;i++) {
      mapArray[0].removeLayer(townVectorLayers[i]);
    }
    clearVecLayersData(0); //my_layers_utils.js
    clearVecLayersData(1);
    //clearVecLayersData(2);
  }
    //clearVecLayersData(0); //my_layers_utils.js
   // clearVecLayersData(1);
  //$('#selectoptions').remove();
  /*
  if(debugMode){
    info.innerHTML = "";
    $('#selectoptions').html('顯示選項<br>');
  }
  */
}

//把圖層陣列中全部的資料加到地圖上
function addAllVectorLayer(tIndex) {
  if(tIndex != -1){
    //addVectorLayer(tIndex);
    for(i = 0;i < vectorLayers.length;i++){
      try {
        addVectorLayer(tIndex,vectorLayers[i]); //my_map_utils.js
      } catch (error) {
        
      }
    }
  }  
}

function addAllTownVectorLayer() {
    for(i = 0;i < townVectorLayers.length;i++){
      try {
        addVectorLayer(0,townVectorLayers[i]); //my_map_utils.js
      } catch (error) {
        
      }
    }
}

function addAllVectorKMLLayer() {
    for(i = 0;i < KMLvectorLayers.length;i++){
      try {
        addVectorLayer(0,KMLvectorLayers[i]); //my_map_utils.js
      } catch (error) {
        
      }
    }  
}
function removeAllVectorKMLLayer() {
    //addVectorLayer(tIndex);
    for(i = 0;i < KMLvectorLayers.length;i++){
      try {
		   mapArray[0].removeLayer(KMLvectorLayers[i]);
      } catch (error) {
        
      }
    }
ClearAllKMLData();
}

function ClearAllKMLData(){
	KMLvectorLayers = []; //read from json file
	KMLvectorLayersId = []; //圖層Id
	KMLshowIdTag = []; //顯示ID
	KMLshowNameTag = []; //顯示名稱
	KMLshowMapIndex = []; //顯示地圖編號(暫時沒用到)
	KMLshowhide= [];
}
//告警閃爍顯示
/*
function toggleUrgent(){
  //info.innerHTML += urgentIndex[0] + ':';
  if(twinkleFlag){
    if(urgentIndex.length > 0){
      //info.innerHTML += '0:' + urgentIndex.length + ':';
      if(tFlag){
        for(i=0;i<urgentIndex.length;i++){
          vectorLayers[urgentIndex[i]].setStyle(vectorStyles[1]);
          vectorLayers[urgentIndex[i]].setZIndex(100);
        }
        tFlag = false;
      }else{
        for(i=0;i<urgentIndex.length;i++){
          vectorLayers[urgentIndex[i]].setStyle(vectorStyles[0]);
          vectorLayers[urgentIndex[i]].setZIndex(0);
        }
        tFlag = true;
      }
    }else{
      //info.innerHTML += '3:';
    }
  }
  //info.innerHTML += '==';
}
*/

//告警ID陣列重設
function resetAllAlarm(){
  for(i=0;i<vectorLayers.length;i++){
    vectorLayers[i].setStyle(vectorStyles[0]);
    vectorLayers[i].setZIndex(0);
  }
  urgentIndex.length = 0;
}

//載入圖資後,顯示指定的圖層
$('#show').click(function() {
  if ( $("input[name='countyselect[]']:checked").length == 0 ) {
    alert('未選擇任何區域');
  }else{
    //隱藏顯示所有圖層
    for(i = 0;i < vectorLayers.length;i++) {
      vectorLayers[i].setVisible(false);
    }
    //alert($("input[name='countyselect[]']:checked").attr("value"));
    $("input[name='countyselect[]']:checked").each(function() {
      //alert($(this).attr("value"));
      //alert(countyJsonFiles.indexOf($(this).attr("value")));

      //只顯示指定圖層
      vectorLayers[vectorLayersId.indexOf($(this).attr("value"))].setVisible(true);
    });

    /*
    map.getView().setCenter(ol.proj.fromLonLat([120.3, 23.8]));
    map.getView().setZoom(7);
    */
  }
});

//清空地圖上所有圖層及所有圖層資料陣列
$('#remove').click(function() {
  removeAllVectorLayers(0); //移除全部
  //mapArray[0].getLayers().removeAt(3); //ok  
  //mapArray[0].removeLayer(vectorLayers[2]); //ok,不存在的圖層看起來沒有error
  //info.innerHTML += ":" + mapArray[0].getLayers().getLength() + ":" + vectorLayers.length;
});

//隱藏所有圖層
$('#hideall').click(function() {
  /*
  $("input[name='countyselect[]']").each(function() {
    $(this).prop("checked", false);
  });
  */

  //hideAllLayers();
  
});
//var x = getVectorStyle1(141,235,0,0.6,'#8cea00','#FF8800',5);
var x = getVectorStyle1(141,235,0,0.6,'#8cea00','#33FF35',3);
function myOwnArea(){
	//var x = getVectorStyle1(141,235,0,0.3,'#8cea00','#FF8800',5);
	//console.log(countyUrgentLevelArray5.length);
	//console.log(vectorLayersId.indexOf(countyUrgentLevelArray5[i]));
	 for(i = 0;i<countyUrgentLevelArray5.length;i++){
                try{
					setInfoBox(countyUrgentLevelArray5[i],"<a onclick='showIbox_2(\"aaa.jsp?"+countyData5[i].split(";")[1]+"\")'>"+countyName[vectorLayersId.indexOf(countyUrgentLevelArray5[i])]+"<br>"+countyData5[i].split(";")[0]+"<br><div align='right' style='font-size:xx-small'><font color='brown'>更多資訊</font></div></a>");
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray5[i])].setStyle(x);
                    vectorLayers[vectorLayersId.indexOf(countyUrgentLevelArray5[i])].setZIndex(12);
					document.getElementById(countyUrgentLevelArray5[i] + '-popup').style.display = 'block';
                }catch(err){}                
            } 
}

$('#showtest').click(function() {
  ShowNow('10009001');
  ShowNow('1001406');
});

$('#hidetest').click(function() {
  HideNow('10009001');
  HideNow('1001406');
});

$('#remove').click(function() {
  removeAllVectorLayers(0); //移除全部
  //mapArray[0].getLayers().removeAt(3); //ok  
  //mapArray[0].removeLayer(vectorLayers[2]); //ok,不存在的圖層看起來沒有error
  //info.innerHTML += ":" + mapArray[0].getLayers().getLength() + ":" + vectorLayers.length;
});


function HideNow(x){
	if(vectorLayersId.indexOf(x) < 0){
		townVectorLayers[townVectorLayersId.indexOf(x)].setVisible(false);;
	}else{
		vectorLayers[vectorLayersId.indexOf(x)].setVisible(false);
	}
	document.getElementById(x + '-popup').style.display = 'none';
}

function ShowNow(x){
	if(vectorLayersId.indexOf(x) < 0){
		townVectorLayers[townVectorLayersId.indexOf(x)].setVisible(true);;
	}else{
		vectorLayers[vectorLayersId.indexOf(x)].setVisible(false);
	}
	//vectorLayers[vectorLayersId.indexOf(x)].setVisible(true);
	document.getElementById(x + '-popup').style.display = 'block';
}

function myOwnAreaTown(){
	//townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray1[i])].setStyle(vectorStyles[2]);
	//console.log(townUrgentLevelArray5[0].substring(0,5));
	//console.log(townUrgentLevelArray5.length);
	//console.log(townVectorLayersId.length);
	 for(i = 0;i<townUrgentLevelArray5.length;i++){
                try{
					setInfoBox(townUrgentLevelArray5[i],"<a onclick='showIbox_2()'>"+countyName[vectorLayersId.indexOf(townUrgentLevelArray5[i].substring(0,5)+"001")]+":"+townJsonFiles[townVectorLayersId.indexOf(townUrgentLevelArray5[i])].split(",")[3]+"<br>"+townData5[i].split(";")[0]+"<br><div align='right' style='font-size:xx-small'><font color='brown'>更多資訊</font></div></a>");
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray5[i])].setStyle(x);
                    townVectorLayers[townVectorLayersId.indexOf(townUrgentLevelArray5[i])].setZIndex(12);
					document.getElementById(townUrgentLevelArray5[i] + '-popup').style.display = 'block';
                }catch(err){}                
            } 
}

function prepareVectorStylesArray(){
  clearAllVectorStyles();

  addToVectorStylesArray(0,0,188,0.1,'#FFFF00');//縣市地圖
  //addToVectorStylesArray1(255,255,255,0.0,'#FFFFFF',"#FFFFFF",1);//縣市地圖
  addToVectorStylesArray(0,214,188,0.2,'#FFFFFF');//鄉鎮區地圖
  addToVectorStylesArray(255,255,0,0.2,'#FFFF00');//黃
  addToVectorStylesArray(255,136,0,0.2,'#FF8800');//橘
  addToVectorStylesArray(255,0,0,0.2,'#FF0000');//紅
  addToVectorStylesArray(0,0,0,0.2,'#000000');//黑
  addToVectorStylesArray(0,115,230,0.2,'#0072e3');//藍(業者顏色)
  addToVectorStylesArray(141,235,0,0.2,'#FF8800');
  addToVectorStylesArray1(255,255,255,0.0,'#FFFFFF',"#FF8800",1);
  vectorStyles[vectorStyles.length] = getVectorStyle1(141,235,0,0.1,'#FFFFFF','#FF8800',2);
}

function prepareVectorArray(){
  //移除所有marker,以地圖為單位
  clearAllInfoBox(0);
  clearAllInfoBox(1);
}

var KML_List=[];
var countyUrgentLevelArray5=[];
var countyData5=[];
var townUrgentLevelArray5=[];
var townData5=[];
//載入其他資料
function preparePlugin(){
  //纜線
  if(cableMapEnable)
    prepareCableVectorArray(); //纜線,my_map_cable_utils.js

  //setAltShiftDragRotateActive(0,false);
  //setDragPanActive(0,false);
  //setDragZoomActive(0,false);
  //setMouseWheelZoomActive(0,false);
  //setDoubleClickZoomActive(0,false);
}

function preparePlugin_Color(q){
  //纜線
  if(cableMapEnable)
    prepareCableVectorArray_Color(q); //纜線,my_map_cable_utils.js

  //setAltShiftDragRotateActive(0,false);
  //setDragPanActive(0,false);
  //setDragZoomActive(0,false);
  //setMouseWheelZoomActive(0,false);
  //setDoubleClickZoomActive(0,false);
}

$('#cleardata').click(function() {
  //清空地圖上所有圖層及所有圖層資料陣列
  removeAllVectorLayers(0);
  hideAllCableLayers(0);
  clear_Marker(); 
  clearCableVecLayersData(0);
});

//依照基地臺資料,抓取各cell 的kml 檔案...組成 KML_List 的陣列, 再RUN Add_KMLLayers(path)

//KML_List=["5G_TWM_台信NR080743000_1_3500_U.kml","5G_TWM_台信NR080743000_2_3500_U.kml","5G_TWM_台信NR080743000_3_3500_U.kml"];
//KML_List = ["5G_TWM_NR080743000_3_3500_R.kml"];

function Add_KMLLayers(kmlpath){
	//kmlpath 指kml所在路徑
	//alert(KML_List[0]);
	var mypath = kmlpath ;
	if (mypath.length > 0) mypath = mypath+"/";
	for (var i=0 ; i < KML_List.length ; i++){
		addKMLToVectorArray(mypath+KML_List[i],0,0,0,KML_List[i].split(".")[0],'kml');
	}
	addAllVectorKMLLayer();
}

//var 
$('#showdata1').click(function() {
	clearAllData();
	clearAllUrgentLevelArray();
	removeAllVectorKMLLayer();
	//prepareVectorArray();
	//addKMLToVectorArray("test1_UMi.kml",0,0,0,"test1_UMi",'kml');
	//console.log(KMLvectorLayers.length);
	//addKMLToVectorArray("5G_TWM_NR080743000_3_3500_R.kml",0,0,0,"5G_TWM_NR080743000_3_3500_R",'kml');
	Add_KMLLayers("");
	
	//addAllVectorKMLLayer();
  //清空地圖上所有圖層及所有圖層資料陣列
  //showLandingStation = "A13,yl,台信NR080743000,120.297822,22.641672,#ce08d1<w>A07,gn,遠傳GC31000039,120.57572093398119,24.076103065212465,#141354<w>A02,gn,遠傳GA20000086,121.49355791005166,24.988047854044794,#e33522<w>A12,yl,台信NR080746000,120.37170075615701,22.660655327259477,#4c7506";
  //showLandingStation = "A11,gn,台信NR080651000,120.314705,22.613221,#ce08d1<w>A12,gn,台信NR080743000,120.297737,22.641717,#ce08d1<w>A13,gn,台信NR0811B1000,120.325387,22.727312,#ce08d1<w>A14,gn,台信NR0802D1000,120.326956,22.621373,#ce08d1<w>A15,gn,台信NR080410000,120.267711,22.627097,#ce08d1<w>A16,gn,台信NR080743000,120.297822,22.641672,#ce08d1<w>A01,gn,台信NR080786000,120.302887,22.650452,#ce08d1<w>A02,gn,台信NR081394000,120.306331,22.665765,#ce08d1<w>A03,gn,台信NR081170000,120.312593,22.728616,#ce08d1<w>A04,gn,台信NR080716000,120.285245,22.64414,#ce08d1<w>A05,gn,台信NR080203000,120.315688,22.627584,#ce08d1<w>A06,gn,台信NR0802D9000,120.308363,22.61616,#ce08d1<w>A07,gn,台信NR080512000,120.268968,22.61281,#ce08d1<w>A08,gn,台信NR0807K9000,120.33187,22.653438,#ce08d1<w>A09,gn,台信NR081303000,120.287811,22.680857,#ce08d1<w>A10,gn,台信NR083060000,120.375087,22.62597,#ce08d1";
  //countyUrgentLevelArray2=["10004001","63000001"];
  //showLandingStation ="A11,gn,國立教育廣播電臺高雄分台<br>3300MHz~3340MHz(台灣之星),120.316813,22.6133871,#000000";
  //KML_List=["5G_TWM_台信NR080743000_1_3500_U.kml","5G_TWM_台信NR080743000_2_3500_U.kml","5G_TWM_台信NR080743000_3_3500_U.kml"];
  KML_List=["Liu98_100m.kml"];
  showLandingStation ="A11,gn,3300MHz~3340MHz(台灣之星),120.316813,22.6133871,#000000";
  countyData5=["3300MHz~3340MHz(台灣之星);id=1233&qq=333","3300MHz~3340MHz(台灣之星);id=333&qq=222"];
  countyUrgentLevelArray5=["68000001","10009001"];
  townData5=["3300MHz~3340MHz(台灣之星);id=122&qq=333","3300MHz~3340MHz(台灣之星);id=1243&qq=222"]
  townUrgentLevelArray5=["1000210","1001406"];
  myOwnArea();
  myOwnAreaTown();
  showNewData();

});
//var SampleLine ="121.543767<w>25.041629<w>121.5516552<w>24.0416218<w>0<w>255<w>0<w>0<w>#FF0000<w>3<w>line0003<w><font size='3'>POP Message..<a href='#' onclick=\"showInfo_Right();\">Detail</a></font>";
var SampleLine ="121.49355791005166,24.988047854044794,120.37170075615701,22.660655327259477<w>0<w>255<w>0<w>0<w>#00ff00<w>3<w>line0003<w><font size='3'>POP Message..<a href='#' onclick=\"showInfo_Right();\">Detail</a></font>"
SampleLine +="<wisoft>121.49355791005166,24.988047854044794,121.50456791005166,24.979057854044794<w>0<w>255<w>0<w>0<w>#3385ff<w>3<w>line0003<w><font size='3'>POP Message..<a href='#' onclick=\"showInfo_Right();\">Detail</a></font>"
SampleLine += "<wisoft>120.38271075615701,22.671665327259477,121.50456791005166,24.979057854044794<w>0<w>255<w>0<w>0<w>#3385ff<w>3<w>line0003<w><font size='3'>POP Message..<a href='#' onclick=\"showInfo_Right();\">Detail</a></font>"
SampleLine += "<wisoft>120.38271075615701,22.671665327259477,120.37170075615701,22.660655327259477<w>0<w>255<w>0<w>0<w>#3385ff<w>3<w>line0003<w><font size='3'>POP Message..<a href='#' onclick=\"showInfo_Right();\">Detail</a></font>"
SampleLine +="<wisoft>120.40353811179641,22.5187114364919,120.37170075615701,22.660655327259477<w>0<w>255<w>0<w>0<w>#00ff00<w>3<w>line0003<w><font size='3'>POP Message..<a href='#' onclick=\"showInfo_Right();\">Detail</a></font>"

//var FanData = "12345610<w>121.4935579100<w>24.9880478540<w>330<w>120<w>0<w>30<w>#FFFF0080";	
//FanData += "<wisoft>123451120<w>121.4935579100<w>24.9880478540<w>330<w>120<w>120<w>30<w>#FFFF0080";
//FanData += "<wisoft>123451240<w>121.4935579100<w>24.9880478540<w>330<w>120<w>240<w>30<w>#FFFF0080";
//FanData += "<wisoft>12345620<w>121.4035381117<w>23.5187114364<w>330<w>120<w>0<w>30<w>#FFFF0080";
var FanData = "120.551316<w>23.92227<wisoft>121.53778<w>25.061361<wisoft>121.578487<w>25.048625<wisoft>121.379434<w>25.079611<wisoft>121.569057<w>25.078567<wisoft>121.438073<w>24.973529<wisoft>120.338778<w>22.631991<wisoft>120.277797<w>23.019529<wisoft>121.486728<w>24.996813<wisoft>120.65749<w>24.17074<wisoft>120.67984<w>24.1016<wisoft>121.13243<w>22.750265<wisoft>120.88562<w>24.703504<wisoft>120.694451<w>24.170905<wisoft>120.194869<w>22.971676<wisoft>121.497357<w>25.061594<wisoft>121.710548<w>25.097677<wisoft>120.296814<w>22.789531<wisoft>120.208896<w>23.021627<wisoft>121.540967<w>25.046415<wisoft>121.579167<w>25.059633<wisoft>121.588313<w>25.054583<wisoft>121.42973<w>25.03073<wisoft>121.529278<w>25.040424<wisoft>121.502544<w>25.040845<wisoft>120.430943<w>23.479356<wisoft>119.500806<w>23.356448<wisoft>119.421917<w>23.197898<wisoft>118.334233<w>24.451826<wisoft>121.47872<w>25.01934<wisoft>121.551935<w>25.047984<wisoft>121.570978<w>25.045879<wisoft>121.56989<w>25.079135<wisoft>121.559467<w>25.049984<wisoft>121.570346<w>25.079319<wisoft>121.578998<w>25.06165<wisoft>121.518526<w>25.045669<wisoft>121.468093<w>25.046745<wisoft>121.507078<w>25.00761<wisoft>121.020471<w>24.781718<wisoft>121.523312<w>25.05693<wisoft>120.19777<w>22.98788<wisoft>121.297466<w>25.024883<wisoft>121.544606<w>25.052176<wisoft>121.529956<w>25.062906<wisoft>121.533419<w>25.054514<wisoft>121.605504<w>25.05289<wisoft>121.527092<w>25.051809<wisoft>121.527011<w>25.05158<wisoft>120.65728<w>24.1591<wisoft>121.523027<w>25.055775<wisoft>121.517267<w>25.049509<wisoft>121.549383<w>25.026683<wisoft>121.007026<w>22.691785<wisoft>120.664866<w>24.149955<wisoft>120.907964<w>23.862704<wisoft>121.537476<w>25.045211<wisoft>120.210958<w>22.9971<wisoft>121.217083<w>24.964985<wisoft>121.774958<w>24.831196<wisoft>120.624674<w>24.17995<wisoft>120.977418<w>24.806915<wisoft>121.53361<w>25.023741<wisoft>121.523039<w>25.05389<wisoft>120.3055<w>22.61043<wisoft>121.360179<w>25.066283<wisoft>121.567641<w>25.038176<wisoft>121.559114<w>25.084196<wisoft>121.560954<w>25.044577<wisoft>121.530527<w>25.044034<wisoft>120.62393<w>24.179323<wisoft>120.290822<w>22.624247<wisoft>121.527114<w>25.0796<wisoft>120.92337<w>23.87078<wisoft>121.525638<w>25.067989<wisoft>121.53331<w>25.048899<wisoft>120.721848<w>24.392316<wisoft>121.516606<w>25.046116<wisoft>121.567544<w>25.038728<wisoft>121.221703<w>25.068482<wisoft>120.30156<w>22.62439<wisoft>120.637273<w>24.165273<wisoft>121.755512<w>24.82775<wisoft>121.548305<w>25.052743<wisoft>121.62782<w>24.0002<wisoft>121.52233<w>25.044487<wisoft>121.533903<w>24.987618<wisoft>121.537343<w>25.025165<wisoft>121.457677<w>25.037853<wisoft>121.501509<w>25.044498<wisoft>121.500056<w>25.033503<wisoft>121.545905<w>25.058139<wisoft>121.326651<w>24.88224<wisoft>121.529112<w>25.09302<wisoft>121.5235<w>25.038003<wisoft>120.670436<w>24.133254<wisoft>121.525871<w>25.061028<wisoft>121.544002<w>25.034052<wisoft>121.027735<w>24.820443<wisoft>121.547428<w>25.052021<wisoft>121.54603<w>25.043013<wisoft>121.525799<w>25.057686<wisoft>120.808614<w>21.95582<wisoft>121.362617<w>25.060307<wisoft>121.369724<w>25.060485<wisoft>121.600209<w>25.004218<wisoft>121.536954<w>25.036679<wisoft>121.94241<w>25.018264<wisoft>120.214245<w>22.996481<wisoft>121.62032<w>23.98172<wisoft>121.523479<w>25.052547<wisoft>121.521417<w>25.037445<wisoft>120.69507<w>24.1317<wisoft>120.246171<w>22.998327<wisoft>121.613186<w>23.982149<wisoft>120.28692<w>22.69415<wisoft>119.56297<w>23.5663<wisoft>121.097863<w>25.036424<wisoft>121.538482<w>25.186936<wisoft>121.735459<w>24.753352<wisoft>121.23414<w>24.932476<wisoft>120.994906<w>24.927779<wisoft>120.74451<w>24.39552<wisoft>120.486786<w>23.32773<wisoft>120.43292<w>22.83032<wisoft>120.5447<w>23.70337<wisoft>121.143454<w>22.751118<wisoft>121.177154<w>23.099931<wisoft>121.857841<w>24.606022<wisoft>118.409289<w>24.460395<wisoft>120.34734<w>22.64956<wisoft>121.511038<w>25.08807<wisoft>121.711795<w>24.751639<wisoft>120.66394<w>24.15344<wisoft>121.565754<w>25.040768<wisoft>121.551102<w>25.040948<wisoft>121.532927<w>25.060242<wisoft>121.52453<w>25.054178<wisoft>120.296038<w>22.619338<wisoft>121.549441<w>25.033959<wisoft>121.463185<w>25.01126<wisoft>121.506609<w>25.135919<wisoft>120.663363<w>24.155977<wisoft>121.545149<w>25.052901<wisoft>121.833917<w>25.092057<wisoft>120.98577<w>24.17686<wisoft>120.85376<w>23.94972<wisoft>120.870098<w>23.739759<wisoft>120.87637<w>23.72534<wisoft>120.87693<w>23.66816<wisoft>120.857096<w>23.527934<wisoft>121.088583<w>24.017315<wisoft>120.60795<w>23.59363<wisoft>120.74475<w>23.59052<wisoft>120.536923<w>23.071974<wisoft>120.592987<w>23.028669<wisoft>120.676197<w>22.526048<wisoft>121.378112<w>23.641711<wisoft>120.63906<w>23.75389<wisoft>121.42608<w>23.74081<wisoft>121.03775<w>22.72507<wisoft>120.91046<w>22.38972<wisoft>121.504648<w>25.042613<wisoft>121.472963<w>25.123504<wisoft>121.600672<w>24.054739<wisoft>121.592691<w>23.995885<wisoft>120.722929<w>24.195066<wisoft>120.295939<w>22.633405<wisoft>121.505719<w>25.045118<wisoft>120.401456<w>22.729815<wisoft>121.542855<w>25.037688<wisoft>121.56219<w>25.035246<wisoft>121.40498<w>25.084049<wisoft>121.514127<w>25.037519<wisoft>121.781318<w>25.071607<wisoft>121.628585<w>23.98861<wisoft>120.855029<w>21.900965<wisoft>116.729305<w>20.698481<wisoft>121.530989<w>25.01764<wisoft>121.587986<w>25.287049<wisoft>121.662363<w>25.202465<wisoft>120.750431<w>21.958595<wisoft>120.948136<w>24.272967<wisoft>121.28694<w>24.16149<wisoft>120.62269<w>24.19377<wisoft>121.511376<w>25.032519<wisoft>121.558263<w>25.165665<wisoft>121.857841<w>24.606022<wisoft>121.786847<w>24.45894<wisoft>121.735053<w>25.138955<wisoft>118.409289<w>24.460395<wisoft>119.948889<w>26.163889<wisoft>120.57322<w>24.06003<wisoft>120.321154<w>22.613659<wisoft>119.582326<w>23.555567<wisoft>121.630965<w>24.006303<wisoft>121.353684<w>23.460381<wisoft>121.15599<w>22.76353<wisoft>121.103478<w>22.823535<wisoft>120.26253<w>22.64295<wisoft>120.482664<w>23.322325<wisoft>121.566846<w>25.142803<wisoft>120.65848<w>22.270149<wisoft>121.485906<w>25.00905<wisoft>121.598773<w>25.056266<wisoft>121.55194<w>25.087937<wisoft>121.539844<w>25.187055<wisoft>121.184588<w>24.867209<wisoft>120.978264<w>24.783162<wisoft>120.743993<w>24.393111<wisoft>120.63652<w>23.94171<wisoft>120.60059<w>23.56432<wisoft>120.487951<w>23.327334<wisoft>120.422699<w>22.816265<wisoft>121.582958<w>25.068775<wisoft>121.610391<w>25.074968<wisoft>119.56506<w>23.56429<wisoft>121.316742<w>23.343382<wisoft>119.94365<w>26.157399<wisoft>120.684996<w>24.097414<wisoft>120.67999<w>24.10166<wisoft>120.674179<w>23.923496<wisoft>121.569113<w>25.078848<wisoft>120.53245<w>24.045896<wisoft>121.55018<w>25.04767<wisoft>121.55612<w>25.04374<wisoft>121.5404<w>25.04609<wisoft>121.51737<w>25.01626<wisoft>121.55796<w>25.04564<wisoft>120.31098<w>22.61871<wisoft>121.0422<w>23.17326<wisoft>120.40112<w>22.73123<wisoft>120.87048<w>24.28241<wisoft>120.6658<w>24.14244<wisoft>121.55234<w>25.08049<wisoft>120.65541<w>24.15857<wisoft>121.52521<w>25.06604<wisoft>121.47102<w>22.67407<wisoft>121.2923<w>24.98733<wisoft>121.49407<w>24.18084<wisoft>121.58129<w>25.04927<wisoft>120.647499<w>24.170894<wisoft>121.581971<w>25.065619<wisoft>121.64885<w>25.06049<wisoft>121.00999<w>24.86074<wisoft>120.14877<w>22.99932<wisoft>121.527353<w>25.077808<wisoft>120.168837<w>23.535396<wisoft>121.41634<w>25.18565<wisoft>120.68729<w>24.13436<wisoft>120.1992<w>22.98749<wisoft>121.5186676<w>25.0647638<wisoft>121.557221<w>25.0423255<wisoft>121.5145175<w>25.1378571<wisoft>121.56632<w>25.08182<wisoft>120.99491<w>24.77747<wisoft>121.020216<w>24.840075<wisoft>121.012249<w>24.839791<wisoft>121.51422<w>24.99264<wisoft>121.51402<w>25.04536<wisoft>120.27784<w>22.65668<wisoft>120.68716<w>24.13599<wisoft>12.532656<w>25.056578<wisoft>121.48939<w>24.99687<wisoft>121.03815<w>24.8107<wisoft>121.01776<w>24.81688<wisoft>121.37332<w>25.07635<wisoft>121.01976<w>24.81656<wisoft>121.30132<w>25.01604<wisoft>121.30144<w>25.01592<wisoft>121.37331<w>25.07633<wisoft>121.55825<w>25.08059<wisoft>121.45993<w>25.15535<wisoft>118.419604<w>24.439468<wisoft>121.52006<w>25.04247<wisoft>121.49844<w>25.12865<wisoft>121.00065<w>24.78979<wisoft>120.68518<w>24.14277<wisoft>121.48524<w>24.99482<wisoft>121.51114<w>25.03348<wisoft>121.5068<w>25.03653";

//載入圖層資訊到圖層資料陣列,並顯示於地圖上
$('#showdata').click(function() {
	clearAllData();
	clearAllUrgentLevelArray();
	removeAllVectorKMLLayer();
	prepareVectorArray();
  //清空地圖上所有圖層及所有圖層資料陣列

});

$('#clearpoint').click(function(){
	clearpoint('A12');
		//document.getElementById(x+'-popup').style.display = 'none';
		
		for(var a=0;a < KMLvectorLayers.length ; a++){
			//alert(KMLshowIdTag[a]);
			if(KMLshowIdTag[a].indexOf('台信NR080743000')>0){
				KMLvectorLayers[a].setVisible(false);
			}
		}	
});

$('#showpoint').click(function(){
	showpoint('A12');
	//document.getElementById(x+'-popup').style.display = 'block';
	for(var a=0;a < KMLvectorLayers.length ; a++){
		if(KMLshowIdTag[a].indexOf('台信NR080743000')>0){
			KMLvectorLayers[a].setVisible(true);
		}
	}
});

$('#gobackmap').click(function() {
  mapShow();
});

$('#marker1').click(function() {
  //setInfoBox('10013001','屏東縣');
  //hideAllCountyInfoBox();
  //hideAllTownInfoBox();
});

$('#marker2').click(function() {
  //setInfoBox('1000802','屏東縣');
  /*
  for(i=0;i<townJsonFiles.length;i++) {
    setInfoBox(townJsonFiles[i].split(",")[0].split("/")[2].split(".")[0],townJsonFiles[i].split(",")[3]);
  }
  */
});

$('#changename').click(function() {
  //alert('ok');
  changeByName('仁愛鄉');
});

function clearAllData(){
  //removeAllVectorLayers(0);
  //removeAllVectorLayers(1);
  hideAllCountyInfoBox();
  hideAllTownInfoBox();
  clear_Marker(); 
  clearCableVecLayersData(0);
}

function showNewData(){
  //preparePlugin();
 // prepareLandingOnlyVectorArray();
  //ShowLandingOnlyVectorArray();
  show_Marker();
  //mapShow();
}

function changeByName(tName){
  //alert(tName);
  //alert(detailInMapIdTag[detailInMapNameTag.indexOf(tName)]);
  try{
    townVectorLayers[townVectorLayersId.indexOf(detailInMapIdTag[detailInMapNameTag.indexOf(tName)])].setStyle(vectorStyles[7]);
  }catch(err){
    alert(err);
  }                
}

//========以下測試用========
function showMapDebugInfo(){
  var NowDate = new Date();
  //$('#mapinfo').html(NowDate.getHours() + ":" + NowDate.getMinutes() + ":" + NowDate.getSeconds());
  document.getElementById('mapinfo').innerHTML = "Time:" + NowDate.getHours() + ":" + NowDate.getMinutes() + ":" + NowDate.getSeconds();
  document.getElementById('mapinfo').innerHTML += "<br>map[0]:" + mapArray[0].getLayers().getLength() + ":" + mapArray[0].getOverlays().getLength();
  document.getElementById('mapinfo').innerHTML += "<br>map[1]:" + mapArray[1].getLayers().getLength() + ":" + mapArray[1].getOverlays().getLength();
  document.getElementById('mapinfo').innerHTML += "<br>detailInMapIdTag:" + detailInMapIdTag.length;
}

function mapShow(){
  $('#map').show();
  mapArray[0].updateSize();
  $('#map1').hide();
}

function map1Show(){
  $('#map').hide();
  /*
  for(i=0;i<townJsonFiles.length;i++) {
    setInfoBox(townJsonFiles[i].split(",")[0].split("/")[2].split(".")[0],townJsonFiles[i].split(",")[3]);
  }
  */
  $('#map1').show();
  mapArray[1].updateSize();  
}

function fakeGetInfoBoxString(){
  $.ajax({
    type: "POST",
    //async為false -> 同步 
    //async為true  -> 非同步
    async: true,
    //預期Server傳回的資料類型,如果沒指定,jQuery會根據HTTP MIME Type自動選擇以responseXML或responseText傳入你的success callback
    /*
    xml:傳回可用jQuery處理的XML
    html:傳回HTML,包含script tags
    script:傳回可執行的JavaScript
    json:傳回JSON
    jsonp:JSONP格式,在URL加上?callback=?參數,並在Server端配合送回此jsonp callback
    text:傳回純文字字串
    */
    //dataType: "text",
    url:"infobox.html",
    success: function (data, textStatus, xhr) {
      //請求成功
      //info.innerHTML = "收到InfoBox訊息:" + data + ":" + textStatus + ":" + xhr.status;
      var dataArray = data.split("<widata>");
      receiveInfoBoxArray(dataArray,"<wi>");
    },
    error: function (xhr, ajaxOptions, thrownError) {
      //通常ajaxOptions或thrownError只有一個有值
      //info.innerHTML = "收到InfoBox訊息:" + xhr.status;
    },
    complete: function (XMLHttpRequest, textStatus) {
      //請求完成時執行的函式(不論結果是success或error)
    }
  });
}

function fakeGetUrgentString(){
  $.ajax({
    type: "POST",
    //async為false -> 同步 
    //async為true  -> 非同步
    async: true,
    //預期Server傳回的資料類型,如果沒指定,jQuery會根據HTTP MIME Type自動選擇以responseXML或responseText傳入你的success callback
    /*
    xml:傳回可用jQuery處理的XML
    html:傳回HTML,包含script tags
    script:傳回可執行的JavaScript
    json:傳回JSON
    jsonp:JSONP格式,在URL加上?callback=?參數,並在Server端配合送回此jsonp callback
    text:傳回純文字字串
    */
    //dataType: "text",
    url:"alarm.html",
    success: function (data, textStatus, xhr) {
      //請求成功
      //info.innerHTML = "收到InfoBox訊息:" + data + ":" + textStatus + ":" + xhr.status;
      console.log("收到Urgent訊息:" + data);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      //通常ajaxOptions或thrownError只有一個有值
      //info.innerHTML = "收到InfoBox訊息:" + xhr.status;
    },
    complete: function (XMLHttpRequest, textStatus) {
      //請求完成時執行的函式(不論結果是success或error)
    }
  });
}

function showpoint(x){
	document.getElementById(x+'-popup').style.display = 'block';
}

function clearpoint(x){
	document.getElementById(x+'-popup').style.display = 'none';
}


function showIbox_1(x,y,z){
		$.colorbox({
                href:x,
                width:1800,
                height:800,
                iframe:true
            });
	}

	function showIbox_2(x,y,z){
			$.colorbox({
                href:x,
                width:800,
                height:800,
                iframe:true
            });
	}
	