package com.rubypaper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rubypaper.config.filter.JWTAuthenticationFilter;
import com.rubypaper.config.filter.JWTAuthorizationFilter;
import com.rubypaper.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration // XML 기반의 설정을 대신하여 Java Config 방식으로 설정을 정의할 때 사용
@EnableWebSecurity //스프링 시큐리티의 웹 보안 기능을 활성화
public class SecurityConfig{
			
	 @Autowired
	 private AuthenticationConfiguration authenticationConfiguration;
	 
	 @Autowired
	 private MemberRepository memberRepo;
	 
	 private CorsConfigurationSource corsSource() {
		 CorsConfiguration config = new CorsConfiguration();
		 config.addAllowedOriginPattern(CorsConfiguration.ALL) ;
		 config.addAllowedMethod(CorsConfiguration.ALL) ;
		 config.addAllowedHeader(CorsConfiguration.ALL);
		 config.setAllowCredentials(true);
		 // 요청을 허용할 서버
		// 요청을 허용할 Method
		 // 요청을 허용할 Header
		// 요청/응답에 자격증명정보 포함을허용
		// true인 경우 addAllowedOrigin(“*”)는 사용 불가 ➔ Pattern으로 변경
		config.addExposedHeader(CorsConfiguration.ALL);
		 // Header에 Authorization을 추가하기 위해서는 필요
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		 source.registerCorsConfiguration("/**", config);
		 return source;
		 }

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.cors(cors->cors.configurationSource(corsSource()));
		
		http.authorizeHttpRequests(security->security
				.requestMatchers("/member/**").hasRole("MEMBER")				
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/board/insertBoard").authenticated()
				.requestMatchers("/board/updateBoard").authenticated()
				.requestMatchers("/board/deleteBoard").authenticated()
				.anyRequest().permitAll());
		
		http.csrf(cf->cf.disable());
		
		http.formLogin(form->form.loginPage("/login")
								  .defaultSuccessUrl("/loginSuccess",false));
		http.exceptionHandling(ex->ex.accessDeniedPage("/accessDenied"));
		
		http.logout(logout->logout.invalidateHttpSession(true)
								  .deleteCookies("JSESSIONID")
								  .logoutSuccessUrl("/login"));
		http.headers(hr->hr.frameOptions(fo->fo.disable()));
				
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), memberRepo));
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepo), AuthorizationFilter.class);
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

