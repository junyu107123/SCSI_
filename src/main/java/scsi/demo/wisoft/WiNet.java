package scsi.demo.wisoft;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class WiNet
{
	
	public static void main(String[] args){}
	

	public WiNet() {
		 }

public static String sentHttpPostRequest(String url, String args) throws IOException {
	//System.out.print("sentHttpPostRequest..."+url+";"+args);
    URLConnection connection = new URL(url).openConnection();
    // by default, connection with enable input, but won't enable output
    connection.setDoOutput(true);
    connection.setDoInput(true);
    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
    try {
    out.write(args);
    out.flush();
    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        safeCloseOutputStream(out);
    }
   	InputStream is = connection.getInputStream();
	//JDK 5 up
	Scanner scanner = new Scanner(is, "UTF-8");
	String stx = scanner.useDelimiter("\\A").next();

    is.close();
    return stx.trim();
}

public static void safeCloseOutputStream(OutputStreamWriter out) {
    if (out != null) {
        try {
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

} //end of class