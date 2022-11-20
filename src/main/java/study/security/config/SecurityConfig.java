package study.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //메모리에 떠야하므로
@EnableWebSecurity //활성화 시킴, 스프링 시큐리티 필터가 스프링 필터체인에 등록됌
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화, 컨트롤러 확인, prePostEnabled는 preAuthorize를 활성화
public class SecurityConfig{

    //시큐리티 사용 시 회원가입할 떄 패스워드 암호화 하기 위해 사용
    @Bean
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        return http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 로그인 한 사람만 접근 가능
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 로그인과 권한 있어야 접근가능
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 로그인과 권한 있어야 접근가능
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access/denied")
                .and()
                .formLogin()
                .loginPage("/loginForm") //authenticated, access 에서 걸린 요청은 /login으로 감
                .loginProcessingUrl("/login") // /login 주소 호출되면 시큐리티가 낚아채서 로그인 진행
//                .usernameParameter("username") //디폴트임
                .defaultSuccessUrl("/") //로그인 성공 시 주소
                .and()

                .csrf().disable() // csrf 비활성
                
                .logout() // 로그아웃 기능 작동함
                .logoutUrl("/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동페이지
                .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키 삭제

                .and().build();
    }
}
