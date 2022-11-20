package study.security.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.security.entity.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 시큐리티가 /login 낚아채서 로그인 진행시킴
 * 로그인 완료되면 시큐리티 session을 만듬 (Security ContextHolder)
 * Security ContextHolder에 들어가는 오브객체는 Authentication 타입의 객체임
 * Authentication 안에 들어갈 객체는 UserDetails 타입의 객체가 들어갈 수 있음
 * UserDetails에 User 정보가 있어야함
 */

//메모리에 강제로 띄울예정
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user=user;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 현재 권한은 user.getRole(),  String 타입 이지만 타입이 정해져있어서 맞춰줘야함
        Collection<GrantedAuthority> collect = new ArrayList<>();

        //GrantedAuthority는 메소드 1개있는 인터페이스임 람다사용가능
//        collect.add((GrantedAuthority) () -> user.getRole());
        collect.add( () -> user.getRole());

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료됬니
    @Override
    public boolean isAccountNonExpired() {
        return true; //아니요가 true임
    }

    //계정 잠겼니
    @Override
    public boolean isAccountNonLocked() {
        return true;  //아니요가 true임
    }

    // 비밀번호 변경 관련 기간 설정할 때 사용
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //아니요가 true임
    }

    // 니 계정 활성화되었니
    @Override
    public boolean isEnabled() {

        //우리 사이트, 1년동안 로그인 안하면 휴먼계정으로 할 때
        // 현재시간 - 로그인 시간 >1년 같은 로직으로 return false; 하면 됌

        return true; //아니요가 true임
    }
}
