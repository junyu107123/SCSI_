package scsi.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import scsi.demo.model.User;
import scsi.demo.repository.UserRepository;
import scsi.demo.scsi.CaseData;
import scsi.demo.security.*;
import scsi.demo.service.UserService;

/**
 * 登录失败回调
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public CaseData cst;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		
		String uid = request.getParameter("userid");
		System.out.println("user fail = "+uid);
//		try {
//		User user = userService.findUserByUserid(uid);
//		String uuid = user.getUserid();
//			System.out.println("uuid= "+uuid);
//		}catch(Exception e) {
//			System.out.println("uid e :"+e.toString());
//		}
		
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//      response.getWriter().write(JSONUtil.toJsonStr(map));
		response.getWriter().write(exception.getMessage().toString());
		request.getSession().setAttribute("userid", uid);
		

		if(exception.getMessage().contains("Bad")) {
		redirectStrategy.sendRedirect(request, response, "/login2?error=1");
		}else {
			redirectStrategy.sendRedirect(request, response, "/login2?error=2");
		}
	}
}