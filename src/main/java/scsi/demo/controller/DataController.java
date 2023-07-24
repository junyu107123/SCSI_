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
	
	@PostMapping(value= {"/myiframe","/scsi/myiframe"})
	public @ResponseBody String myiframe(@ModelAttribute("name") String get_scable,
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
			return ("<center><font color='red' size='3'> 暫無更多說明</font></center>");
		}else{
			
		}
	return "";
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

