package scsi.demo.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * 验证码验证器
 */
public class VerifyCodeAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获得请求验证码值
        String code = req.getParameter("insrand");
        // 获得 session 中的 验证码值
        HttpSession session = req.getSession();
        String sessionVerifyCode = (String) session.getAttribute("verify_code");
        if (StringUtils.isEmpty(code)){
            throw new AuthenticationServiceException("驗證碼不能為空!");
        }
        if(StringUtils.isEmpty(sessionVerifyCode)){
            throw new AuthenticationServiceException("請重新刷新驗證碼!");
        }
        if (!code.toLowerCase().equals(sessionVerifyCode.toLowerCase())) {
            throw new AuthenticationServiceException("驗證碼錯誤!");
        }
        // 验证码验证成功，清除 session 中的验证码
        session.removeAttribute("verify_code");
        // 验证码验证成功，走原本父类认证逻辑
        return super.authenticate(authentication);
    }
}