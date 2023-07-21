package scsi.demo.wisoft;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnect  {
	public java.sql.Connection getDBConnect() throws IOException, SQLException{
		Connection conn=null;
	try{    
			Context ctx = new InitialContext();      
	  		String strLookup = "java:comp/env/jdbc/simulation"; 
	  		DataSource ds =(DataSource) ctx.lookup(strLookup);
			conn = ds.getConnection();
	}catch (Exception e){
		conn= null;
		System.out.print(e.toString());
	}
	return conn;
	}
}
