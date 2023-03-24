package com.ddalggak.finalproject.global.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ddalggak.finalproject.global.jwt.JwtAuthFilter;
import com.ddalggak.finalproject.global.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {
	private final JwtUtil jwtUtil;

	//
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// h2-console 사용 및 resources 접근 허용 설정
		return (web) -> web.ignoring()
			.requestMatchers(PathRequest.toH2Console())
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 설정
		http.csrf().disable();
		// 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//http.authorizeRequests().anyRequest().authenticated();
		http.authorizeRequests()
			.antMatchers("/api/auth/**")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/user")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/project/**")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/task/**")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/ticket/**")
			.permitAll()
			.antMatchers("/ddal-ggak/docs")
			.permitAll()
			.antMatchers("/ddal-ggak.html")
			.permitAll()

			//                .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
			// JWT 인증/인가를 사용하기 위한 설정
			.and()
			.addFilterBefore(new JwtAuthFilter(jwtUtil),
				UsernamePasswordAuthenticationFilter.class); //    private final JwtUtil jwtUtil; 추가하기!
		http.cors();
		// 로그인 사용
		http.formLogin().permitAll();// 로그인 페이지가 있을 경우 넣기!.loginPage(".api/user/login-page").permitAll();
		// 로그인 실패
		http.exceptionHandling().accessDeniedPage("/api/auth/login");

		http.logout()//.permitAll() // 로그아웃 기능 작동함
			.logoutUrl("Logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
			.logoutSuccessUrl("/api/auth/login") // 로그아웃 성공 후 이동페이지
			.deleteCookies("JSESSIONID", "remember-me");

		return http.build();
	}
	//    protected void cofigure(HttpSecurity http) throws Exception {
	//        // 로그아웃
	//        http.logout()//.permitAll() // 로그아웃 기능 작동함
	//                .logoutUrl("Logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
	//                .logoutSuccessUrl("/api/user/login") // 로그아웃 성공 후 이동페이지
	//                .deleteCookies("JSESSIONID", "remember-me"); // 로그아웃 후 쿠키 삭제
	//                .addLogoutHandler(logoutHandler()) // 로그아웃 핸들러 익명의 객체 넣기
	//                .logoutSuccessHandler(logoutSuccessHandler()); // 로그아웃 성공 후 핸들러
	//    }

	//    private LogoutHandler logoutHandler() {
	//    }
	//
	//    private LogoutSuccessHandler logoutSuccessHandler() {
	//        return null;
	//    }

	// 이 설정을 해주면, 우리가 설정한대로 CorsFilter가 Security의 filter에 추가되어
	// 예비 요청에 대한 처리를 해주게 됩니다.
	// CorsFilter의 동작 과정이 궁금하시면, CorsFilter의 소스코드를 들어가 보세요!
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();

		// 사전에 약속된 출처를 명시
		config.addAllowedOrigin("http://localhost:3000");
		//config.addAllowedOrigin("http://charleybucket.s3-website.ap-northeast-2.amazonaws.com");

		// 특정 헤더를 클라이언트 측에서 사용할 수 있게 지정
		// 만약 지정하지 않는다면, Authorization 헤더 내의 토큰 값을 사용할 수 외없음
		config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

		// 본 요청에 허용할 HTTP method(예비 요청에 대한 응답 헤더에 추가됨)
		config.addAllowedMethod("*");

		// 본 요청에 허용할 HTTP header(예비 요청에 대한 응답 헤더에 추가됨)
		config.addAllowedHeader("*");

		// 기본적으로 브라우저에서 인증 관련 정보들을 요청 헤더에 담지 않음
		// 이 설정을 통해서 브라우저에서 인증 관련 정보들을 요청 헤더에 담을 수 있도록 해줍니다.
		config.setAllowCredentials(true);

		// allowCredentials 를 true로 하였을 때,
		// allowedOrigin의 값이 * (즉, 모두 허용)이 설정될 수 없도록 검증합니다.
		config.validateAllowCredentials();

		// 어떤 경로에 이 설정을 적용할 지 명시합니다. (여기서는 전체 경로)
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}






/*
- **CSRF(사이트 간 요청 위조, Cross-site request forgery)**
    - 공격자가 인증된 브라우저에 저장된 쿠키의 세션 정보를 활용하여 웹 서버에 사용자가 의도하지 않은 요청을 전달하는 것
    - CSRF 설정이 되어있는 경우 html 에서 CSRF 토큰 값을 넘겨주어야 요청을 수신 가능
    - 쿠키 기반의 취약점을 이용한 공격 이기 때문에 REST 방식의 API에서는 disable 가능
    - POST 요청마다 처리해 주는 대신 **CSRF protection 을 disable**
 */