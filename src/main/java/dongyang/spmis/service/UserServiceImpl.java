package dongyang.spmis.service;

import dongyang.spmis.mapper.UserMapper;
import dongyang.spmis.model.ModifyPasswordDTO;
import dongyang.spmis.model.UserAutuorityDTO;
import dongyang.spmis.model.UserDTO;
import dongyang.spmis.properties.JoinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;


	@Override
	public boolean insert(UserDTO user) {
		return userMapper.insertUser(user);
	}

	@Override
	public JoinProperties join(UserDTO user) {
		// 같은 이메일로 회원가입된 유저가 있는지 확인
		if(userMapper.findByUserEmail(user.getUser_email()) != null){
			// 같은 이메일로 회원가입된 유저가 있으면 이메일 인증되어있는지 확인해서 결과 반환
			if(!userMapper.findByUserEmail(user.getUser_email()).isActivated()){
				return JoinProperties.CHECK_NOT_EMAIL;
			}
			return JoinProperties.DUPLICATE_EMAIL;
		}
		// 같은이메일로 회원가입된 유저가 없으면 기본적으로 activated 를 false로 기본값 설정
		user.setActivated(false);
		// 디비에 저장하기전에 패스워드 인코딩
		user.setUser_pw(bCryptPasswordEncoder.encode(user.getUser_pw()));
		System.out.println(user.getUser_pw());
		// 권한 저장을 위한 객체 생성 default="ROLE_USER"
		UserAutuorityDTO userAutuority = new UserAutuorityDTO(user.getUser_email(), "ROLE_USER");
		System.out.println(user.isActivated());

		// 유저 정보 저장
		if(userMapper.save(user)){
			// 유저 권한정보 저장
			if(userMapper.userAuthoritySave(userAutuority)){
				// 모든 정보 저장에 성공하면 SUCCESS 반환
				return JoinProperties.SUCCESS;
			}
			// 유저 권한 정보 저장에 실패하면 AUTHORITY_SAVE_FAIL 반환
			return JoinProperties.AUTHORITY_SAVE_FAIL;
		}
		// 유저 정보 저장에 실패하면 FAIL 반환
		return JoinProperties.USER_SAVE_FAIL;

	}

	@Override
	public UserDTO userLogin(UserDTO user) {
		// TODO Auto-generated method stub
		return userMapper.userLogin(user);
	}

	@Override
	public UserDTO getUser(UserDTO user) {
		// TODO Auto-generated method stub
		return userMapper.getUser(user);
	}

	@Override
	public int emailCheck(UserDTO user) {
		// TODO 중복 이메일 검사
		return userMapper.emailCheck(user);
	}

	@Override
	public boolean modifyAccount(UserDTO user) {
		// TODO 계정 정보 수정
		userMapper.modifyAccount(user);
		return true;
	}

	@Override
	public boolean deleteAccount(UserDTO user) {
		// TODO 계정 삭제
		userMapper.deleteAccount(user);
		return true;
	}

	@Override
	public boolean modifyPassword(ModifyPasswordDTO modifyPassword) {
		// TODO 회원 비밀번호 변경
		return userMapper.modifyPassword(modifyPassword);
	}

	@Override
	public void updateProfileImg(UserDTO user) {
		// TODO cloudinary에 업로드된 이미지 경로를 DB에 저장하여 CDN으로 활용
		userMapper.updateProfileImg(user);
	}

}
