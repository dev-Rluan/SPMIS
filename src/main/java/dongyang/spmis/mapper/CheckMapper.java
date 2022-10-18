package dongyang.spmis.mapper;

import dongyang.spmis.model.UserActivatedDTO;
import dongyang.spmis.model.UserDTO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CheckMapper extends DefaultDBInfo {


	// 인증문자확인
	@Select("SELECT checknum FROM " + USER + " WHERE user_email=#{user_email}")
	String selectchecknum(UserDTO user);

	// 회원가입확인 / 이메일 인증완료 확인 / 0이면 미인증, 1이면 인증한 것
	@Select("SELECT activated FROM " + USER + " WHERE user_email=#{user_email}")
	boolean selectActivated(UserActivatedDTO user);

	// 비밀번호찾기시 이메일인증문자 수정 및 회원가입시 인증문자추가
	@Update("UPDATE " + USER + " SET checknum=#{checknum} WHERE user_email=#{user_email}")
	void updatechecknum(UserDTO user);

	// 인증코드 인증완료시 checkconfirm 변경
	@Update("UPDATE " + USER + " SET activated=#{activated} WHERE user_email=#{user_email}")
	void updateactivated(UserDTO user);

}
