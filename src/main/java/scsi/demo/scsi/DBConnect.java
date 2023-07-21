package scsi.demo.scsi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DBConnect  {

	 
	public java.sql.Connection getDBConnect() throws IOException, SQLException, NamingException{

		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/scsi");
		Connection conn = ds.getConnection();
		return conn;	
	}
	
}
