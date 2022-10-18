package dongyang.spmis.security;

import dongyang.spmis.mapper.UserMapper;
import dongyang.spmis.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        System.out.println(authentication.getName());
        UserDTO user = userMapper.findByUserEmail(authentication.getName());
        user.setUser_pw(null);

        if(user.isActivated()){
            request.getSession().setAttribute("mem", user );
            response.sendRedirect("/");
        }else{
            response.sendRedirect("/emailcheck?user_email="+ user.getUser_email() +" ");
        }


    }
}
