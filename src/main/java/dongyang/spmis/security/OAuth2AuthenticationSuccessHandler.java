package dongyang.spmis.security;

import dongyang.spmis.auth.CustomUserDetails;
import dongyang.spmis.mapper.UserMapper;
import dongyang.spmis.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserMapper userMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserDTO user = userMapper.findByUserEmail(authentication.getName());
        user.setUser_pw(null);


//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(
//                        new CustomUserDetails(user, oAuth2User.getAttributes()),
//                        null, oAuth2User.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(user.isActivated()){
            request.getSession().setAttribute("mem", user );
            response.sendRedirect("/");
        }else{
            response.sendRedirect("/emailcheck?user_email="+ user.getUser_email() +" ");
        }

    }
}
