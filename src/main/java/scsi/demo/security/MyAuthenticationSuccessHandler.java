package scsi.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import scsi.demo.scsi.CaseData;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
/**
 * 登录成功回调
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	public String jjwt() {
		 Date expireDate = 
		            //設定30min過期
		            new Date(System.currentTimeMillis()+ 30 * 60 * 1000);
		            String jwtToken = Jwts.builder()
		            .setSubject("abc") //我以email當subject
		            .setExpiration(expireDate)
		            //MySecret是自訂的私鑰，HS512是自選的演算法，可以任意改變
		            .signWith(SignatureAlgorithm.HS512,"MySecret")
		            .compact();
//		            System.out.println(jwtToken);
		            return jwtToken.toString();
		 }
		            //直接將JWT傳回
	
	 @Override
     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String jwtToken=jjwt(); 
		String username="";
        HashMap<String, Object> map = new HashMap<>();
       
//        map.put("status", 200);
//        map.put("msg", "認證成功");
        //map.put("token", jjwt());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
//        map.put("user_info", authentication.getPrincipal());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.getWriter().write(JSONUtil.toJsonStr(username));
//        response.getWriter().write(JSONUtil.toJsonStr(jwtToken));
        request.getSession().setAttribute("userid", username);
        response.sendRedirect("main");
//        response.sendRedirect("main");
     }
}