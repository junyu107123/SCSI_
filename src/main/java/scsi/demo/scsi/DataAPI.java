package scsi.demo.scsi;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import scsi.demo.wisoft.Data;
import scsi.demo.wisoft.DataProcess;
import scsi.demo.wisoft.WiNet;
import scsi.demo.wisoft.WiNetHttps;

import java.sql.SQLException;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

// Extend HttpServlet class
public class DataAPI extends HttpServlet {
 
  private String message;
  private HttpSession session;
  
  public void init() throws ServletException
  {
      // Do required initialization
      message = "";
  }
 
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
  }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session = request.getSession();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
		
		Data dta = new Data();

		String referrer = request.getHeader("referer");
		String feedback = "no";
		try{
		String myfunc = (String)request.getParameter("func");
		//myfunc 1 : scableList
		//myfunc 2 : scableNations
		//myfunc 3 : scableBackup
		//myfunc 4 : scableroute
		//myfunc 5 : scableScenario , id
		String get_scable = (String)request.getParameter("scable");
		String get_loc = (String)request.getParameter("loc");

		String[] typevalue ={};
		String[] para={} ;
		int goNext = 0 ;
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
        
		feedback = getNewData(myfunc,typevalue,para).trim();
	   //feedback = dta.cleanSQLInject(getNewData(myfunc,typevalue,para)).trim();
		}catch (Exception e){
			feedback = "Exception";
		}finally{
			try{
				dta.closeall();
			}catch(SQLException e){
			}
		}
	  if(referrer.indexOf("final") >=0){
		response.getWriter().write( URLEncoder.encode(feedback, "UTF-8") );
	  }
    }
	
  public void destroy()
  {
	  try{
			
	}catch (Exception e){}
      // do nothing.
  }
  
  
  public String getNewData(String st, String[] typevalue1, String[] para1) throws IOException, SQLException{
		DataProcess dta1 = new DataProcess();
		String retval = "QQ";
		try{
			if(st.equals("1")) retval = dta1.ScableList(typevalue1,para1);
			if(st.equals("2")) retval = dta1.ScableNations(typevalue1,para1);
			if(st.equals("3")) retval = dta1.ScableBackup(typevalue1,para1);
			if(st.equals("4")) retval = dta1.ScableRoute(typevalue1,para1);
			if(st.equals("5")) retval = dta1.ScableScenario(typevalue1,para1);
			if(st.equals("6")){
				String testID = (String)session.getId();
				String AttachID = (String)session.getAttribute("myid");
				String myKey = (String)session.getAttribute("mykey");
				dta1.setThisKey(myKey);
				//retval =  dta1.getJsonData(dta1.getEncData(para1[0])).toString();
				retval =  dta1.getEncData(para1[0]).toString();
				//retval =  testID +"/wisoft/"+ myKey +"/wisoft/"+AttachID+"/wisoft/"+para1[0];
			}
			if(st.equals("7")) retval =  dta1.getNodeInfo(typevalue1,para1).toString();
			if(st.equals("8")){
				String[] fieldname = {"sysid","link_id","link_name","link_owner","link_nodeA","link_nodeB","nodeA_id","nodeB_id","failure","failure_bandwidth","max_bandwidth","rest_bandwidth","using_bandwidth","nodeA_type","nodeB_type","nodeA_failure","nodeB_failure"};
				retval =  dta1.getNodeInfo(typevalue1,para1,fieldname).toString();
			}
			if(st.equals("9")){
				String[] fieldname = {"sysid","node_id","node_name","node_type","node_failure"};
				retval =  dta1.getOneNodeRelate(typevalue1,para1,fieldname).toString();
			}
			if(st.equals("10")){
				String[] fieldname = {"sysid","link_id","details_id","details_operator","link_nodeA","link_nodeB","nodeA_id","nodeB_id","details_failure","details_failure_bandwidth"};
				retval =  dta1.getOneNodeRelateLine(typevalue1,para1,fieldname).toString();
			}
			if(st.equals("11")){
				String myKey = (String)session.getAttribute("mykey");
				dta1.setThisKey(myKey);
				//retval =  dta1.getEncData(dta1.getCountryInfo(typevalue1,para1).toString()).toString() + "..."+myKey+"...";
				retval =  dta1.getCountryInfo(typevalue1,para1).toString();
			}
			if(st.equals("12")){
				String[] fieldname = {"sysid","link_id","link_name","link_owner","link_nodeA","link_nodeB","nodeA_id","nodeB_id","failure","failure_bandwidth"};
				retval =  dta1.getPathNode(typevalue1,para1).toString();
			}
			if(st.equals("13")){
				retval =  dta1.getNodeSEQ(typevalue1,para1,"").toString();
			}
			if(st.equals("14")){
				String[] fieldname = {"sysid","connection_id","connection_A","connection_B","nidA_lon","nidB_lon","nidA_lat","nidB_lat","notes","nidA_failure","nidB_failure","level","linecolor","InterVoice","Internet","InterLeasing"};
				retval =  dta1.getConnectionSEQ(typevalue1,para1,fieldname,para1,para1).toString();
			}
			if(st.equals("15")){
				try (InputStream input = new FileInputStream("config.properties")) {
					Properties prop = new Properties();
					prop.load(input);
					String u1= prop.getProperty("urltype")+"://";
					u1 += prop.getProperty("url")+"/";
					u1 += prop.getProperty("runpath");
					u1 += prop.getProperty("runfile");
					WiNet win = new WiNet();
					retval = win.sentHttpPostRequest(u1,"");

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if(st.equals("16")){
				String[] fieldname = {"sysid","connection_id","connection_A","connection_B","nidA_lon","nidB_lon","nidA_lat","nidB_lat","notes","nidA_failure","nidB_failure","level","linecolor","InterVoice","Internet","InterLeasing","Leasing_circuit_line","Leasing_circuit_bw","Leasing_ethernet_line","Leasing_ethernet_bw","Leasing_vpn_line","Leasing_vpn_bw","failvoice","failinternet","failleasing","faildatetime","failid","faildesc","failleasing_circuit_line","failleasing_circuit_bw","failleasing_ethernet_line","failleasing_ethernet_bw","failleasing_vpn_line","failleasing_vpn_bw"};
				retval =  dta1.getConnectionFailSEQ(typevalue1,para1,fieldname,para1,para1).toString();
			}
			if(st.equals("17")){
				retval =  dta1.updateFailStatus(para1);
			}
			if(st.equals("18")){
					WiNetHttps wt = new WiNetHttps();
					String url = "http://211.22.193.189:9443/cnocapi/rooms";
					retval = wt.HttpClientGET(url);
			}
			
		}catch (Exception e){
			retval += e.toString();
		}finally{
			dta1.closeall();
		}
		return retval ;
  }
} //end of class