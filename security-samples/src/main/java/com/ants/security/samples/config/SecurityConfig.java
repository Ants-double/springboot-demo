package com.ants.security.samples.config;

import com.ants.security.samples.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${server.servlet.context-path}")
	private String servletPath;

	@Autowired
	private MyUserDetailsServiceImpl userDetailsService;
	/**
	 * 登出成功的处理
	 */
	@Autowired
	private LoginFailureHandler loginFailureHandler;
	/**
	 * 登录成功的处理
	 */
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	/**
	 * 登出成功的处理
	 */
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	/**
	 * 未登录的处理
	 */
	@Autowired
	private AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint;
	/**
	 * 超时处理
	 */
	@Autowired
	private InValidSessionHandler invalidSessionHandler;
	/**
	 * 顶号处理
	 */
	@Autowired
	private SessionInformationExpiredHandler sessionInformationExpiredHandler;
	/**
	 * 登录用户没有权限访问资源
	 */
	@Autowired
	private LoginUserAccessDeniedHandler loginUserAccessDeniedHandler;

	@Autowired
	private MyAuthenticationProvider myAuthenticationProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(myAuthenticationProvider).userDetailsService(userDetailsService);
	}


	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
	@Override
	public void init(WebSecurity web) throws Exception {
		super.init(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.disable();
		// http.headers().frameOptions().disable();

		http.authorizeRequests()
				// 放行接口
				.antMatchers(AUTH_LIST).permitAll()
				.mvcMatchers(AUTH_LIST).servletPath(servletPath).permitAll()
				//.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				// .mvcMatchers("/assignment/*").servletPath(servletPath).access("hasRole('ROLE_admin')")

				//.antMatchers("/assignment/**").hasAnyAuthority("ad")
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated()
				// 异常处理(权限拒绝、登录失效等)
				.and().exceptionHandling()
				//匿名用户访问无权限资源时的异常处理 AccessDeniedException
				.authenticationEntryPoint(anonymousAuthenticationEntryPoint)
				.and().exceptionHandling()
				//登录用户没有权限访问资源
				.accessDeniedHandler(loginUserAccessDeniedHandler)

				// 登入 //允许所有用户
				.and().formLogin().loginProcessingUrl("/user/login").usernameParameter("userName")
				.passwordParameter("verification").successForwardUrl("/user/success").permitAll()
				//登录成功处理逻辑
				.successHandler(loginSuccessHandler)
				//登录失败处理逻辑
				.failureHandler(loginFailureHandler)
				// 登出//允许所有用户
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/loginOut"))
				.logoutSuccessUrl("/index.html").deleteCookies("auth_code","JSESSIONID","ForeinSessionID")
				.logoutUrl("/user/loginOut")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				//登出成功处理逻辑
				.logoutSuccessHandler(logoutSuccessHandler)
				.deleteCookies(RestHttpSessionIdResolver.AUTH_TOKEN)



				//.invalidSessionStrategy(invalidSessionHandler)
				//** 处理身份验证表单提交。 授予权限 */
				.and()
				//.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				//** 处理HTTP请求的BASIC授权标头，然后将结果放入SecurityContextHolder 。 */
				//.addFilter(new JwtAuthorizationFilter(authenticationManager()))
				// 会话管理                             // 超时处理
				//.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.invalidSessionUrl("/user/force_login")

				//同一账号同时登录最大用户数
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false)
				.expiredUrl("/user/force_login")
				//.sessionRegistry(sessionRegistry)
				// 顶号处理

				.expiredSessionStrategy(sessionInformationExpiredHandler)
		;
	}

	private static final String[] AUTH_LIST = {
			"/v2/api-docs",
			"/configuration/ui",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**",
			"/user/verification",
			"/user/login"
	};
}
