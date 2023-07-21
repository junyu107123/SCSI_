package scsi.demo.scsi;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class getData extends Utility
{
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	PreparedStatement pst =null;
	PreparedStatement pst1 =null;
	PreparedStatement pst2 =null;
	private int rsIndex = -1;
	String get_name = "" ;
	String get_type = "" ;
	int get_name_length = 0 ;
	String get_ip = "" ;
	String get_user = "" ;
	String get_session = "" ;
	String get_jsp = "" ;
	int sync_flag = 0 ;
	String this_keyvalue = "" ;
	String this_keytype = "" ;
	String tbnames = "";
	String tbfield = "";
	String sql="";
	String s="";
	
	List<List<String>> al = new ArrayList<List<String>>();
	
public static void main(String[] args){}

private String[] cmd ;
private String seek_db = "" ;
private int rec_num = 0 ;
//private String cmd[]={"sysid","show_title","show_words","para_words"};
	
public getData() {}

public void closeall()throws IOException, SQLException
{
	super.closeall();	
	if(rs!=null)	rs.close();
	if(rs1!=null)	rs1.close();
	if(rs2!=null)	rs2.close();
}




public void setKeyValue(String st) throws IOException, SQLException
{
	this_keyvalue = st ;
}


public void init(String st) throws IOException, SQLException
{
	
	seek_db = st ;
	//System.out.println("seek_db="+seek_db);
	DatabaseMetaData meta = conn.getMetaData();
  rs2 = meta.getColumns(null, null, seek_db , null);
  get_name = "" ;
  get_type = "" ;
  while (rs2.next()) {
    if (get_name.equals("")){
    	get_name += rs2.getString("COLUMN_NAME");
    	get_type += rs2.getString("TYPE_NAME");
    }else{
  		get_name += ";" + rs2.getString("COLUMN_NAME");
    	get_type += ";" + rs2.getString("TYPE_NAME");
  	}
  }
  cmd = get_name.split(";");
  get_name_length = cmd.length;
  al.clear();
}

public ArrayList<?> getThisData() throws IOException, SQLException
{
	return (ArrayList<?>) al.get(0);
}



public void init(String st, String st1) throws IOException, SQLException
{
	seek_db = st ;
	DatabaseMetaData meta = conn.getMetaData();
  rs2 = meta.getColumns(null, null, seek_db , null);
  get_name = "" ;
  while (rs2.next()) {
    if (get_name.equals("")){
    	get_name += rs2.getString("COLUMN_NAME");
    }else{
  		get_name += ";" + rs2.getString("COLUMN_NAME");
  	}
  }
  get_name += ";" + st1;
  cmd = get_name.split(";");
  get_name_length = cmd.length;
  al.clear();
}

public String get_type() throws IOException, SQLException
{
	return get_type ;
}

public String get_field() throws IOException, SQLException
{
	return get_name ;
}

public String get_field(int n)throws IOException, SQLException
{
	String retcmd = "" ;
	if (n >= 0 && n < get_name_length){
		retcmd = cmd[n] ;
	}
	return retcmd ;
}

public void queryMeAll() throws IOException, SQLException
{
	rs = queryData("select * from "+seek_db);
	rec_num = showCount();
	rs.beforeFirst();
	rs.next();
	for( int i = 0 ; i < rec_num ; i++ ) { 
		List<String> list = new ArrayList<String>();
    al.add( list );
    for( int j = 0 ; j < cmd.length ; j++ ) { 
			list.add(getNotNull(rs.getObject(cmd[j])));
    }
    rs.next();
  }
}

public void queryMeAll(String st0) throws IOException, SQLException
{
	rs = queryData("select * from "+seek_db + " "+st0);
	rec_num = showCount();
	rs.beforeFirst();
	rs.next();
	for( int i = 0 ; i < rec_num ; i++ ) { 
		List<String> list = new ArrayList<String>();
    al.add( list );
    for( int j = 0 ; j < cmd.length ; j++ ) { 
			list.add(getNotNull(rs.getObject(cmd[j])));
    }
    rs.next();
  }
}

public String showDataInput(String st, int n) throws IOException, SQLException
{
	String retval = "" ;
	int ch_num;
	for(ch_num=0;ch_num<cmd.length;++ch_num)
    if(st.equals(cmd[ch_num])) break;
	if ((n < rec_num) && (ch_num < cmd.length)){
		ArrayList<?> innerList = (ArrayList<?>) al.get(n);
		retval = innerList.get(ch_num).toString();
	}

	retval = retval.replaceAll("'","&apos;");
	retval = retval.replaceAll("\"","&quot;");

	return retval ;
}

public String ChangeInput(String st) throws IOException, SQLException
{
	String retval = "" ;
	retval = getNotNull(st);
	retval = retval.replaceAll("'","&apos;");
	retval = retval.replaceAll("\"","&quot;");

	return retval ;		
}

public void queryMe2(String st) throws IOException, SQLException
{
	//System.out.print(st);
	goQueryBySql(st);
	//recordQuery(st);
}


public void queryMe1(String st, String st1) throws IOException, SQLException
{
	goQuery(st,st1);
	//recordQuery(st,st1);
}

public void queryMe(String st) throws IOException, SQLException
{
	//System.out.println(st);
	goQuery(st);
	//recordQuery(st);
 }

public void queryMe(String st, String st1) throws IOException, SQLException
{
	goQuery(st);
	//recordQuery(st);
	recordKeyword(st1);	
}

public void queryMeSingle(String st,String tb) throws IOException, SQLException
{
	//goQuery(st);
	//recordQuery(st);
	rs = queryData("select * from "+tb + " where "+st);
	//System.out.print("select * from "+tb + " where "+st);
	
	rec_num = showCount();
	rs.beforeFirst();
	rs.next();	
 }

public String showDataSingle(String st,int n) throws IOException, SQLException
{
	String retval = "" ;
	
	retval = rs.getString(st);

	return retval ;
}

public ResultSet queryMeResultset(String st,String tb) throws IOException, SQLException
{
	//System.out.print(st);
	//goQuery(st);
	//recordQuery(st);
	rs = queryData("select * from "+tb + " where "+st);
	rec_num = showCount();
	rs.beforeFirst();
	rs.next();	
	
	return rs;
}

public ResultSet queryMeResultsetSql(String st) throws IOException, SQLException
{
	//System.out.print(st);
	//goQuery(st);
	//recordQuery(st);
	rs = queryData(st);
	rec_num = showCount();
	rs.beforeFirst();
	rs.next();	
	
	return rs;
}


public void KeywordMe(String st) throws IOException, SQLException
{
	recordKeyword(st);
}

public void updateData(String st) throws IOException, SQLException
{
	try {
		Thread.sleep(1);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	super.updateData(st);
	//recordUpdate(st);
	//saveToSyncList(st);
}

public void updateData(String st, String st1) throws IOException, SQLException
{
	this_keyvalue = st1 ;
	super.updateData(st);
	//recordUpdate(st);
	//saveToSyncList(st);
}

//	updateData("update coupon_prize set prize_takeaway = prize_takeaway - 1 , prize_notes = CONCAT_WS(CHAR(10 using utf8), 'give_up_"+m+"_"+st+"_"+st1+";"+todaytime()+"', prize_notes) where sysid = "+now_prize_id+" ");	

public String showData(String st, int n) throws IOException, SQLException
{
	String retval = "" ;
	int ch_num;
	for(ch_num=0;ch_num<cmd.length;++ch_num)
    if(st.equals(cmd[ch_num])) break;
	if ((n < rec_num) && (ch_num < cmd.length)){
		ArrayList<?> innerList = (ArrayList<?>) al.get(n);
		retval = innerList.get(ch_num).toString();
	}
	return retval ;
}


public int SeekCount() throws IOException, SQLException
{
	return rec_num ;
}

private void goQueryBySql(String st) throws IOException, SQLException
{
	
	rs = queryData(st);
	rec_num = showCount();
	rs.beforeFirst();
	rs.next();
	for( int i = 0 ; i < rec_num ; i++ ) { 
		List<String> list = new ArrayList<String>();
    al.add( list );
    for( int j = 0 ; j < cmd.length ; j++ ) { 
			list.add(getNotNull(rs.getObject(cmd[j])));
    }
    rs.next();
  }
}

private void goQuery(String st) throws IOException, SQLException
{
	rs = queryData("select * from "+seek_db + " where "+st);
	//System.out.println("select * from "+seek_db + " where "+st);
	rec_num = showCount();
	rs.beforeFirst();
	rs.next();
	for( int i = 0 ; i < rec_num ; i++ ) { 
		List<String> list = new ArrayList<String>();
    al.add( list );
    for( int j = 0 ; j < cmd.length ; j++ ) { 
			list.add(getNotNull(rs.getObject(cmd[j])));
    }
    rs.next();
  }
}

private void goQuery(String st, String st1) throws IOException, SQLException
{
	rs = queryData("select * ,"+st1+" from "+seek_db + " where "+st);
	rec_num = showCount();
	rs.beforeFirst();
	rs.next();
	for( int i = 0 ; i < rec_num ; i++ ) { 
		List<String> list = new ArrayList<String>();
    al.add( list );
    for( int j = 0 ; j < cmd.length ; j++ ) { 
			list.add(getNotNull(rs.getObject(cmd[j])));
    }
    rs.next();
  }
}






private void recordKeyword(String st) throws IOException, SQLException
{
  String stw = getNotNull(st);
  String keyme = "" ;
  this_keyvalue = todaytime2();
  if (!stw.equals("")){
try{
		keyme = "insert into keyword_log (sysid, log_date, log_keyword, log_user, log_session, log_ip) values ('"+this_keyvalue+"','"+today()+"','"+stw+"', '"+get_user+"' , '"+get_session+"', '"+get_ip+"')";
		super.updateData(keyme);
		//recordUpdate(keyme);
}catch (IOException e) 
{
	stw = getNotNull(stw);
	keyme = "insert into keyword_log (sysid, log_date, log_keyword, log_user, log_session, log_ip) values ('"+this_keyvalue+"','"+today()+"','"+stw+"', '"+get_user+"' , '"+get_session+"', '"+get_ip+"')";
	super.updateData(keyme);
	//recordUpdate(keyme);	
}finally{
	//saveToSyncList(keyme);
}
}

}



} //end of class