package scsi.demo.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    
    private boolean useImageCaptcha;

    public LoginFilter(String defaultUrl, boolean useImageCaptcha) {
        super(defaultUrl);
        this.useImageCaptcha = useImageCaptcha;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String uri = httpServletRequest.getServletPath();
        String method = httpServletRequest.getMethod();
        if (this.useImageCaptcha && uri.equals("/login") && method.equalsIgnoreCase("POST")) {
            if (session.getAttribute("verifyCode").toString().equalsIgnoreCase(request.getParameter("imageCaptchaCode"))) {
                session.removeAttribute("verifyCode");
            } else {
                session.setAttribute("error", "verifyCodeError");
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
                return;
            }
        }
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        return null;
    }
}