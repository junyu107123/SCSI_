package scsi.demo.scsi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DBConnect  {

	 
//	public java.sql.Connection getDBConnect() throws IOException, SQLException, NamingException{
//
//		Context initContext = new InitialContext();
//		Context envContext = (Context) initContext.lookup("java:comp/env");
//		DataSource ds = (DataSource) envContext.lookup("jdbc/scsi");
//		Connection conn = ds.getConnection();
//		return conn;	
//	}
//	private static String url="jdbc:mariadb://localhost:3307/scsi?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8&allowPublicKeyRetrieval=true";
	private static String url="jdbc:mysql://localhost:3306/scsi?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8&allowPublicKeyRetrieval=true";
	public static Connection getDBConnect() throws SQLException , Exception{
//		Class.forName("org.mariadb.jdbc.Driver");
//		Connection conn =DriverManager.getConnection(url, "root", "p0o9i8u7");
	
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection conn =DriverManager.getConnection(url, "root", "p0o9i8u7");
	return conn;
	}
	
}
