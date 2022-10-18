package dongyang.spmis.config;

import dongyang.spmis.oauth.CustomOauth2UserService;
import dongyang.spmis.security.CustomLoginSuccessHandler;
import dongyang.spmis.security.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Autowired
//    private CorsConfig corsConfig;
//
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//    public SecurityConfig(
//            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
//            JwtAccessDeniedHandler jwtAccessDeniedHandler){
//        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
//        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
//    }

    // 로그인 성공시 호출될 핸들러
    @Autowired
    CustomLoginSuccessHandler customLoginSuccessHandler;

    // Oauth 로그인 활용을 위한 서비스
    @Autowired
    CustomOauth2UserService customOauth2UserService;

    // Oauth 로그인 성공시 호출될 핸들러
    @Autowired
    OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;


    // 스프링 버전이 올라감에 따라서 configure 를 override 하는 방식이 아닌 빈을 등록하는 방식으로 바뀌엇다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**"
                , "/favicon.ico"
                , "/error", "/api/test",
                "/assets/**","/join",
                "/emailcheck","/emailcheck2", "/sendEmail", "/publicprojectlist",
                "/checknumconfirm" );
    }
    // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                        .antMatchers("/login").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/signupPOST").permitAll() // 회원가입
                    .anyRequest()
                        .access("hasRole('ROLE_USER')")
//                .anyRequest().authenticated()

                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .usernameParameter("user_email")
                    .passwordParameter("user_pw")
                    .loginProcessingUrl("/login")
                .successHandler(customLoginSuccessHandler)
                .and()
                    .oauth2Login()
                    .loginPage("/login")
                .redirectionEndpoint()
//                .baseUri("login/oauth2/**")
                .and()
                    // 구글 로그인이 완료된 이후의 후처리가 필요함. 1. 코드받기(인증), 2.엑세스 토큰(권한) 3. 사용자 프로필 정보 가져오기
                    // 4-1. 그 정보르 토대로 회원가입을 자동으로 시킴
                    // 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> 집주소 백화점 몰
                    // 엑세스 토큰과 사용자 프로필 정보를 받는다
                    .userInfoEndpoint()
                    .userService(customOauth2UserService)

                        .and()
                .successHandler(oAuth2AuthenticationSuccessHandler);
        return httpSecurity.build();

    }


    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest>
    authorizationRequestRepository() {

        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

}
