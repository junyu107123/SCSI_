package scsi.demo.scsi;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class QueryDB extends DBConnect
{
	static final String MYSQL_DB_TYPE_NAME = "MySQL";
    static final String SQL_ORDER_BY = " order by ";
	Connection conn = null;
	Statement stmt = null ;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	PreparedStatement pst =null;
	PreparedStatement pst1 =null;
	PreparedStatement pst2 =null;
	private String nowtime = null;
	String sql="";
	int rs_count = 0 ;
	
public static void main(String[] args){}
	
public QueryDB() {
	try
		{
			conn = getDBConnect();
//		System.out.println("DB conn="+conn);
			stmt = conn.createStatement();
			pst =conn.prepareStatement(sql);
		}
		catch(Exception e)
		{
			//System.out.print(e);
		}
	
}

public void closeall() throws IOException, SQLException
{
//	System.out.println("DBC conn="+conn);
	 try {
         if (rs != null) {
             rs.close();
             rs = null;
//             System.out.println("rs ="+rs);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
	 
	 try {
         if (rs1 != null) {
             rs1.close();
             rs1 = null;
//             System.out.println("rs ="+rs);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
	 
	 try {
         if (rs2 != null) {
             rs2.close();
             rs2 = null;
//             System.out.println("rs ="+rs);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }

     try {
         if (pst != null) {
             pst.close();
             pst = null;
//             System.out.println("pst ="+pst);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     
     try {
         if (pst1 != null) {
             pst1.close();
             pst1 = null;
//             System.out.println("pst ="+pst);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     
     try {
         if (pst2 != null) {
             pst2.close();
             pst2 = null;
//             System.out.println("pst ="+pst);
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }

     try {
    	 if(stmt!=null) {
         stmt.close();
         stmt = null;
//        System.out.println("stmt ="+stmt);
    	 }
     } catch (SQLException e) {
         e.printStackTrace();
     }

     try {
    	 if(conn!=null) {
         conn.close();
         conn = null;
//         System.out.println("conn="+conn);
    	 }
     } catch (SQLException e) {
         e.printStackTrace();
     }
}	

public int showCount() throws IOException, SQLException
{
	return rs_count ;
}

/**
 * 執行PreparedStatementQuery
 * @return ResultSet
 * @throws SQLException
 */
public ResultSet doPreparedStatementQuery () throws SQLException {
    if (this.rs != null && !this.rs.isClosed()) { this.rs.close(); }
    if (this.pst != null && !this.pst.isClosed()) {
        this.rs = this.pst.executeQuery();
        this.rs_count = getQueryCount();
    }
    return this.rs;
}

public ResultSet doPreparedStatementQuery4MsSQL () throws SQLException {
    if (this.rs != null && !this.rs.isClosed()) { this.rs.close(); }
    if (this.pst != null && !this.pst.isClosed()) {
        this.rs = this.pst.executeQuery();
    }
    return this.rs;
}

public int getQueryCount () {
    int count = 0;
    try {
            while(this.rs.next()) {
                count++;
            }
            this.rs = this.pst.executeQuery();
            this.rs.last();
            count = this.rs.getRow();
            this.rs.beforeFirst();
    } catch (Exception e) {
       // LogUtil.logger.warning(e.toString());
    }
    return count;
}

public ResultSet queryData(String st) throws IOException, SQLException
{
		rs_count = 0 ;
		rs = stmt.executeQuery(st);
		rs.last();
		rs_count = rs.getRow();
		return rs ;
}

public void updateData(String st) throws IOException, SQLException
{
	//System.out.println(st);		
	stmt.executeUpdate(st) ;
}
public String todaytime2() throws IOException, SQLException {
	Calendar rightNow = Calendar.getInstance();
	nowtime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(rightNow.getTime());
	return nowtime;
}

} //end of class