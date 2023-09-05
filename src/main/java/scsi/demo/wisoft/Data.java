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

public class Data extends QueryDB
{
	
	public static void main(String[] args){}
	
	private int i = 0 ;
	private int lg = 0 ;
	private ResultSet rs1, rs_album = null;
	private ResultSet rs2, rs_track = null;
	
	public Data() {
		 }
		 
	public void closeall() throws IOException, SQLException
	{
		if(rs1 != null) rs1.close();
		if(rs2 != null) rs2.close();
		super.closeall();
	}

public JSONObject getInfobyJSON(String[] typevalue , String[] para , String[] fieldnamelist , String root , String qryString ) throws IOException, SQLException
{
	JSONObject json = new JSONObject();
	ResultSet rs0 = null ;
	try{ 
		int noOfField = fieldnamelist.length ;
		String qry_Statement = qryString;
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			JSONObject json1 = new JSONObject();
			for (int p=0 ; p< noOfField ; p++){
				json1.append(fieldnamelist[p],rs0.getString(fieldnamelist[p]));
			}
			json.append(root,json1);
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return json ;
}


public String ScableScenario(String[] typevalue , String[] para) throws IOException, SQLException
{
	String retval = "";
	ResultSet rs0 = null ;
	try{ 
		String qry_Statement = "SELECT * FROM sc_scenario where sc_num = ? and sc_disable = 0 ";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			retval += rs0.getString("sc_failsc")+"<w>";
			retval += rs0.getString("sc_failloc")+"<w>";
			retval += rs0.getString("sc_faillocname")+"<w>";
			retval += rs0.getString("sc_failbw")+"<w>";
			retval += rs0.getString("sc_faildesc")+"<w>";
			retval += rs0.getString("sc_subsc")+"<w>";
			retval += rs0.getString("sc_subtext")+"<w>";
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return retval ;
}

public String ScableRoute(String[] typevalue , String[] para) throws IOException, SQLException
{
	String retval = "";
	ResultSet rs0 = null ;
	try{ 
		String qry_Statement = "SELECT * FROM sc_route a INNER JOIN sc_path b ON a.sc_pathname = b.shortname WHERE a.sc_name = ? AND a.sc_sn = ? LIMIT 1";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			retval += rs0.getString("geometry_coordinates");
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return retval ;
}

public String ScableNations(String[] typevalue , String[] para) throws IOException, SQLException
{
	String retval = "";
	ResultSet rs0 = null ;
	try{ 
		String qry_Statement = "SELECT * FROM sc_country a left join scsi.sc_countryname b on a.country_code = b.country_code where a.sc_name = ? and a.onoff=1 ";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			retval += rs0.getString("country_code")+"<i>"+rs0.getString("country_name")+"<w>";
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return retval ;
}		
	
public String ScableBackup(String[] typevalue , String[] para) throws IOException, SQLException
{
	String retval = "";
	ResultSet rs0 = null ;
	try{ 
		String qry_Statement = "SELECT * FROM sc_route c INNER JOIN (SELECT a.sc_name , a.country_code , b.country_name FROM sc_country a LEFT JOIN sc_countryname b ON b.country_code = a.country_code WHERE a.sc_name <> ? AND a.country_code = ?) d ON d.sc_name = c.sc_name AND c.sc_sn = ?  AND length(c.sc_pathname) > 0 GROUP BY c.sc_name";
		rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			retval += rs0.getString("sc_name")+"/"+rs0.getString("sc_abw")+"/"+rs0.getString("sc_tbw")+"<w>";
		}	
	}catch(Exception e){
		System.out.print(e.toString());
	}finally{
		if(rs0 != null) rs0.close();
	}
	return retval ;
}	

public String ScableList(String[] typevalue , String[] para) throws IOException, SQLException
{
	String retval = "";
	try{ 
		String qry_Statement = "SELECT * FROM sc_info where sc_type=0 and sc_disable = 0 GROUP BY sc_name; ";
		ResultSet rs0 = queryData(qry_Statement, para , typevalue);
		rs0.last();
		rs0.beforeFirst();
		while (rs0.next()){
			retval += rs0.getString("sc_name")+"<i>"+rs0.getString("sc_name")+"<w>";
		}
		if(rs0 != null) rs0.close();
	}catch(Exception e){
			System.out.print(e.toString());
	}
	return retval ;
}

public static String getNotNull(Object obj) {
		if (obj == null || String.valueOf(obj).length() == 0 || obj.equals("null"))
			obj = "";
		else
			obj = obj;
		if (String.valueOf(obj).length() == 0){
			 return String.valueOf(obj);
			}	else {
				if (String.valueOf(obj).indexOf("'") != -1) {
						obj = String.valueOf(obj).replaceAll("'","SingleQuote");
					}
					else {
				if (String.valueOf(obj).indexOf("SingleQuote") != -1 ) {
						obj = String.valueOf(obj).replaceAll("SingleQuote","'");
					}
				}
				if (String.valueOf(obj).indexOf("\"") != -1) {
						obj = String.valueOf(obj).replaceAll("\"","DoubleQuote");
					}
					else {
				if (String.valueOf(obj).indexOf("DoubleQuote") != -1 ) {
						obj = String.valueOf(obj).replaceAll("DoubleQuote","\"");
					}
				}
				return String.valueOf(obj).trim();
			}
	}

	public String today(int n) throws IOException, SQLException
	{
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DAY_OF_MONTH, n);
		String nowtime = new SimpleDateFormat("yyyyMMdd").format(rightNow.getTime());
		return nowtime ;
	}

	public String addBR(String str) throws IOException, SQLException
	{
		String[] tmp = str.split("\n");
		i = 0 ;
		String ret_value = "";
		int lg = tmp.length;
			while (i < lg)
		{
			ret_value = ret_value + tmp[i];
			i++ ;
			if (i<lg) ret_value = ret_value + "<br>" ;
		}
		return ret_value ;	
	}
	
	public String noTAG(String st , String tg_begin , String tg_end) throws IOException, SQLException
{
	String plain_intro = st ;
	String begin_me = tg_begin ;
	String end_me = tg_end ;
	String string1 = "" ;
	String string2 = "" ;
	int first_string = plain_intro.indexOf(begin_me) ;
	int end_string = 0 ;
	while ( first_string >= 0){
		end_string = plain_intro.indexOf(end_me);
		if (first_string == 0){
			plain_intro = plain_intro.substring(end_string+1,plain_intro.length());
		}
		else{
			string1 = plain_intro.substring(0,first_string) ;
			string2 = plain_intro.substring(end_string+1,plain_intro.length());
			plain_intro = string1 + string2 ;
		}
		first_string = plain_intro.indexOf(begin_me) ;
	}
	plain_intro = checkString(plain_intro);
	return plain_intro ;	
}

public int secretid(int n) throws IOException,SQLException
{
	//return (((n+thisyear())*2)-1911);
	return (((n+2008)*2)-1911);
}

public int desecretid(int n) throws IOException,SQLException
{
	//return (((n+1911)/2)-thisyear());
	return (((n+1911)/2)-2008);
}

private String checkString(String tstr) {
	String returnString = "";
	tstr = tstr.replace("&","&amp;");
	tstr = tstr.replace("<","&lt;");
	tstr = tstr.replace(">","&gt;");
	tstr = tstr.replace("'","&apos;");
	tstr = tstr.replace("\"","&quot;");
	return tstr;
}

/*
public String[] getParameterValues(String s) {
String str[] = super.getParameterValues(s);

if (str == null) {
return null;
}
int i = str.length;
String as1[] = new String[i];
for (int j = 0; j < i; j++  ) {
as1[j] = cleanXSS(cleanSQLInject(str[j]));
}
return as1;
}
public String getParameter(String s) {
String s1 = super.getParameter(s);

if (s1 == null) {
return null;
} else {
return cleanXSS(cleanSQLInject(s1));
}
}
public String getHeader(String s) {
String s1 = super.getHeader(s);

if (s1 == null) {
return null;
} else {
return cleanXSS(cleanSQLInject(s1));
}
}
*/
public String cleanXSS(String src) {
String temp =src;
//System.out.println("xss---temp-->"+ src);
src = src.replaceAll("<", "<").replaceAll(">", ">");
// if (src.indexOf("address")==-1)
//	{
src = src.replaceAll("\\(", "(").replaceAll("\\)", ")");
//}
src = src.replaceAll("'", "'");
Pattern pattern=Pattern.compile("(eval\\((.*)\\)|script)",Pattern.CASE_INSENSITIVE);  
Matcher matcher=pattern.matcher(src);  
src = matcher.replaceAll("");
pattern=Pattern.compile("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']",Pattern.CASE_INSENSITIVE); 
matcher=pattern.matcher(src);
src = matcher.replaceAll("\"\"");

src = src.replaceAll("script", "").replaceAll(";", "")
.replaceAll("\"", "").replaceAll("@", "")
.replaceAll("0x0d", "")
.replaceAll("0x0a", "").replaceAll(",", "");
if(!temp.equals(src)){
System.out.println("Xss Found!!");
System.out.println("Source Input-->"+ temp);
System.out.println("ReProcess -->"+ src);
}
return src;
}

public String cleanSQLInject(String src) {
String temp =src;
src = src.replaceAll("insert ", "forbidI")
.replaceAll("select ", "forbidS")
.replaceAll("update ", "forbidU")
.replaceAll("delete ", "forbidD")
.replaceAll(" and ", "forbidA")
.replaceAll(" or ", "forbidO");
if(!temp.equals(src)){
System.out.println("SQL Attack!");
System.out.println("Source Input -->"+ temp);
System.out.println("Reprocess-->"+ src);
}
return src;
}


} //end of class