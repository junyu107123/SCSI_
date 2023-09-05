package scsi.demo.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import scsi.demo.repository.*;
import scsi.demo.wisoft.Data;
import scsi.demo.wisoft.DataProcess;
import scsi.demo.wisoft.PathSort;
import scsi.demo.scsi.DataAPI;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userid")
public class DataController {

	@Autowired
	public UserRepository userRepository ;

	@PostMapping(value= {"/DataAPI","/scsi/DataAPI"})
	public @ResponseBody String DataAPI(@ModelAttribute("func") String myfunc,
			@ModelAttribute("scable") String get_scable, @ModelAttribute("loc") String get_loc, Data dta, DataAPI api) {
		ModelAndView model = new ModelAndView("DataAPI");
		System.out.println("myfunc=" + myfunc + "/get_scable=" + get_scable + "/get_loc=" + get_loc);
		String[] typevalue = {};
		String[] para = {};
		int goNext = 0;
		String feedback = "no";

		if(get_scable != null){
			if(myfunc.equals("6") || myfunc.equals("7") || myfunc.equals("8")|| myfunc.equals("13")|| myfunc.equals("17")){
				goNext++;
			}else{
				get_scable = get_scable.matches("[A-Za-z0-9,-_%]+") ? get_scable : "";
				if(dta.cleanXSS(dta.cleanSQLInject(get_scable)).indexOf("forbid")< 0) goNext++;
			}
		}else{
			if(myfunc.equals("1")) goNext++;
		}
		if(get_loc != null){
			get_loc = get_loc.matches("[A-Za-z0-9,-_%]+") ? get_loc : "";
			if(dta.cleanXSS(dta.cleanSQLInject(get_loc)).indexOf("forbid")< 0) goNext++;
		}else{
			if(myfunc.equals("1") || myfunc.equals("2") || myfunc.equals("5")) goNext++;
		}
	if(goNext == 2){
		if(myfunc.equals("1")){
			String[] typevaluex = {};
			String[] parax = {};
			typevalue = typevaluex;
			para = parax;
		}


		if(myfunc.equals("2")){
			String[] typevaluex = {"s"};
			String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
			typevalue = typevaluex;
			para = parax;
		}

		if(myfunc.equals("3")){
			String[] typevaluex = {"s","s","s"};
			String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim(),dta.cleanXSS(dta.cleanSQLInject(get_loc)).trim(),dta.cleanXSS(dta.cleanSQLInject(get_loc)).trim()};
			typevalue = typevaluex;
			para = parax;
		}


		if(myfunc.equals("4")){
			String[] typevaluex = {"s","s"};
			if(get_scable.indexOf("EAC")>=0) get_scable="EAC";
			if(get_scable.indexOf("NAL")>=0) get_scable="FNALRNAL";
			String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim().replaceAll("-",""),dta.cleanXSS(dta.cleanSQLInject(get_loc)).trim()};
			typevalue = typevaluex;
			para = parax;
		}
		
		if(myfunc.equals("5")){
			String[] typevaluex = {"s"};
			String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("6")){
			String[] typevaluex = {"s"};
			String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("7")){
			String[] typevaluex = {"s"};
			String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("8")){
			String[] typevaluex = {};
			String[] parax = {};
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("9")){
			String[] typevaluex = {"i","i"};
			String[] parax = dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim().split("-");
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("10")){
			String[] typevaluex = {"i","i"};
			String[] parax = dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim().split("-");
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("11")){
			String[] typevaluex = {};
			String[] parax = {};
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("12")){
			String[] typevaluex = {"s","s"};
			String[] parax = {dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim(),dta.cleanXSS(dta.cleanSQLInject(get_scable)).trim()};
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("13") || myfunc.equals("14") || myfunc.equals("15")){
			String[] typevaluex = {};
			String[] parax = {};
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("17")){
			String[] typevaluex = {"s"};
			String[] parax = {get_scable};
			typevalue = typevaluex;
			para = parax;
		}
		if(myfunc.equals("18")){
			String[] typevaluex = {"s"};
			String[] parax = {get_scable};
			typevalue = typevaluex;
			para = parax;
		}
		
	}
		
		try {
			for(String str:typevalue) {
				System.out.println("typevalue="+str);
			}
			for(String str2:para){
				System.out.println("para="+str2);
			}
			feedback = api.getNewData(myfunc,typevalue,para).trim();
		} catch (IOException e) {
			
			System.out.println("e ="+e.toString());
		} catch (SQLException e1) {
			
			System.out.println("e1="+e1.toString());
		}
		//System.out.println("DACON feedback ="+feedback.length());
		return feedback;
//		return "OK";
	}
	
	@PostMapping(value= {"/findPath2_adv1","/scsi/findPath2_adv1"})
	public @ResponseBody String findPath2(@ModelAttribute("start") int startint ,@ModelAttribute("end") int endint,@ModelAttribute("r") int ascordesc,PathSort pst,DataProcess dtp){
		
		System.out.println("start="+startint+"/end="+endint+"/r="+ascordesc);
		String s="";
		List<String> list =new ArrayList<String>();
		int crossfog = 0 ;
		String getWeight = "rest_bandwidth";
		//System.out.println("da 193");
		try {
			String nowtime = dtp.todaytime_hhmm();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			//System.out.println("ioe="+ioe.toString());
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			//System.out.println("sqle"+sqle.toString());
		}
		//System.out.println("da 202");
try {
	//System.out.println("da 205");
		try {
		pst.setHopNum(8);
		}catch(Exception e5) {System.out.println("e5="+e5.toString());}
		//System.out.println("da 209");
		try {
		pst.BuildVituralNetwork(startint,endint);
		}catch(Exception e6) {System.out.println("e6="+e6.toString());}
		//System.out.println("da 213");
		try {
		pst.startRUN(startint, endint, ascordesc , "rest_bandwidth");
		}catch(Exception e7) {System.out.println("e7="+e7.toString());}
		String[] NodeName = pst.getArrayResult("NodeName");
		ArrayList showpath = pst.getArrayList("al");
		String[][] showLink = pst.getArrayResult2D("retLink");
		String deletelist = "";
		String backTaiwan = "0";
		//System.out.println("da 222");
		try {
		if(crossfog == 1){
			deletelist = "_";
		}else{
			//System.out.println("showpath.size()="+showpath.size());
		//	System.out.println("showLink[0].length="+showLink[0].length);
		for (int i=0 ; i < showpath.size() ; i++){
			backTaiwan = "0";
			int isF = 0 ;
			for(int j=1 ; j < showLink[0].length ; j++){
				if(showLink[i][j]!=null){
					String matchString = showLink[i][j];
					int FirstForeign = matchString.indexOf("FOREIGN") ;
					if(FirstForeign >= 0){
						isF++;
					}
				}
			}
			if(isF > 1) deletelist += i +"_";
		}
		if(deletelist == "") deletelist = "_";
		}
		//deletelist = "_";
		}catch(Exception e3) {System.out.println("e3="+e3.toString());}
		String[] noshow = deletelist.split("_");
		try {
			//System.out.println("showpath2.size()="+showpath.size());
		for (int i=0 ; i < showpath.size() ; i++){
			int showmenow = 1 ;
			for(int k=0 ; k<noshow.length ; k++){
				if(i == Integer.parseInt(noshow[k])) showmenow = 0 ;
			}
			if(showmenow == 1){
				list.add("*"+showpath.get(i)+"");
			}
		}
		}catch(Exception e4) {System.out.println("e4="+e4.toString());}
		int beginprint = 0 ;
		list.add("<WI>");
		list.add("{\"node_info\":[");
		try {
		for (int i=0 ; i < showpath.size() ; i++){
			int showmenow = 1 ;
			for(int k=0 ; k<noshow.length ; k++){
				if(i == Integer.parseInt(noshow[k])) showmenow = 0 ;
			}
			if(showmenow == 1){
			for(int j=1 ; j < showLink[0].length ; j++){
				if(showLink[i][j]!=null){
					if(beginprint > 0){
						list.add(",");
					}
					list.add(showLink[i][j]);
					beginprint = 1 ;
				}
			}
			}
		}
		}catch(Exception e2) {System.out.println("e2="+e2.toString());}
		list.add("]}");
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<list.size();i++) {
			sb.append(list.get(i));
			s=sb.toString();
			}
		
		//System.out.println("SSS="+s);
}catch(Exception e1) {System.out.println("e1="+e1.toString());}
		return s;
	}
	@RequestMapping(value= {"/index","/scsi/index"})
	public ModelAndView index(@ModelAttribute("name") String get_scable,@ModelAttribute("zoom") String get_zoom) {
		ModelAndView model = new ModelAndView("index"); 
		
		String cableList [] = {"APCN2","APG","CSCN","SMW3","TSE-1","TPE","NCP","C2C","EAC","EAC2","FNAL","RNAL","FASTER","PLCN","TDM2","TM1-A","TM1-B","TM1-C","TM1-D","TM3-A","TM3-B","TM3-C","TM3-D","NB2","DX","TK2","TP1","TP2","TP3","PK1-A","PK1-B","PK3-A","PK3-B"};

	    String cablezoom [] = {"4","4","10","3","7","3","3","4","4","4","5","5","3","3"};

	    String cableName = "";

	    String cableZoom = "";

	    for( int i=0; i<cableList.length; i++ ){
	        if( cableList[i].equals(get_scable) ){
	            cableName = cableList[i];
	            continue;
	        }
	    }

	    for( int i=0; i<cablezoom.length; i++ ){
	        if( cablezoom[i].equals(get_zoom) ){
	            cableZoom = cablezoom[i];
	            continue;
	        }
	    }
		
		String SMW3_1 =  "SMW3法新歐亞三號海纜<br>啟用時間：1999年09月<br>纜線長度： 39,000 km";
		String SMW3="</font><font size='4'>擁有者：<br>Orange、英國電信、KDDI、新加坡電信、Telecom Italia Sparkle、馬來西亞電訊公司、OTEGlobe、AT&T、Proximus、泰國通訊機構、中國電信集團、德國電信、阿聯酋電信、埃及電信、澳門電訊、印尼電訊、汶萊通訊服務、韓國電信、葡萄牙電訊公司、摩洛哥電信、菲律賓長途電話公司、沙烏地電信公司、斯里蘭卡電信、土耳其電信公司、塔塔通訊、中華電信、威訊無線、荷蘭皇家電信、奧地利電信、奧普特斯電信公司、澳大利亞電信、越南國際電信公司、Omantel、電訊盈科、巴基斯坦電信、Cyta、eircom、LG U+、 Softbank Telecom、Telkom、俄羅斯電信公司、Orange波蘭、阿根廷電信公司、緬甸郵電公司、斯普林特公司、Vocus Communications、吉布地電信、巴西電信、沃達豐、土耳其電信公司、烏克蘭電信<br></font><font size='4'>URL：http://www.smw3.com<br>登陸點：<br>亞歷山大港 (埃及)、安佐爾 (印尼)、八打雁 (菲律賓)、干尼亞 (希臘)、科契 (印度)、峴港 (越南)、深水灣 (香港)、吉布地市 (吉布地)、枋山 (台灣)、富吉拉 (阿聯)、Goonhilly Downs (英國)、吉達 (沙烏地阿拉伯)、喀拉蚩 (巴基斯坦)、巨濟 (南韓)、馬爾馬里斯 (土耳其)、馬紮拉德爾瓦洛 (義大利)、棉蘭 (印尼)、豐盛港 (馬來西亞)、代希瓦勒-芒特拉維尼 (斯里蘭卡)、孟買 (印度)、馬斯喀特 (阿曼)、諾爾登 (德國)、沖繩 (日本)、奧斯滕德 (比利時)、檳城 (馬來西亞)、龐馬爾克 (法國)、伯斯 (澳洲)、皮亞朋 (緬甸)、沙敦 (泰國)、塞新布拉 (葡萄牙)、上海 (中國)、汕頭 (中國)、蘇伊士 (埃及)、氹仔 (中國)、頭城 (台灣)、大士 (新加坡)、Tungku (汶萊)、得土安 (摩洛哥)、Yeroskipos (賽普勒斯)";
		String APCN2_1 = "APCN2亞太網路二號<br>啟用時間：2001年12月<br>纜線長度： 19,000 km";
		String APCN2="擁有者：<br></font><font size='4'>新加坡電信、威訊無線、KDDI、中華電信、AT&T、英國電信、Orange、Softbank Telecom、日本電信電話、塔塔通訊、馬來西亞電訊公司、星和電信、菲律賓長途電話公司、中國聯通、韓國電信、奧普特斯電信公司、澳大利亞電信、電訊盈科、中國電信集團、LG U+、香港寬頻、沃達豐<br></font><font size='6'>URL：http://www.apcn2na.com<br></font><font size='6'>登陸點：<br>八打雁 (菲律賓)、千倉 (日本)、崇明 (中國)、加東 (新加坡)、北茨城 (日本)、關丹 (馬來西亞)、大嶼山 (香港)、釜山 (南韓)、汕頭 (中國)、淡水 (台灣)";
		String APG_1 = "APG亞太直達海纜<br>啟用時間：2016年11月<br>纜線長度： 10,400 km";
		String APG = "擁有者：<br></font><font size='4'>日本電信電話、中國電信集團、中國聯通、中華電信、韓國電信、星和電信、LG U+、中國移動、越南軍用電子電信、越南國際電信公司、Global Transit、Facebook<br></font><font size='6'>登陸點：<br>崇明 (中國)、峴港 (越南)、關丹 (馬來西亞)、丸山 (日本)、南匯 (中國)、釜山 (南韓)、志摩 (日本)、宋卡 (泰國)、丹那美拉 (新加坡)、頭城 (台灣)、將軍澳 (香港)";
		String CSCN_1 = "CSCN金廈海纜<br>啟用時間：2012年08月<br>纜線長度：21 km";
		String CSCN = "擁有者：<br></font><font size='4'>中華電信、中國電信集團、中國聯通、中國移動<br></font><font size='6'>登陸點：<br>大嶝島 (中國)、觀音山 (中國)、古寧頭 (台灣)、慈湖 (台灣)";
		String TSE1_1 = "TSE-1海峽光纜1號<br>啟用時間：2013年01月<br>纜線長度：260 km";
		String TSE1 = "擁有者：<br></font><font size='4'>中國聯通、遠傳電信、台灣大哥大、中國電信集團、中華電信、中國移動<br></font><font size='6'>登陸點：<br>福州 (中國)、淡水 (台灣)";
		String TPE_1 = "TPE橫太平洋快速海纜<br>啟用時間：2008年08月<br>纜線長度：17,000 km";
		String TPE = "擁有者：<br></font><font size='4'>中國電信集團、中國聯通、中華電信、韓國電信、威訊無線、日本電信電話、AT&T<br></font><font size='6'>URL：http://tpecable.org<br>登陸點：<br>崇明 (中國)、巨濟 (南韓)、丸山 (日本)、尼多拿海灘 (美國俄勒岡州)、青島 (中國)、淡水 (台灣)";
		String NCP_1 = "NCP新橫太平洋海纜<br>啟用時間：2018年<br>纜線長度：13,618 km";
		String NCP = "擁有者：<br></font><font size='4'>中國電信集團、中國聯通、中華電信、韓國電信、中國移動、微軟、Softbank Telecom<br></font><font size='6'>登陸點：<br>崇明 (中國)、臨港 (中國)、丸山 (日本)、南匯 (中國)、太平洋城 (美國俄勒岡州)、釜山 (南韓)、頭城 (台灣)";
		String C2C_1 = "C2C市通市海纜<br>啟用時間：2002年11月<br>纜線長度：36,500 km";
		String C2C = "擁有者：<br></font><font size='4'>澳大利亞電信<br></font><font size='6'>URL：https://www.telstraglobal.com<br>登陸點：<br>阿字浦 (日本)、八打雁 (菲律賓)、甲米地 (菲律賓)、樟宜 (新加坡)、千倉 (日本)、舂坎角 (香港)、枋山 (台灣)、八里 (台灣)、釜山 (南韓)、青島 (中國)、上海 (中國)、志摩 (日本)、薪斗里 (南韓)、淡水 (台灣)、將軍澳 (香港)";
		String EAC_1 = "EAC東亞光網海纜/C2C市通市海纜<br>啟用時間：2002年11月<br>纜線長度：36,500 km";
		String EAC = "擁有者：<br></font><font size='4'>澳大利亞電信<br></font><font size='6'>URL：https://www.telstraglobal.com<br>登陸點：<br>阿字浦 (日本)、八打雁 (菲律賓)、甲米地 (菲律賓)、樟宜 (新加坡)、千倉 (日本)、舂坎角 (香港)、枋山 (台灣)、八里 (台灣)、釜山 (南韓)、青島 (中國)、上海 (中國)、志摩 (日本)、薪斗里 (南韓)、淡水 (台灣)、將軍澳 (香港)";
		String FNAL_1 = "FNAL北亞海纜/RNAL北亞光纜環系統<br>啟用時間：2001年06月<br>纜線長度：9,504 km";
		String FNAL = "擁有者：<br></font><font size='4'>Global Cloud Xchange、電訊盈科、澳大利亞電信<br></font><font size='6'>登陸點：<br>釜山 (南韓)、塘福 (香港)、頭城 (台灣)、和田 (日本)";
		String RNAL_1 = "RNAL北亞光纜環系統<br>啟用時間：2001年06月<br>纜線長度：9,504 km";
		String RNAL = "擁有者：<br></font><font size='4'>Global Cloud Xchange、電訊盈科、澳大利亞電信<br></font><font size='6'>登陸點：<br>釜山 (南韓)、塘福 (香港)、頭城 (台灣)、和田 (日本)";
		String FASTER_1 = "FASTER<br>啟用時間：2016年06月<br>纜線長度：11,629 km";
		String FASTER = "擁有者：<br></font><font size='4'>Google、KDDI、新加坡電信、中國電信集團、中國移動、Global Transit<br></font><font size='6'>登陸點：<br>班頓 (美國俄勒岡州)、千倉 (日本)、志摩 (日本)、淡水 (台灣)";
		String EAC1_1 = "EAC1東亞光網海纜<br>啟用時間：2002年11月<br>纜線長度：36,500 km";
		String EAC1 = "擁有者：<br></font><font size='4'>澳大利亞電信<br></font><font size='6'>URL：https://www.telstraglobal.com<br>登陸點：<br>阿字浦 (日本)、千倉 (日本)、舂坎角 (香港)、枋山 (台灣)、八里 (台灣)、釜山 (南韓)、青島 (中國)、志摩 (日本)、薪斗里 (南韓)、淡水 (台灣)、將軍澳 (香港)";
		String EAC2_1 = "EAC2東亞光網海纜<br>啟用時間：2002年11月<br>纜線長度：36,500 km";
		String EAC2 = "擁有者：<br></font><font size='4'>澳大利亞電信<br></font><font size='6'>URL：https://www.telstraglobal.com<br>登陸點：<br>八打雁 (菲律賓)、樟宜 (新加坡)、舂坎角 (香港)、淡水 (台灣)、將軍澳 (香港)";
		String PLCN_1 = "PLCN<br>啟用時間：2022年01月<br>纜線長度：11,806 km";
		String PLCN = "擁有者：<br></font><font size='4'>Google、Meta<br></font><font size='6'>登陸點：<br>El Segundo(埃爾塞貢多), CA (美國加利福尼亞州)、巴萊爾 (菲律賓)、頭城 (台灣)";


		String showDataNow = "";
		String showDataPop = "";
		String myheight = "700";
		String myleft = "50";
		
		if(cableName.equals("SMW3")){showDataNow = SMW3;showDataPop=SMW3_1;myheight = "600";}
		if(cableName.equals("APCN2")){showDataNow = APCN2;showDataPop=APCN2_1;}
		if(cableName.equals("APG")){showDataNow = APG;showDataPop=APG_1;}
		if(cableName.equals("CSCN")){showDataNow = CSCN;showDataPop=CSCN_1;myheight = "800";}
		if(cableName.equals("TSE-1")){showDataNow = TSE1;showDataPop=TSE1_1;myheight = "800";myleft="63";}
		if(cableName.equals("TPE")){showDataNow = TPE;showDataPop=TPE_1;}
		if(cableName.equals("NCP")){showDataNow = NCP;showDataPop=NCP_1;myheight = "800";}
		if(cableName.equals("C2C")){showDataNow = C2C;showDataPop=C2C_1;}
		if(cableName.equals("EAC")){showDataNow = EAC;showDataPop=EAC_1;}
		if(cableName.equals("EAC1")){showDataNow = EAC1;showDataPop=EAC1_1;}
		if(cableName.equals("EAC2")){showDataNow = EAC2;showDataPop=EAC2_1;}
		if(cableName.equals("FNAL")){showDataNow = FNAL;showDataPop=FNAL_1;}
		if(cableName.equals("RNAL")){showDataNow = RNAL;showDataPop=RNAL_1;}
		if(cableName.equals("FASTER")){showDataNow = FASTER;showDataPop=FASTER_1;}
		if(cableName.equals("PLCN")){showDataNow = PLCN;showDataPop=PLCN_1;}
		
		model.addObject("showDataNow",showDataNow);
		model.addObject("showDataPop",showDataPop);
		model.addObject("cableName",cableName);
		model.addObject("myheight",myheight);
		model.addObject("myleft",myleft);
		
		return model;
	}
	
	
	@RequestMapping(value= {"/myiframe","/scsi/myiframe"})
	public ModelAndView myiframe(@ModelAttribute("name") String get_scable,
			@ModelAttribute("zoom") String get_zoom) {
		ModelAndView model = new ModelAndView("myiframe");
		String cableList [] = {"APCN2","APG","CSCN","SMW3","TSE-1","TPE","NCP","C2C","EAC","EAC2","FNAL","RNAL","FASTER","PLCN","TDM2","TM1-A","TM1-B","TM1-C","TM1-D","TM3-A","TM3-B","TM3-C","TM3-D","NB2","DX","TK2","TP1","TP2","TP3","PK1-A","PK1-B","PK3-A","PK3-B"};

	    String cablezoom [] = {"4","4","10","3","7","3","3","4","4","4","5","5","3"};

	    String cableName = "";

	    String cableZoom = "";

	    for( int i=0; i<cableList.length; i++ ){
	        if( cableList[i].equals(get_scable) ){
	            cableName = cableList[i];
	        }
	    }

	    for( int i=0; i<cablezoom.length; i++ ){
	        if( cablezoom[i].equals(get_zoom) ){
	            cableZoom = cablezoom[i];
	        }
	    }
	    
	    if(cableName.equals("")){
			model.addObject("cableName","暫無更多說明");
		}else{
			model.addObject("cableName",cableName);
			model.addObject("cablezoom",cableZoom);
		}
	return model;
	}
	
	@RequestMapping(value= {"/cruite_write","/scsi/cruite_write"})
	public @ResponseBody String cruite_write(@ModelAttribute("CA") String st1,@ModelAttribute("CB") String st2,@ModelAttribute("CT1") String st3,@ModelAttribute("CT2") String st4,@ModelAttribute("CT3") String st5,@ModelAttribute("CT4") String st6,@ModelAttribute("CT5") String st7,DataProcess dpr) throws IOException, SQLException {
		ModelAndView model = new ModelAndView("cruite_write");

		if(st1.equals("0") || st2.equals("0")){
			return "請選擇電路起迄站點";
		}else{
			if(st3.equals("0")){
				return "請輸入電路編號";
			}else{
		
		String qry_Statement = "SELECT * from sc_connection where connection_id = ? and (del_mark is NULL or del_mark <> '1')" ;
		String[] para = {st3};
		String[] typevalue = {"s"};
		ResultSet rs0 = dpr.queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		int infoReady = 0 ;
		while (rs0.next()){
			infoReady++;
		}
		if(infoReady == 0){
			String[] para1 = {st1,st2,st2,st1};
			String[] typevalue1 = {"s","s","s","s"};
			qry_Statement = "SELECT * from sc_connection where ((connection_A = ? and connection_B = ?) or (connection_A = ? and connection_B = ?) ) and (del_mark is NULL or del_mark <> '1')" ;
			rs0 = dpr.queryData(qry_Statement, para1 , typevalue1);
			rs0.last();
			rs0.beforeFirst();
			infoReady = 0 ;
			while (rs0.next()){
				infoReady++;
			}
			if(infoReady == 0){
				String statements = "insert into sc_connection (connection_id , connection_A , connection_B, InterVoice, Internet, Leasing_circuit_line,Leasing_circuit_bw , notes ) values (?,?,?,?,?,?,?,'服務尚無影響')";
				String[] values={st3,st1,st2,st4,st5,st6,st7};
				String[] valuestype={"s","s","s","s","s","s","s"};
				dpr.updateData(statements , values , valuestype);
				return "OK";
			}else{
				return "電路起迄地點已存在, 請重新輸入";
			}
		}else{
			return "電路編號已存在, 請重新輸入";
		}
		}
		}
	}
	
	@RequestMapping(value= {"/cruite_input","/scsi/cruite_input"})
	public ModelAndView cruite_input(@ModelAttribute("id") String st,DataProcess dpr) {String[] fieldname = {"sysid","connection_id","connection_A","connection_B","nidA_lon","nidB_lon","nidA_lat","nidB_lat","notes","nidA_failure","nidB_failure","level","linecolor","InterVoice","Internet","InterLeasing","Leasing_circuit_line","Leasing_circuit_bw","Leasing_ethernet_line","Leasing_ethernet_bw","Leasing_vpn_line","Leasing_vpn_bw","failvoice","failinternet","failleasing","faildatetime","failid","faildesc","failleasing_circuit_line","failleasing_circuit_bw","failleasing_ethernet_line","failleasing_ethernet_bw","failleasing_vpn_line","failleasing_vpn_bw"};
	String[] para1 ={};
	String[] para = {st};
	String[] typevalue1 = {"s"};
	String getStrings1 = "";
	String[] trimValue = {"failvoice","failinternet","failleasing","failleasing_circuit_line","failleasing_circuit_bw","failleasing_ethernet_line","failleasing_ethernet_bw","failleasing_vpn_line","failleasing_vpn_bw","InterVoice","Internet","InterLeasing","Leasing_circuit_line","Leasing_circuit_bw","Leasing_ethernet_line","Leasing_ethernet_bw","Leasing_vpn_line","Leasing_vpn_bw","connection_id"};
		ModelAndView model = new ModelAndView("cruite_input");
		model.addObject("fieldname",fieldname);
		model.addObject("typevalue1",typevalue1);
		model.addObject("trimValue",trimValue);
		return model;
	}
	
//	@PostMapping(value= {"/findpath2_adv2","/scsi/findpath2_adv2"})
//	public @ResponseBody String findpath2_adv2(DataProcess dpr) {
//		ModelAndView model = new ModelAndView("findpath2_adv2");
//		
//			String st[]= new String[1];
//			st[0]='{"f8":"T2","f9":"2","f10":"2","f11":"0","f12":"0","f13":"0","f14":"0","f15":"0","f16":"0"}';
//			dpr.updateFailStatus(st);
//
//		
//		return("[Internal1, PERM-2, 東亞交匯海纜一號EAC1, 淡水-Backhaul-1]");
//	}
}

