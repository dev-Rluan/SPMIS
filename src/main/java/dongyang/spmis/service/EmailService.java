package dongyang.spmis.service;

import dongyang.spmis.model.UserDTO;

public interface EmailService {

	//이메일 발송 메서드
	public void mailSend(UserDTO user);

	//인증 확인 메서드 / 0이면 미인증 / 1이면 인증
	public boolean selectActivated(UserDTO user);

	//인증문자 확인 메서드
	public String selectCheckNum(UserDTO user);

	// 비밀번호찾기시 이메일인증문자 수정 및 회원가입시 인증문자추가 메서드
	public boolean updateCheckNum(UserDTO user);

	// 인증코드 인증완료시 checkconfirm(인증확인) 변경하는 메서드
	public boolean updateActivated(UserDTO user);
}
