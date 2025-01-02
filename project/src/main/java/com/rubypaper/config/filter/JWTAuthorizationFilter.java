package com.rubypaper.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;
import com.rubypaper.service.SecurityUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	
	// 인가 설정을위해사용자의Role 정보를 읽어들이기위한객체설정
	private final MemberRepository memberRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String srcToken = request.getHeader("Authorization"); // 요청 헤더에서 Authorization을 얻어온다.
		if(srcToken == null || !srcToken.startsWith("Bearer ")) {
			filterChain.doFilter(request, response); // 없거나 “Bearer ”로 시작하지 않는다면 // 필터를 그냥 통과
			return; 
		}
		String jwtToken = srcToken.replace("Bearer ", "");// 토큰에서 “Bearer ”를 제거
		
		 // 토큰에서 username 추출
		String userid = JWT.require(Algorithm.HMAC256("edu.pnu.jwt")).build().verify(jwtToken).getClaim("userid").asString();
		
		Optional<Member> opt = memberRepository.findByUserid(userid);// 토큰에서 얻은 username으로 DB를 검색해서 사용자를 검색
		if(!opt.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Member findmember = opt.get();
		 // DB에서 읽은 사용자정보를이용해서UserDetails 타입의 객체를생성
//		User user = new User(findmember.getUserid(), findmember.getPassword(),
//							 AuthorityUtils.createAuthorityList(findmember.getRole().toString()));
		SecurityUser secuser = new SecurityUser(findmember);
		 // Authentication 객체를 생성 : 사용자명과 권한 관리를 위한 정보를 입력(암호는 필요 없음)
		Authentication auth = new UsernamePasswordAuthenticationToken(secuser, null, AuthorityUtils.createAuthorityList(findmember.getRole().toString()));
		
		// 시큐리티 세션에등록한다.
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		filterChain.doFilter(request, response);
		
	}
}
