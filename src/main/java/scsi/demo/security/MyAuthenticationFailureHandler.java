package scsi.demo.security;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
/**
 * 登录失败回调
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
	 @Override
     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        exception.printStackTrace();
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 401);
        map.put("msg", "認證失敗");
          map.put("exception", exception.getMessage());
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.getWriter().write(JSONUtil.toJsonStr(map));
//          response.getWriter().write(exception.getMessage().toString());
        
        	response.sendRedirect("login?error");
     }
}