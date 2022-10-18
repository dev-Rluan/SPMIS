package dongyang.spmis.auth;

import dongyang.spmis.model.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomUserDetails implements UserDetails, OAuth2User {

    private UserDTO user;
    private Map<String, Object> attributes;

    // 일반 로그인
    public CustomUserDetails(UserDTO user) {
        this.user = user;
    }

    // OAuth 로그인
    public CustomUserDetails(UserDTO user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority>  authorities  = new ArrayList<GrantedAuthority>();
        user.getAuthorities().forEach(r -> {
            authorities.add(()->{ return r; });
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUser_pw();
    }

    @Override
    public String getUsername() {
        return user.getUser_email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2
    @Override
    public String getName() {
        return user.getUser_name();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
