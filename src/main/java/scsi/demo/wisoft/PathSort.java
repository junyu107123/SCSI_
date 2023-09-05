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

public class PathSort extends Data
{
	
	public static void main(String[] args){}
	
	private int i = 0 ;
	private int lg = 0 ;
	private ResultSet rs1, rs_album = null;
	private ResultSet rs2, rs_track = null;
	private String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private Random rd = new Random();
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
	private String[][] FailLink = null;
	private String[][] LinkList = null ;
	private String[][] retLink = null ;
	private String[][] LinkInfo = null ;
	private ArrayList SortedLink = null ;
	private ArrayList ReSortedLink = null ;
	private String[] NodeName = null ;
	private String[] NodeInfo = null ;
	private JSONObject fail_json = null;
	private JSONObject Link_json = null;
	private JSONObject All_Link = null;
	private JSONObject Node_AllInfo = null;
	private ArrayList al1 = new ArrayList();
	
	private int maxHopNum = 0 ;
	
	public PathSort() {
		 }
		 
	public void closeall() throws IOException, SQLException
	{
		if(rs1 != null) rs1.close();
		if(rs2 != null) rs2.close();
		super.closeall();
	}

public void setHopNum(int n) throws IOException, SQLException
{
	maxHopNum = n ;
}

public void startRUN(int a , int b , int w ) throws IOException, SQLException,JSONException
{
startRUN(a , b , w , "max_bandwidth");
}

public void startRUN(int a , int b , int w , String fd) throws IOException, SQLException,JSONException
{

	SearchAllPath sap = new SearchAllPath();
	sap.setNetworks(NodeLinkALL,NodeName);
	al1 = sap.runNow(a,b);
	
	ArrayList newal1 = new ArrayList();
	
	for (int i=0 ; i < al1.size() ; i++){
		if( Integer.parseInt(al1.get(i).toString().split(",")[0]) <= maxHopNum){
			newal1.add(al1.get(i));
		}else{
			break;
		}
	}
	
	al1 = new ArrayList();
	al1 = newal1 ;
	
	int getMaxNode = sap.getMaxPathNode();
	LinkInfo = new String[al1.size()*getMaxNode][10] ;
	LinkList = findA2BLinkJSON(al1,getMaxNode,a,b,Link_json,"link_info","w_length",1);
	ArrayList result = SortPathbyBandwidth(al1,getMaxNode,LinkList,fd,w);
	String[][] stringValues = new String[al1.size()*getMaxNode][10];
	for (int i=0 ; i<ReSortedLink.size() ; i++){
		stringValues[i] = (String[])ReSortedLink.get(i);
	}
	al1 = result ;
	String nowpos = "0" ;
	int seqvalue = 1 ;
	String resultSort = "" ;
	for(int i=0 ; i < stringValues.length ; i++){
		if(stringValues[i][0] != null){
			if(!stringValues[i][0].equals(nowpos)){
				nowpos = stringValues[i][0] ;
				resultSort += (resultSort.equals("")?"":";") + nowpos ;
				seqvalue = 1 ;
			}else{
				if(i==0) resultSort = nowpos ;
			}
			if(LinkList[Integer.parseInt(nowpos)][seqvalue]!=null){
				LinkList[Integer.parseInt(nowpos)][seqvalue] = chageOrderLink(LinkList[Integer.parseInt(nowpos)][seqvalue],stringValues[i][2]);
				seqvalue++;
			}
		}else{
			i=stringValues.length;
		}
	}

	if(resultSort.indexOf(";") > 0){
		String[] tmps = resultSort.split(";");

		for (int i=0 ; i< tmps.length ; i++){
			try{
				retLink[i] = LinkList[Integer.parseInt(tmps[i])];
			}catch (Exception e){
				System.out.println(e.toString());
			}
		}
	}else{
		retLink = LinkList ;
	}	
}

public String checkLinkFail(String st) throws IOException, SQLException,JSONException
{
	String retval = "";
	JSONArray jsonArray = new JSONArray("["+st+"]");
	for (int u = 0; u < jsonArray.length(); u++) {
		JSONObject explrObject = jsonArray.getJSONObject(u);
		retval += changeArray2String(explrObject.get("nidA_failure").toString());
		retval += changeArray2String(explrObject.get("nidB_failure").toString());
		retval += changeArray2String(explrObject.get("failure").toString());
	}
	return retval ;
}

public String checkLinkminiBandwidth(String st , String st1) throws IOException, SQLException,JSONException
{
	String retval = "";
	String thisretval = "";
	JSONArray jsonArray = new JSONArray("["+st+"]");
	for (int u = 0; u < jsonArray.length(); u++) {
		JSONObject explrObject = jsonArray.getJSONObject(u);
		thisretval = changeArray2String(explrObject.get(st1).toString());
		if(retval.equals("")){
			retval = thisretval ;
		}else{
			if(Float.valueOf(retval) > Float.valueOf(thisretval)) retval = thisretval ;
		}
	}
	return retval ;
}

public JSONObject getNodeSEQ() throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		String[] para = {};
		String[] typevalue = {};
		rs1 = getFieldList("scsi","sc_nodes");
		String qry_Statement = "SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where del_mark is null or  del_mark <> '1' ";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			rs1.beforeFirst();
			while (rs1.next()){
				String getFieldName = rs1.getString("column_name");
				json1.append(getFieldName,rs0.getString(getFieldName));
			}
			json1.append("node_rn",rs0.getString("rn"));
			json.append("node_info",json1);
		}
		Node_AllInfo = json ;
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}

public JSONObject getJsonLink(String st){
	if(st.equals("fail")) return fail_json ;
	if(st.equals("all")) return All_Link ;
	if(st.equals("link")) return Link_json ;
	if(st.equals("node")) return Node_AllInfo ;
	return null;
}

public String[][] getArrayResult2D(String st) throws IOException, SQLException
{
	if(st.equals("NodeLinkALL")) return NodeLinkALL ;
	if(st.equals("NodeLink")) return NodeLink ;
	if(st.equals("FailLink")) return FailLink ;
	if(st.equals("retLink")) return retLink ; 
	if(st.equals("LinkInfo")) return LinkInfo;
	return null ;
}

public String[] getArrayResult(String st) throws IOException, SQLException
{
	if(st.equals("NodeName")) return NodeName ;
	if(st.equals("NodeInfo")) return NodeInfo ;
	
	return null ;
}

public String changeArray2String(String st) throws IOException, SQLException{
	String retval = "";
	if(!st.equals("[null]")){
		retval = st.replace("[\"","").replace("\"]","");
	}
	return retval;
}

public ArrayList getArrayList(String st) throws IOException, SQLException{
	if(st.equals("sorted")) return SortedLink ;
	if(st.equals("resorted")) return ReSortedLink;
	if(st.equals("al")) return al1;
	return null ;
}

public ArrayList SortPathbyBandwidth(ArrayList al1, int getMaxNode, String[][] LinkList0, String weightField , int w) throws IOException, SQLException,JSONException
{
	ArrayList retval = new ArrayList() ;
	SortedLink = new ArrayList();
	ReSortedLink = new ArrayList();
	//retLink = LinkList0 ;
	String[][] nodeLink = null ;
	nodeLink = new String[al1.size()][2] ;
	int cntme = 0 ;
	for(int i=0 ; i < al1.size() ; i++){
		String[] tmp = al1.get(i).toString().split("->");
		
		//if( Integer.parseInt(al1.get(i).toString().split(",")[0]) <= maxHopNum){
		
		float bandwidth_ttl = 0.0f;
		float bandwidth_min = 1000000000.0f;
		for(int j=1 ; j<tmp.length ; j++){
			if(j>0){
				String[] getInfo = CalLinkInfo(i,LinkList0[i][j],weightField);
				LinkInfo[cntme] = getInfo ; cntme++;
				SortedLink.add(getInfo);
				if(bandwidth_min > Float.valueOf(getInfo[5])){
					bandwidth_min = Float.valueOf(getInfo[5]);
				}
			}
		}
		nodeLink[i][0]=Integer.toString(i);
		nodeLink[i][1]=Float.toString(bandwidth_min);
		//}		
	}
	retval = resortLink(al1,nodeLink,w);
	return retval ;
}

public ArrayList resortLink(ArrayList alx, String[][] nodeLink1 , int w) throws IOException, SQLException
{
	ArrayList retvals = new ArrayList();
	int swapme = 0 ;
	for(int u=0 ; u<nodeLink1.length ; u++){
		for(int v=1 ; v<nodeLink1.length ; v++){
			swapme = 0 ;
			if((w == 0) && (Float.valueOf(nodeLink1[v-1][1]) < Float.valueOf(nodeLink1[v][1]))) swapme = 1;
			if((w == 1) && (Float.valueOf(nodeLink1[v-1][1]) > Float.valueOf(nodeLink1[v][1]))) swapme = 1;
			
			if(swapme == 1){
				String[] tmpx = nodeLink1[v-1];
				nodeLink1[v-1] = nodeLink1[v];
				nodeLink1[v] = tmpx ;
			}
		}
	}
	for(int u=0 ; u<nodeLink1.length ; u++){
		retvals.add(alx.get(Integer.parseInt(nodeLink1[u][0])));
		for (int v=0 ; v<SortedLink.size() ; v++){
			String[] stringValues = (String[])SortedLink.get(v);
			if(stringValues[0].equals(nodeLink1[u][0])) ReSortedLink.add(SortedLink.get(v)); 
		}
	}

	return retvals ;
}

public String chageOrderLink(String st, String orderlist) throws IOException ,SQLException,JSONException
{
	String retval = "" ;
	if(orderlist.indexOf(";") > 0){
	String[] tmp = orderlist.split(";");
	JSONArray jsonArray = new JSONArray("["+st+"]");
	for (int v = 0 ; v < tmp.length  ;v++){
		for (int u = 0; u < jsonArray.length(); u++) {
			JSONObject explrObject = jsonArray.getJSONObject(u);
			String get_linkid = changeArray2String(explrObject.get("link_id").toString());
			if(get_linkid.equals(tmp[v])) retval += (retval.equals("")?"":",") + explrObject.toString();
		}
	}
	}else{
		retval = st ;
	}
	return retval ;
}

public String[] CalLinkInfo(int x, String st, String fd) throws IOException, SQLException,JSONException
{
	JSONArray jsonArray = new JSONArray("["+st+"]");
	String bw_list ="";
	String linkid_list = "";
	String link_status = "";
	String my_status = "";
	float bw_ttl = 0.0f;
	float bw_fail = 0.0f;
	float bw_work = 0.0f;
	float bw_min = 1000000000.0f;
	float bw_min_tmp = 0.0f;
	for (int u = 0; u < jsonArray.length(); u++) {
		JSONObject explrObject = jsonArray.getJSONObject(u);
		String get_bw = changeArray2String(explrObject.get(fd).toString());
		float bw_this = Float.valueOf((get_bw.equals("")?"0":get_bw));
		bw_ttl += bw_this ;
		if(changeArray2String(explrObject.get("failure").toString()).equals("0") && changeArray2String(explrObject.get("nidA_failure").toString()).equals("0") && changeArray2String(explrObject.get("nidB_failure").toString()).equals("0")){
			bw_work += bw_this;
			bw_min_tmp = bw_this;
			my_status = "O";
		}else{
			if(changeArray2String(explrObject.get("failure").toString()).equals("1")){
				String get_failbw = changeArray2String(explrObject.get("failure_bandwidth").toString());
				bw_fail += Float.valueOf((get_failbw.equals("")?"0":get_failbw));
				bw_work += bw_this - Float.valueOf((get_failbw.equals("")?"0":get_failbw));
				bw_min_tmp = bw_this - Float.valueOf((get_failbw.equals("")?"0":get_failbw));
				my_status = "P";
			}else{
				bw_fail += bw_this;
				bw_min_tmp = 0.0f;
				my_status = "X";
			}
		}
		if(!bw_list.equals("")){
			bw_list += ";";
			linkid_list += ";";
			link_status += ";";
		}
		bw_list += (get_bw.equals("")?"0":get_bw);
		linkid_list += changeArray2String(explrObject.get("link_id").toString());
		link_status += my_status;
		if(bw_min > bw_min_tmp) bw_min = bw_min_tmp ;
	}
	String sortString = sortme(linkid_list,bw_list,link_status);
	float bw_failrate = bw_fail / bw_ttl ;
	String[] retval = {Integer.toString(x), Integer.toString(jsonArray.length()), sortString.split("_")[0] , sortString.split("_")[1] , sortString.split("_")[2] ,Float.toString(bw_ttl) , Float.toString(bw_work) , Float.toString(bw_fail) , Float.toString(bw_failrate) , Float.toString(bw_min)};
	return retval ;
}

public String sortme(String st, String st1, String st2) throws IOException, SQLException{
	String[] tmp = null ;
	String[] tmp1 = null ;
	String[] tmp2 = null ;
	String retval = "";
	if(st.indexOf(";")>0){
		tmp = st.split(";");
		tmp1 = st1.split(";");
		tmp2 = st2.split(";");
		for (int i=0 ; i<tmp.length ; i++){
			for (int j=1 ; j<tmp.length ; j++){
				if(Float.valueOf(tmp1[j-1]) < Float.valueOf(tmp1[j])){
					String tmpme = tmp[j];
					String tmpme1 = tmp1[j];
					String tmpme2 = tmp2[j];
					tmp[j] = tmp[j-1];
					tmp1[j] = tmp1[j-1];
					tmp2[j] = tmp2[j-1];
					tmp[j-1]=tmpme ;
					tmp1[j-1]=tmpme1 ;
					tmp2[j-1]=tmpme2 ;
				}			
			}
		}
		for (String num : tmp) {
			retval += (retval.equals("")?"":";")+num;
        }
		String retval1="";
		for (String num : tmp1) {
			retval1 += (retval1.equals("")?"":";")+num;
        }
		String retval2="";
		for (String num : tmp2) {
			retval2 += (retval2.equals("")?"":";")+num;
        }
		retval += "_"+retval1 +"_"+retval2;
	}else{
		retval = st+"_"+st1+"_"+st2 ;
	}
	
	return retval ;
}

public String BuildVituralNetwork(int startint , int endint )
{
	System.out.println("stint ="+startint+"/endint ="+endint);
	String start_type = "";
	String end_type = "";
	String[] typevalue ={};
	String[] para={} ;
	int NodeLinkArr = 0 ;
	JSONObject json = new JSONObject();
	try {
	json = getNodeSEQ();
	//System.out.println("json ="+json);
	if(json.has("node_info")){
		JSONArray jsonArray = json.getJSONArray("node_info");
		NodeLinkArr = jsonArray.length()+1;
		NodeName = new String[NodeLinkArr];
		NodeName[0]="";
		for(int u=0 ; u< jsonArray.length() ; u++){
			JSONObject jsonObject1 = jsonArray.getJSONObject(u);
			
			String getStrings1 = changeArray2String(jsonObject1.optString("node_rn").toString());
			//System.out.println("getStrings1 ="+getStrings1);
			String getStrings2 = changeArray2String(jsonObject1.optString("node_name").toString());
			//System.out.println("getStrings2 ="+getStrings2);
			NodeName[u+1]=getStrings2;
			if(Integer.parseInt(getStrings1) == startint){
				start_type = changeArray2String(jsonObject1.optString("node_type").toString());
			}
			if(Integer.parseInt(getStrings1) == endint){
				end_type = changeArray2String(jsonObject1.optString("node_type").toString());
			}				
		}
	}
	//System.out.println("start_type ="+start_type);
	//System.out.println("end_type ="+end_type);
}catch(Exception e) {System.out.println("e ="+e.toString());}
	String[] typevalue1 ={};
	String[] para1={} ;
	NodeLinkALL = new String[NodeLinkArr][NodeLinkArr];
	NodeLink = new String[NodeLinkArr][NodeLinkArr];
	
	String filterString1 = "";
	String filterString2 = "";
	
	if(!start_type.equals("FOREIGN") &&  !end_type.equals("FOREIGN")){
		filterString1 = "nidA_type,nidB_type";
		filterString2 = "FOREIGN,FOREIGN";
	}
	
	String[] fieldname = {"sysid","link_id","link_name","link_owner","link_nodeA","link_nodeB","failure","max_bandwidth","using_bandwidth","rest_bandwidth","failure_bandwidth","nidA","nidB","nidA_type","nidB_type","nidA_failure","nidB_failure","w_length","w_latency","w_weight"};
	String[] filterlist = filterString1.split(",");
	String[] filtervalue = filterString2.split(",");
	try {
	json=getMultiLinkSEQ(typevalue1,para1,fieldname,filterlist,filtervalue);
	}catch(Exception e1) {}
	String[] nodeInfo={"node_info","fail_info"};
	try {
	for (int z=0 ; z < nodeInfo.length ; z++){
		String checkNode = nodeInfo[z];
		if(json.has(checkNode)){
			JSONArray jsonArray = json.getJSONArray(checkNode);
			
			for(int u=0 ; u< jsonArray.length() ; u++){
				JSONObject jsonObject1 = jsonArray.getJSONObject(u);
				String getStrings1 = changeArray2String(jsonObject1.optString("nidA").toString());
				String getStrings2 = changeArray2String(jsonObject1.optString("nidB").toString());
				String getStrings3 = changeArray2String(jsonObject1.optString("failure").toString());
				String getStrings4 = changeArray2String(jsonObject1.optString("nidA_failure").toString());
				String getStrings5 = changeArray2String(jsonObject1.optString("nidB_failure").toString());
				if(z==0){
					if((getStrings3.equals("0")) && (getStrings4.equals("0")) && (getStrings5.equals("0"))){
						NodeLink[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)]=getStrings2;
						NodeLink[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)]=getStrings1;				
					}
				}
				NodeLinkALL[Integer.parseInt(getStrings1)][Integer.parseInt(getStrings2)]=getStrings2;
				NodeLinkALL[Integer.parseInt(getStrings2)][Integer.parseInt(getStrings1)]=getStrings1;
			}
		}
	}
	}catch(Exception e2) {System.out.println("e2 ="+e2.toString());}
	return "Finish";
}

public JSONObject getMultiLinkSEQ(String[] typevalue , String[] para , String[] fieldnamelist , String[] filterlist, String[] filtervalue)
{
	System.out.println("getMultiLinkSEQ ");
	Link_json = new JSONObject();
	fail_json = new JSONObject();
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = "SELECT * , (SELECT b.node_failure FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where del_mark is null or  del_mark <> '1')b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA_failure , (SELECT b.node_failure FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where del_mark is null or del_mark <> '1' )b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB_failure,(SELECT b.node_type FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where (del_mark is null or del_mark <> '1'))b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name) AS nidA_type , (SELECT b.node_type FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where del_mark is null or del_mark <> '1')b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB_type, (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where del_mark is null or del_mark <> '1' )b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidA , (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes where del_mark is null or del_mark <> '1' )b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name and (a.del_mark is null or a.del_mark <> '1')) AS nidB FROM sc_links c where c.del_mark is null or c.del_mark <> '1'  GROUP BY CONCAT(link_nodeA,'_',link_nodeB,'_',link_id)  ORDER BY nidA, nidB";
		//String qry_Statement = "SELECT * , (SELECT b.node_failure FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes )b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name) AS nidA_failure , (SELECT b.node_failure FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes )b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name) AS nidB_failure,(SELECT b.node_type FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes )b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name) AS nidA_type , (SELECT b.node_type FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes )b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name) AS nidB_type, (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes )b ON b.node_id = a.node_id WHERE c.link_nodeA = a.node_name) AS nidA , (SELECT b.rn FROM sc_nodes a LEFT JOIN (SELECT * , ROW_NUMBER() OVER (order by node_id ) as rn  FROM sc_nodes )b ON b.node_id = a.node_id WHERE c.link_nodeB = a.node_name) AS nidB FROM sc_links c  GROUP BY CONCAT(link_nodeA,'_',link_nodeB,'_',link_id)  ORDER BY nidA, nidB";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			int filterout = 0 ;
			try {
			for (int v=0 ; v<filterlist.length ; v++){
				if(rs0.getString(filterlist[v]).equals(filtervalue[v])){
					filterout = 1;
					break;
				}
			}
			}catch(Exception e1) {}
			if(filterout == 0){
				int checkmore = 0 ;
				JSONObject json1 = new JSONObject();
				try {
				for (int p=0 ; p< noOfField ; p++){
					if(fieldnamelist[p].equals("failure") || fieldnamelist[p].equals("nidA_failure") || fieldnamelist[p].equals("nidB_failure")){
						if( rs0.getString(fieldnamelist[p]) == null){
							checkmore = 9 ;
							break;
						}else{
						String getValues = (String)rs0.getString(fieldnamelist[p]);
						if(!getValues.equals("0")){
							checkmore = 1;
							//break;
						}
						}
					}
					json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
				}
				}catch(Exception e2) {}
				try {
				if(checkmore != 9){
					if(checkmore == 0){
						json.append("node_info",json1);
					}else{
						json.append("fail_info",json1);
						fail_json.append("node_info",json1);
					}
					Link_json.append("link_info",json1);
				}
				}catch(Exception e3) {}
			}
		}	
	}catch(Exception e4){
		//System.out.print("ps e4="+e4.toString());
	}finally{
		if(rs0 != null)
			try {
				rs0.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	All_Link = json ;
	//System.out.println("get json ="+All_Link);
	return json ;
}

public String[][] findA2BLinkJSON(ArrayList al, int getMaxNode, int n , int m, JSONObject jsb , String root, String weightfield , int b) throws IOException, SQLException, JSONException
{
	LinkList = new String[al.size()+1][getMaxNode*2+1];
	retLink = new String[al.size()+1][getMaxNode*2+1];
	if(jsb.has(root)){
		JSONArray jsonArray = jsb.getJSONArray(root);
		for(int u=0 ; u < al.size() ; u++){
			String[] tmp = al.get(u).toString().split(",")[1].split("->");
			
			//if(Integer.parseInt(al.get(u).toString().split(",")[0]) <= maxHopNum){
			
			LinkList[u][0] = al.get(u).toString().split(",")[0];
			for(int v=1 ; v < tmp.length ; v++){
				String tmp0 = tmp[v-1].split("/")[0].replace("node","");
				String tmp1 = tmp[v].split("/")[0].replace("node","");
				for(int w=0 ; w< jsonArray.length() ; w++){
					JSONObject jsonObject1 = jsonArray.getJSONObject(w);
					if((changeArray2String(jsonObject1.optString("nidA").toString()).equals(tmp0) && changeArray2String(jsonObject1.optString("nidB").toString()).equals(tmp1)) || (changeArray2String(jsonObject1.optString("nidB").toString()).equals(tmp0) && changeArray2String(jsonObject1.optString("nidA").toString()).equals(tmp1))){
						if(LinkList[u][v] != null){
							if(LinkList[u][v].indexOf(jsonObject1.toString()) < 0){
								LinkList[u][v] += ","+jsonObject1.toString() ;
							}
						}else{
							LinkList[u][v] = jsonObject1.toString() ;
						}
					}
				}
			}
			
			//}
		}
	}
	return LinkList ;
}

public String[][] SortLink(String[][] link_org, String myWeight) throws IOException, SQLException{
	String[][] tmp_link = link_org ;
	return tmp_link;	
}

public ResultSet getFieldList(String db, String table) throws IOException, SQLException{
	String[] typevaluex = {"s","s"};
	String[] parax = {db, table};
	String sql_statement = "select column_name from information_schema.columns where table_schema = ? and table_name = ?";
	ResultSet rs =queryData(sql_statement,parax, typevaluex);
	return rs ;
}

public String findNodeStatus(String st, JSONObject jsons , String st1 , String mfd , String ffd) throws IOException, SQLException,  JSONException
{
	String retval = "";
	JSONArray jsonArray = jsons.getJSONArray(st1);
	for(int w=0 ; w< jsonArray.length() ; w++){
		JSONObject jsonObject1 = jsonArray.getJSONObject(w);
		if(changeArray2String(jsonObject1.optString(mfd).toString()).equals(st)){
			retval = changeArray2String(jsonObject1.optString(ffd).toString());
		}
	}
	return retval ;
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