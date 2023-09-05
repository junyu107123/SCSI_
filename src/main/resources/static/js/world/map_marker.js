var markers = [];
var marker_i = 0 ;
var markerData ;
var APCN2=[{"title": 'Batangas',"lat": 13.748943864545204 ,"lng": 121.047363690575438,"description": 'Philippines'},{"title": 'Chikura',"lat": 34.97047 ,"lng": 139.960195,"description": 'Japan'},{"title": 'Chongming',"lat": 31.531777265297933 ,"lng": 121.8668443669537,"description": 'China'},{"title": 'Katong',"lat": 1.305469978575265 ,"lng": 103.93064017130645,"description": 'Singapore'},{"title": 'Kitaibaraki',"lat": 36.792527 ,"lng": 140.741799,"description": 'Japan'},{"title": 'Kuantan',"lat": 3.767461618707068 ,"lng": 103.342584442946688,"description": 'Malaysia'},{"title": 'Lantau',"lat": 22.22528 ,"lng": 113.929371,"description": 'Hong Kong'},{"title": 'Pusan',"lat": 35.178228 ,"lng": 129.195528,"description": 'South Korea'},{"title": 'Shantou',"lat": 23.345471681904325 ,"lng": 116.747207011516124,"description": 'China'},{"title": 'Shantou',"lat": 23.344494946706519 ,"lng": 116.744416339522402,"description": 'China'},{"title": 'Tanshui',"lat": 25.166667 ,"lng": 121.433333,"description": 'Taiwan'}];
var APG=[{"title": 'Chongming',"lat": 31.531777265297933 ,"lng": 121.8668443669537,"description": 'China'},{"title": 'Da Nang',"lat": 16.014449538459488 ,"lng": 108.261814147472819,"description": 'Vietnam'},{"title": 'Kuantan',"lat": 3.767461618707068 ,"lng": 103.342584442946688,"description": 'Malaysia'},{"title": 'Maruyama',"lat": 35.005963 ,"lng": 139.974752,"description": 'Japan'},{"title": 'Nanhui District',"lat": 30.918752015707323 ,"lng": 121.888173600712321,"description": 'China'},{"title": 'Pusan',"lat": 35.178228 ,"lng": 129.195528,"description": 'South Korea'},{"title": 'Shima',"lat": 34.322629 ,"lng": 136.87935,"description": 'Japan'},{"title": 'Songkhla',"lat": 7.206111 ,"lng": 100.596667,"description": 'Thailand'},{"title": 'Tanah Merah',"lat": 1.320833206176758 ,"lng": 103.959976196289063,"description": 'Singapore'},{"title": 'Toucheng',"lat": 24.859920695879087 ,"lng": 121.825760637889147,"description": 'Taiwan'},{"title": 'Tseung Kwan O',"lat": 22.291632838679245 ,"lng": 114.269988983719671,"description": 'Hong Kong'}];
var SMW3=[{"title": 'Alexandria',"lat": 31.218969523862842 ,"lng": 29.916842886830565,"description": 'Egypt'},{"title": 'Batangas',"lat": 13.748943864545204 ,"lng": 121.047363690575438,"description": 'Philippines'},{"title": 'Chania',"lat": 35.521324005092303 ,"lng": 24.006144643749145,"description": 'Greece'},{"title": 'Da Nang',"lat": 16.014449538459488 ,"lng": 108.261814147472819,"description": 'Vietnam'},{"title": 'Deep Water Bay',"lat": 22.244890120604655 ,"lng": 114.184136358403109,"description": 'Hong Kong'},{"title": 'Djibouti',"lat": 11.573246467810657 ,"lng": 43.162051226289172,"description": 'Republic of Djibouti'},{"title": 'Fangshan',"lat": 22.256900238715026 ,"lng": 120.64681561027794,"description": 'Taiwan'},{"title": 'Goonhilly',"lat": 50.005733333333303 ,"lng": -5.16345,"description": 'United Kingdom'},{"title": 'Jeddah',"lat": 21.531498545943357 ,"lng": 39.141076163881564,"description": 'Saudi Arabia'},{"title": 'Karachi',"lat": 24.819172473500888 ,"lng": 67.012836125812612,"description": 'Pakistan'},{"title": 'Keoje',"lat": 34.778 ,"lng": 128.640536,"description": 'South Korea'},{"title": 'Marmaris',"lat": 36.85 ,"lng": 28.266667,"description": 'Turkey'},{"title": 'Marmaris',"lat": 36.841203777367618 ,"lng": 28.277661944957142,"description": 'Turkey'},{"title": 'Mazara',"lat": 37.667715532070417 ,"lng": 12.541637849672441,"description": 'Italy'},{"title": 'Mazara del Vallo',"lat": 37.635948124471135 ,"lng": 12.545711049515948,"description": 'Italy'},{"title": 'Medan',"lat": 3.734243527870153 ,"lng": 98.799022329088316,"description": 'Indonesia'},{"title": 'Mersing',"lat": 2.435537932417575 ,"lng": 103.859569086537803,"description": 'Malaysia'},{"title": 'Mount Lavinia',"lat": 6.843198157865281 ,"lng": 79.851553950045442,"description": 'Sri Lanka'},{"title": 'Muscat',"lat": 23.627823889914289 ,"lng": 58.629328390632075,"description": 'Oman'},{"title": 'Mumbai',"lat": 19.080606608843734 ,"lng": 72.824369804668237,"description": 'India'},{"title": 'Norden',"lat": 53.668166666665158 ,"lng": 7.282466666666669,"description": 'Germany'},{"title": 'Okinawa',"lat": 26.212411 ,"lng": 127.675053,"description": 'Japan'},{"title": 'Oostende',"lat": 51.219133333331726 ,"lng": 2.88473333333333,"description": 'Belgium'},{"title": 'Penang',"lat": 5.561649999999702 ,"lng": 100.358133300000077,"description": 'Malaysia'},{"title": 'Penmarch',"lat": 47.8395 ,"lng": -4.34436666666667,"description": 'France'},{"title": 'Perth',"lat": -32.046016976193151 ,"lng": 115.731490394278481,"description": 'Australia'},{"title": 'Pyapon',"lat": 16.169767348735526 ,"lng": 95.79204550672975,"description": 'Myanmar'},{"title": 'Satun',"lat": 6.570741109060312 ,"lng": 100.004282941470748,"description": 'Thailand'},{"title": 'Sesimbra',"lat": 38.435653244126534 ,"lng": -9.112826622063263,"description": 'Portugal'},{"title": 'Shanghai',"lat": 31.168591114723039 ,"lng": 121.727000579956211,"description": 'China'},{"title": 'Shantou',"lat": 23.344494946706519 ,"lng": 116.744416339522402,"description": 'China'},{"title": 'Shantou',"lat": 23.345471681904325 ,"lng": 116.747207011516124,"description": 'China'},{"title": 'Suez',"lat": 29.985892102771722 ,"lng": 32.544013176301057,"description": 'Egypt'},{"title": 'Taipa',"lat": 22.187001140273178 ,"lng": 113.551857475636751,"description": 'Macau'},{"title": 'Toucheng',"lat": 24.859920695879087 ,"lng": 121.825760637889147,"description": 'Taiwan'},{"title": 'Tuas',"lat": 1.319392672694984 ,"lng": 103.641213214350287,"description": 'Singapore'},{"title": 'Tuas',"lat": 1.316495062675199 ,"lng": 103.645076694376655,"description": 'Singapore'},{"title": 'Tungku',"lat": 4.743994047135169 ,"lng": 114.576586289933573,"description": 'Brunei'},{"title": 'Tetuan',"lat": 35.621007047827085 ,"lng": -5.272433569675714,"description": 'Morocco'},{"title": 'Yeroskipou',"lat": 34.718839193874878 ,"lng": 32.466102106233684,"description": 'Cyprus'}];
var TSE1=[{"title": 'Tanshui',"lat": 25.166667 ,"lng": 121.433333,"description": 'Taiwan'},{"title": 'Fuzhou',"lat": 25.916667 ,"lng": 119.55,"description": 'China'}];
var TPE=[{"title": 'Chongming',"lat": 31.531777265297933 ,"lng": 121.8668443669537,"description": 'China'},{"title": 'Keoje',"lat": 34.778 ,"lng": 128.640536,"description": 'South Korea'},{"title": 'Maruyama',"lat": 35.005963 ,"lng": 139.974752,"description": 'Japan'},{"title": 'Nedonna Beach',"lat": 45.61323960982083 ,"lng": -123.929396526721064,"description": 'USA'},{"title": 'Qingdao',"lat": 36.039253326398679 ,"lng": 120.348842965402923,"description": 'China'},{"title": 'Tanshui',"lat": 25.166667 ,"lng": 121.433333,"description": 'Taiwan'}];
var NCP=[{"title": 'Chongming',"lat": 31.531777265297933 ,"lng": 121.8668443669537,"description": 'China'},{"title": 'Maruyama',"lat": 35.005963 ,"lng": 139.974752,"description": 'Japan'},{"title": 'Nanhui District',"lat": 30.918752015707323 ,"lng": 121.888173600712321,"description": 'China'},{"title": 'Pacific City',"lat": 42.226591701848555 ,"lng": -124.373893440213777,"description": 'USA'},{"title": 'Pusan',"lat": 35.178228 ,"lng": 129.195528,"description": 'South Korea'},{"title": 'Toucheng',"lat": 24.859920695879087 ,"lng": 121.825760637889147,"description": 'Taiwan'}];
var EAC=[{"title": 'Ajigaura',"lat": 36.380301 ,"lng": 140.616147,"description": 'Japan'},{"title": 'Batangas',"lat": 13.748943864545204 ,"lng": 121.047363690575438,"description": 'Philippines'},{"title": 'Changi',"lat": 1.346361810840154 ,"lng": 103.97,"description": 'Singapore'},{"title": 'Chikura',"lat": 34.97047 ,"lng": 139.960195,"description": 'Japan'},{"title": 'Chung Hom Kok',"lat": 22.215960291012578 ,"lng": 114.209483025315279,"description": 'Hong Kong'},{"title": 'Fangshan',"lat": 22.256900238715026 ,"lng": 120.64681561027794,"description": 'Taiwan'},{"title": 'Pali',"lat": 25.279925683577936 ,"lng": 121.574712395542662,"description": 'Taiwan'},{"title": 'Shindu-Ri',"lat": 36.849528439348141 ,"lng": 125.991834341581836,"description": 'South Korea'},{"title": 'Pusan',"lat": 35.178228 ,"lng": 129.195528,"description": 'South Korea'},{"title": 'Qingdao',"lat": 36.039253326398679 ,"lng": 120.348842965402923,"description": 'China'},{"title": 'Shanghai',"lat": 31.168591114723039 ,"lng": 121.727000579956211,"description": 'China'},{"title": 'Shima',"lat": 34.322629 ,"lng": 136.87935,"description": 'Japan'},{"title": 'Tanshui',"lat": 25.166667 ,"lng": 121.433333,"description": 'Taiwan'},{"title": 'Tseung Kwan O',"lat": 22.291632838679245 ,"lng": 114.269988983719671,"description": 'Hong Kong'}];
var EAC1=[{"title": 'Ajigaura',"lat": 36.380301 ,"lng": 140.616147,"description": 'Japan'},{"title": 'Chung Hom Kok',"lat": 22.215960291012578 ,"lng": 114.209483025315279,"description": 'Hong Kong'},{"title": 'Fangshan',"lat": 22.256900238715026 ,"lng": 120.64681561027794,"description": 'Taiwan'},{"title": 'Pali',"lat": 25.279925683577936 ,"lng": 121.574712395542662,"description": 'Taiwan'},{"title": 'Shindu-Ri',"lat": 36.849528439348141 ,"lng": 125.991834341581836,"description": 'South Korea'},{"title": 'Qingdao',"lat": 36.039253326398679 ,"lng": 120.348842965402923,"description": 'China'},{"title": 'Shima',"lat": 34.322629 ,"lng": 136.87935,"description": 'Japan'},{"title": 'Tanshui',"lat": 25.166667 ,"lng": 121.433333,"description": 'Taiwan'},{"title": 'Tseung Kwan O',"lat": 22.291632838679245 ,"lng": 114.269988983719671,"description": 'Hong Kong'}];
var EAC2=[{"title": 'Batangas',"lat": 13.748943864545204 ,"lng": 121.047363690575438,"description": 'Philippines'},{"title": 'Changi',"lat": 1.346361810840154 ,"lng": 103.97,"description": 'Singapore'},{"title": 'Chung Hom Kok',"lat": 22.215960291012578 ,"lng": 114.209483025315279,"description": 'Hong Kong'},{"title": 'Tanshui',"lat": 25.166667 ,"lng": 121.433333,"description": 'Taiwan'},{"title": 'Tseung Kwan O',"lat": 22.291632838679245 ,"lng": 114.269988983719671,"description": 'Hong Kong'}];
var C2C=[{"title": 'Ajigaura',"lat": 36.380301 ,"lng": 140.616147,"description": 'Japan'},{"title": 'Batangas',"lat": 13.748943864545204 ,"lng": 121.047363690575438,"description": 'Philippines'},{"title": 'Changi',"lat": 1.346361810840154 ,"lng": 103.97,"description": 'Singapore'},{"title": 'Chikura',"lat": 34.97047 ,"lng": 139.960195,"description": 'Japan'},{"title": 'Chung Hom Kok',"lat": 22.215960291012578 ,"lng": 114.209483025315279,"description": 'Hong Kong'},{"title": 'Fangshan',"lat": 22.256900238715026 ,"lng": 120.64681561027794,"description": 'Taiwan'},{"title": 'Pali',"lat": 25.279925683577936 ,"lng": 121.574712395542662,"description": 'Taiwan'},{"title": 'Shindu-Ri',"lat": 36.849528439348141 ,"lng": 125.991834341581836,"description": 'South Korea'},{"title": 'Pusan',"lat": 35.178228 ,"lng": 129.195528,"description": 'South Korea'},{"title": 'Qingdao',"lat": 36.039253326398679 ,"lng": 120.348842965402923,"description": 'China'},{"title": 'Shanghai',"lat": 31.168591114723039 ,"lng": 121.727000579956211,"description": 'China'},{"title": 'Shima',"lat": 34.322629 ,"lng": 136.87935,"description": 'Japan'},{"title": 'Tanshui',"lat": 25.166667 ,"lng": 121.433333,"description": 'Taiwan'},{"title": 'Tseung Kwan O',"lat": 22.291632838679245 ,"lng": 114.269988983719671,"description": 'Hong Kong'}];
var FNAL=[{"title": 'Pusan',"lat": 35.178228 ,"lng": 129.195528,"description": 'South Korea'},{"title": 'Tong Fuk',"lat": 22.224505 ,"lng": 113.928274,"description": 'Hong Kong'},{"title": 'Toucheng',"lat": 24.859920695879087 ,"lng": 121.825760637889147,"description": 'Taiwan'},{"title": 'Wada',"lat": 35.026511 ,"lng": 139.994003,"description": 'Japan'}];
var RNAL=[{"title": 'Pusan',"lat": 35.178228 ,"lng": 129.195528,"description": 'South Korea'},{"title": 'Tong Fuk',"lat": 22.224505 ,"lng": 113.928274,"description": 'Hong Kong'},{"title": 'Toucheng',"lat": 24.859920695879087 ,"lng": 121.825760637889147,"description": 'Taiwan'},{"title": 'Wada',"lat": 35.026511 ,"lng": 139.994003,"description": 'Japan'}];
var FASTER=[{"title": 'Bandon',"lat": 43.251167 ,"lng": -124.3845,"description": 'USA'},{"title": 'Chikura',"lat": 34.97047 ,"lng": 139.960195,"description": 'Japan'},{"title": 'Shima',"lat": 34.322629 ,"lng": 136.87935,"description": 'Japan'},{"title": 'Tanshui',"lat": 25.166667 ,"lng": 121.433333,"description": 'Taiwan'}];
var CSCN=[{"title": 'Lake Ci',"lat": 24.455516 ,"lng": 118.289745,"description": 'Taiwan'},{"title": 'Dadeng Island',"lat": 24.556736 ,"lng": 118.300975,"description": 'China'},{"title": 'Guningtou',"lat": 24.476145 ,"lng": 118.307128,"description": 'Taiwan'},{"title": 'Guanyin Mountain',"lat": 24.493919 ,"lng": 118.188964,"description": 'Taiwan'}];
var PLCN=[{"title": 'El Segundo',"lat": 33.919920018514716 ,"lng": -118.41596126184774,"description": 'USA'},{"title": 'Baler',"lat": 15.761539465842048 ,"lng": 121.56018812156228,"description": 'Philippines'},{"title": 'Toucheng',"lat": 24.859920695879087 ,"lng": 121.825760637889147,"description": 'Taiwan'}];

	function setMarkerData(){
		//alert(cableNow);
		if(cableNow == "SMW3") markerData = SMW3 ;
		if(cableNow == "APCN2") markerData = APCN2 ;
		if(cableNow == "APG") markerData = APG ;
		if(cableNow == "TSE-1") markerData = TSE1 ;
		if(cableNow == "TPE") markerData = TPE ;
		if(cableNow == "NCP") markerData = NCP ;
		if(cableNow == "EAC") markerData = EAC ;
		if(cableNow == "EAC1") markerData = EAC1 ;
		if(cableNow == "EAC2") markerData = EAC2 ;
		if(cableNow == "C2C") markerData = C2C ;
		if(cableNow == "FNAL") markerData = FNAL ;
		if(cableNow == "RNAL") markerData = RNAL ;
		if(cableNow == "FASTER") markerData = FASTER ;
		if(cableNow == "CSCN") markerData = CSCN ;
		if(cableNow == "PLCN") markerData = PLCN ;
		//alert('m='+markerData);
		showAllMarker(markerData);
	}
 
	function addmarker(lat,lng,gettitle,pcolor){
		//alert('add :'+lat+'/'+lng+'/'+gettitle+'/'+pcolor);
        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform([lng,lat],'EPSG:4326', 'EPSG:3857')),
            name: gettitle
        });
        var iconStyle = new ol.style.Style({
            image: new ol.style.Icon(({
                anchor: [0.5, 66],
                anchorXUnits: 'fraction',
                anchorYUnits: 'pixels',
                opacity: 0.75,
                src: pcolor
            })),
		  text: new ol.style.Text({
          font: '24px Calibri,sans-serif',
		  offsetX : 0,
		  offsetY : -60,
          text: gettitle,
          fill: new ol.style.Fill({
            color: '#000'
          }),
          stroke: new ol.style.Stroke({
            color: '#00ff00',
            width: 3
          })
        })	
        });
		//alert('is='+iconStyle.toString);
        iconFeature.setStyle(iconStyle);
        vectorSource = new ol.source.Vector({
            features: [iconFeature]
        });
        /*t clear the markers*/
        /*vectorSource.clear();*/
        vectorLayer = new ol.layer.Vector({
            source: vectorSource
        });
		
        map.addLayer(vectorLayer);
		markers.push(vectorLayer);
		marker_i++;
		
   }

   function showAllMarker(markerData){
	   //alert('s='+markerData.length);
	   	for(i=0;i<markerData.length;i++){
			invokeAddMarker(i,0);
		}
   }
   function makeMarker(a,b,c){
	//if(a > 0){invokeAddMarker(2,a);}
	//if(b > 0){invokeAddMarker(0,b);}
	//if(c > 0){invokeAddMarker(1,c);}
	invokeAddMarker(2,a);
	invokeAddMarker(0,b);
	invokeAddMarker(1,c);
	invokeAddMarker(3,c);
	invokeAddMarker(4,c);
   }
/*
{"title": 'Port Hedland',"lat": -20.31 ,"lng": 118.601,"description": 'Australia'},
		{"title": 'McGaurans Beach' ,"lat": -38.454261 ,"lng": 147.094059,"description": 'Australia'},
{"title": 'Four Mile Beach',"lat": -41.032557 ,"lng": 146.854935,"description": 'Australia'},
{"title": 'Stanley',"lat": -40.762 ,"lng": 145.2954,"description": 'Australia'},
{"title": 'Inverloch',"lat": -38.633333 ,"lng": 145.716667,"description": 'Australia'},
{"title": 'Sandy Point',"lat": -38.816667 ,"lng": 146.1,"description": 'Australia'},
{"title": 'Boat Harbour',"lat": -40.94605 ,"lng": 145.63716,"description": 'Australia'},
			{"title": 'Deep Water Bay' ,"lat": 22.244890120604655 ,"lng": 114.184136358403109,"description": 'Hong Kong'},
			{"title": 'Saltcoats' ,"lat": 55.6401 ,"lng": -4.80231666666667,"description": 'UK'},
*/ 
     function invokeAddMarker(i,x){
		 //alert(i+'/'+x);
		 var cl  ;
		 if(x == 3) cl="images/red2.png";
		 if(x == 2) cl="images/og2.png";
		 if(x == 1) cl="images/yl2.png";
		 if(x == 0) cl="images/gn2.png";		 
       
        //for(var i=0;i<markerData.length;i++){
        addmarker(markerData[i].lat,markerData[i].lng,markerData[i].title,cl);
        //}
    }

	function removeAllMarkers(){
		for(i=0;i<markers.length;i++){
			map.removeLayer(markers[i]);
		}
		marker_i = 0 ;
		markers = [];
	}
	
	function removeAMarker(xx){
		map.removeLayer(markers[xx]);
	}