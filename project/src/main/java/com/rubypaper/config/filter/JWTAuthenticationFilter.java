package com.rubypaper.config.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;//인증객체	
	private final MemberRepository memRepo;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		// request에서 json 타입의 [username/password]를 읽어서 Member 객체를 생성한다.
		ObjectMapper mapper = new ObjectMapper();
		try {
			//본문(body)을 바이트 스트림으로 반환,JSON을 Member 객체로 변환
			Member member = mapper.readValue(request.getInputStream(), Member.class);
			 // Security에게 자격 증명 요청에 필요한 객체 생성
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUserid(),member.getPassword());
			 // 인증 진행-> UserDetailsService의 loadUserByUsername에서 DB로부터 사용자 정보를 읽어온 뒤
			// 사용자 입력 정보와비교한뒤자격증명에성공하면Authenticaiton객체를 만들어서리턴한다.
			return authenticationManager.authenticate(authToken);
		}catch(Exception e) {
			
			log.info(e.getMessage());// “자격 증명에 실패하였습니다.” 로그 출력
			response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 자격 증명에 실패하면응답코드리턴
		}
		
		return null;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		// 자격 증명이 성공하면loadUserByUsername에서 만든 객체가 authResult에 담겨져 있다.
		User user = (User)authResult.getPrincipal();
		System.out.println("auth:"+ user); // user 객체를 콘솔에 출력해서 확인
		
		Member member = memRepo.findByUserid(user.getUsername()).get();
		// username으로 JWT를 생성해서 Response Header - Authorization에 담아서 돌려준다.
		 // 이것은 하나의 예시로서필요에따라추가정보를담을수있다.
		String token = JWT.create().withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))
								   .withClaim("userid",user.getUsername())//userid를 말함
								   .sign(Algorithm.HMAC256("edu.pnu.jwt"));
		response.addHeader(HttpHeaders.AUTHORIZATION,"Bearer " +token);
		response.getWriter().write(member.getNickname());
		response.setStatus(HttpStatus.OK.value());
	}
	
}
