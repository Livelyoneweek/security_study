package study.security.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.security.entity.User;
import study.security.repository.UserRepository;

import java.util.Optional;

/**
 * 시큐리티 설정에서 .loginProcessingUrl("/login");
 *  /login 설정이 오면 자동으로 UserDetailsService 타입에 IoC 되어있는(Bean 찾아서) loadUserByUsername 함수가 실행됌!!
 */

// 시큐리티 session(내부 Authentication(내부 UserDetails))
@Service //메모리 띄움
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username 로그인 시도={}",username);
        Optional<User> findUser = userRepository.findByUsername(username);

//        if (findUser.isPresent()) {
//            return new PrincipalDetails(findUser.get());
//        }
//        return null;
        return findUser.map(PrincipalDetails::new).orElse(null);
    }
}
