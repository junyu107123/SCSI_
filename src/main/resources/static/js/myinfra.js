var elementArr =[];
var gaps = 0 ;
var resultArr = [];
//var colors = ["","green","yellow","orange","red","black"];
var colors = ["","normal","red","purple","red","black"];
var FailelementArr = [];
var corridor_head = ["left","middle1","middle2","right"];
var corridor_max = 300 ;
var corridor_linegap = 5; //8
var corridor_linegapx = 5;
var corridor_x = [0,0,0,0,0,0] ;
var noderec =[];
var color_cnt = 0 ;
var passKey = "I love this Key";
var squarewidth = "120" ;
var squareheight = "90" ; //120
var IDList = "";
var moveList =["201,103,002,301","101,005,301"];
var move_h = 0 ;
var findpathresult=[];
var showfindpathresult=[];
var alterPath = 0 ;
var altertick = null ;
var x_point = 10 ;
var x_gap = 320 ;
var y_point = 10 ;
var y_gap = 120 ; //200
var mouseoverobj = null ;

window.addEventListener('resize', function(event) {
    //console.log("Changed:"+window.innerWidth);
	//console.log("Changed:"+window.innerHeight);
}, true);
//console.log(window.innerWidth);
//console.log(window.innerHeight);

var AppendElement = document.getElementById("drawarea");
var AllElement = document.getElementById("dataarea");
var TargetElementItems = "circle rect animateMotion text line polyline";
var checkElements = TargetElementItems.split(" ");
var TargetElementItems1 = "circle rect text line polyline";
var checkElements1 = TargetElementItems1.split(" ");

var downobject = null;
var clickflag = 0 ;

AppendElement.addEventListener('click', function (event) {
	clickflag = (clickflag+1)%2 ;
	var gonext = false ;
	for(var u=0 ; u<checkElements.length ; u++){
		if(event.target.matches(checkElements[u])){
			gonext = true ;
			break;
		}
	}
	if(!gonext){
		resetElements();
		return ;
	}else{
		showInfo(event.target.id);
	}
}, false);

AppendElement.addEventListener("mouseover", function( event ) {
	var gonext = false ;
	for(var u=0 ; u<checkElements1.length ; u++){
		if(event.target.matches(checkElements1[u])){
			gonext = true ;
			break;
		}
	}
	//if(clickflag == 0){
	//	resetElements();
	//}
	if(!gonext){
		if(document.getElementById("testpoint") != null) clearMouseOverBox();
		return ;
	}else{
		if(event.target.id == "testpoint"){
		}else{
			showInfoPoint(event.target.id,event.clientX,event.clientY);
		}
	}
}, false);

AppendElement.addEventListener("mousemove", function( event ) {
	var gonext = false ;
	for(var u=0 ; u<checkElements1.length ; u++){
		if(event.target.matches(checkElements1[u])){
			gonext = true ;
			break;
		}
	}
	if(downobject != null){
		moveBlock(event.clientX,event.clientY);
	}
	if(!gonext){
		if(document.getElementById("testpoint") != null) clearMouseOverBox();
		return ;
	}else{
		if(event.target.id == "testpoint"){
		}else{
			showInfoPoint(event.target.id,event.clientX,event.clientY);
		}
	}
}, false);

var switchme = 0 ;
AppendElement.addEventListener("dblclick", function( event ) {
	
	$('html, body').animate({ scrollTop: 0 }, 'fast');
	if(event.target.matches("line") || event.target.matches("polyline")){
		window.scrollTop = 0 ;
		AllElement.scrollTop = 0 ;
		//showLineDetail(event.target.id);
		colorBoxON(event.target.id);
	}else{
		if (!event.target.matches("svg")){
			 window.scrollTop = 0 ;
			 AllElement.scrollTop = 0 ;
			 colorBoxNodeON(event.target.id);
			//showNodeDetail(event.target.id);
		}
	}

	//if(event.target.matches("rect") || event.target.matches("polyline")){
	//	showDES();
	//}
}, false);

AppendElement.addEventListener('mouseout', function( event ) {
	clearMouseOverBox();
},false);

function setAreaHeight(ids){
	var mappingArea = document.getElementsByClassName("right");
	document.getElementById(ids).style.height = (Number(mappingArea[0].offsetHeight)-10) +"px";
}

function setAreaWidth(ids){
	var mappingArea = document.getElementsByClassName("right");
	document.getElementById(ids).style.width = (Number(mappingArea[0].offsetWidth)-100) +"px";
}

function pushArr(x,ids,el1, el2,css,desc,others,myObj){
	elementArr.push([x,ids,el1,el2,css,desc,others,myObj]);
}

function drawSquare(ids,x,y,w,h,c,txt,c1,myObj){
	var tmpStr = '<rect id="'+ids+'" x="'+x+'" y="'+y+'" rx="20" ry="20" width="'+w+'" height="'+h+'" class="'+c+'" />';
	pushArr("rect",ids,null,null,c,txt,w+";"+h+";"+c1,myObj);
	AppendElement.insertAdjacentHTML('beforeend', tmpStr);
	if(txt!= null){
		addText(ids,txt,c1);
	}
}

function drawCircle(ids,x,y,r,c,txt,c1,myObj){
	var tmpStr = '<circle id="'+ids+'" cx="'+x+'" cy="'+y+'" r="'+r+'" class="'+c+'" />';
	pushArr("circle",ids,null,null,c,txt,r+";"+c1,myObj);
	AppendElement.insertAdjacentHTML('beforeend', tmpStr);
	if(txt!= null){
		addText(ids,txt,c1);
	}
}

function AddMoveCircle(ids,c,s,inv,r){
	var pathStr = "M ";
	var targetElement = document.getElementById(ids);
	if(targetElement.getAttribute("points") == null){
		//console.log("no points");
		if(inv == 1){
			pathStr += targetElement.getAttribute("x2")+" "+targetElement.getAttribute("y2")+" L "+targetElement.getAttribute("x1")+" "+targetElement.getAttribute("y1");
		}else{
			pathStr += targetElement.getAttribute("x1")+" "+targetElement.getAttribute("y1")+" L "+targetElement.getAttribute("x2")+" "+targetElement.getAttribute("y2");
		}
	}else{
		//console.log("points");
		var tmppoint = targetElement.getAttribute("points").split(" ");
		if(inv == 1){
			for (var i=tmppoint.length-1 ; i >= 0 ; i--){
				if(i< (tmppoint.length-1)) pathStr += " L ";
				pathStr += tmppoint[i].replace(","," ");
			}
		}else{
			for (var i=0 ; i< tmppoint.length ; i++){
				if(i>0) pathStr += " L ";
				pathStr += tmppoint[i].replace(","," ");
			}
		}
	}
	pushArr("circlemove","circle_"+ids,null,null,c,s+";"+inv+";"+r,null);
	targetElement.insertAdjacentHTML('afterend', '<circle id="circle_'+ids+'" r="'+r+'" fill="'+c+'"><animateMotion id="dot_'+ids+'" dur="'+s+'s" repeatCount="indefinite" path="'+pathStr+'"></animateMotion></circle>');
}

function clearOneElement(ids){
	try{
		var targetElement = document.getElementById(ids);
		targetElement.remove();
		for(var u=0 ; u<elementArr.length ; u++){
		if(elementArr[u][1] == ids){
			elementArr.splice(u, 1);
			u--;
		}
	}
	}catch{}
}

function getCirclePoint(ids,a){
	var targetElement = document.getElementById(ids);
	var px = Number(targetElement.getAttribute("cx"));
	var py = Number(targetElement.getAttribute("cy"));
	var pr = Number(targetElement.getAttribute("r"));
	var newx = px + pr * Math.cos(Number(a)*Math.PI/180);
	var newy = py + pr * Math.sin(Number(a)*Math.PI/180);
	var retval = newx +","+newy ;
	return retval;
}

function AddMultiMoveCircle(ids,c,s,inv,r,rec){
	var pathStr = "M ";
	var targetElementArray = ids.split(",");
	var ElementId = "";
	if(inv == 1){
		for(var j=targetElementArray.length-1 ; j >= 0 ; j--){
			ElementId += targetElementArray[j] +"_";
			var targetElement = document.getElementById(targetElementArray[j]);
			if(j< targetElementArray.length-1) pathStr += " L ";
			if(targetElement.getAttribute("points") == null){
				if(inv == 1){
					pathStr += targetElement.getAttribute("x2")+" "+targetElement.getAttribute("y2")+" L "+targetElement.getAttribute("x1")+" "+targetElement.getAttribute("y1");
				}else{
					pathStr += targetElement.getAttribute("x1")+" "+targetElement.getAttribute("y1")+" L "+targetElement.getAttribute("x2")+" "+targetElement.getAttribute("y2");
				}
			}else{
				var tmppoint = targetElement.getAttribute("points").split(" ");
				if(inv == 1){
					for (var i=tmppoint.length-1 ; i >=0 ; i--){
						if(i < (tmppoint.length-1)) pathStr += " L ";
						pathStr += tmppoint[i].replace(","," ");
					}
					
				}else{
					for (var i=0 ; i< tmppoint.length ; i++){
						if(i>0) pathStr += " L ";
						pathStr += tmppoint[i].replace(","," ");
					}
				}
			}
		}
	}else{
		for(var j=0 ; j < targetElementArray.length ; j++){
			ElementId += targetElementArray[j] +"_";
			var targetElement = document.getElementById(targetElementArray[j]);
			if(j>0) pathStr += " L ";
			if(targetElement.getAttribute("points") == null){
				pathStr += targetElement.getAttribute("x1")+" "+targetElement.getAttribute("y1")+" L "+targetElement.getAttribute("x2")+" "+targetElement.getAttribute("y2");
			}else{
				var tmppoint = targetElement.getAttribute("points").split(" ");
				for (var i=0 ; i< tmppoint.length ; i++){
					if(i>0) pathStr += " L ";
					pathStr += tmppoint[i].replace(","," ");
				}
			}
		}
	}
	ElementId += inv ;
	if(rec){
		pushArr("circlemove","circle_"+ElementId,ids,null,null,s+";"+inv+";"+r,null);
		pushArr("animateMotion","dot_"+ElementId,null,null,c,null,null);
	}
	targetElement.insertAdjacentHTML('afterend', '<circle id="circle_'+ElementId+'" r="'+r+'" fill="'+c+'"><animateMotion id="dot_'+ElementId+'" dur="'+s+'s" repeatCount="indefinite" path="'+pathStr+'"></animateMotion></circle>');
}

function AddMultiNodeMoveCircle(ids,c,s,r,rec){
	resultArr = [];
	var pathStr = "M ";
	var targetElementArray = ids.split(",");
	var ElementId = "";
	for (var u=0 ; u < targetElementArray.length-1 ; u++){
		if(u>0) ElementId += "_";
		ElementId += targetElementArray[u];
		findElements(targetElementArray[u],targetElementArray[u+1]);
	}
	//console.log(resultArr);
	for (var v = 0 ; v < resultArr.length ; v = v+2){
		var targetElement = document.getElementById(resultArr[v][1]);

		if(v>0) pathStr += " L ";
		if(targetElement.getAttribute("points") == null){
			if(resultArr[v+1] == 1){
					pathStr += targetElement.getAttribute("x2")+" "+targetElement.getAttribute("y2")+" L "+targetElement.getAttribute("x1")+" "+targetElement.getAttribute("y1");
				}else{
					pathStr += targetElement.getAttribute("x1")+" "+targetElement.getAttribute("y1")+" L "+targetElement.getAttribute("x2")+" "+targetElement.getAttribute("y2");
				}
		}else{
			var tmppoint = targetElement.getAttribute("points").split(" ");
			if(resultArr[v+1] == 1){
				for (var i=tmppoint.length-1 ; i >=0 ; i--){
					if(i < (tmppoint.length-1)) pathStr += " L ";
					pathStr += tmppoint[i].replace(","," ");
				}
					
			}else{
				for (var i=0 ; i< tmppoint.length ; i++){
					if(i>0) pathStr += " L ";
					pathStr += tmppoint[i].replace(","," ");
				}
			}
		}
	}
	if(rec){
		pushArr("circlemultimove","circle_"+ElementId,ids,c,s,r,null);
		pushArr("animateMotion","dot_"+ElementId,null,null,c,null,null,null);
	}
	AppendElement.insertAdjacentHTML('beforeend', '<circle id="circle_'+ElementId+'" r="'+r+'" fill="'+c+'"><animateMotion id="dot_'+ElementId+'" dur="'+s+'s" repeatCount="indefinite" path="'+pathStr+'"></animateMotion></circle>');
}

function addText(ids,txt,c){
	var _b_ele = AppendElement.getBoundingClientRect();
	var top_x = _b_ele.x ;
	var top_y = _b_ele.y ;
	var poss = document.getElementById(ids).getBoundingClientRect();
	var goy = poss.y + (poss.height/2) - top_y;
	var gox = poss.x + (poss.width/2) - top_x ;
	var tmpText = '<text id="txt_'+ids+'" x="'+gox+'" y="'+goy+'" class="'+c+'">'+txt+'</text>';
	AppendElement.insertAdjacentHTML('beforeend', tmpText);

	var txtElement = document.getElementById("txt_"+ids);
	poss = txtElement.getBoundingClientRect();
	var moveposx = (poss.width/2)+(poss.left - gox - top_x);
	var moveposy = (poss.height/4);
	txtElement.setAttribute("x",gox-moveposx);
	txtElement.setAttribute("y",goy+moveposy);
	pushArr("text","txt_"+ids,null,null,c,txt,null,null);
}

function addTextr(ids,txt,c,ag){
	var poss = document.getElementById(ids).getBoundingClientRect();
	var goy = poss.y + (poss.height/2);
	var gox = poss.x + (poss.width/2) ;
	var tmpText = '<text id="txt_'+ids+'" x="'+gox+'" y="'+goy+'" transform="" class="'+c+'">'+txt+'</text>';
	AppendElement.insertAdjacentHTML('beforeend', tmpText);
	var txtElement = document.getElementById("txt_"+ids);
	poss = txtElement.getBoundingClientRect();
	var movepos = (poss.width/2)+(poss.left-gox);
	var newpos = gox-movepos;
	var newposy = poss.y-(poss.height* (Math.ceil(ag/10)-1));
	txtElement.setAttribute("x",newpos);
	txtElement.setAttribute("y",newposy);	
	var rotatestr = "rotate("+ag+", "+newpos+", "+newposy+")";
	txtElement.setAttribute("transform",rotatestr);
	pushArr("text","txt_"+ids,null,null,c,txt,ag,null);
}

function drawLine(x,y,c,desc){
	var _b_ele = AppendElement.getBoundingClientRect();
	var top_x = _b_ele.x ;
	var top_y = _b_ele.y ;
	var final_left , final_right ;
	var poss = document.getElementById(x).getBoundingClientRect();
	var poss1 = document.getElementById(y).getBoundingClientRect();
	var leftobj , rightobj;
	if(poss.x <= poss1.x ){
		final_left = x ;
		final_right = y ;
		leftobj = poss ;
		rightobj = poss1 ;
	}else{
		final_left = y ;
		final_right = x ;
		leftobj = poss1 ;
		rightobj = poss ;
	}
	
	var leftstart_x = leftobj.right - gaps - top_x;
	var leftstart_y = leftobj.y + (leftobj.height/2) - gaps - top_y;
	
	var rightend_x = rightobj.left - gaps - top_x;
	var rightend_y = rightobj.y + (rightobj.height/2) - gaps - top_y;
	var tmpText = '<line id="line_'+x+'_'+y+'" x1="'+leftstart_x+'" y1="'+leftstart_y+'" x2="'+rightend_x+'" y2="'+rightend_y+'" class="'+c+'" />';
	AppendElement.insertAdjacentHTML('beforeend', tmpText);
	pushArr("line","line_"+x+"_"+y,final_left,final_right,c,desc,null,null);
}

function drawLine1(x,y,c,g,desc,rec,myObj){
	var _b_ele = AppendElement.getBoundingClientRect();
	var top_x = _b_ele.x ;
	var top_y = _b_ele.y ;
	var numOfLine = 0 ;
	var numOfElement_left , numOfElement_right;
	var final_left , final_right ;
	var poss = document.getElementById(x).getBoundingClientRect();
	var poss1 = document.getElementById(y).getBoundingClientRect();
	var leftobj , rightobj;
	if(Math.abs(poss.x-poss1.x) < poss.width){
		drawLine2(x,y,c,Number(g),Number(g),3,1,desc,rec,myObj);
	}else{
		if(poss.x <= poss1.x){
			final_left = x ;
			final_right = y ;
			leftobj = poss ;
			rightobj = poss1 ;
			numOfElement_left = NodeRecSearch(x);
			numOfElement_right = NodeRecSearch(y);
			NodeRecEdit(y,1,0);
			NodeRecEdit(x,0,1);
			numOfLine = corridor_xEdit(x);
		}else{
			final_left = y ;
			final_right = x ;
			leftobj = poss1 ;
			rightobj = poss ;
			numOfElement_left = NodeRecSearch(y);
			numOfElement_right = NodeRecSearch(x);
			NodeRecEdit(x,1,0);
			NodeRecEdit(y,0,1);
			numOfLine = corridor_xEdit(y);
		}

		var numLine_left = Number(numOfElement_left.split(";")[1]);
		var numLine_right = Number(numOfElement_right.split(";")[0]);
		if((numLine_left%2) == 0){
			numLine_left = Math.floor(numLine_left/2) * corridor_linegap ;
		}else{
			numLine_left = 0 -( (Math.floor(numLine_left/2)+(numLine_left%2)) * corridor_linegap) ;
		}
		if((numLine_right%2) == 0){
			numLine_right = Math.floor(numLine_right/2) * corridor_linegap ;
		}else{
			numLine_right = 0 -( (Math.floor(numLine_right/2) + (numLine_right%2)) * corridor_linegap) ;
		}
		var leftstart_x = leftobj.right - gaps - top_x ;
		//org var leftstart_y = leftobj.y + (leftobj.height/2) - gaps + Number(g) - top_y;
		var leftstart_y = leftobj.y + (leftobj.height/2) - gaps + numLine_left - top_y;

		var rightend_x = rightobj.left - gaps  - top_x;
		// org var rightend_y = rightobj.y + (rightobj.height/2) - gaps + Number(g) - top_y;
		var rightend_y = rightobj.y + (rightobj.height/2) - gaps + numLine_right - top_y;
		
		if(getOneElementDesc(final_left,0) == "circle"){

			if(numLine_left < 0){
				numLine_left = 360 + Number(numLine_left)*2;
			}else{
				numLine_left = 0 + Number(numLine_left)*2
			}
			var getxy = getCirclePoint(final_left,numLine_left);
			//org 
			//if(g<0) g = 360+ Number(g)*2 ;
			//var getxy = getCirclePoint(final_left,g);
			leftstart_x = Number(getxy.split(",")[0]) ;
			leftstart_y = Number(getxy.split(",")[1]) ;
		}
		if(getOneElementDesc(final_right,0) == "circle"){			
			var getxy = getCirclePoint(final_right,(180-(Number(numLine_right)*2)));
			// org var getxy = getCirclePoint(final_right,(180-g*2));
			rightend_x = Number(getxy.split(",")[0]) ;
			rightend_y = Number(getxy.split(",")[1]) ;
		}
		
		// center...
		//var middle_x = leftstart_x + ((rightend_x - leftstart_x)/2) + Number(g);
		
		var middle_x = leftstart_x + (numOfLine*corridor_linegapx) + corridor_linegapx;
		var middle_y = leftstart_y + ((rightend_y - leftstart_y)/2);

		var tmpText = '<polyline id="poline_'+x+'_'+y+'_'+numLine_left+''+'" points="'+leftstart_x+','+leftstart_y+' '+middle_x+','+leftstart_y+' '+middle_x+','+rightend_y+' '+rightend_x+','+rightend_y+'" class="'+c+'" />';
		if(rec){
			
			pushArr("polyline","poline_"+x+"_"+y+"_"+numLine_left,final_left,final_right,c,desc,"1;"+g,myObj);
		}
	
		AppendElement.insertAdjacentHTML('beforeend', tmpText);
	}
}

function drawLine2(x,y,c,g1,g2,l,lr,desc,rec,myObj){

	//x : obj , y:obj , c:style , g1:left obj step , g2:right obj step , l:no of lines , lr : (0)left side or (1)right side 
	var _b_ele = AppendElement.getBoundingClientRect();
	var top_x = _b_ele.x ;
	var top_y = _b_ele.y ;
	var numOfLine = 0 ;
	var final_left , final_right ;
	var linepadding = 10 * 1 ;
	var poss = document.getElementById(x).getBoundingClientRect();
	var poss1 = document.getElementById(y).getBoundingClientRect();
	var leftobj , rightobj , tmp_g;
	if((poss.x <= poss1.x)){
		final_left = x ;
		final_right = y ;
		leftobj = poss ;
		rightobj = poss1 ;
	}else{
		tmp_g = g1 ;
		g1 = g2 ;
		g2 = g1 ;
		final_left = y ;
		final_right = x ;
		leftobj = poss1 ;
		rightobj = poss ;
		
	}
	var left_golr = leftobj.right ;
	var right_golr = rightobj.right ;
	var numLine_left = 0;
	var numLine_right = 0;
	numOfElement_left = NodeRecSearch(final_left);
	numOfElement_right = NodeRecSearch(final_right);
	numOfLine = corridor_xEdit(final_left);
	if (lr==0){
		NodeRecEdit(final_left,1,0);
		NodeRecEdit(final_right,1,0);
		left_golr = leftobj.left ;
		right_golr = rightobj.left ;
		linepadding = 0-linepadding;
		numLine_left = Number(numOfElement_left.split(";")[0]);
		numLine_right = Number(numOfElement_right.split(";")[0]);
	}else{
		NodeRecEdit(final_left,0,1);
		NodeRecEdit(final_right,0,1);
		
		numLine_left = Number(numOfElement_left.split(";")[1]);
		numLine_right = Number(numOfElement_right.split(";")[1]);
	}
			
		if((numLine_left%2) == 0){
			numLine_left = Math.floor(numLine_left/2) * corridor_linegap ;
		}else{
			numLine_left = 0 -( (Math.floor(numLine_left/2)+(numLine_left%2)) * corridor_linegap) ;
		}
		if((numLine_right%2) == 0){
			numLine_right = Math.floor(numLine_right/2) * corridor_linegap ;
		}else{
			numLine_right = 0 -( (Math.floor(numLine_right/2) + (numLine_right%2)) * corridor_linegap) ;
		}
	
	var leftstart_x = left_golr - gaps - top_x;
	var leftstart_y = leftobj.y + (leftobj.height/2) - gaps + numLine_left - top_y;
	
	var rightend_x = right_golr - gaps - top_x;
	var rightend_y = rightobj.y + (rightobj.height/2) - gaps + numLine_right - top_y;
	
	if(getOneElementDesc(final_left,0) == "circle"){
		var getxy;
		if (lr==0){
			getxy = getCirclePoint(final_left,(180-(Number(numLine_left)*2)));
		}else{
			if(numLine_left < 0){
				numLine_left = 360 + Number(numLine_left)*2;
			}else{
				numLine_left = 0 + Number(numLine_left)*2
			}
			getxy = getCirclePoint(final_left,numLine_left);
		}
			leftstart_x = Number(getxy.split(",")[0]) ;
			leftstart_y = Number(getxy.split(",")[1]) ;
	}
	if(getOneElementDesc(final_right,0) == "circle"){			
		var getxy ;
		if (lr==0){
			getxy = getCirclePoint(final_right,(180-(Number(numLine_right)*2)));
		}else{
			if(numLine_right < 0){
				numLine_right = 360 + Number(numLine_right)*2;
			}else{
				numLine_right = 0 + Number(numLine_right)*2
			}
			getxy = getCirclePoint(final_right,numLine_right);
		}

		rightend_x = Number(getxy.split(",")[0]) ;
		rightend_y = Number(getxy.split(",")[1]) ;
	}
	
	
	var middle_x = rightend_x + (numOfLine*corridor_linegapx);;
	var middle_y1 = leftstart_y ;
	var middle_y2 = rightend_y ;
	
	var tmpText = '<polyline id="poline_'+x+'_'+y+'_'+g1+'_'+g2+'_'+l+'_'+lr+'_'+numLine_left+'" points="'+leftstart_x+','+leftstart_y+' '+middle_x+','+middle_y1+' '+middle_x+','+middle_y2+' '+rightend_x+','+rightend_y+'" class="'+c+'" />';
	if(rec){
		pushArr("polyline","poline_"+x+"_"+y+"_"+g1+"_"+g2+"_"+l+"_"+lr+'_'+numLine_left,final_left,final_right,c,desc,"2"+";"+g1+";"+g2+";"+l+";"+lr,myObj);
	}
	AppendElement.insertAdjacentHTML('beforeend', tmpText);
}

function findElements(x,y){
	for(var u=0 ; u<elementArr.length ; u++){
		var invflag = 0 ;
		if(elementArr[u].indexOf(x) >= 0 && elementArr[u].indexOf(y)>=0){
			if(elementArr[u].indexOf(x) > elementArr[u].indexOf(y)) invflag = 1;
			resultArr.push(elementArr[u],invflag);
		}
	}
}

function findOneElements(x){
	for(var u=0 ; u<elementArr.length ; u++){
		var invflag = 0 ;
		if(elementArr[u].indexOf(x) >= 0){
			resultArr.push(elementArr[u],invflag);
		}
	}
}

function getOneElementDesc(x,y){
	var retval="";
	for(var u=0 ; u<elementArr.length ; u++){
		if(elementArr[u][1] == x){
			retval = elementArr[u][y];
			break;
		}
	}
	return retval ;
}

function changeElements(x){
	for(var u=0 ; u<elementArr.length ; u++){
		var founds = 0 ;
		for (var v=0 ; v<FailelementArr.length ; v=v+2){
			if(FailelementArr[v][1] == elementArr[u][1]){
				founds = 1;
				document.getElementById(elementArr[u][1]).setAttribute("class",FailelementArr[v+1]);
				break;
			}
		}
		if(founds == 0){
			document.getElementById(elementArr[u][1]).setAttribute("class",elementArr[u][4]);
		}
		if(elementArr[u].indexOf(x) >= 0){
			if(elementArr[u][0] == "rect") document.getElementById(elementArr[u][1]).setAttribute("class","rect_click");
			if(elementArr[u][0] == "circle") document.getElementById(elementArr[u][1]).setAttribute("class","rect_click");
			if(elementArr[u][0] == "line"){
				document.getElementById(elementArr[u][1]).setAttribute("class","mylinex_click");
				document.getElementById(elementArr[u][2]).setAttribute("class","rect_click");
				document.getElementById(elementArr[u][3]).setAttribute("class","rect_click");
			}
			if(elementArr[u][0] == "polyline"){
				document.getElementById(elementArr[u][1]).setAttribute("class","mylinex_click");
				document.getElementById(elementArr[u][2]).setAttribute("class","rect_click");
				document.getElementById(elementArr[u][3]).setAttribute("class","rect_click");
			}
		}
	}
}

function showLineDetail(x){
	for(var u=0 ; u<elementArr.length ; u++){
		if(elementArr[u].indexOf(x) >= 0){
			//console.log(elementArr[u]);
			if(elementArr[u][0] == "rect") document.getElementById(elementArr[u][1]).setAttribute("class","rect_click");
			if(elementArr[u][0] == "circle") document.getElementById(elementArr[u][1]).setAttribute("class","rect_click");
			if(elementArr[u][0] == "line"){
				document.getElementById(elementArr[u][1]).setAttribute("class","mylinex_click");
				document.getElementById(elementArr[u][2]).setAttribute("class","rect_click");
				document.getElementById(elementArr[u][3]).setAttribute("class","rect_click");
			}
			if(elementArr[u][0] == "polyline"){
				document.getElementById(elementArr[u][1]).setAttribute("class","mylinex_click");
				document.getElementById(elementArr[u][2]).setAttribute("class","rect_click");
				document.getElementById(elementArr[u][3]).setAttribute("class","rect_click");
			}
		}
	}
}

function changeLineClass(x,y){
	for(var u=0 ; u<elementArr.length ; u++){
		if((elementArr[u][0] == "polyline") && (elementArr[u][1] == x)){
			elementArr[u][4]=y;
			break;
		}
	}
}

function showNodeDetail(x){
	if(x=="testpoint"){
	}else{
		//console.log(switchme);
		//if(switchme%2 == 0){
			var myID = x.substring(x.indexOf("-")+1,x.length);
			//console.log(myID);
			posttest(myID);
		//}else{
		//	showDES();
		//}
		//switchme = (switchme+1)%2;
	}
}


function posttest(x){
	$.post("/scsi/DataAPI", {scable:x,func:"9",loc:"111"})
	.done(function( data ) {
		clearSVG();
		readFeedBackJsonMix(decodeURIComponent(data));
		$.post("/scsi/DataAPI", {scable:x,func:"10",loc:"111"})
		.done(function( data ) {
			//console.log("*"+data+"*");
			readLineJsonDetail(decodeURIComponent(data));
			//console.log(elementArr);
		});
	});
}

function resetElements(){
	for(var u=0 ; u<elementArr.length ; u++){
		var founds = 0 ;
		for (var v=0 ; v<FailelementArr.length ; v=v+2){
			if(FailelementArr[v][1] == elementArr[u][1]){
				founds = 1;
				document.getElementById(elementArr[u][1]).setAttribute("class",FailelementArr[v+1]);
				break;
			}
		}
		if(founds == 0){
			document.getElementById(elementArr[u][1]).setAttribute("class",elementArr[u][4]);
		}
			//document.getElementById(elementArr[u][1]).setAttribute("class",elementArr[u][4]);
	}
}

function setElementClass(x,y){
	document.getElementById(x).setAttribute("class",y);
}

function replaceElementClass(x,y){
	setElementClass(x,y)
	changeClass(x,y);
}

function changeClass(x,y){
	for(var u=0 ; u<elementArr.length ; u++){
		if(elementArr[u][1] == x){
			elementArr[u][4]=y;
			break;
		}
	}
}

function rearrangeLine(x){
	for(var u=0 ; u<elementArr.length ; u++){
		if(elementArr[u].indexOf(x) >= 0){
			//console.log( elementArr[u][1]);
			if(elementArr[u][0] == "line"){
				//drawLine1(elementArr[u][2],elementArr[u][3],elementArr[u][1].split("_")[3],elementArr[u][4],elementArr[u][5],false);
			}
			if(elementArr[u][0] == "polyline"){
				var tmpLine = elementArr[u][1].split("_");
				if(tmpLine.length <= 4){
					drawLine1(elementArr[u][1].split("_")[1],elementArr[u][1].split("_")[2],elementArr[u][4],elementArr[u][1].split("_")[3],elementArr[u][5],false,null);
				}else{
					drawLine2(elementArr[u][2],elementArr[u][3],elementArr[u][4],elementArr[u][1].split("_")[3],elementArr[u][1].split("_")[4],elementArr[u][1].split("_")[5],elementArr[u][1].split("_")[6],elementArr[u][5],false,null);
				}
			}
		}
	}
}

function clearSVG(){

	for(var u=0 ; u<elementArr.length ; u++){
		try{
		document.getElementById(elementArr[u][1]).remove();
		}catch{}
	}
	elementArr=[];
	noderec=[];
	corridor_xReset();
	var tmpText = "<defs><linearGradient id=\"myGradient\" x1=\"0%\" y1=\"0%\" x2=\"0%\" y2=\"100%\" spreadMethod=\"pad\">"
		tmpText += "<stop offset=\"0%\"   stop-color=\"＃e0f3f7\" stop-opacity=\"1\"/>";
    	tmpText += "<stop offset=\"50%\" stop-color=\"#b5e1ec\" stop-opacity=\"1\"/>";
    	tmpText += "<stop offset=\"100%\" stop-color=\"#96c5d9\" stop-opacity=\"1\"/></linearGradient></defs>";
	  //AppendElement.insertAdjacentHTML('beforeend', tmpText);
}

function showInfo(x)
{
	if(x != "testpoint"){
		if(x.indexOf("txt_")==0){
			x=x.replace("txt_","");
		}
		changeElements(x);
	}
}

function showInfoPoint(ids,x,y){
	if(document.getElementById("testpoint") != null) clearMouseOverBox();
	var showData = getOneElementDesc(ids,5);
	if(showData != null){
		var _b_ele = AppendElement.getBoundingClientRect();
		var top_x = _b_ele.x ;
		var top_y = _b_ele.y ;
		var gox = x-top_x + 5 ;
		var goy = y-top_y - 50 - 5;
		var tmpStr = '<rect id="testpoint" x="'+gox+'" y="'+goy+'" rx="10" ry="10" width="200" height="50" class="rect_white" />';
		AppendElement.insertAdjacentHTML('beforeend', tmpStr);
		addText("testpoint",showData,"word16_white");
		/*
		if(getOneElementDesc(ids,0) == "polyline"){
			setElementClass(ids,"mylinex_8");
		}else{
			if(ids.indexOf("txt_")==0){
				ids=ids.replace("txt_","");
			}
			setElementClass(ids,"rect_click");
		}
		*/
	}
}

function moveNode(ids,x,y){
	var NodeArray =[];
	for(var u=0 ; u<elementArr.length ; u++){
		if(elementArr[u][1] == ids){
			NodeArray.push(elementArr[u]);
			document.getElementById(elementArr[u][1]).remove();
			elementArr.splice(u, 1);
			u--;
		}
		if(elementArr[u][1] == "txt_"+ids){
			document.getElementById(elementArr[u][1]).remove();
			elementArr.splice(u, 1);
			u--;
		}	
	}
	for(var u=0 ; u<NodeArray.length ; u++){
		if(NodeArray[u][0] == "rect"){
			var tmpInfo = NodeArray[u][6].split(";");
			drawSquare(NodeArray[u][1],x,y,tmpInfo[0],tmpInfo[1],NodeArray[u][4],NodeArray[u][5],tmpInfo[2],NodeArray[u]);
		}
	}
	redrawLine();
}

function redrawLine(){
	var deleteElement = 0 ;
	var moveArray =[];
	var LinemoveArray=[];
	for(var u=0 ; u<elementArr.length ; u++){
		deleteElement = 0 ;
		if(elementArr[u][0] == "polyline") deleteElement =1;
		if(elementArr[u][0] == "line") deleteElement =1;
		if(elementArr[u][0] == "circlemultimove") deleteElement =1;
		if(elementArr[u][0] == "circlemove") deleteElement =1;
		if(elementArr[u][0] == "animateMotion") deleteElement =1;
		if(deleteElement == 1){
			if(elementArr[u][0] == "animateMotion"){
				elementArr.splice(u, 1);
				u--;
			}else{
				//console.log(document.getElementById(elementArr[u][1]));
				document.getElementById(elementArr[u][1]).remove();
				if(elementArr[u][0] == "circlemultimove"){
					moveArray.push(elementArr[u]);
					elementArr.splice(u, 1);
					u--;
				}else if(elementArr[u][0] == "circlemove"){
					moveArray.push(elementArr[u]);
					elementArr.splice(u, 1);
					u--;
				}else{
					LinemoveArray.push(elementArr[u]);
					elementArr.splice(u, 1);
					u--;
				}
			}	
		}
	}
	noderec=[];
	corridor_xReset();
	for(var u=0 ; u<LinemoveArray.length ; u++){
		deleteElement = 0 ;
		if(LinemoveArray[u][0] == "polyline") deleteElement =1;
		if(LinemoveArray[u][0] == "line") deleteElement =1;
		if(deleteElement == 1){
			var otherstmp = LinemoveArray[u][6].split(";");
			if(otherstmp[0]==1){
				drawLine1(LinemoveArray[u][2],LinemoveArray[u][3],LinemoveArray[u][4],otherstmp[1],LinemoveArray[u][5],true,LinemoveArray[u]);
			}else{
				drawLine1(LinemoveArray[u][2],LinemoveArray[u][3],LinemoveArray[u][4],otherstmp[1],LinemoveArray[u][5],true,LinemoveArray[u]);
				//drawLine2(LinemoveArray[u][2],LinemoveArray[u][3],LinemoveArray[u][4],otherstmp[1],otherstmp[2],otherstmp[3],otherstmp[4],LinemoveArray[u][5],true);
			}
		}
	}

	for(var u=0 ; u<moveArray.length ; u++){
		deleteElement = 0 ;
		if(moveArray[u][0] == "circlemultimove") deleteElement =1;
		if(moveArray[u][0] == "circlemove") deleteElement =1;
		if(deleteElement == 1){
			AddMultiNodeMoveCircle(moveArray[u][2],moveArray[u][3],moveArray[u][4],moveArray[u][5],true);
		}
	}
	for (var v=0 ; v<FailelementArr.length ; v=v+2){
		document.getElementById(FailelementArr[v][1]).setAttribute("class",FailelementArr[v+1]);
	}
			
}

function moveBlock(x,y){
	var _b_ele = AppendElement.getBoundingClientRect();
	var top_x = _b_ele.x ;
	var top_y = _b_ele.y ;
	if(downobject != null){
		//console.log(downobject);
		var oldx = Number(document.getElementById(downobject).getAttribute("x"));
		var oldy = Number(document.getElementById(downobject).getAttribute("y"));
		var newx = (x - top_x);
		var newy = (y - top_y);	
		document.getElementById(downobject).setAttribute("x",newx);
		document.getElementById(downobject).setAttribute("y",newy);
	}
}

function clearMouseOverBox(){
	clearOneElement("testpoint");
	clearOneElement("txt_testpoint");
}

function changeColor(x){
	if(color_cnt > 5) color_cnt = 0;
	var _classname = "rect";
	if(color_cnt>0) _classname += "_" + colors[color_cnt];
	var tt = document.getElementById(x);
	tt.setAttribute("class",_classname);
	color_cnt++;
}


function addElementListener(){
	for(var u=0 ; u<elementArr.length ; u++){
		addListener(elementArr[u][1]);
	}
}

function addListener(x){
document.getElementById(x).addEventListener("mouseover", function( event ) {
	var gonext = false ;
	for(var u=0 ; u<checkElements.length ; u++){
		if(event.target.matches(checkElements[u])){
			gonext = true ;
			break;
		}
	}
	if(!gonext){
		if(document.getElementById("testpoint") != null) clearMouseOverBox();
		return ;
	}else{
		showInfoPoint(event.clientX,event.clientY);
	}
}, false);
}

function fetchBoxMessage(){
	var testjson ={"person":"a","box":"b","checks":"c"};
	$.colorbox.settings.opacity = 0.5;
	$.colorbox({
		onLoad: function() {
			$('#cboxClose').remove();
			$('#cboxContent').css("background","transparent");
		},
		html:"<h1 style='padding:30px;border-radius: 10px 10px 10px 10px;background:#a87132;line-height:50px;'>資料處理中, 請稍待...</h1>",
		overlayClose: false
		});
}

function getCountry(){
$.post( "/scsi/test2", {})
	.done(function( data ) {
		passKey = data.replace(/(^[\s]*)|([\s]*$)/g, "");
		//console.log(passKey);
		//test3.jsp is a sample for encrypt & decrypt
		var encrypted = CryptoJS.AES.encrypt("thisisatestforencryptedfiles this is a test...", "thisisakeyforencry");
		$.post("test3.jsp", {scable:encrypted.toString(),func:"11",loc:"111"})
		//$.post("/scsi/DataAPI", {scable:encrypted,func:"11",loc:"111"})
		.done(function( data ) {
		//console.log(decodeURIComponent(data));
		//var bytes1  = CryptoJS.AES.decrypt(decodeURIComponent(data).replace(/(^[\s]*)|([\s]*$)/g, ""), "thisisakeyforencry");
		//console.log(bytes1.toString(CryptoJS.enc.Utf8));
		//console.log(getDataDes(decodeURIComponent(data),"thisisakeyforencry"));
		});
	});
}

function showDES(){
	clearTimeout(altertick);
	FailelementArr=[];
	fetchBoxMessage();
try{
$.post( "/scsi/test2", {})
	.done(function( data ) {
		passKey = data.replace(/(^[\s]*)|([\s]*$)/g, "");
		$.post("/scsi/DataAPI", {scable:"INTR",func:"7",loc:"111"})
		//$.post("/scsi/DataAPI", {scable:PostDataEnc(testjson,passKey),func:"6",loc:"111"})
			.done(function( data ) {
				//console.log(decodeURIComponent(data));
				//console.log(getDataDes(decodeURIComponent(data),passKey));
				//var bytes1  = CryptoJS.AES.decrypt(data.replace(/(^[\s]*)|([\s]*$)/g, ""), 'Secret Passphrase');
				//var decryptedData1 = JSON.parse(bytes1.toString(CryptoJS.enc.Utf8));
				clearSVG();
				//console.log(switchme);
				readFeedBackJson(decodeURIComponent(data),1);
				$.post("/scsi/DataAPI", {scable:"INTL",func:"7",loc:"111"})
				//$.post("/scsi/DataAPI", {scable:PostDataEnc(testjson,passKey),func:"6",loc:"111"})
				.done(function( data ) {
					readFeedBackJson(decodeURIComponent(data),2);
					$.post("/scsi/DataAPI", {scable:"ISLAND",func:"7",loc:"111"})
					.done(function( data ) {
							readFeedBackJson(decodeURIComponent(data),0);
							//$.post("/scsi/DataAPI", {scable:"",func:"11",loc:"111"})
							$.post("/scsi/DataAPI", {scable:"FOREIGN",func:"7",loc:"111"})
							.done(function( data ) {
								readFeedBackJson(decodeURIComponent(data),3);
								//readFeedBackCountryJson(decodeURIComponent(data));
								$.post("/scsi/DataAPI", {scable:"",func:"8",loc:"111"})
									//$.post("/scsi/DataAPI", {scable:PostDataEnc(testjson,passKey),func:"6",loc:"111"})
								.done(function( data ) {
									//console.log(decodeURIComponent(data));
									readLineJson(decodeURIComponent(data),1);
									move_h = 0 ;
									//showMoveNow();
									//testMoveLine();
									console.log(elementArr);
								})
							})
							.fail(function(xhr, status, error){
								setTimeout("failmessage();",500);
							});
					})
					.fail(function(xhr, status, error){
						setTimeout("failmessage();",500);
					});
					
				})
				.fail(function(xhr, status, error){
					$.colorbox.close();
					setTimeout("failmessage();",500);
				});
				
				passKey=null;
				$.colorbox.close();
			})
			.fail(function(xhr, status, error){
				$.colorbox.close();
				setTimeout("failmessage();",500);
			});
	})
	.fail(function(xhr, status, error){
	  $.colorbox.close();
	});
}catch{
	$.colorbox.close();
}
getNodeInfo("node_left");
getNodeInfo("nodemiddle1");
}  

function pressclose(){
	$.colorbox.close();
}

function failmessage(){
	$.colorbox.settings.opacity = 0.5;
	$.colorbox({
		onLoad: function() {
			$('#cboxClose').remove();
			$('#cboxContent').css("background","transparent");
		},
		html:"<h1 style='padding:30px;border-radius: 10px 10px 10px 10px;background:#a87132;line-height:50px;'>資料有誤,未選位完成, 請重選...</h1>"
		});
	setTimeout("$.colorbox.close();",3000);
}

function showMoveNow(){
	IDList = "";
	if(move_h < moveList.length ){
		getActiveNode(moveList[move_h]);
	}
}

function getActiveNode(n)
{
	var tmp ;
	var thisvalues = "";
	if(n.indexOf(",")>0){
		tmp = n.split(",");
		thisvalues = tmp[0];
	}else{
		thisvalues = n ;
	}
	$.post("/scsi/DataAPI", {scable:thisvalues,func:"12",loc:"111"})
	.done(function( data ) {
		//console.log(decodeURIComponent(data));
		foundElement(decodeURIComponent(data),n);
	})
}



function changeSVG(){
	var node1 = $("#node_left").find("option:selected").val();
	var node2 = $("#nodemiddle1").find("option:selected").val();
	var linkstatus = $("#failstatus").find("option:selected").val();
	resultArr=[];
	findElements(node1,node2);
	for(var u=0 ; u < resultArr.length ; u=u+2){
		if(linkstatus == 0){
			FailElementManage(resultArr[u],"mylinex");
			setElementClass(resultArr[u][1],"mylinex");
		}
		if(linkstatus == 1){
			FailElementManage(resultArr[u],"mylinex_yellow");
			setElementClass(resultArr[u][1],"mylinex_yellow");
		}
		if(linkstatus == 9){
			FailElementManage(resultArr[u],"mylinex_red");
			setElementClass(resultArr[u][1],"mylinex_red");
		}
	}
	inputCollect("g");
}

function resetSVG(){
	FailelementArr=[];
	resetElements();
}

function FailElementManage(x,y){
	var founds = 0 ;
	
	for(var u=0 ; u < FailelementArr.length ; u=u+2){
		if(FailelementArr[u][1] == x[1]){
			founds = 1 ;
			if( y == "mylinex"){
				FailelementArr.splice(u,2);
			}else{
				FailelementArr[u+1] = y ;
			}
			break ;
		}
	}
	if(founds == 0){
		FailelementArr.push(x,y);
	}
}

function NodeRecEdit(x,y,z){
	var founds = 0 ;
	for(var u=0 ; u<noderec.length ; u++){
		if(noderec[u][0] == x){
			noderec[u][1] += Number(y) ;
			noderec[u][2] += Number(z) ;
			founds = 1 ;
			break;
		}
	}
	if(founds == 0){
		noderec.push([x,Number(y),Number(z)]);
	}
}

function NodeRecSearch(x){
	var founds = "0;0" ;
	for(var u=0 ; u<noderec.length ; u++){
		if(noderec[u][0] == x){
			founds = noderec[u][1]+";"+noderec[u][2];
			break;
		}
	}
	return founds;
}

function corridor_xEdit(x){
	var retval = 0 ;
	for (var q=0 ; q<corridor_head.length ; q++){
		if(x.indexOf(corridor_head[q]) == 0){
			corridor_x[q]++;
			retval = corridor_x[q];
			break;
		}
	}
	return retval ;
}

function corridor_xReset(){
	corridor_x = [0,0,0,0,0,0] ;
}

function inputCollect(g){
	var sendInput = {};
	var getValues = null ;
	for (var u=1 ; u < 30 ; u++){
		var getEle = g+u ;
		if($("#"+getEle).attr('id') == undefined){
			break;
		}
		if(($("#"+getEle).prop('type') == "checkbox") || ($("#"+getEle).prop('type') == "radio")){
			getValues = $("input[id='"+getEle+"']:checked").map(function() { return DataReorg($(this).val()); }).get();
			sendInput[$("#"+getEle).attr('id')] = getValues;
		}else{
			sendInput[$("#"+getEle).attr('id')]=DataReorg($("#"+getEle).val());
		}
	}
	//console.log(sendInput);
	var de = PostDataEnc(sendInput,passKey);
	//var de = sendInput;
	//console.log(de);
	return de ;
	//return sendInput ;
}

function DataReorg(x){
	return decodeURI(x).replace(new RegExp("%2F","gm"),"/").replace(new RegExp("%2C","gm"),",").replace(new RegExp("%3B","gm"),";").replace(/(^[\s]*)|([\s]*$)/g, "");
}

function foundElement(c,n){
	var newJson = JSON.parse(c).node_info ;
	var j = 0 ;

	for (var u=0 ; u < newJson.length  ; u++){
		if (IDList.length>0) IDList += ",";
		if(newJson[u].node_type == "INTR") j=1;
		if(newJson[u].node_type == "INTL") j=2;
		if(newJson[u].node_type == "ISLAND") j=0;
		if(newJson[u].node_type == "FOREIGN") j=3;
		var tag_id = corridor_head[j]+"-";
		var myID = tag_id+newJson[u].node_id+"-"+newJson[u].sysid;
		IDList += myID ;
	}
	//console.log(n);
	if(n.indexOf(",")>0){
		
	var tmp = n.split(",");
	var new_n = "";
	
	if(tmp.length > 1){
		for(var h=1 ; h<tmp.length ; h++){
			if(h>1) new_n += ",";
			new_n += tmp[h];
		}
	}else{
		new_n = tmp[1];
	}
	//console.log(IDList);
	getActiveNode(new_n);
	}else{
		ActiveMove();
		move_h++;
		showMoveNow();
	}
}

function testMoveLine(){
	$.post("/scsi/test6", {scable:"",func:"8",loc:"111"})
	.done(function( data ) {
		var altroute = data.replace(/(^[\s]*)|([\s]*$)/g, "");
		//"poline_left-201-17_middle1-103-11_0,poline_middle1-103-11_middle1-105-13_0_0_3_1_-48,poline_middle1-105-13_middle2-005-5_-16,poline_middle2-005-5_middle1-102-10_32";
		if(altroute.length>0){
		var tmproute = altroute.split(",");
		for (var v=0 ; v< tmproute.length ; v++){
			var tt = document.getElementById(tmproute[v]);
			tt.setAttribute("class","mylinex_orange");
			changeLineClass(tmproute[v],"mylinex_orange");
		}
		}
	});
	//AddMultiMoveCircle("poline_left-201-17_middle1-103-11_0,poline_middle1-103-11_middle1-105-13_0_0_3_1_-48,poline_middle1-105-13_middle2-005-5_-16,poline_middle2-005-5_middle1-102-10_32","pink",5,0,10);
}

function ActiveMove(){
	AddMultiNodeMoveCircle(IDList,ballColor,10,15,true);
}



function readFeedBackJson(c,j){
	if(c!="{}"){
	var tag_id = corridor_head[j]+"-";
	var startx = x_point + (j* x_gap) ;
	var starty = y_point ;
	var newJson = JSON.parse(c).node_info ;
	var showstyle = "rect_blue";
	for (var u=0 ; u < newJson.length  ; u++){
		showstyle = "rect_blue";
		if(newJson[u].node_failure == "9"){
			showstyle = "rect_red";
		}
		if(newJson[u].node_failure == "1"){
			showstyle = "rect_orange";
		}
		//showstyle="area_text";
		//drawSquare(tag_id+newJson[u].node_id+"-"+newJson[u].sysid,startx , starty,squarewidth,squareheight,showstyle,newJson[u].node_name,"text",newJson[u]);
		drawSquare(tag_id+newJson[u].node_id+"-"+newJson[u].sysid,startx , starty,squarewidth,squareheight,showstyle,newJson[u].node_name,"word18_black",newJson[u]);
		starty += y_gap ;
		if(newJson[u].node_failure == "1"){
			//console.log(tag_id+newJson[u].node_id+"-"+newJson[u].sysid);
			PutIntoFail(tag_id+newJson[u].node_id+"-"+newJson[u].sysid,"mylinex_red");
		}
		if(newJson[u].node_failure == "9"){
			//console.log(tag_id+newJson[u].node_id+"-"+newJson[u].sysid);
			PutIntoFail(tag_id+newJson[u].node_id+"-"+newJson[u].sysid,"mylinex_purple");
		}
	}
	}
	//console.log(FailelementArr);
}

function PutIntoFail(x,y){
	var founds = 0 ;
	for(var u=0 ; u < FailelementArr.length ; u=u+2){
		if(FailelementArr[u][0] == x){
			founds = 1 ;
			if( y == "mylinex"){
				FailelementArr.splice(u,2);
			}else{
				FailelementArr[u+1] = y ;
			}
			break ;
		}
	}
	if(founds == 0){
		FailelementArr.push(x,y);
	}
}

function showFailElements(){
	resultArr=[];
	for (var v=0 ; v < FailelementArr.length ; v=v+2){
		for(var u=0 ; u<elementArr.length ; u++){
			var invflag = 0 ;
			if(elementArr[u].indexOf(FailelementArr[v]) >= 0){
				if(elementArr[u][0] == "polyline"){
					resultArr.push(elementArr[u],invflag);
				}
			}
		}
	}
	for(var u=0 ; u < resultArr.length ; u=u+2){
		setElementClass(resultArr[u][1],"mylinex_yellow");
	}
}

function readFeedBackCountryJson(c){
	var headseq = 3 ;
	var tag_id = corridor_head[headseq]+"-";
	//var startx = 400 * (((j*1.5)+1)) ;
	var startx = x_point +(headseq* x_gap);
	var starty = y_point ;
	var newJson = JSON.parse(c).country_info ;
	for (var u=0 ; u < newJson.length  ; u++){
		drawSquare(tag_id+newJson[u].country_code+"-"+newJson[u].sysid,startx , starty,squarewidth,squareheight,"rect_blue",newJson[u].country_name,"word18_black",newJson[u]);
		starty += y_gap ;
	}
}

function readFeedBackCountryJson1(c){
	var headseq = 0 ;
	var tag_id = corridor_head[headseq]+"-";
	//var startx = 400 * (((j*1.5)+1)) ;
	var startx = x_point+(headseq* x_gap);
	var starty = y_point ;
	var newJson = JSON.parse(c).country_info ;
	for (var u=0 ; u < newJson.length  ; u++){
		drawSquare(tag_id+newJson[u].country_code+"-"+newJson[u].sysid,startx , starty,squarewidth,squareheight,"rect_blue",newJson[u].country_name,"word18_black",newJson[u]);
		starty += y_gap ;
	}
}

function readFeedBackJsonMix(c){

	var noitem = [0,0,0,0];
	var headseq = 0 ;
	var newJson = JSON.parse(c).node_info ;
	for (var u=0 ; u < newJson.length  ; u++){
		if(newJson[u].node_type == "INTL")	 headseq = 2;
		if(newJson[u].node_type == "INTR")	 headseq = 1;
		if(newJson[u].node_type == "ISLAND") headseq = 0;
		if(newJson[u].node_type == "FOREIGN") headseq = 3;
		noitem[headseq]++;
		var tag_id = corridor_head[headseq]+"-";
		var startx = x_point+(headseq* x_gap);
		var starty = noitem[headseq]*200 - 100 ;
		
		drawSquare(tag_id+newJson[u].node_id+"-"+newJson[u].sysid,startx , starty, squarewidth,squareheight,"rect_blue",newJson[u].node_name,"word18_black",newJson[u]);
	}
}



function readLineJson(c,k){
	var showStyle = "mylinex_normal";
	var newJson = JSON.parse(c).node_info ;
	var nodeA = corridor_head[1]+"-";
	var nodeB = corridor_head[2]+"-";
	//console.log(c);
	for (var u=0 ; u < newJson.length  ; u++){
		showStyle = "mylinex_normal";
		if(newJson[u].nodeA_type == "INTL")	 headseq = 2;
		if(newJson[u].nodeA_type == "INTR")	 headseq = 1;
		if(newJson[u].nodeA_type == "ISLAND") headseq = 0;
		if(newJson[u].nodeA_type == "FOREIGN") headseq = 3;
		//console.log(newJson[u].nodeA_type);
		nodeA = corridor_head[headseq]+"-";
		if(newJson[u].nodeB_type == "INTL")	 headseq = 2;
		if(newJson[u].nodeB_type == "INTR")	 headseq = 1;
		if(newJson[u].nodeB_type == "ISLAND") headseq = 0;
		if(newJson[u].nodeB_type == "FOREIGN") headseq = 3;
		nodeB = corridor_head[headseq]+"-";

		if(newJson[u].failure == "9"){
			showStyle = "mylinex_purple";
		}else{
			if(newJson[u].failure == "1"){
				showStyle = "mylinex_red";
			}else{
				showStyle = checkNodeFailure(nodeA+newJson[u].nodeA_id,nodeB+newJson[u].nodeB_id);
			}
		}
		drawLine1(nodeA+newJson[u].nodeA_id,nodeB+newJson[u].nodeB_id,showStyle,"0",newJson[u].link_name,true,newJson[u]);
	}
}

function checkNodeFailure(x,y){
	var retval = "mylinex";
	//console.log(FailelementArr);
	for (var v=0 ; v < FailelementArr.length ; v=v+2){
		if((FailelementArr[v] == x) || (FailelementArr[v] == y)){
			retval = FailelementArr[v+1];
		}
	}
	return retval ;
}

function readLineJsonDetail(c){
	var newJson = JSON.parse(c).node_info ;
	if(newJson != undefined){
	var nodeA = corridor_head[2]+"-";
	var nodeB = corridor_head[3]+"-";
	for (var u=0 ; u < newJson.length  ; u++){
		if(newJson[u].nodeA_id[0].substring(0,1)=="0"){nodeA = corridor_head[2]+"-";}
		if(newJson[u].nodeA_id[0].substring(0,1)=="1"){nodeA = corridor_head[1]+"-";}
		if(newJson[u].nodeA_id[0].substring(0,1)=="2"){nodeA = corridor_head[0]+"-";}
		if(newJson[u].nodeA_id[0].substring(0,1)=="3"){nodeA = corridor_head[3]+"-";}		
		if(newJson[u].nodeB_id[0].substring(0,1)=="0"){nodeB = corridor_head[2]+"-";}
		if(newJson[u].nodeB_id[0].substring(0,1)=="1"){nodeB = corridor_head[1]+"-";}
		if(newJson[u].nodeB_id[0].substring(0,1)=="2"){nodeB = corridor_head[0]+"-";}
		if(newJson[u].nodeB_id[0].substring(0,1)=="3"){nodeB = corridor_head[3]+"-";}

		drawLine1(nodeA+newJson[u].nodeA_id,nodeB+newJson[u].nodeB_id,"mylinex_normal","0",newJson[u].details_id,true,newJson[u]);
	}
	}
	
}

function colorMessage(x){
	$.colorbox.settings.opacity = 0.5;
	$.colorbox({
		onLoad: function() {
			$('#cboxClose').remove();
			$('#cboxContent').css("background","transparent");
		},
		html:"<h1 style='padding:30px;border-radius: 10px 10px 10px 10px;background:#a87132;line-height:50px;'>"+x+"</h1>",
		overlayClose: true
		});
}


function colorBoxNodeON(x){
	var thisInfo = getOneElementDesc(x,7);
	//$.colorbox.settings.opacity = 0.5;
	$.colorbox({
		onLoad: function() {
			//$('#cboxClose').remove();
			//$('#cboxContent').css("background","transparent");
		},
		//href:"scsi/node_pop.jsp?sysid="+thisInfo.sysid,
		//href:"scsi/link_pop.jsp?sysid=1",
		html:"<h1 style='padding:30px;border-radius: 10px 10px 10px 10px;background:#a87132;line-height:50px;'>"+thisInfo.node_name+"<BR>"+getFailMessage(thisInfo.node_failure)+"</h1>",
		overlayClose: true
		});
}

function getFailMessage(x){
	var retval = "無障礙";
	if(x==1) retval = "部份障礙";
	if(x==9) retval = "全部毁損";
	return retval ;
}

function colorBoxON(x){
	LineBoxMessage(x);
	
}

function abc(x){
	$.colorbox.close();
	setTimeout("showdetail('"+x+"');",500);
}

function showdetail(x){
	$.colorbox({
		onLoad: function() {
			$('#cboxClose').remove();
			$('#cboxContent').css("background","transparent");
		},
		href:"jerry.jsp",
		//html:"<h1 style='padding:30px;border-radius: 10px 10px 10px 10px;background:#a87132;line-height:50px;'>"+x+"</h1>",
		overlayClose: true
		});
}

function LineBoxMessage(x){
	var thisInfo = getOneElementDesc(x,7);
	var showInfoData = "";
	//console.log(thisInfo);
	if(thisInfo.details_id == null){
		showInfoData = thisInfo.link_name +"<BR>"+thisInfo.link_nodeA+" 到 "+thisInfo.link_nodeB;
		if(thisInfo.failure== '0'){  
			showInfoData += "<BR>無障礙，最大頻寬:"+thisInfo.max_bandwidth+"GB";
			showInfoData += "<BR>已用頻寬:"+thisInfo.using_bandwidth+"GB";
		}
		if(thisInfo.failure== '1'){
			showInfoData += "<BR>部份中斷，最大頻寬:"+thisInfo.max_bandwidth+"GB";
			showInfoData += "<BR>障礙頻寬:"+thisInfo.fail_bandwidth+"GB";
		}
		if(thisInfo.failure== '9'){
			showInfoData += "<BR>全中斷，影響頻寬:"+thisInfo.max_bandwidth+"GB";
		}
		//showInfoData += "<BR><input onclick='abc("+thisInfo.sysid+");' value='狀態編輯' type='button'>";
	}else{
		showInfoData = thisInfo.details_id;
	}
	$.colorbox.settings.opacity = 0.5;
	$.colorbox({
		onLoad: function() {
			$('#cboxClose').remove();
			$('#cboxContent').css("background","transparent");
		},
		html:"<h1 style='padding:30px;border-radius: 10px 10px 10px 10px;background:#a87132;line-height:50px;'>"+showInfoData+"</h1>",
		overlayClose: true
		});
}

function statusMap(x){
	var retval = "無";
	if(x== '0'){retval = "無障礙";}
	if(x== '1'){retval = "部份中斷";}
	if(x== '9'){retval = "全中斷";}
	return retval ;
}

var testMove = 0 ;
var ballColor = "brown";

function showMoveBall(){
	clearMoveBall();
	if(testMove == 0){
		moveList =["201,103,001","108,002,301"];
		testMove = 1;
		ballColor = "blue";
	}else{
		moveList =["201,103,002,301","101,005,301"];
		testMove = 0;
		ballColor = "brown";
	}
	move_h = 0 ;
	showMoveNow();
}



function clearMoveBall(){
	for(var u=0 ; u<elementArr.length ; u++){
		deleteElement = 0 ;
		if(elementArr[u][0] == "circlemultimove") deleteElement =1;
		if(elementArr[u][0] == "circlemove") deleteElement =1;
		if(elementArr[u][0] == "animateMotion") deleteElement =1;
		if(deleteElement == 1){
			if(elementArr[u][0] == "animateMotion"){
				elementArr.splice(u, 1);
				u--;
			}else{
				document.getElementById(elementArr[u][1]).remove();
				if(elementArr[u][0] == "circlemultimove"){
					elementArr.splice(u, 1);
					u--;
				}else if(elementArr[u][0] == "circlemove"){
					elementArr.splice(u, 1);
					u--;
				}else{
					elementArr.splice(u, 1);
					u--;
				}
			}	
		}
	}
}

function getNodeInfo(x){
	$.post("/scsi/DataAPI", {scable:"",func:"13",loc:"111"})
	//$.post("/scsi/DataAPI", {scable:PostDataEnc(testjson,passKey),func:"6",loc:"111"})
	.done(function( data ) {
		//console.log(decodeURIComponent(data));
		var newJson = JSON.parse(decodeURIComponent(data)).node_info ;
		var mylist = "";
		//console.log(newJson.length);
		for(var v=0 ; v<newJson.length ; v++){
			if(mylist.length>0) mylist += "<w>";
			mylist += newJson[v].node_rn+"<i>"+newJson[v].node_name+"("+newJson[v].node_type_name+")";
		}
		showSelect(mylist+"<w>",x);
	});
}



function getNodeInfo1(x,y){
	$("#"+x).find('option').each(function(){
    	$(this).show();
    });
	if(y!="0"){
	 $("#"+x+" option[value='"+y+"']").hide();
	}
}

var simFeedBack = "null" ;
var findstart = "null";
var findend = "null";

function findPath(){
	runPathinit();
	var node1 = $("#node_left").find("option:selected").val();
	var node2 = $("#nodemiddle1").find("option:selected").val();
	findstart = $("#node_left").find("option:selected").text();
	findend = $("#nodemiddle1").find("option:selected").text();
	var weight1 = $("#g1").find("option:selected").val();
	//console.log(node1+"/"+node2);
	//$.post("findPath2_adv.jsp", {start:node1,end:node2,r:weight1})
	$.post("findPath2.jsp", {start:node1,end:node2,r:weight1})
	//$.post("/scsi/DataAPI", {scable:PostDataEnc(testjson,passKey),func:"6",loc:"111"})
	.done(function( data ) {
		//console.log(decodeURIComponent(data));
		var data1 = data.replace(/(^[\s]*)|([\s]*$)/g, "");
		if(data1 == ""){
			document.getElementById("abc").innerHTML = "無路徑";
			simFeedBack = "null";
			showAllElement();
			colorMessage("   無路徑   ");
		}else{
			hideAllElement();
			simFeedBack = decodeURIComponent(data1);
			findLineID(decodeURIComponent(data1));
		}
	});
}

function findPath1(){
	runPathinit();
	var node1 = $("#node_left").find("option:selected").val();
	var node2 = $("#nodemiddle1").find("option:selected").val();
	findstart = $("#node_left").find("option:selected").text();
	findend = $("#nodemiddle1").find("option:selected").text();
	var weight1 = $("#g1").find("option:selected").val();
	$.post("findPath2_adv.jsp", {start:node1,end:node2,r:weight1})
	//$.post("findPath2.jsp", {start:node1,end:node2,r:weight1})
	//$.post("/scsi/DataAPI", {scable:PostDataEnc(testjson,passKey),func:"6",loc:"111"})
	.done(function( data ) {
		//console.log(decodeURIComponent(data));
		var data1 = data.replace(/(^[\s]*)|([\s]*$)/g, "");
		if(data1 == ""){
			document.getElementById("abc").innerHTML = "無路徑";
			simFeedBack = "null";
			showAllElement();
			colorMessage("   無路徑   ");
		}else{
		hideAllElement();
		simFeedBack = decodeURIComponent(data1);
		findLineID(decodeURIComponent(data1));
		}
	});
}

function findPath2(){
	runPathinit();
	var node1 = $("#node_left").find("option:selected").val();
	var node2 = $("#nodemiddle1").find("option:selected").val();
	if(node1 != 0 && node2 != 0 ){
	findstart = $("#node_left").find("option:selected").text();
	findend = $("#nodemiddle1").find("option:selected").text();
	var weight1 = $("#g1").find("option:selected").val();
	var bwneeds = $("#bw_needs").val();
	$.post("findPath2_adv1.jsp", {start:node1,end:node2,r:weight1})
	//$.post("findPath2.jsp", {start:node1,end:node2,r:weight1})
	//$.post("/scsi/DataAPI", {scable:PostDataEnc(testjson,passKey),func:"6",loc:"111"})
	.done(function( data2 ) {
		var datac = data2.replace(/(^[\s]*)|([\s]*$)/g, "");
		var dataarray = decodeURIComponent(datac).split("<WI>");
		var data1 = dataarray[0];
		var datashow = dataarray[1];
		hideAllElement();
		if(data1 == ""){
			document.getElementById("startname1").innerHTML = findstart;
			document.getElementById("endname1").innerHTML = findend;
			document.getElementById("abc").innerHTML = "<center><font color='red' size='24px'>無路徑</font></center>";
			simFeedBack = "null";
			//showAllElement();
			colorMessage("   無路徑   ");
		}else{
			simFeedBack = datashow;
			document.getElementById("abc").innerHTML = "";
			//console.log(data1);
			findLineID1(decodeURIComponent(data1),decodeURIComponent(datashow),bwneeds);
			showPathDetail(simFeedBack);
		}
	});
	}else{
		colorMessage("   起迄站點資料請選擇   ");
	}
}
var min_bw = "";
var min_bw_path = "";
var resource_path = "";
var resource_bw = "";
var resource_eachpath = "";
function findLineID1(x,y,z){
	min_bw = "";
	min_bw_path = "";
	resource_path = "";
	resource_bw = "";
	resource_eachpath = "";
	document.getElementById("startname1").innerHTML = findstart;
	document.getElementById("endname1").innerHTML = findend;
	
	document.getElementById("abc").innerHTML = "";
	var thisPathContent = "";
	//try{
		var tmpfrom = x.split("*");

		thisPathContent+= "<div class='data_group'>"
		thisPathContent+= "<div class='start'><p>"+findstart+"</p></div>";
		thisPathContent+= "<div class='end'><p>"+findend+"</p></div>";
		thisPathContent+= "<div class='routing'>";
		for(var u=1 ; u< tmpfrom.length ; u++){
			var tmpx = tmpfrom[u].split(",")[1];
			thisPathContent+= "<div id='rowline_"+u+"' class='routing_data'>";
			thisPathContent+= "<div id='circle_"+u+"' class='circle'>"+u+"</div>";
			var tmpnode = tmpx.split("->");
			for(var v=0 ; v < tmpnode.length ; v++){ 
				if(v>0) thisPathContent+= "<div id='"+u+"_"+tmpnode[v-1].split("/")[0]+"_"+tmpnode[v].split("/")[0]+"_triangle' class='data_triangle'></div>";			
				thisPathContent+= "<p>"+tmpnode[v].split("/")[2]+"</p>";
			}
			thisPathContent+="<span id='"+u+"_bw'></span></div>";
		}
		thisPathContent+= "</div>";
		thisPathContent+= "<div id='showareason' class='illustrate'></div><div id='showaresult' class='result'></div></div>";
	//}catch {}
	document.getElementById("abc").innerHTML += thisPathContent +"<BR><BR><BR>";
	var searchid = "begin";
	var searchcount = 0 ;
	var searchLine = JSON.parse(y).node_info;
	//console.log(searchLine.length);
	var areason = "";
	var aresult = "";
	for(var u=1 ; u< tmpfrom.length ; u++){
		var myRestBW = 99999999 ;
		var myRestBWPath = "";
		var tmpx = tmpfrom[u].split(",")[1];
		var tmpnode = tmpx.split("->");
		var CanUseBW = 0 ;
		var CanUseBWPath = "";
		var nodeareason = "";
		var pathfail = 0 ;
		resource_eachpath += (resource_eachpath ==""?"":"*");
		for(var v=0 ; v < tmpnode.length-1 ; v++){
			var tmpRestBW = 0 ;
			var tmpRestBWPath = "" ;
			var totalDisconnect = 0 ;
			var connectCount = 0 ;
			var nodeDown = 0 ;
			CanUseBW = 0 ;
			CanUseBWPath = "";
			while (u > 0){
				connectCount++;
				var link_angleid = u+"_node"+searchLine[searchcount].nidA[0]+"_node"+searchLine[searchcount].nidB[0]+"_triangle";
				if(document.getElementById(link_angleid) == null){
					link_angleid = u+"_node"+searchLine[searchcount].nidB[0]+"_node"+searchLine[searchcount].nidA[0]+"_triangle";
				}
				var linkinfo = searchLine[searchcount].link_id[0]+":"+searchLine[searchcount].link_name[0]+" "+statusMap(searchLine[searchcount].failure[0])+"("+(searchLine[searchcount].rest_bandwidth[0] == ""?"0":searchLine[searchcount].rest_bandwidth[0])+")";
				var addbw = 0 ;

				if(searchLine[searchcount].failure[0] != 0){
					if(searchLine[searchcount].failure[0] != 9){
						addbw = 1 ;
					}else{
						totalDisconnect++;
					}
					
					link_red(link_angleid,linkinfo);
				}else{
					link_normal(link_angleid,linkinfo);
					addbw = 1 ;
					//var tmpCal = (Number((searchLine[searchcount].rest_bandwidth[0] == ""?"0":searchLine[searchcount].rest_bandwidth[0])) - Number((searchLine[searchcount].failure_bandwidth[0] == ""?"0":searchLine[searchcount].failure_bandwidth[0])) );
					//tmpRestBW += (tmpCal < 0 ? 0 : tmpCal);
				}
				if(searchLine[searchcount].nidA_failure[0] == '9'){
					var tmpNodereason = searchLine[searchcount].link_nodeA[0] +"毁損";
					//console.log(nodeareason.indexOf(tmpNodereason));
					if(nodeareason.indexOf(tmpNodereason) < 0){
						nodeareason += (nodeareason == ""?"":"") + searchLine[searchcount].link_nodeA[0] +"毁損<BR>";
					}
					//console.log(nodeareason);
					nodeDown = 1;
					node_red(u);
					//if(faillink_already == 0) link_red(link_angleid,searchLine[searchcount].linkinfo);
					//faillink_already++ ;
				}
				if(searchLine[searchcount].nidB_failure[0] == '9'){
					var tmpNodereason = searchLine[searchcount].link_nodeB[0] +"毁損";
					if(nodeareason.indexOf(tmpNodereason) < 0){
						nodeareason += (nodeareason == ""?"":"") + searchLine[searchcount].link_nodeB[0] +"毁損<BR>";
					}
					nodeDown = 1;
					node_red(u);
					//if(faillink_already == 0) link_red(link_angleid,linkinfo);
					//faillink_already++ ;
				}
				if(addbw == 1){
					CanUseBW += Number((searchLine[searchcount].rest_bandwidth[0] == ""?"0":searchLine[searchcount].rest_bandwidth[0]));
					//tmpRestBW += Number((searchLine[searchcount].rest_bandwidth[0] == ""?"0":searchLine[searchcount].rest_bandwidth[0]));
					CanUseBWPath = link_angleid ;
				}
				searchcount++;
				if(searchcount < searchLine.length){
					var link_angleid1 = u+"_node"+searchLine[searchcount].nidA[0]+"_node"+searchLine[searchcount].nidB[0]+"_triangle";
					if(document.getElementById(link_angleid1) == null){
						link_angleid1 = u+"_node"+searchLine[searchcount].nidB[0]+"_node"+searchLine[searchcount].nidA[0]+"_triangle";
					}
					if(link_angleid != link_angleid1){
						if(totalDisconnect == connectCount){
							node_red(u);
							var matchstr = "路徑 "+searchLine[searchcount-1].link_nodeA[0] + " ~ " + searchLine[searchcount-1].link_nodeB[0] + "全中斷" ;
							if(areason.indexOf(matchstr) < 0){
								areason += (areason == ""?"":"") + (pathfail==1?"":"<font color='red'>路徑 "+u+" :</font> <BR>")+ searchLine[searchcount-1].link_nodeA[0];
								areason += " ~ " + searchLine[searchcount-1].link_nodeB[0] + "全中斷<BR>";
								pathfail = 1 ;
							}
						}
						//areason += (nodeareason==""?"":"<BR>"+nodeareason) ;
						tmpRestBW = (nodeDown == 1 ? 0 : CanUseBW);
						if(resource_path.indexOf(":"+link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+":") < 0){
							resource_path += (resource_path == ""? "":"*")+":"+link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+":"
							resource_bw += (resource_bw==""?"":"*")+tmpRestBW;
						}
						resource_eachpath += link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+":" ;
						break;
					}
				}else{
					if(totalDisconnect == connectCount){
						node_red(u);
						var matchstr = "路徑 "+searchLine[searchcount-1].link_nodeA[0] + " ~ " + searchLine[searchcount-1].link_nodeB[0] + "全中斷" ;
						if(areason.indexOf(matchstr) < 0){
							areason += (areason == ""?"":"") + (pathfail==1?"":"<font color='red'>路徑 "+u+" :</font> <BR>")+searchLine[searchcount-1].link_nodeA[0];
							areason += " ~ " + searchLine[searchcount-1].link_nodeB[0] + "全中斷<BR>";
							pathfail = 1 ;
						}
					}
					
					tmpRestBW = (nodeDown == 1 ? 0 : CanUseBW);
					if(resource_path.indexOf(":"+link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+":") < 0){
						resource_path += (resource_path == ""? "":"*")+":"+link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+":"
						resource_bw += (resource_bw==""?"":"*")+tmpRestBW;
					}
					resource_eachpath += link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+":" ;
					break;
				}
			}
			if(tmpRestBW < myRestBW){
				myRestBW = tmpRestBW ;
				myRestBWPath = CanUseBWPath ;
			}
		}
		areason += (nodeareason==""?"":(pathfail == 1?"":"<font color='red'>路徑 "+u+" : </font><BR>")+nodeareason) ;
		document.getElementById(u+"_bw").innerHTML = "( 可使用剩餘頻寬 "+myRestBW +"GB )";
		min_bw += (min_bw==""?myRestBW:"_"+myRestBW) ;
		min_bw_path += (min_bw_path==""?myRestBWPath:"**"+myRestBWPath) ;
		if(resource_path.indexOf(":"+link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+":") < 0){
			resource_path += (resource_path == ""? "":"*")+":"+link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+":"
			resource_bw += (resource_bw==""?"":"*")+tmpRestBW;
		}
		//resource_eachpath += link_angleid.split("_")[1]+"_"+link_angleid.split("_")[2]+"C:" ;
	}
	if(document.getElementById(u+"_bw") != null){
		document.getElementById(u+"_bw").innerHTML = "( 可使用剩餘頻寬 "+myRestBW +"GB )";
		min_bw += (min_bw==""?myRestBW:"_"+myRestBW) ;
		min_bw_path += (min_bw_path==""?myRestBWPath:"**"+myRestBWPath) ;
	}
	var rpath_array = resource_path.split("*");
	var rbw_array = resource_bw.split("*");
	var samegroup = "";
	if(z == ""){
		aresult = "無替代路由頻寬需求";
	}else{
		if((/^\d+$/.test(z))){
			var checkconnection = resource_eachpath.split("*");
			var calBW = min_bw.split("_");
			var calBWPath = min_bw_path.split("**");
			var totalbw = Number(z);
			for (var w=0 ; w < calBW.length ; w++){
				if(Number(calBW[w]) > 0){
					var tmppath = checkconnection[w].split(":");
					var enoughflag = 1 ;
					var tmpnum = "";
					for (var k=0 ; k<tmppath.length -1 ; k++){
						for(var p=0 ; p < rpath_array.length ; p++){
							if((":"+tmppath[k]+":") == rpath_array[p]){
								if(totalbw > Number(calBW[w])){
									if((rbw_array[p] - calBW[w]) >= 0){
										tmpnum += (tmpnum==""?"":",")+p+":"+(rbw_array[p] - calBW[w]);
									}else{
										enoughflag = 0 ;
									}										
								}else{
									if((rbw_array[p] - totalbw) >= 0){
										tmpnum += (tmpnum==""?"":",")+p+":"+(rbw_array[p] - totalbw);
									}else{
										enoughflag = 0 ;
									}
								}
								 
							}
						}
					}
					if(enoughflag == 1){
						var numback = tmpnum.split(",");
						for(var p=0 ; p<numback.length ; p++){
							rbw_array[numback[p].split(":")[0]] = numback[p].split(":")[1];
						}
						if(totalbw > Number(calBW[w])){
							aresult += "路由 "+(w+1)+" :<BR> 可分散頻寬需求 "+calBW[w]+" GB<BR>";
						}else{
							aresult += "路由 "+(w+1)+" :<BR> 可分散頻寬需求 "+totalbw+" GB<BR>";
						}
						totalbw = totalbw - Number(calBW[w]);
						if(totalbw < 0) break ;	
					}
					
				}
			}
			if(totalbw > 0){
				aresult += "<font color='red'>";
				if(aresult != ""){
					aresult += "剩餘頻寬需求 "+totalbw + "GB 無適當頻寬路由, 可提供調度之用<BR>";
				}else{
					aresult += "頻寬需求 "+totalbw + "GB 無適當頻寬路由, 可提供調度之用<BR>";
				}
				aresult += "</font>";
			}
			if (aresult == "") aresult = "因<font color='red'>無足夠頻寬</font>，故無任何路由可調度給頻寬需求 "+totalbw+ "GB<BR>"; 
		}else{
			aresult = "訊務需求頻寬, 請填入數字";
		}
	}	
	$("#showareason").html(areason);
	$("#showaresult").html(aresult);
}

function node_red(x){
	document.getElementById("rowline_"+x).setAttribute("class","routing_data red");
	document.getElementById("circle_"+x).setAttribute("class","circle red");
}

function link_red(x,y){
	//console.log(x);
	document.getElementById(x).setAttribute("class","data_triangle red");
	var org_title = document.getElementById(x).getAttribute("title");
	if(org_title == null) org_title = "";
	document.getElementById(x).setAttribute("title",org_title+"\n"+y);
}

function link_normal(x,y){
	//console.log(x);
	
	var org_title = document.getElementById(x).getAttribute("title");
	if(org_title == null) org_title = "";
	document.getElementById(x).setAttribute("title",org_title+"\n"+y);
}

function showPathDetail(x){
	var searchLine = JSON.parse(x).node_info;
	for(var u=0 ; u < searchLine.length ; u++){
		for(var v=0 ; v< elementArr.length ; v++){
			if(elementArr[v][0] == "polyline"){
				if(elementArr[v][7].link_id == searchLine[u].link_id[0]){
					var tt = document.getElementById(elementArr[v][1]);
						$("#"+elementArr[v][1]).show();
						$("#"+elementArr[v][2]).show();
						$("#"+elementArr[v][3]).show();
						$("#txt_"+elementArr[v][2]).show();
						$("#txt_"+elementArr[v][3]).show();
				}
			}
		}
	}	
}

function showPathDetail1(x){
	var searchLine = x.split("<w>");
	for(var u=0 ; u < searchLine.length-1 ; u++){
		var thisLine = "";
		var showthisLine = "";
		var eachLine = searchLine[u].split("<i>");
		for(var w=0 ; w < eachLine.length-1 ; w++){
			for(var v=0 ; v< elementArr.length ; v++){
				if(elementArr[v][0] == "polyline"){
					if(elementArr[v][7].link_id == eachLine[w]){
						var tt = document.getElementById(elementArr[v][1]);
						$("#"+elementArr[v][1]).show();
						$("#"+elementArr[v][2]).show();
						$("#"+elementArr[v][3]).show();
						$("#txt_"+elementArr[v][2]).show();
						$("#txt_"+elementArr[v][3]).show();
						//tt.setAttribute("class","mylinex_"+colornum);
						//changeLineClass(elementArr[v][1],"mylinex_"+colornum);
						//console.log(v+"/"+elementArr[v][7].link_id+"/"+searchLine[u]+"/"+elementArr[v][1]);
						thisLine += elementArr[v][1]+"<i>"+elementArr[v][2]+"<i>"+elementArr[v][3]+"<w>";
						if(showthisLine.length > 0) showthisLine += " ~~ ";
						showthisLine += changeFailColor(elementArr[v][7].link_name,elementArr[v][7].failure)+"/"+elementArr[v][7].max_bandwidth+"("+changeFailColor(elementArr[v][7].link_nodeA,elementArr[v][7].nodeA_failure)+"~"+changeFailColor(elementArr[v][7].link_nodeB,elementArr[v][7].nodeB_failure)+") ";
						//console.log(showthisLine);
						break;
					}
				}
			}
		}
	}	
}

function findLineID(x){
	var searchLine = x.split("<w>");
	showfindpathresult=[];
	findpathresult = [];
	//for(var u=searchLine.length-2 ; u >= 0 ; u--){
		//var colornum = u+1 ;
		//if(colornum > 9) colornum = (colornum%9);
	for(var u=0 ; u < searchLine.length-1 ; u++){
		var thisLine = "";
		var showthisLine = "";
		var eachLine = searchLine[u].split("<i>");
		for(var w=0 ; w < eachLine.length-1 ; w++){
			for(var v=0 ; v< elementArr.length ; v++){
				if(elementArr[v][0] == "polyline"){
					if(elementArr[v][7].link_id == eachLine[w]){
						var tt = document.getElementById(elementArr[v][1]);
						$("#"+elementArr[v][1]).show();
						$("#"+elementArr[v][2]).show();
						$("#"+elementArr[v][3]).show();
						$("#txt_"+elementArr[v][2]).show();
						$("#txt_"+elementArr[v][3]).show();
						//tt.setAttribute("class","mylinex_"+colornum);
						//changeLineClass(elementArr[v][1],"mylinex_"+colornum);
						//console.log(v+"/"+elementArr[v][7].link_id+"/"+searchLine[u]+"/"+elementArr[v][1]);
						thisLine += elementArr[v][1]+"<i>"+elementArr[v][2]+"<i>"+elementArr[v][3]+"<w>";
						if(showthisLine.length > 0) showthisLine += " ~~ ";
						showthisLine += changeFailColor(elementArr[v][7].link_name,elementArr[v][7].failure)+"/"+elementArr[v][7].max_bandwidth+"("+changeFailColor(elementArr[v][7].link_nodeA,elementArr[v][7].nodeA_failure)+"~"+changeFailColor(elementArr[v][7].link_nodeB,elementArr[v][7].nodeB_failure)+") ";
						//console.log(showthisLine);
						break;
					}
				}
			}
		}
		findpathresult.push(thisLine);
		showfindpathresult.push(showthisLine);
	}
	alterPath = findpathresult.length-1 ;
	//altertick = setTimeout("nextrunAlterPath();",3000);
	document.getElementById("startname1").innerHTML = findstart;
	document.getElementById("endname1").innerHTML = findend;
	document.getElementById("startname2").innerHTML = findstart.replace("(","<BR>(");
	document.getElementById("endname2").innerHTML = findend.replace('(','<BR>(');
	document.getElementById("abc").innerHTML = "";
	var y = findpathresult.length ;
	var thisPathContent = "";
	thisPathContent = "";
	thisPathContent+= "<div class='data_group mgt-2'>"
	thisPathContent+= "<div class='start'><p>"+findstart+"</p></div>";
	thisPathContent+= "<div class='end'><p>"+findend+"</p></div>";
	thisPathContent+= "<div class='routing red'>";
	for(var b=0 ; b<y ; b++){
		thisPathContent+= "<div class='routing_data red'>";
		thisPathContent+= (b+1)+". ";
		thisPathContent+= showfindpathresult[b];
		thisPathContent+="</div>";
	}
	thisPathContent+= "</div>";
	thisPathContent+= "<div class='illustrate'></div><div class='result'></div></div>";
	document.getElementById("abc").innerHTML += thisPathContent ;
	
}

function changeFailColor(x,y){
	//console.log(x+"/"+y);
	var retval = x ;
	if(y=="1") retval = "<span style='color:orange'>"+x+"</span>";
	if(y=="9") retval = "<span style='color:red'>"+x+"</span>";
	return retval ;
}

function hideAllElement(){
	for(var v=0 ; v< elementArr.length ; v++){
		$("#"+elementArr[v][1]).hide();
	}
}

function showAllElement(){
	for(var v=0 ; v< elementArr.length ; v++){
		$("#"+elementArr[v][1]).show();
	}
}

function showSimResult(){
	if(simFeedBack != "null"){
	hideAllElement();
	showPathDetail(simFeedBack);
	}
}

function runAlterPath(){
	clearTimeout(altertick);
	var y = findpathresult.length ;
	if(alterPath < 0) {
		alterPath = y-1 ;
	}
	document.getElementById("shorestpath").innerHTML=showfindpathresult[alterPath];
	
	alterPath--;
	altertick = setTimeout("nextrunAlterPath();",3000);
}

function runPathinit(){
	clearTimeout(altertick);
	for(var u=0 ; u < elementArr.length ; u++){
		if(elementArr[u][1].indexOf("poline_") == 0){
			replaceElementClass(elementArr[u][1],elementArr[u][4]);
		}
	}
	
}

function nextrunAlterPath(){
	//resetElements();
	runAlterPath();
}

function PostDataEnc(st,key){
	var stringForEncryption = JSON.stringify(st);
	var encrypted = CryptoJS.AES.encrypt(stringForEncryption, key);
	return encrypted.toString();
}

function getDataDes(st,key){
	var bytes  = CryptoJS.AES.decrypt(st.replace(/(^[\s]*)|([\s]*$)/g, ""), key);
	var decryptedData = JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
	return decryptedData;
}



//console.log(elementArr);