package scsi.demo.scsi;
import java.util.*;

import org.springframework.stereotype.Controller;

import scsi.demo.model.Details;
import scsi.demo.model.EditF;
import scsi.demo.model.Link;
import scsi.demo.model.Nodes;
import scsi.demo.model.User;
import scsi.demo.scsi.CaseData;
import scsi.demo.scsi.ReserveData;
import scsi.demo.scsi.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class ReserveData extends getData
{
String oname = "" ;
String booking_result = "" ;
public int total;

public static void main(String[] args){}
	
public ReserveData() {}

public void closeall()throws IOException, SQLException
{
	super.closeall();	
}

public void nodebytype(String st0,String st1) throws IOException, SQLException {
	init("sc_nodes");
	if(st1.equals("admin")) {
		queryMe("node_type = '"+st0+"' and del_mark is null order by sysid");
	}else {
	queryMe("node_type = '"+st0+"' and owner='"+st1+"' and del_mark is null order by sysid");
	}
}


public String getlinktype(String st0) throws SQLException {
	pst=conn.prepareStatement("select node_type from sc_nodes where node_name = ? and del_mark is null ");
	pst.setString( 1,"" + st0 + "");
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	rs.next();
	s=rs.getString("node_type");
	return s;
}

public void getnode() throws IOException, SQLException, InterruptedException{
	init("sc_nodes");
	queryMe("sysid != 'null' and del_mark is null order by sysid");
	
}

public  void getnode(String st0) throws IOException, SQLException, InterruptedException{
	init("sc_nodes");
	queryMe("owner ='"+st0+"' and del_mark is null order by sysid");
//	pst=conn.prepareStatement("select * from sc_nodes where owner like ? and del_mark is null");
//	pst.setString(1, "%"+st0+"%");
//	rs=pst.executeQuery();
//	return rs;
}

public void getdetail() throws IOException, SQLException, InterruptedException{
	init("sc_links_details");
	queryMe("sysid != 'null' and del_mark is null order by sysid");
	
}

public void getLinkId2(String st0) throws IOException, SQLException, InterruptedException{
	init("sc_links_details");
	queryMe("sysid ='"+st0+"' and del_mark is null order by sysid");
	
}
	
public String getNamec(int st0) throws SQLException {
	pst=conn.prepareStatement("select name_ch from sc_code_tab where code_id = ? and del_mark is null ");
	pst.setString( 1,"" + st0 + "");
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	rs.next();
	s=rs.getString("name_ch");
	return s;
}

public String getNamez(String st0) throws SQLException {
	pst=conn.prepareStatement("select name_zh from sc_code_tab where seq = ? and del_mark is null ");
	pst.setString( 1, ""+st0+"" );
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	rs.next();
	s=rs.getString("name_zh");
	return s;
}

public int getCodeid(String st0) throws SQLException {
	pst=conn.prepareStatement("select code_id from sc_code_tab where seq = ? and del_mark is null ");
	pst.setString( 1, ""+st0+"" );
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	rs.next();
	int ss=0;
	ss=rs.getInt("code_id");
	return ss;
}

public String getDesc(String st0,String st1) throws SQLException {
	pst=conn.prepareStatement("select name_desc_zh from sc_code_tab where name_zh = ? and code_id=?");
	pst.setString( 1,"" + st0 + "");
	pst.setString( 2,"" + st1 + "");
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	rs.next();
	s=rs.getString("name_desc_zh");
	return s;
}

public ResultSet getnode1(String st0) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_nodes where (node_id like ?  or node_name like ? or owner like ? "
			+ "or address like ? or node_type like ? or node_country like ?  "
			+ " ) and del_mark is null order by sysid ");
	pst.setString( 1,"%" + st0 + "%");//'"+st0+"'  '%"+st0+"%'  //st0
	pst.setString( 2,"%" + st0 + "%");
	pst.setString( 3,"%" + st0 + "%");
	pst.setString( 4,"%" + st0 + "%");
	pst.setString( 5,"%" + st0 + "%");
	pst.setString( 6,"%" + st0 + "%");
	//System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getnode2(String st0,String st1) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_nodes where (node_id like ?  or node_name like ? or owner like ? "
			+ "or address like ? or node_type like ? or node_country like ?  "
			+ " )and owner = ? and del_mark is null order by sysid ");
	pst.setString( 1,"%" + st0 + "%");//'"+st0+"'  '%"+st0+"%'  //st0
	pst.setString( 2,"%" + st0 + "%");
	pst.setString( 3,"%" + st0 + "%");
	pst.setString( 4,"%" + st0 + "%");
	pst.setString( 5,"%" + st0 + "%");
	pst.setString( 6,"%" + st0 + "%");
	pst.setString( 7,"" + st1 + "");
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getNode2(String st0) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_nodes where owner = ? and del_mark is null order by sysid ");
	pst.setString( 1,"" + st0 + "");//'"+st0+"'  '%"+st0+"%'  //st0
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getuser(String st0) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_users where user_sysid = ? and del_mark is null");
	pst.setString( 1,"" + st0 + "");//'"+st0+"'  '%"+st0+"%'  //st0
	//System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getuser2(String st0,String st1) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_users where user_sysid = ? and user_gr = ? and del_mark is null");
	pst.setString( 1,"" + st0 + "");//'"+st0+"'  '%"+st0+"%'  //st0
	pst.setString( 2,"" + st1 + "");
	//System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getEditlist(String st0) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_code_tab where code_id= ? and del_mark is null order by code_id");
	pst.setString( 1,"" + st0 + "");//'"+st0+"'  '%"+st0+"%'  //st0
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getEditF(String st0) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_code_tab where seq= ? and del_mark is null");
	pst.setString( 1,"" + st0 + "");//'"+st0+"'  '%"+st0+"%'  //st0
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getlinkdetail(String st0) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_links_details where (links_sysid = ? ) and del_mark is null order by sysid ");
	pst.setString( 1,"" + st0 + "");//'"+st0+"'  '%"+st0+"%'  //st0
//	System.out.println(pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}
public String getlinkname(String st0) throws SQLException {
	pst=conn.prepareStatement("select link_name from sc_links where sysid = ? and del_mark is null ");
	pst.setString( 1,"" + st0 + "");
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	rs.next();
	s=rs.getString("link_name");
	return s;
}


public void getFunction(String st0) throws IOException, SQLException
{
//	init("function_list1");
//	this.queryMe("seq='"+st0+"'  order by sort");
	//System.out.print("seq='"+st0+"'");
	pst=conn.prepareStatement("select * from function_list1 where seq =? order by sort ");
	pst.setString( 1,"" + st0 + "");//'"+st0+"'  '%"+st0+"%'  //st0
	rs=pst.executeQuery();
}

public boolean checknoden(String st0) throws SQLException {
	pst=conn.prepareStatement("select * from sc_nodes where node_name=? and del_mark is NULL",ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
	pst.setString(1, ""+st0+"");
	rs=pst.executeQuery();
	boolean b=false;
	int o=rs.getRow();
	System.out.println("pst="+pst);
	rs.beforeFirst();
	if(rs.next()) {
		b=true;
	}else {
		b=false;
	}
	return b;
}

public boolean checknode(String st0) throws SQLException {
	pst=conn.prepareStatement("select * from sc_links where link_nodeA = ? or link_nodeB = ? and del_mark is null");
	pst.setString(1, ""+st0+"");
	pst.setString(2, ""+st0+"");
	rs=pst.executeQuery();
	rs.next();
	boolean b=false;
	int o=rs.getRow();
//	System.out.println("pst="+pst);
	if(o>0) {
		b=true;
	}else {
		b=false;
	}
	return b;
}

public boolean checkcode(String st0,String st1,String st2) throws SQLException {
	switch(st1) {//seq,code,name
	case "2":
		pst=conn.prepareStatement("select * from sc_nodes where node_country = ? and del_mark is null");
		pst.setString(1, ""+st2+"");
		break;
	case "3":
		pst=conn.prepareStatement("select * from sc_nodes where node_type = ? and del_mark is null");
		pst.setString(1, ""+st2+"");
		break;
	case "4":
		pst=conn.prepareStatement("select * from sc_nodes where owner = ? and del_mark is null");
		pst.setString(1, ""+st2+"");
		pst1=conn.prepareStatement("select * from sc_links where link_owner = ? and del_mark is null");
		pst1.setString(1, ""+st2+"");
		pst2=conn.prepareStatement("select * from sc_links_details where details_operator = ? and del_mark is null");
		pst2.setString(1, ""+st2+"");
		break;
	case "5":
		pst=conn.prepareStatement("select * from sc_links where link_type = ? and del_mark is null");
		pst.setString(1, ""+st2+"");
		break;
	case "6":
		pst=conn.prepareStatement("select * from sc_links where failure = ? and del_mark is null");
		pst.setString(1, ""+st2+"");
		break;
	case "7":
		pst=conn.prepareStatement("select * from sc_nodes where node_failure = ? and del_mark is null");
		pst.setString(1, ""+st2+"");
		break;
	case "8":
		pst=conn.prepareStatement("select * from sc_links where link_protocol = ? and del_mark is null");
		pst.setString(1, ""+st2+"");
		break;
	}
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	rs.next();
	boolean b=false;
	int o=rs.getRow();
	if(st1.equals("4")) {
		rs1=pst1.executeQuery();
		rs2=pst2.executeQuery();
	if(rs1.next()) {
		o=rs1.getRow();
		if(o>0) {
			b=true;
		}else {
			b=false;
		}
	}else if(rs2.next()){
		o=rs2.getRow();
		if(o>0) {
			b=true;
		}else {
			b=false;
		}
	}else {
//		System.out.println("o="+o);
		if(o>0) {
			b=true;
		}else {
			b=false;
		}
	}
	}else {
//	System.out.println("o="+o);
	if(o>0) {
		b=true;
	}else {
		b=false;
	}
	}
	return b;
}


//public void getFunctionGroup() throws IOException, SQLException
//{
//		init("function_list1");
//		pst=conn.prepareStatement("select * from function_list1 where seq !=? order by sort ");
//		this.queryMe(" seq !='' order by sort");
//		pst.setString( 1,"");//'"+st0+"'  '%"+st0+"%'  //st0
//		//System.out.println("func_group_id='"+st2+"'");
//		rs=pst.executeQuery();
//	
//}

public ResultSet getlink2(String st0) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_links where (link_id like ? or link_name like ? "
			+ "or link_owner like ? or link_nodeA like ? or link_nodeB like ?  "
			+ " ) and del_mark is null order by sysid ");
	pst.setString( 1,"%" + st0 + "%");//'"+st0+"'  '%"+st0+"%'  //st0
	pst.setString( 2,"%" + st0 + "%");
	pst.setString( 3,"%" + st0 + "%");
	pst.setString( 4,"%" + st0 + "%");
	pst.setString( 5,"%" + st0 + "%");
	//System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getlink1(String st0,String st1) throws IOException, SQLException, InterruptedException{
//	init("detail");
//	
//	String key = "(alms like '%"+st0+"%' or rec_id like '%"+st0+"%' or note like '%"+st0+"%' or donate like '%"+st0+"%' or donor like '%"+st0+"%' or DATE_FORMAT(sdate,'%Y-%m-%d') like '%"+st0+"%'"
//			+ " or handler like '%"+st0+"%' or DATE_FORMAT(sysid,'%Y-%m-%d') like '%"+st0+"%' or type like '%"+st0+"%') and del_mark is null order by sysid desc";
//	this.queryMe(key+" ");
	pst=conn.prepareStatement("select * from sc_links where (link_id like ? or link_name like ? "
			+ "or link_owner like ? or link_nodeA like ? or link_nodeB like ?  "
			+ " ) and link_owner = ? and del_mark is null order by sysid ");
	pst.setString( 1,"%" + st0 + "%");//'"+st0+"'  '%"+st0+"%'  //st0
	pst.setString( 2,"%" + st0 + "%");
	pst.setString( 3,"%" + st0 + "%");
	pst.setString( 4,"%" + st0 + "%");
	pst.setString( 5,"%" + st0 + "%");
	pst.setString( 6,"" + st1 + "");
	//System.out.println("pst="+pst);
	rs=pst.executeQuery();
	//getData(rs);
	return rs;
}

public ResultSet getNodeId(String st0)throws IOException, SQLException, InterruptedException{
//	init("detail");
//	this.queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_nodes where sysid = ? and del_mark is null");
	pst.setString(1, "" + st0 + "");
	rs=pst.executeQuery();
	//System.out.println("pst="+pst);
	//getData(rs);
	return rs;
}

public ResultSet getNId(String st0)throws IOException, SQLException, InterruptedException{
//	init("detail");
//	this.queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_nodes where node_id = ? and del_mark is null");
	pst.setString(1, "" + st0 + "");
	rs=pst.executeQuery();
	//System.out.println("pst="+pst);
	//getData(rs);
	return rs;
}

public ResultSet getCIdd(String st0,String st1,String st2)throws IOException, SQLException, InterruptedException{
//	init("detail");
//	this.queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_connection where connection_id = ? and connection_A = ? and connection_B = ? and del_mark is NULL");
	pst.setString(1, "" + st0 + "");
	pst.setString(2, "" + st1 + "");
	pst.setString(3, "" + st2 + "");
	rs=pst.executeQuery();
	System.out.println("pst="+pst);
	//getData(rs);
	return rs;
}

public ResultSet getCIdf(String st0)throws IOException, SQLException, InterruptedException{
	pst=conn.prepareStatement("select * from sc_connection where connection_id = ? and del_mark is NULL",ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
	pst.setString(1, "" + st0 + "");
	rs=pst.executeQuery();
	System.out.println("pst="+pst);
	return rs;
}

public ResultSet getLId(String st0)throws IOException, SQLException, InterruptedException{
//	init("detail");
//	this.queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_links where link_id = ? and del_mark is null");
	pst.setString(1, "" + st0 + "");
	rs=pst.executeQuery();
	//System.out.println("pst="+pst);
	//getData(rs);
	return rs;
}

public ResultSet getUserId(String st0)throws IOException, SQLException, InterruptedException{
//	init("detail");
//	this.queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_users where user_sysid = ? and del_mark is null");
	pst.setString(1, "" + st0 + "");
	rs=pst.executeQuery();
	//System.out.println("pst="+pst);
	//getData(rs);
	return rs;
}

public ResultSet getDetailId(String st0)throws IOException, SQLException, InterruptedException{
//	init("detail");
//	this.queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_links_details where sysid = ? and del_mark is null");
	pst.setString(1, "" + st0 + "");
	rs=pst.executeQuery();
	//System.out.println("pst="+pst);
	//getData(rs);
	return rs;
}

public ResultSet getLinkId(String st0)throws IOException, SQLException, InterruptedException{
//	init("detail");
//	this.queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_links where sysid = ? and del_mark is null");
	pst.setString(1, "" + st0 + "");
	rs=pst.executeQuery();
	//System.out.println("pst="+pst);
	//getData(rs);
	return rs;
}

public void getType() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("code_id='3' and del_mark is null");
}

public void getLType() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("code_id='5' and del_mark is null");
}

public void getProtocol() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("code_id='8' and del_mark is null");
}

public void getLFailure() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("code_id='6' and del_mark is null");
}

public void getNFailure() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("code_id='7' and del_mark is null");
}

public void getOwner() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("code_id='4' and del_mark is null");
}

public void getcodeidli() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("seq group by code_id and del_mark is null");
}

public void getcodeid() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("seq  and del_mark is null");
}

public void getLink1() throws IOException,SQLException
{
	init("sc_links");
	queryMe("sysid != 'null' and del_mark is null order by sysid");
}

public void getcountry() throws IOException,SQLException
{
	init("sc_code_tab");
	queryMe("code_id='2' and del_mark is null");
}

public ResultSet getLink() throws IOException,SQLException
{
	//init("user1");
	//queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_links where sysid !='null' and del_mark is null ");
	//pst.setString(1, st0);
	//System.out.println(pst);
	rs=pst.executeQuery();
	rs.beforeFirst();
	return rs;
}

public ResultSet getLink2(String st0) throws IOException,SQLException
{
	//init("user1");
	//queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_links where sysid !='null' and link_owner =? and del_mark is null ");
	pst.setString(1, ""+st0+"");
	//System.out.println(pst);
	rs=pst.executeQuery();
	rs.beforeFirst();
	return rs;
}

public ResultSet getNode() throws IOException,SQLException
{
	//init("user1");
	//queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_nodes where sysid !='null' and del_mark is null ");
	//pst.setString(1, st0);
	//System.out.println(pst);
	rs=pst.executeQuery();
	rs.beforeFirst();
	return rs;
}

public void getNode3(String st0) throws IOException,SQLException
{
	//init("user1");
	//queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_nodes where owowner=?nd del_mark is null ");
	pst.setString(1,st0);
	System.out.println("node3"+pst);
	rs=pst.executeQuery();
////s.beforeFirst();
}

public ResultSet getUser() throws IOException,SQLException
{
	//init("user1");
	//queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_users where user_sysid !='null' and del_mark is null ");
	//pst.setString(1, st0);
	//System.out.println(pst);
	rs=pst.executeQuery();
	rs.beforeFirst();
	return rs;
}

public ResultSet getUser2(String st0) throws IOException,SQLException
{
	//init("user1");
	//queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_users where user_sysid !='null' and user_gr=? and del_mark is null ");
	pst.setString(1, st0);
////ystem.out.println(pst);
	rs=pst.executeQuery();
	rs.beforeFirst();
	return rs;
}

public ResultSet getEditF() throws IOException,SQLException
{
	//init("user1");
	//queryMe("sysid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_code_tab where seq !='null' and del_mark is null order by code_id");
	//pst.setString(1, st0);
	//System.out.println(pst);
	rs=pst.executeQuery();
	rs.beforeFirst();
	return rs;
}

public ResultSet getUserbyid(String st0) throws IOException,SQLException
{
	//init("user1");
	//queryMe("userid='"+st0+"' and del_mark is null");
	pst=conn.prepareStatement("select * from sc_users where user_id= ? and del_mark is null");
	pst.setString(1, ""+st0+"");
	System.out.println(pst);
	rs=pst.executeQuery();
	rs.beforeFirst();
	return rs;
}

public ArrayList<User> getUsers(ResultSet rs) {
	
	ArrayList<User> result=new ArrayList<User>();
	try {
		while (rs.next()) {
			User users=new User();
			users.setUsersysid(rs.getInt("user_sysid"));
			users.setUserid(rs.getString("userid"));
			users.setPassword(rs.getString("password"));
			users.setUsername(rs.getString("user_name"));
			users.setFail_times(rs.getInt("fail_times"));
			users.setLock_flag(rs.getString("lock_flag"));
			users.setUser_gr(rs.getString("user_gr"));
			users.setLock_st(rs.getString("lock_st"));
			result.add(users);
		}
	} catch (Exception ex) {
		System.out.println(ex);
	}
	return result;
}

public ArrayList<Nodes> getNodes(ResultSet rs) {
	
	ArrayList<Nodes> result=new ArrayList<Nodes>();
	try {
		while (rs.next()) {
			Nodes nodes=new Nodes();
			nodes.setSysid(rs.getString("sysid"));
			nodes.setNode_id(rs.getString("node_id"));
			nodes.setNode_name(rs.getString("node_name"));
			nodes.setOwner(rs.getString("owner"));
			nodes.setAddress(rs.getString("address"));
			nodes.setLon(rs.getString("lon"));
			nodes.setLat(rs.getString("lat"));
			nodes.setNode_type(rs.getString("node_type"));
			nodes.setNode_country(rs.getString("node_country"));
			nodes.setPos(rs.getString("pos"));
			nodes.setNode_failure(rs.getString("node_failure"));
			result.add(nodes);
		}
	} catch (Exception ex) {
		System.out.println(ex);
	}
	return result;
}

public ArrayList<Details> getDetails(ResultSet rs) {
	ArrayList<Details> result=new ArrayList<Details>();
	try {
		while (rs.next()) {
			Details details=new Details();
			details.sysid=rs.getString("sysid");
			details.links_sysid=rs.getString("links_sysid");
			details.details_id=rs.getString("details_id");
			details.details_type=rs.getString("details_type");
			details.details_operator=rs.getString("details_operator");
			details.details_bandwidth=rs.getString("details_bandwidth");
			details.details_failure=rs.getString("details_failure");
			details.details_failure_bandwidth=rs.getString("details_failure_bandwidth");
			result.add(details);
		}
	} catch (Exception ex) {
		System.out.println(ex);
	}
	return result;
}

public ArrayList<EditF> getEditF(ResultSet rs) {
	ArrayList<EditF> result=new ArrayList<EditF>();
	try {
		while (rs.next()) {
			EditF ed=new EditF();
			ed.seq=rs.getInt("seq");
			ed.code_id=rs.getInt("code_id");
			ed.name_zh=rs.getString("name_zh");
			ed.name_desc_zh=rs.getString("name_desc_zh");
			ed.name_ch=rs.getString("name_ch");
			result.add(ed);
		}
	} catch (Exception ex) {
		System.out.println(ex);
	}
	return result;
}

public ArrayList<Link> getLink(ResultSet rs) {
	
	ArrayList<Link> result=new ArrayList<Link>();
	try {
		while (rs.next()) {
			Link link=new Link();
			link.setSysid(rs.getString("sysid"));
			link.setLink_id(rs.getString("link_id"));
			link.setLink_name(rs.getString("link_name"));
			link.setLink_owner(rs.getString("link_owner"));
			link.setLink_nodeA(rs.getString("link_nodeA"));
			link.setLink_nodeB(rs.getString("link_nodeB"));
			link.setMax_bandwidth(rs.getString("max_bandwidth"));
			link.setUsing_bandwidth(rs.getString("using_bandwidth"));
			link.setRest_bandwidth(rs.getString("rest_bandwidth"));
			link.setPathfile(rs.getString("pathfile"));
			link.setPathdata(rs.getString("pathdata"));
			link.setLink_type(rs.getString("link_type"));
			link.setLink_protocol(rs.getString("link_protocol"));
			link.setFailure(rs.getString("failure"));
			link.setFailure_bandwidth(rs.getString("failure_bandwidth"));
			link.setW_length(rs.getFloat("w_length"));
			link.setW_latency(rs.getFloat("w_latency"));
			link.setW_weight(rs.getFloat("w_weight"));
			result.add(link);
		}
	} catch (Exception ex) {
		System.out.println(ex);
	}
	return result;
}

public void get3pass(String st0) throws SQLException, IOException {
	init("sc_pass");
	queryMe("userid='"+st0+"' order by sysid desc limit 3");
}

public String check3pass(String st0,String st1) throws IOException, SQLException {
	pst=conn.prepareStatement("select pass from sc_pass where userid = ? order by sysid desc limit 3 ");
	pst.setString( 1, ""+st0+"" );
//	System.out.println("pst="+pst);
	rs=pst.executeQuery();
	rs.beforeFirst();
	while(rs.next()) {
		if(st1.equals(rs.getString("pass"))) {
			s="1";
			break;
		}else {
			s="0";
		}
	}
	return s;
	
}

public ResultSet getfail0(String st0)throws IOException, SQLException, InterruptedException{
	pst=conn.prepareStatement("select * from sc_connectionfail where connectionid= ? and failceased ='0' ",ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
	pst.setString(1, st0 );
	System.out.println("st0="+st0);
	rs=pst.executeQuery();
	System.out.println("pst="+pst);
	return rs;
}

public boolean checkABBA(String st0,String st1) throws SQLException {
	pst=conn.prepareStatement("select * from sc_connection where (connection_A =? and connection_B =?) or  (connection_A =? and connection_B =?)and del_mark is NULL",ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
	pst.setString(1, "" + st0 + "");
	pst.setString(2, "" + st1 + "");
	pst.setString(3, "" + st1 + "");
	pst.setString(4, "" + st0 + "");
	rs=pst.executeQuery();
	rs.beforeFirst();
	boolean b=false;
	System.out.println("pst="+pst);
	if(rs.next()) {
		b=true;
	}else {
		b=false;
	}
	return b;
	
}

} //end of class