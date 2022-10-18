package dongyang.spmis.oauth;

import dongyang.spmis.auth.CustomUserDetails;
import dongyang.spmis.mapper.UserMapper;
import dongyang.spmis.model.Authority;
import dongyang.spmis.model.UserAutuorityDTO;
import dongyang.spmis.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {



    @Autowired
    UserMapper userMapper;

    @Autowired
    public PasswordEncoder bCryptPasswordEncoder;

    // 구글로 부터 받은 userRequest 데이터에 대한 후 처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        System.out.println("userRequest = " + userRequest);
        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken());
        System.out.println("super.loadUser(userRequest).getAttributes() = " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("getAttributes :  " + oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("provider = " + provider);
        String providerId = oAuth2User.getAttribute("sub");
        System.out.println("providerId = " + providerId);
        System.out.println();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("spmis_pw");
        String email = oAuth2User.getAttribute("email");
        String profile = oAuth2User.getAttribute("picture");

        UserDTO user =  userMapper.findByUserEmail(email);
        user.setAuthorities(userMapper.authorityFindByEmail(user.getUser_email()));
        if(user == null){
            user = UserDTO.builder()
                    .user_name(email)
                    .user_pw(password)
                    .activated(true)
                    .user_name(oAuth2User.getAttribute("name"))
                    .provider(provider)
                    .provider_id(providerId)
                    .profile(profile)
                    .build();
            userMapper.oauthUserSave(user);
            userMapper.userAuthoritySave(new UserAutuorityDTO(email, "ROLE_USER"));

        }else{
            if(user.getProvider() == null || user.getProvider_id() == null ) {
                userMapper.updateUserProvider(provider, providerId, email);
                user.setProvider(provider);
                user.setProvider_id(providerId);
            }
        }
        System.out.println("user: " + user);
        System.out.println("oAuth2User.getAttributes"+oAuth2User.getAttributes());
        return new CustomUserDetails(user, oAuth2User.getAttributes());

    }
}
