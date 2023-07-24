package scsi.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import scsi.demo.filter.LoginFilter;
import scsi.demo.repository.UserRepository;
import scsi.demo.security.CustomUserDetailsService;
import scsi.demo.security.MyAuthenticationFailureHandler;
import scsi.demo.security.MyAuthenticationSuccessHandler;
import scsi.demo.security.VerifyCodeAuthenticationProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Autowired
	UserRepository userRepository;

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {

//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("admin").password("a").roles("admin").build());
//		manager.createUser(User.withUsername("briton").password("1qaz2wsx").roles("user").build());
//		return manager;
		return new CustomUserDetailsService();
	}

//	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(verifyCodeAuthenticationProvider());
    }

	@Bean
	VerifyCodeAuthenticationProvider verifyCodeAuthenticationProvider() {
		VerifyCodeAuthenticationProvider provider = new VerifyCodeAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		ProviderManager manager = new ProviderManager(verifyCodeAuthenticationProvider());
		return manager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		LoginFilter loginFilter = new LoginFilter(LOGIN_PAGE, useImageCaptcha);
		http.csrf().disable(); // 禁用 csrf 保护
		http.authorizeRequests() // 开启配置
				// 验证码接口放行
				.antMatchers("/verify-code", "/","/allo").permitAll()
				.antMatchers("/scsi/verify-code", "/","/scsi/allo").permitAll()
				.anyRequest() // 其他请求
				.authenticated()// 验证 表示其他请求需要登录才能访问
				.and()
				.formLogin()
				.loginPage("/") // 登录页面
				.loginProcessingUrl("/main") // 登录接口，此地址可以不真实存在
				.usernameParameter("userid") // 用户名字段
				.passwordParameter("password") // 密码字段
				.successHandler(new MyAuthenticationSuccessHandler())
				.failureHandler(new MyAuthenticationFailureHandler())
//				.permitAll() // 上述 login.html 页面、/auth/login接口放行
//				.and()
//				.logout()
//				.logoutUrl("/logout")
//				.logoutSuccessUrl("/")
//				.permitAll()
				;
	}
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/js/**","/css/**","/images/**","/used_scable/**");
	}
}
