package scsi.demo.wisoft;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Date;

import scsi.demo.scsi.DBConnect;

import java.lang.*;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class QueryDB extends DBConnect
{
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	Statement stmt1 = null ;
	ResultSet rs1 = null;

	PreparedStatement pst = null;	

	int rs_count = 0 ;
	int rs_count1 = 0 ;
	
public static void main(String[] args){}
	
public QueryDB() {
	try
		{
			conn = getDBConnect();
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
		}
		catch(Exception e)
		{
			//System.out.print(e);
		}
}

public void closeall() throws IOException, SQLException
{
	if (rs != null) rs.close();
	if (rs1 != null) rs1.close();
	if (stmt != null) stmt.close();
	if (stmt1 != null) stmt1.close();
	if (pst != null) pst.close();
	if (conn != null) conn.close();
}	

public int showCount() throws IOException, SQLException
{
	return rs_count ;
}

public ResultSet queryData(String st, String[] al) throws IOException, SQLException
{
	//String myState = st.matches("[A-Za-z0-9,-_%]+") ? st : "";
	//pst = conn.prepareStatement(myState);
	pst = conn.prepareStatement(st,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	try{
		int al_up = al.length;
		for (int x=1 ; x <= al_up ; x++){
			pst.setString(x, al[x-1]);
		}
		rs1=pst.executeQuery();
	}catch (Exception e){
		System.out.println(e.toString());
	}
	return rs1 ;
}

public ResultSet queryData(String st, String[] al ,int n) throws IOException, SQLException
{
	//String myState = st.matches("[A-Za-z0-9,-_%]+") ? st : "";
	//pst = conn.prepareStatement(myState);
	pst = conn.prepareStatement(st,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	try{
		int al_up = al.length;
		for (int x=1 ; x <= al_up ; x++){
			pst.setString(x, al[x-1]);
		}
		pst.setInt(al_up+1, n);
		rs1=pst.executeQuery();
	}catch (Exception e){
		System.out.println(e.toString());
	}
	return rs1 ;
}

public ResultSet queryData(String st, String[] al ,String[] al1) throws IOException, SQLException
{
	//String myState = st.matches("[A-Za-z0-9,-_%]+") ? st : "";
	//pst = conn.prepareStatement(myState);
	//System.out.println("st=" + st + "/al.length=" + al.length + "/al1.length=" + al1.length);
	try {
	pst = conn.prepareStatement(st,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	}catch (Exception e){
		System.out.println("QDB_104="+e.toString());
	}
	try{
		int al_up = al.length;
		for (int x=1 ; x <= al_up ; x++){
			if(al1[x-1].equals("s")){
				pst.setString(x, al[x-1]);
			}
			if(al1[x-1].equals("i")){
				pst.setInt(x, Integer.parseInt(al[x-1]));
			}
			if(al1[x-1].equals("l")){
				pst.setLong(x, Long.valueOf(al[x-1]));
			}
		}
//		System.out.println("p_st="+pst);
		rs1=pst.executeQuery();
	}catch (Exception e){
		System.out.println("QDB_118="+e.toString());
	}
	return rs1 ;
}

public void updateData(String st,String[] al) throws IOException, SQLException
{
	PreparedStatement pst = conn.prepareStatement(st,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	try{
		int al_up = al.length;
		for (int x=1 ; x <= al_up ; x++){
			pst.setString(x, al[x-1]);
		}
		int x = pst.executeUpdate();
	}catch (Exception e){
		System.out.println(e.toString());
	}finally{
		pst.close();
	}
}

public void updateData(String st,String[] al, String[] al1) throws IOException, SQLException
{
	PreparedStatement pst = conn.prepareStatement(st,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	try{
		int al_up = al.length;
		for (int x=1 ; x <= al_up ; x++){
			if(al1[x-1].equals("s")){
				pst.setString(x, al[x-1]);
			}
			if(al1[x-1].equals("i")){
				pst.setInt(x,  Integer.parseInt(al[x-1]));
			}
			if(al1[x-1].equals("l")){
				pst.setLong(x, Long.valueOf(al[x-1]));
			}
		}
		int x = pst.executeUpdate();
	}catch (Exception e){
		System.out.println(e.toString());
	}finally{
		pst.close();
	}
}

} //end of class