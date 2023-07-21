package scsi.demo.wisoft;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;
import java.lang.Object;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import org.json.*;

import scsi.demo.wisoft.*;

public class DataProcess extends Data
{
	
	public static void main(String[] args){}
	
	private int i = 0 ;
	private int lg = 0 ;
	private ResultSet rs1, rs_album = null;
	private ResultSet rs2, rs_track = null;
	private String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private SecureRandom rd = new SecureRandom();
	private String ncode = "" ;
	private int j = 0 ;
	private int k = 0 ;
	private int m = 0 ;
	private EncUtil eul = new EncUtil();
	private String enc_key = null ;
	
	private float[] PathWeight_min = null ;
	private float[] PathWeight_max = null;
	private String[][] NodeLink = null;
	private String[][] NodeLinkALL = null;
	private String[][] WeightLink_min = null;
	private String[][] WeightLink_max = null;
	private String[][] WeightLink1 = null;
	private String[][] WeightLink2 = null;
	private String[][] LinkBandwidth = null;
	private String[][] FailLink = null;
	private String[][] LinkList = null ;
	private String[] NodeName = null ;
	private String[] NodeInfo = null ;
	private JSONObject fail_json = null;
	private JSONObject Link_json = null;
	private JSONObject All_Link = null;
	
	public DataProcess() {
		 }
		 
	public void closeall() throws IOException, SQLException
	{
		if(rs1 != null) rs1.close();
		if(rs2 != null) rs2.close();
		super.closeall();
	}


public void setThisKey(String st) throws IOException, SQLException{
	enc_key = st ;
}

public JSONObject getEncData(String st) throws Exception,IOException, JSONException, SQLException{
	if(enc_key == null){
		return null ;
	}else{
	String xx = eul.decryString(st,enc_key);
	JSONObject json = new JSONObject(xx);
		return json ;
	}
}

public JSONObject  getJsonData(JSONObject jsb) throws IOException, JSONException, SQLException{
	System.out.println(jsb.getString("person"));
	System.out.println(jsb.getString("box"));
	System.out.println(jsb.getString("checks"));
	return jsb ;
}

public JSONObject getCountryInfo(String[] typevalue , String[] para) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	String[] fieldname = {"sysid","country_code","country_name"};
	json = getInfobyJSON(typevalue,para, fieldname, "country_info","SELECT * FROM sc_countryname");
	return json ;
}

public JSONObject getPathNode(String[] typevalue , String[] para) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		String qry_Statement = "SELECT * FROM sc_nodes where node_name = ? or node_id = ? and del_mark is null or del_mark <> '1' order by node_type , pos ";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			json1.append("sysid",rs0.getString("sysid"));
			json1.append("node_id",rs0.getString("node_id"));
			json1.append("node_name",rs0.getString("node_name"));
			json1.append("node_owner",rs0.getString("owner"));
			json1.append("node_lon",rs0.getString("lon"));
			json1.append("node_lat",rs0.getString("lat"));
			json1.append("node_failure",rs0.getString("node_failure"));
			json1.append("node_type",rs0.getString("node_type"));
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public JSONObject getNodeSEQ(String[] typevalue , String[] para , String exceptND) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		String qry_Statement = "SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes a left join (select * from sc_code_tab where code_id='3') b on b.name_zh = a.node_type where (a.del_mark is null or a.del_mark <> '1')";
		if(!exceptND.equals("")) qry_Statement += "where node_type <> ? ";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			json1.append("sysid",rs0.getString("sysid"));
			json1.append("node_id",rs0.getString("node_id"));
			json1.append("node_name",rs0.getString("node_name"));
			json1.append("node_owner",rs0.getString("owner"));
			json1.append("node_lon",rs0.getString("lon"));
			json1.append("node_lat",rs0.getString("lat"));
			json1.append("node_failure",rs0.getString("node_failure"));
			json1.append("node_type",rs0.getString("node_type"));
			json1.append("node_type_name",rs0.getString("name_desc_zh"));
			json1.append("node_rn",rs0.getString("rn"));
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public JSONObject getLinkSEQ(String[] typevalue , String[] para , String[] fieldnamelist) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT * , (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA , (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1') )b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB FROM sc_links c where (c.del_mark is null or c.del_mark <> '1') GROUP BY CONCAT(link_nodeA,'_',link_nodeB)  ORDER BY nidA, nidB ";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			for (int p=0 ; p< noOfField ; p++){
				json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
			}
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}


public JSONObject getLinkSEQ(String[] typevalue , String[] para , String[] fieldnamelist , String[] filterlist, String[] filtervalue) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT * , (SELECT b.node_failure FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA_failure , (SELECT b.node_failure FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB_failure,(SELECT b.node_type FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA_type , (SELECT b.node_type FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB_type, (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA , (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1') )b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB FROM sc_links c where (c.del_mark is null or c.del_mark <> '1') GROUP BY CONCAT(link_nodeA,'_',link_nodeB)  ORDER BY nidA, nidB";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			int filterout = 0 ;
			for (int v=0 ; v<filterlist.length ; v++){
				if(rs0.getString(filterlist[v]).equals(filtervalue[v])){
					filterout = 1;
					break;
				}
			}
			if(filterout == 0){
				int checkmore = 0 ;
				JSONObject json1 = new JSONObject();
				for (int p=0 ; p< noOfField ; p++){
					if(fieldnamelist[p].equals("failure") || fieldnamelist[p].equals("nidA_failure") || fieldnamelist[p].equals("nidB_failure")){
						String getValues = (String)rs0.getString(fieldnamelist[p]);
						if(!getValues.equals("0")){
							checkmore = 1;
							break;
						}
					}
					json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
				}
				if(checkmore == 0){
					json.append("node_info",json1);
				}
			}
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public JSONObject getFailLink(){
	return fail_json ;
}

public JSONObject getAllLink(){
	return All_Link ;
}

public JSONObject getMultiLinkSEQ(String[] typevalue , String[] para , String[] fieldnamelist , String[] filterlist, String[] filtervalue) throws IOException, SQLException
{
	Link_json = new JSONObject();
	fail_json = new JSONObject();
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT * , (SELECT b.node_failure FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA_failure , (SELECT b.node_failure FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB_failure,(SELECT b.node_type FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1') )b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA_type , (SELECT b.node_type FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB_type, (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA , (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB FROM sc_links c where (c.del_mark is null or c.del_mark <> '1') GROUP BY CONCAT(link_nodeA,'_',link_nodeB,'_',link_id)  ORDER BY nidA, nidB";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			int filterout = 0 ;
			for (int v=0 ; v<filterlist.length ; v++){
				if(rs0.getString(filterlist[v]).equals(filtervalue[v])){
					filterout = 1;
					break;
				}
			}
			if(filterout == 0){
				int checkmore = 0 ;
				JSONObject json1 = new JSONObject();
				for (int p=0 ; p< noOfField ; p++){
					if(fieldnamelist[p].equals("failure") || fieldnamelist[p].equals("nidA_failure") || fieldnamelist[p].equals("nidB_failure")){
						String getValues = (String)rs0.getString(fieldnamelist[p]);
						if(!getValues.equals("0")){
							checkmore = 1;
							//break;
						}
					}
					json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
				}
				if(checkmore == 0){
					json.append("node_info",json1);
				}else{
					json.append("fail_info",json1);
					fail_json.append("node_info",json1);
				}
				Link_json.append("link_info",json1);
			}
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	All_Link = json ;
	return json ;
}

public JSONObject getNodeInfo(String[] typevalue , String[] para) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		String qry_Statement = "SELECT * FROM sc_nodes where node_type = ? and (del_mark is null or del_mark <> '1') order by node_type , pos ";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			json1.append("sysid",rs0.getString("sysid"));
			json1.append("node_id",rs0.getString("node_id"));
			json1.append("node_name",rs0.getString("node_name"));
			json1.append("node_owner",rs0.getString("owner"));
			json1.append("node_lon",rs0.getString("lon"));
			json1.append("node_lat",rs0.getString("lat"));
			json1.append("node_failure",rs0.getString("node_failure"));
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}



public JSONObject getNodePath(String[] typevalue , String[] para , String[] fieldnamelist) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT a.* , (SELECT concat(node_id,'-',sysid) FROM sc_nodes WHERE a.link_nodeA = node_name and (del_mark is null or del_mark <> '1')) AS nodeA_id ,(SELECT concat(node_id,'-',sysid) FROM sc_nodes WHERE a.link_nodeB = node_name and (del_mark is null or del_mark <> '1')) AS nodeB_id FROM sc_links a where (link_name = 'SCCC' or link_id = '112') and (a.del_mark is null or a.del_mark <> '1')";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			for (int p=0 ; p< noOfField ; p++){
				json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
			}
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public String[][] getArrayResult2D(String st) throws IOException, SQLException
{
	if(st.equals("NodeLinkALL")) return NodeLinkALL ;
	if(st.equals("NodeLink")) return NodeLink ;
	if(st.equals("WeightLink")) return WeightLink_min ;
	if(st.equals("WeightLink_min")) return WeightLink_min ;
	if(st.equals("WeightLink_max")) return WeightLink_max ;
	if(st.equals("WeightLink1")) return WeightLink1 ;
	if(st.equals("WeightLink2")) return WeightLink2 ;
	if(st.equals("LinkBandwidth")) return LinkBandwidth ;
	if(st.equals("FailLink")) return FailLink ;
	return null ;
}

public String[] getArrayResult(String st) throws IOException, SQLException
{
	if(st.equals("NodeName")) return NodeName ;
	if(st.equals("NodeInfo")) return NodeInfo ;
	
	return null ;
}

public JSONObject getLinkInfoJSON(){
	return Link_json ;
}

public void BuildVituralNetwork(int startint , int endint ) throws IOException, SQLException,JSONException
{
	String start_type = "";
	String end_type = "";
	String[] typevalue ={};
	String[] para={} ;
	int NodeLinkArr = 0 ;
	JSONObject json = new JSONObject();
	json = getNodeSEQ(typevalue,para,"");
	if(json.has("node_info")){
		JSONArray jsonArray = json.getJSONArray("node_info");
		NodeLinkArr = jsonArray.length()+1;
		NodeName = new String[NodeLinkArr];
		NodeInfo = new String[NodeLinkArr];
		NodeName[0]="";
		for(int u=0 ; u< jsonArray.length() ; u++){
			JSONObject jsonObject1 = jsonArray.getJSONObject(u);
			String getStrings1 = jsonObject1.optString("node_rn").toString().replace("[\"","").replace("\"]","");
			String getStrings2 = jsonObject1.optString("node_name").toString().replace("[\"","").replace("\"]","");
			String getStrings3 = jsonObject1.optString("node_id").toString().replace("[\"","").replace("\"]","");
			String getStrings4 = jsonObject1.optString("sysid").toString().replace("[\"","").replace("\"]","");
			String getStrings5 = jsonObject1.optString("node_type").toString().replace("[\"","").replace("\"]","");
			String getStrings6 = jsonObject1.optString("node_failure").toString().replace("[\"","").replace("\"]","");
			NodeName[u+1]=getStrings2;
			NodeInfo[u+1]=getStrings4+"_"+getStrings5+"_"+getStrings6+"_"+getStrings3;
			if(Integer.parseInt(getStrings1) == startint) start_type = getStrings5;
			if(Integer.parseInt(getStrings1) == endint) end_type = getStrings5;
		}
	}
		
	String[] typevalue1 ={};
	String[] para1={} ;
	NodeLinkALL = new String[NodeLinkArr][NodeLinkArr];
	NodeLink = new String[NodeLinkArr][NodeLinkArr];
	WeightLink_min = new String[NodeLinkArr][NodeLinkArr];
	WeightLink_max = new String[NodeLinkArr][NodeLinkArr];
	WeightLink1 = new String[NodeLinkArr][NodeLinkArr];
	WeightLink2 = new String[NodeLinkArr][NodeLinkArr];
	FailLink = new String[NodeLinkArr][NodeLinkArr];
	LinkBandwidth = new String[NodeLinkArr][NodeLinkArr];
	
	String filterString1 = "";
	String filterString2 = "";
	if(!start_type.equals("FOREIGN") &&  !end_type.equals("FOREIGN")){
		filterString1 = "nidA_type,nidB_type";
		filterString2 = "FOREIGN,FOREIGN";
	}
	String[] fieldname = {"sysid","link_id","link_name","link_owner","link_nodeA","link_nodeB","failure","max_bandwidth","using_bandwidth","rest_bandwidth","failure_bandwidth","nidA","nidB","nidA_type","nidB_type","nidA_failure","nidB_failure","w_length","w_latency","w_weight"};
	String[] filterlist = filterString1.split(",");
	String[] filtervalue = filterString2.split(",");
	json=getMultiLinkSEQ(typevalue1,para1,fieldname,filterlist,filtervalue);
	String[] nodeInfo={"node_info","fail_info"};
	for (int z=0 ; z < nodeInfo.length ; z++){
		String checkNode = nodeInfo[z];
	if(json.has(checkNode)){
		JSONArray jsonArray = json.getJSONArray(checkNode);
		for(int u=0 ; u< jsonArray.length() ; u++){
			JSONObject jsonObject1 = jsonArray.getJSONObject(u);
			String getStrings1 = jsonObject1.optString("nidA").toString().replace("[\"","").replace("\"]","");
			String getStrings2 = jsonObject1.optString("nidB").toString().replace("[\"","").replace("\"]","");
			String getStrings3 = jsonObject1.optString("failure").toString().replace("[\"","").replace("\"]","");
			String getStrings4 = jsonObject1.optString("nidA_failure").toString().replace("[\"","").replace("\"]","");
			String getStrings5 = jsonObject1.optString("nidB_failure").toString().replace("[\"","").replace("\"]","");
			String getStrings6 = jsonObject1.optString("link_id").toString().replace("[\"","").replace("\"]","");
			String getStrings7 = jsonObject1.optString("link_name").toString().replace("[\"","").replace("\"]","");
			String getStrings8 = jsonObject1.optString("w_length").toString().replace("[\"","").replace("\"]","");
			String getStrings9 = jsonObject1.optString("w_latency").toString().replace("[\"","").replace("\"]","");
			String getStrings10 = jsonObject1.optString("w_weight").toString().replace("[\"","").replace("\"]","");
			String getStrings11 = getStrings6;
			String getStrings12 = jsonObject1.optString("failure_bandwidth").toString().replace("[\"","").replace("\"]","");
			String getStrings13 = jsonObject1.optString("max_bandwidth").toString().replace("[\"","").replace("\"]","");
			String getStrings14 = jsonObject1.optString("using_bandwidth").toString().replace("[\"","").replace("\"]","");
			String getStrings15 = jsonObject1.optString("rest_bandwidth").toString().replace("[\"","").replace("\"]","");
			if(z==0){
			if((getStrings3.equals("0")) && (getStrings4.equals("0")) && (getStrings5.equals("0"))){
				if(NodeLink[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)]!=null){
					WeightLink2[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)] += "_"+getStrings11;
					WeightLink2[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)] += "_"+getStrings11;
					WeightLink1[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)] += "@"+getStrings6+"_"+getStrings7+"_"+getStrings8+"_"+getStrings9+"_"+getStrings10;
					WeightLink1[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)] += "@"+getStrings6+"_"+getStrings7+"_"+getStrings8+"_"+getStrings9+"_"+getStrings10;
					LinkBandwidth[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)] += "@"+getStrings6+"_"+getStrings13+"_"+getStrings14+"_"+getStrings15+"_"+getStrings12;
					LinkBandwidth[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)] += "@"+getStrings6+"_"+getStrings13+"_"+getStrings14+"_"+getStrings15+"_"+getStrings12;
				}else{
					WeightLink2[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)] = getStrings11;
					WeightLink2[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)] = getStrings11;
					WeightLink1[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)] = getStrings6+"_"+getStrings7+"_"+getStrings8+"_"+getStrings9+"_"+getStrings10;
					WeightLink1[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)] = getStrings6+"_"+getStrings7+"_"+getStrings8+"_"+getStrings9+"_"+getStrings10;
					LinkBandwidth[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)] = getStrings6+"_"+getStrings13+"_"+getStrings14+"_"+getStrings15+"_"+getStrings12;
					LinkBandwidth[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)] = getStrings6+"_"+getStrings13+"_"+getStrings14+"_"+getStrings15+"_"+getStrings12;
				
				}
				NodeLink[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)]=getStrings2;
				NodeLink[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)]=getStrings1;
				WeightLink_min[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)]=getStrings6;
				WeightLink_min[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)]=getStrings6;
				WeightLink_max[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)]=getStrings6;
				WeightLink_max[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)]=getStrings6;
				
			}else{
				if(FailLink[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)]!=null){
					FailLink[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)] += "@"+getStrings6+"_"+getStrings13+"_"+getStrings14+"_"+getStrings15+"_"+getStrings12;
					FailLink[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)] += "@"+getStrings6+"_"+getStrings13+"_"+getStrings14+"_"+getStrings15+"_"+getStrings12;
				}else{
					FailLink[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)] = getStrings6+"_"+getStrings13+"_"+getStrings14+"_"+getStrings15+"_"+getStrings12;
					FailLink[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)] = getStrings6+"_"+getStrings13+"_"+getStrings14+"_"+getStrings15+"_"+getStrings12;
				}			
			}
			}
			NodeLinkALL[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)]=getStrings2;
			NodeLinkALL[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)]=getStrings1;
		}
	}
	}
}

public String[][] findA2BLinkJSON(ArrayList al, int getMaxNode, int n , int m, JSONObject jsb , String root, String weightfield , int b) throws IOException, SQLException, JSONException
{
	LinkList = new String[al.size()][getMaxNode*2+1];
	if(jsb.has(root)){
		JSONArray jsonArray = jsb.getJSONArray(root);
		for(int u=0 ; u < al.size() ; u++){
			float totalweight = 0.0f ;
			String[] tmp = al.get(u).toString().split(",")[1].split("->");
			LinkList[u][0] = al.get(u).toString().split(",")[0];
			int thisV = Integer.parseInt(LinkList[u][0]);
			for(int v=0 ; v < tmp.length ; v++){
				float myweight = 0.0f ;
				String getInfo = "";
				if(v>0){
					String tmp0 = tmp[v-1].split("/")[0].replace("node","");
					String tmp1 = tmp[v].split("/")[0].replace("node","");
					
					for(int w=0 ; w< jsonArray.length() ; w++){
						JSONObject jsonObject1 = jsonArray.getJSONObject(w);
						if((jsonObject1.optString("nidA").toString().equals("[\""+tmp0+"\"]") && jsonObject1.optString("nidB").toString().equals("[\""+tmp1+"\"]")) || (jsonObject1.optString("nidB").toString().equals("[\""+tmp0+"\"]") && jsonObject1.optString("nidA").toString().equals("[\""+tmp1+"\"]"))){
							if(!getInfo.equals("")) getInfo += "@";
							getInfo += jsonObject1.optString("link_id").toString()+"/"+jsonObject1.optString("link_name").toString()+"/"+jsonObject1.optString(weightfield).toString();
							getInfo += "/"+jsonObject1.optString("failure").toString()+"/"+jsonObject1.optString("nidA_failure").toString()+"/"+jsonObject1.optString("nidB_failure").toString();
							getInfo += "/"+jsonObject1.optString("max_bandwidth").toString()+"/"+jsonObject1.optString("using_bandwidth").toString()+"/"+jsonObject1.optString("rest_bandwidth").toString()+"/"+jsonObject1.optString("failure_bandwidth").toString();
							//System.out.println(jsonObject1.optString(weightfield).toString());
							if(!jsonObject1.optString(weightfield).toString().equals("[\"\"]")){
								float thisweight = Float.parseFloat(jsonObject1.optString(weightfield).toString().replace("[\"","").replace("\"]",""));
								if(myweight == 0){
									myweight = thisweight ;
								}else{
									if(b == 0){
										if(myweight > thisweight) myweight = thisweight ;
									}
									if(b == 1){
										if(myweight < thisweight) myweight = thisweight ;
									}
								}
							}
						}
					}
					LinkList[u][v] = getInfo ;
				}
				totalweight += myweight ;
				LinkList[u][thisV+v] = String.valueOf(myweight);
			}
			LinkList[u][thisV] = String.valueOf(totalweight);
		}
	}
	return LinkList ;
}

public void findWeight(ArrayList al, String[][] WeightLink , int weightpos) throws IOException, SQLException
{
	PathWeight_min = new float[al.size()];
	PathWeight_max = new float[al.size()];
	Arrays.fill(PathWeight_min, 100.0f);
    Arrays.fill(PathWeight_max, -100.0f);
	
	for(int u=0 ; u<al.size() ; u++){
		String[] tmp = al.get(u).toString().split("->");
		for(int v=0 ; v < tmp.length ; v++){
			String showtmp = "";
			showtmp = tmp[v].split("/")[0];
			int mypos = Integer.parseInt(showtmp.substring(showtmp.indexOf("node")+4,showtmp.length()));
			if(v>0){
				String weightInfo = WeightLink[Integer.parseInt(tmp[v-1].split("/")[0].substring(tmp[v-1].split("/")[0].indexOf("node")+4,tmp[v-1].split("/")[0].length()))][mypos];
				System.out.println(weightInfo);
				System.out.println("Min:"+WeightLink_min[Integer.parseInt(tmp[v-1].split("/")[0].substring(tmp[v-1].split("/")[0].indexOf("node")+4,tmp[v-1].split("/")[0].length()))][mypos]);
				System.out.println("Max:"+WeightLink_max[Integer.parseInt(tmp[v-1].split("/")[0].substring(tmp[v-1].split("/")[0].indexOf("node")+4,tmp[v-1].split("/")[0].length()))][mypos]);
				if(weightInfo.indexOf("@")>0){
					String[] tmptmp = weightInfo.split("@");
					for (int w=0 ; w< tmptmp.length ; w++){
						float fweight = Float.parseFloat(tmptmp[w].split("_")[weightpos+2]);
						if(PathWeight_min[u] > fweight){
							PathWeight_min[u] = fweight;
							WeightLink_min[Integer.parseInt(tmp[v-1].split("/")[0].substring(tmp[v-1].split("/")[0].indexOf("node")+4,tmp[v-1].split("/")[0].length()))][mypos] = tmptmp[w].split("_")[0];
						}
						if(PathWeight_max[u] < fweight){
							PathWeight_max[u] = fweight;
							WeightLink_max[Integer.parseInt(tmp[v-1].split("/")[0].substring(tmp[v-1].split("/")[0].indexOf("node")+4,tmp[v-1].split("/")[0].length()))][mypos] = tmptmp[w].split("_")[0];
						}
					}
				}else{
					float fweight = Float.parseFloat(weightInfo.split("_")[weightpos+2]);
					if(PathWeight_min[u] > fweight)	PathWeight_min[u] = fweight;
					if(PathWeight_max[u] < fweight) PathWeight_max[u] = fweight;
				}
			}
		}
	}
}

public float[] getPathWeight(int n) throws IOException, SQLException
{	float[] PathWeight_retrun = null ;
	if(n==0) PathWeight_retrun = PathWeight_min;
	if(n==1) PathWeight_retrun = PathWeight_max;
	return PathWeight_retrun ;
}

public 	ArrayList<Integer> ArraySortSEQ(float[] farr , int sortme) throws IOException, SQLException
{
	ArrayList<Integer> alsort = new ArrayList<Integer>();
	int arrlength = farr.length ;
	for(int u=0 ; u<arrlength ; u++){
		if(alsort.size() == 0){
			alsort.add(u);
		}else{
			int insertnum = 0;
			for (int x=0 ; x < alsort.size() ; x++){
				float q = farr[u];
				int isInsert = 0 ;
				if(sortme == 0){
					if(farr[alsort.get(x)] > q) isInsert = 1;
				}
				if(sortme == 1){
					if(farr[alsort.get(x)] < q) isInsert = 1;
				}
				if(isInsert == 1){
					alsort.add(x,u);
					insertnum = 1 ;
					break;
				}
			}
			if(insertnum ==0) alsort.add(u);
		}
	}
	return alsort ;
}

public JSONObject getNodeInfo(String[] typevalue , String[] para , String[] fieldnamelist) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT a.* ,(SELECT node_failure FROM sc_nodes WHERE a.link_nodeA = node_name and (del_mark is null or del_mark <> '1')) AS nodeA_failure , (SELECT node_failure FROM sc_nodes WHERE a.link_nodeB = node_name and (del_mark is null or del_mark <> '1')) AS nodeB_failure , (SELECT node_type FROM sc_nodes WHERE a.link_nodeA = node_name and (del_mark is null or del_mark <> '1')) AS nodeA_type , (SELECT node_type FROM sc_nodes WHERE a.link_nodeB = node_name and (del_mark is null or del_mark <> '1')) AS nodeB_type , (SELECT concat(node_id,'-',sysid) FROM sc_nodes WHERE a.link_nodeA = node_name and (del_mark is null or del_mark <> '1')) AS nodeA_id ,(SELECT concat(node_id,'-',sysid) FROM sc_nodes WHERE a.link_nodeB = node_name and (del_mark is null or del_mark <> '1')) AS nodeB_id FROM sc_links a where (a.del_mark is null or a.del_mark <> '1')";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			for (int p=0 ; p< noOfField ; p++){
				json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
			}
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public JSONObject getNodeInfo(String[] typevalue , String[] para , String[] fieldnamelist, String conditions) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT a.* , (SELECT concat(node_id,'-',sysid) FROM sc_nodes WHERE a.link_nodeA = node_name and (del_mark is null or del_mark <> '1')) AS nodeA_id ,(SELECT concat(node_id,'-',sysid) FROM sc_nodes WHERE a.link_nodeB = node_name and (del_mark is null or del_mark <> '1')) AS nodeB_id FROM sc_links a "+conditions;
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			for (int p=0 ; p< noOfField ; p++){
				json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
			}
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public JSONObject getOneNodeRelate(String[] typevalue , String[] para , String[] fieldnamelist) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT DISTINCT c.sysid , c.* FROM sc_nodes c INNER JOIN ( SELECT a.* from sc_links a inner JOIN (SELECT * FROM sc_nodes WHERE  node_id = ? and sysid=? and (del_mark is null or del_mark <> '1')) b ON a.link_nodeA = b.node_name OR a.link_nodeB = b.node_name) d ON d.link_nodeA = c.node_name OR d.link_nodeB = c.node_name where (c.del_mark is null or c.del_mark <> '1');";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			for (int p=0 ; p< noOfField ; p++){
				json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
			}
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public JSONObject getOneNodeRelateLine(String[] typevalue , String[] para , String[] fieldnamelist) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT c.*, d.* from sc_links_details c inner JOIN ( SELECT a.* , (SELECT concat(node_id,'-',sysid) from sc_nodes WHERE a.link_nodeA = node_name and (del_mark is null or del_mark <> '1')) AS nodeA_id , (SELECT concat(node_id,'-',sysid) from sc_nodes WHERE a.link_nodeB = node_name and (del_mark is null or del_mark <> '1')) AS nodeB_id from sc_links a inner JOIN ( SELECT * FROM sc_nodes WHERE  node_id = ? and sysid= ? and (del_mark is null or del_mark <> '1')) b ON a.link_nodeA = b.node_name OR a.link_nodeB = b.node_name) d ON d.sysid = c.links_sysid where (c.del_mark is null or c.del_mark <> '1');";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			for (int p=0 ; p< noOfField ; p++){
				json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
			}
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public JSONObject getLineDetailInfo(String[] typevalue , String[] para , String[] fieldnamelist) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		/*
		SELECT f.* , g.* FROM sc_links_details f INNER JOIN ( 
SELECT c.*,  FROM sc_links c INNER JOIN (
SELECT a.sysid FROM sc_links a inner JOIN sc_nodes b ON a.link_nodeA = b.node_name or a.link_nodeB = b.node_name WHERE b.sysid = 1 
) d ON c.sysid=d.sysid
INNER JOIN (
SELECT a.sysid FROM sc_links a inner JOIN sc_nodes b ON a.link_nodeA = b.node_name or a.link_nodeB = b.node_name WHERE b.sysid = 11 
) e ON c.sysid = e.sysid 
) g
ON g.sysid = f.links_sysid ;
		*/
		String qry_Statement = "SELECT a.* , (SELECT concat(node_id,'-',sysid) FROM sc_nodes WHERE a.link_nodeA = node_name and (del_mark is null or del_mark <> '1')) AS nodeA_id ,(SELECT concat(node_id,'-',sysid) FROM sc_nodes WHERE a.link_nodeB = node_name and (del_mark is null or del_mark <> '1')) AS nodeB_id FROM sc_links a ";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			for (int p=0 ; p< noOfField ; p++){
				json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
			}
			json.append("node_info",json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public String gencode(int n)throws IOException, SQLException
{
	ncode = "" ;
	i = 0 ;
	m = 0 ;
	k = 0 ;
	while (i < n) {
		 	j = rd.nextInt(source.length());
		 	k = k+ (source.substring(j,j+1).hashCode() * (m+1));
			ncode = ncode + source.substring(j,j+1);
			m++;
			i++;
			if (i == (n-1)) {
				j = k % source.length() ;
				ncode = ncode + source.substring(j,j+1);
				m++;
				i++;
			}
		}
		return ncode;
}

} //end of class