package scsi.demo.scsi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jasypt.encryption.StringEncryptor;

import scsi.demo.model.EncryptorConfig;

public class DBConnect  {
	
	private static String url="jdbc:mysql://localhost:3306/scsi?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8&allowPublicKeyRetrieval=true";
	public static Connection getDBConnect() throws SQLException , Exception{
	Enco eno = new Enco();
	Class.forName("com.mysql.cj.jdbc.Driver");
//	String aa = eno.decod("cDBvOWk4dTc="); //p0
	String aa = eno.decod("MXFhejJ3c3g="); //iq
	Connection conn = DriverManager.getConnection(url, "root", aa);
	return conn;
	}
	
}
