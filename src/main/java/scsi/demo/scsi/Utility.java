package scsi.demo.scsi;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;



public class Utility extends QueryDB
{
	//private String xmlURLStringsuc ="https://api.sys.scu.edu.tw/query/LibSeatAuth.ashx";
	ResultSet rs = null;
	ResultSet rs1 = null; 
	
	public static void main(String[] args){}
	
	private String nowtime = null ;
	private String qry_sql = null ;
	private String ret_value = null;
	private String tmp[];
	private String num = "0123456789";
	private String Cdate = "" ;
	private int rs_count = 0 ;
	private int i = 0 ;
	private int lg = 0 ;
	private int rs_count1 = 0 ;
	private int rtval = 0 ;

	private String source = "ABCDEFGHJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz0123456789*^$()@!-/:~}{][";
	private String source1 = "ABCDEFGHJKLMNOPQRSTUVWXYZ0123456789";
	private String source2 = "0123456789";
	private String ncode = "" ;
	private int j = 0 ;
	private int k = 0 ;
	private int m = 0 ;
	private Random rd = new Random();

	private int perpage = 10 ;
	private int totalpage = 0 ;
	private int totalitem = 0 ;
	private int pagebegin = 0 ;
	private int pageend = 0 ;
	private int pagefirst = 0 ;
	private int pagelast = 0 ;
	private int pagenumber = 10 ;
	private int tmp_int = 0 ;
	private int tmp1 = 0 ;
	
public Utility() {}

public void closeall() throws IOException, SQLException
	{
 	super.closeall();
 	if (rs != null) rs.close();
	if(rs1!=null)	rs1.close();
	if(rs2!=null)	rs2.close();
	}	

public ResultSet getData(String sql) throws IOException, SQLException {
	ResultSet rs = this.queryData(sql);
	return rs;

}

public static String getNotNull(Object obj) {
	Object obj1;
	if (obj == null || String.valueOf(obj).length() == 0 || obj.equals("null"))
		obj1 = "";
	else
		obj1 = obj;
	if (String.valueOf(obj1).length() == 0){
		 return String.valueOf(obj1);
		}	else {
				if (String.valueOf(obj1).indexOf("'") != -1) {
					obj1 = String.valueOf(obj1).replaceAll("'","&apos;");
				}
				else 
				{
						
					if (String.valueOf(obj1).indexOf("SingleQuote") != -1 ) {
					obj1 = String.valueOf(obj1).replaceAll("SingleQuote","'");
					}
				}
			if (String.valueOf(obj1).indexOf("\"") != -1) {
					obj1 = String.valueOf(obj1).replaceAll("\"","&quot;");
				}
			else 
			{
				if (String.valueOf(obj1).indexOf("DoubleQuote") != -1 ) {
					obj1 = String.valueOf(obj1).replaceAll("DoubleQuote","\"");
				}
			}
			
			if(String.valueOf(obj1).indexOf("& #39;") != -1){
				obj1 = String.valueOf(obj1).replaceAll("& #39;","&#39;");
			}
			if (String.valueOf(obj1).indexOf("& #40;") != -1)
			{
				obj1 = String.valueOf(obj1).replaceAll("& #40;","&#40;");
			}
			if (String.valueOf(obj1).indexOf("& lt;") != -1)
			{
				obj1 = String.valueOf(obj1).replaceAll("& lt;","&lt;");
			}
			if (String.valueOf(obj1).indexOf("& gt;") != -1)
			{
				obj1 = String.valueOf(obj1).replaceAll("& gt;","&gt;");
			}
			if (String.valueOf(obj1).indexOf("&gt;") != -1)
			{
				obj1 = String.valueOf(obj1).replaceAll("&gt;",">");
			}
			if (String.valueOf(obj1).indexOf("&lt;") != -1)
			{
				obj1 = String.valueOf(obj1).replaceAll("&lt;","<");
			}
			return String.valueOf(obj1);
		}
}

public String today() throws IOException, SQLException
{
		Calendar rightNow = Calendar.getInstance();
		nowtime = new SimpleDateFormat("yyyy-MM-dd").format(rightNow.getTime());
		return nowtime ;
}
	
public String todaytime_hhmm() throws IOException, SQLException
{
	Calendar rightNow = Calendar.getInstance();
	nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rightNow.getTime());
	return nowtime ;
}	

public String todaytime() throws IOException, SQLException
{
	Calendar rightNow = Calendar.getInstance();
	nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rightNow.getTime());
	return nowtime ;
}
	
	
	






	














	


		
	

		
	

	
	


	
	
	









	









	public void SetPageinfo(int m, int n) throws IOException, SQLException
	{
		totalitem=m;
		perpage=n;
		totalpage=(int)(totalitem / perpage);
		tmp_int=totalitem%perpage ;
		if(tmp_int > 0) totalpage = totalpage + 1 ;	
		pagefirst = 1 ;
		if (totalpage > pagenumber) pagelast = pagenumber ;
		else pagelast = totalpage ;
		callpage(1);
	}
	
	public void resetall() throws IOException, SQLException
	{
		
			perpage = 10 ;
			totalpage = 0 ;
			pagebegin = 0 ;
			pageend = 0 ;
			pagefirst = 0 ;
			pagelast = 0 ;
			pagenumber = 10 ;
			tmp_int = 0 ;
	}	
	
	public int showPagebegin()
	{
		return pagebegin;
	}
	
	public int showPageend()
	{
		return pageend ;
	}
	
	public int showTotalpage()
	{
		return totalpage ;
	}
	
	public void gotoPage(int n) throws IOException
	{
		if (n <= 0) n = 1;
		callpage(n);
	}

	public void setPagenumber(int n) throws IOException, SQLException
	{
		pagenumber = n ;
	}
	
	public int PagetagBegin() throws IOException, SQLException
	{
		return pagefirst ;
	}

	public int PagetagEnd() throws IOException, SQLException
	{
		return pagelast ;
	}
	
	public void JumpPage(int n, int p) throws IOException, SQLException
	{
		// n -- current page tag , p -- next = 1 , prev = -1
		tmp_int = (int)(n / pagenumber) + p ;
		if (tmp_int <= 0) tmp_int = 0;
		tmp1 = tmp_int * pagenumber + 1 ;
		if (tmp1 > totalpage) {
			pagefirst = totalpage % pagenumber ;
			if (pagefirst == 0) pagefirst = totalpage - pagenumber + 1 ;
			else pagefirst = totalpage - pagefirst + 1 ;
			//pagefirst = (tmp-1) * pagenumber + 1 ;
			tmp1 = pagefirst ;
		} else pagefirst = tmp1 ;
		tmp1 = tmp1 + pagenumber - 1 ;
		if (tmp1 > totalpage) pagelast = totalpage ;
		else pagelast = tmp1 ;
	}
	
	private void callpage(int n) throws IOException
	{
		if(totalitem<=perpage){
			pagebegin = 0 ;
			pageend = totalitem ;
		}else{
			tmp_int = n * perpage;
			if(totalitem >= tmp_int){
				pagebegin = (n-1) * perpage ;
				pageend = (n * perpage) ; 
			}else{
				pagebegin = perpage * (int)(totalitem / perpage);
				pageend = totalitem ;
			}
		}
	}











/**
 * 判斷當前時間是否在[startTime, endTime]區間，注意時間格式要一致
 * 
 * @param nowTime 當前時間
 * @param startTime 開始時間
 * @param endTime 結束時間
 * @return
 * @author jqlin
 */









} //end of class
