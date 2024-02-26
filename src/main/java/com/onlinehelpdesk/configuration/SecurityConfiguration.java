package com.onlinehelpdesk.configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.onlinehelpdesk.services.NhanvienService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration{
	
@Autowired
	private NhanvienService nhanvienService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		
		return http.cors(cor -> cor.disable())
					.csrf(cs -> cs.disable())
					.authorizeHttpRequests(auth -> {
						auth
						.requestMatchers("/").permitAll()
	                    .requestMatchers("/nhanvien/login").permitAll()

	                    .requestMatchers("/nhanvien/update/**").permitAll()
	                    .requestMatchers("/css/**").permitAll()
	                    //.requestMatchers("/fonts/**").permitAll()
	                    .requestMatchers("/images/**").permitAll()
	                    .requestMatchers("/libs/**").permitAll()
	                    .requestMatchers("/scss/**").permitAll()
	                    .requestMatchers("/js/**").permitAll()
	                    .requestMatchers("/nhanvien/add/**").hasRole("ADMIN")
	                    .requestMatchers("/nhanvien/listyeucau").hasRole("ADMIN")
	                    .requestMatchers("/nhanvien/register").hasRole("ADMIN")
						.requestMatchers("/nhanvien/index").hasRole("ADMIN")
						.requestMatchers("/support/**").hasRole("SUPPORTER")
						.requestMatchers("/employee/**").hasRole("EMPLOYEE")
						.anyRequest().authenticated();

					})
					.formLogin(formLogin -> {
						formLogin.loginPage("/nhanvien/login")
						.loginProcessingUrl("/nhanvien/process-login")
						.usernameParameter("username")
						.passwordParameter("password")
						.successHandler(new AuthenticationSuccessHandler() {
							
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) throws IOException, ServletException {
								System.out.println(authentication.getName());
								
								//tạo 1 collection các quyền được xác thực
								Collection<GrantedAuthority> roles= (Collection<GrantedAuthority>) authentication.getAuthorities();
								System.out.println("roles");
								
								// Map<quyền, đường dẫn của quyền đó sau khi đăng nhập> 
								//chứa các quyền được xác thực và url của quyền đó sau khi đăng nhập
								Map<String, String> urls= new HashMap<>();
								urls.put("ROLE_ADMIN", "/nhanvien/index");
								urls.put("ROLE_SUPPORTER", "/support/index");
								urls.put("ROLE_EMPLOYEE", "/employee/index");
								String url= "";								

								for(GrantedAuthority role:roles)
								{
									System.out.println("role: "+role.getAuthority());
									if(urls.containsKey(role.getAuthority()))
									{
										url= urls.get(role.getAuthority());
										break;
									}
								}
								System.out.println("url: "+ url);
								response.sendRedirect(url);
							}
						})
						.failureUrl("/nhanvien/login?error");
					})
					.logout(logout->{logout.logoutUrl("/nhanvien/logout").logoutSuccessUrl("/nhanvien/login?logout");
						})
					.exceptionHandling(ex->{
						ex.accessDeniedPage("/nhanvien/accessdenied");
					})
					.build();
					
	}
	@Autowired
	public void configureglobal(AuthenticationManagerBuilder builder) throws Exception
	{
		builder.userDetailsService(nhanvienService);
	}
	
	@Bean //doi tuong tham gia vao qua trinh xy ly
	public BCryptPasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
	}

}
