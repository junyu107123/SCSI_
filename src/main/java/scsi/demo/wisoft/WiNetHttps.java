package scsi.demo.wisoft;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.*;
import java.util.*;
import java.net.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class WiNetHttps
{
	static X509TrustManager delegate = null;
		  // default user agent to send requests with
    private final static String USER_AGENT = "Mozilla/5.0";
    // File to save response to
    private final static String RESPONSE_FILE_LOCATION = "D:\\file.html";

    static {
        // this part is needed cause Lebocoin has invalid SSL certificate, that cannot be normally processed by Java
        TrustManager[] trustAllCertificates = new TrustManager[]{
        };

        HostnameVerifier trustAllHostnames = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
            	HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify(hostname, session);
                }
            
        };

        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, trustAllCertificates, new SecureRandom());
            
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
        } catch (GeneralSecurityException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
	
	public static String HttpClientGET(String st) throws Exception
	{
		String retval = "";
		URL url = new URL(st);
		URLConnection connection = url.openConnection();
		try (InputStream in = connection.getInputStream();) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = in.read(buffer)) != -1) {
				output.write(buffer, 0, len); 
			} 
			retval = new String(output.toByteArray(),"utf-8"); 
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		return retval;
	}
	
	/**
     * Make post request for given URL with given parameters and save response into RESPONSE_FILE_LOCATION
     *
     * @param url        HTTPS link to send POST request
     * @param parameters POST request parameters. currently expecting following parameters:
     *                   name, email, phone, body, send
     */
//    public static void makePostRequest(String url, Map<String, String> parameters) {
//        try {
//            ensureAllParametersArePresent(parameters);
//            //we need this cookie to submit form
//            url = new String(url.getBytes("UTF-8"), "ISO-8859-1");
//            String regex = "[`~!@#$%^&*()\\+\\=\\{}|:\"?><【】\\/r\\/n]";
//
//            Pattern pa = Pattern.compile(regex);
//
//            Matcher ma = pa.matcher(url);
//
//            if(ma.find()){
//
//             url = ma.replaceAll("");
//
//            }
//            String initialCookies = getUrlConnection(url, "").getHeaderField("Set-Cookie");
//            HttpsURLConnection con = getUrlConnection(url, initialCookies);
//            String urlParameters = processRequestParameters(parameters);
//            // Send post request
//            sendPostParameters(con, urlParameters);
//            BufferedReader in = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(url)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
//            
//            File outputFile = new File(RESPONSE_FILE_LOCATION);
//            if (!outputFile.exists()) {
//                outputFile.createNewFile();
//            }
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                bw.write(inputLine);
//            }
//            if(in!=null) {
//            	safeclose(in);
//            }
//            if(bw!=null) {
//            	safeclose1(bw);
//            }
//            bw.flush();
//            //print result
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    
//    public String makePostRequestNoFile(String url, Map<String, String> parameters) {
//        StringBuffer buffer = new StringBuffer();
//    	try {
//            ensureAllParametersArePresent(parameters);
//            //we need this cookie to submit form
//            url = new String(url.getBytes("UTF-8"), "ISO-8859-1");
//            String regex = "[`~!@#$%^&*()\\+\\=\\{}|:\"?><【】\\/r\\/n]";
//
//            Pattern pa = Pattern.compile(regex);
//
//            Matcher ma = pa.matcher(url);
//
//            if(ma.find()){
//
//             url = ma.replaceAll("");
//
//            }
//            String initialCookies = getUrlConnection(url, "").getHeaderField("Set-Cookie");
//            HttpsURLConnection con = getUrlConnection(url, initialCookies);
//            String urlParameters = processRequestParameters(parameters);
//            // Send post request
//            sendPostParameters(con, urlParameters);
//            BufferedReader in = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(url)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
//            String str = null;
//            while ((str = in.readLine()) != null) {
//                buffer.append(str);
//            }
//            String result = buffer.toString();
//            if(in!=null) {
//            	safeclose(in);
//            }
//            return result;
//            //print result
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//    }
//    	
//}    	
    public static void safeclose(BufferedReader in) {
		if(in!=null) {
			try {
				in.close();
			}catch (Exception e) {
				
			}
			
		}
	}
    public static void safeclose1(BufferedWriter bw) {
		if(bw!=null) {
			try {
				bw.close();
			}catch (Exception e) {
				
			}
			
		}
	}
    public static void safeclose2(DataOutputStream wr) {
		if(wr!=null) {
			try {
				wr.close();
			}catch (Exception e) {
				
			}
			
		}
	}
    
    /**
     * Send POST parameters to given connection
     *
     * @param con           connection to set parameters on
     * @param urlParameters encoded URL POST parameters
     * @throws IOException
     */
//    private static void sendPostParameters(URLConnection con, String urlParameters) throws IOException {
//        con.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
//        wr.flush();
//        if(wr!=null) {
//        	safeclose2(wr);
//        }
//        
//    }

    /**
     * Create HttpsURLConnection for given URL with given Cookies
     *
     * @param url     url to query
     * @param cookies cookies to use for this connection
     * @return ready-to-use HttpURLConnection
     * @throws IOException
     */
    private static HttpsURLConnection getUrlConnection(String url, String cookies) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();
        cookies = new String(cookies.getBytes("UTF-8"), "ISO-8859-1");
      String regex = "[`~!@#$%^&*()\\+\\=\\{}|:\"?><【】\\/r\\/n]";

      Pattern pa = Pattern.compile(regex);

      Matcher ma = pa.matcher(cookies);

      if(ma.find()){

       cookies = ma.replaceAll("");

      }
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Cookie", cookies);
        return con;
    }

    private static void ensureAllParametersArePresent(Map<String, String> parameters) {
        if (parameters.get("send") == null) {
            parameters.put("send", "Envoyer votre message");
        }
        if (parameters.get("phone") == null) {
            parameters.put("phone", "");
        }
    }

    /**
     * Convert given Map of parameters to URL-encoded string
     *
     * @param parameters request parameters
     * @return URL-encoded parameters string
     */
    private static String processRequestParameters(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        for (String parameterName : parameters.keySet()) {
            sb.append(parameterName).append('=').append(urlEncode(parameters.get(parameterName))).append('&');
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Encode given String with URLEncoder in UTF-8
     *
     * @param s string to encode
     * @return URL-encoded string
     */
    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // This is impossible, UTF-8 is always supported according to the java standard
            throw new RuntimeException(e);
        }
    }
	
} //end of class