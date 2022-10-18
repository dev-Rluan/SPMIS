package dongyang.spmis.mapper;

import dongyang.spmis.model.ModifyPasswordDTO;
import dongyang.spmis.model.UserAutuorityDTO;
import dongyang.spmis.model.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface UserMapper extends DefaultDBInfo {

	
	// 회원 정보 생성 메서드 (비밀번호 암호화)
	@Insert("INSERT INTO " + USER + " VALUES (#{user_email},#{user_pw}, #{user_name}, now(), #{profile},#{activated}, #{checknum}, null, null) ")
	boolean insertUser(UserDTO user);

	@Insert("INSERT INTO " + USER + " VALUES (#{user_email},#{user_pw}, #{user_name}, now(), #{profile},#{activated}, #{checknum}, null, null) ")
	boolean save(UserDTO user);

	@Insert("INSERT INTO " + USER + " VALUES (#{user_email},#{user_pw}, #{user_name}, now(), #{profile},#{activated}, " +
			"#{checknum}, #{provider}, #{provider_id}) ")
	boolean oauthUserSave(UserDTO user);

	@Insert("INSERT INTO user_authority VALUES (#{user_email}, #{authority_name})  ")
	boolean userAuthoritySave(UserAutuorityDTO userAutuority);

	@Update("UPDATE " + USER + " SET provider=#{provider}, provider_id=#{provider_id} WHERE user_email=#{user_email}")
	boolean updateUserProvider(@Param("provider")String provider, @Param("provider_id")String provider_id, @Param("user_email")String user_email);

	// 회원 로그인 메서드
	@Select("SELECT user_email, user_pw FROM " + USER + " WHERE user_email=#{user_email}")
	UserDTO userLogin(UserDTO user);

	// 회원 조회 메서드
	@Select("SELECT * FROM " + USER + " WHERE user_email=#{user_email}")
	UserDTO getUser(UserDTO user);

	@Select("SELECT * FROM " + USER + " WHERE user_email=#{user_email}")
	UserDTO findByUserEmail(String user_email);

	@Select("SELECT authority_name FROM user_authority WHERE user_email= #{user_email}")
	ArrayList<String> authorityFindByEmail(String user_email);

	// 회원 이메일 중복 확인 메서드
	@Select("SELECT COUNT(*) FROM " + USER + " WHERE user_email=#{user_email}")
	int emailCheck(UserDTO user);

	// 게시판 댓글 작성 사용자 정보 조회 메서드(프로필 이미지, 닉네임)
	@Select("SELECT * FROM " + USER + " WHERE user_email=${user_email}")
	UserDTO getCommentUser(UserDTO user);

	// 회원 정보 수정 메서드
	@Update("UPDATE " + USER + " SET user_name=#{user_name} WHERE user_email=#{user_email}")
	boolean modifyAccount(UserDTO user);

	// 회원 탈퇴 메서드
	@Delete("DELETE FROM " + USER + " WHERE user_email=#{user_email}")
	boolean deleteAccount(UserDTO user);

	// 회원 비밀번호 변경 메서드
	@Update("UPDATE " + USER + " SET user_pw=#{newPass} WHERE user_email=#{user_email}")
	boolean modifyPassword(ModifyPasswordDTO modifyPasswordDTO);

	// 회원 프로필 이미지 변경 메서드
	@Update("UPDATE " + USER + " SET profile_image=#{img_url} where user_email=#{user_email}")
	boolean updateProfileImg(UserDTO user);
}
