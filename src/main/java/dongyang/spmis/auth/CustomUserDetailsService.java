package dongyang.spmis.auth;

import dongyang.spmis.mapper.UserMapper;
import dongyang.spmis.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userMapper.findByUserEmail(username);
        user.setAuthorities(userMapper.authorityFindByEmail(username));
        System.out.println("user.getUser_pw() = " + user.getUser_pw());
        System.out.println("로그인 페이지 접근");
        return new CustomUserDetails(user);
    }

}
