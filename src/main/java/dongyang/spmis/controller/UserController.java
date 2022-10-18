package dongyang.spmis.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import dongyang.spmis.model.ModifyPasswordDTO;
import dongyang.spmis.model.ProjectJoinDTO;
import dongyang.spmis.model.UserDTO;
import dongyang.spmis.properties.JoinProperties;
import dongyang.spmis.service.EmailService;
import dongyang.spmis.service.ProjectService;
import dongyang.spmis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	HttpSession session;

	@GetMapping("/test")
	public @ResponseBody	String test(Authentication authentication){
		System.out.println("text/login======");
		System.out.println("authentication :" + authentication.getPrincipal());
		System.out.println("authentication :" + authentication.getAuthorities());
		System.out.println("authentication :" + authentication.getAuthorities());
		System.out.println("authentication :" + authentication.getName());
		System.out.println("authentication :" + authentication.getCredentials());
		System.out.println("authentication :" + authentication.getDetails());
		System.out.println("authentication :" + authentication.getClass());
		System.out.println("authentication :" + authentication.toString());
		return "test";
	}

	// 회원 로그인
	@GetMapping("login")
	public String login(Model model) {
		System.out.println("로그인 시도????");

		return "login";
	}

	// passwordfind 화면이동
	@GetMapping("passwordfind")
	public String passwordfind(Model model) {
		return "passwordfind";
	}

	// emailcheck 화면이동
	@GetMapping("emailcheck")
	public String emailcheck(Model model, String user_email) {
		System.out.println("user_email = " + user_email);
		model.addAttribute("user_email",user_email);
		return "emailcheck";
	}

//	// 회원가입
//	@PostMapping("/join")
//	public Long join(@RequestBody Map<String, String> user) {
//		return userRepository.save(User.builder()
//				.email(user.get("email"))
//				.password(passwordEncoder.encode(user.get("password")))
//				.roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
//				.build()).getId();
//	}

	@PostMapping("/join")
	public void join(UserDTO user, HttpServletResponse response) throws IOException {
		JoinProperties result = userService.join(user);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		System.out.println("회원가입 요청");
		switch (result){
			case SUCCESS:	// 회원가입 성공
				out.println("<script>");
				out.println("alert('회원정보가 입력되었습니다. 이메일인증을 진행해주세요');");
				out.println("location.href='emailcheck?user_email=" +user.getUser_email() + "';");
				out.println("</script>");
				break;
			case DUPLICATE_EMAIL:	// 이미 이메일 인증된 회원이 존재
				out.println("<script>");
				out.println("alert('이미 같은 이메일로 회원가입된 계정이 있습니다.');");
				out.println("location.href='join';");
				out.println("</script>");
				break;
			case CHECK_NOT_EMAIL:	// 같은 이메일을 쓰는 회원은 존재하지만 이메일 체크는 되어있지 않음
				out.println("<script>");
				out.println("alert('이미 같은 이메일로 인증을 진행중인 계정이 있습니다. 이메일인증을 진행해주세요');");
				out.println("location.href='emailcheck?user_email=" +user.getUser_email() + "';");
				out.println("</script>");
				break;
			case AUTHORITY_SAVE_FAIL:	// 권한정보 입력 실패
				out.println("<script>");
				out.println("alert('유저 권한 정보 입력에 실패하였습니다. 관리자에게 문의하세요');");
				out.println("location.href='join';");
				out.println("</script>");
				break;
			case USER_SAVE_FAIL:	// 이유를 알 수 없는 에러 -> 다시 시도하도록 유도
				out.println("<script>");
				out.println("alert('알 수 없는 에러가 발생하였습니다. 관리자에게 문의하세요');");
				out.println("location.href='join';");
				out.println("</script>");
				break;
		}


	}

	// 회원 로그인 전송
	@PostMapping("loginPOST")
	public void loginPOST(Model model, UserDTO user, HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		System.out.println("stest");
		session = req.getSession();
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		System.out.println(user);
		UserDTO userInfo = userService.getUser(user);
		if (userInfo == null) {
			out.println("<script>");
			out.println("alert('없는 회원입니다. 회원가입 후 로그인 진행하시길 바랍니다.');");
			out.println("location.href='signup';");
			out.println("</script>");

			if (user.getUser_pw().equals(userInfo.getUser_pw())) {
				session.setAttribute("mem", userInfo);
				out.println("<script>");
				out.println("location.href='projectList';");

				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('비밀번호가 틀렸습니다. 다시 확인해주세요');");
				out.println("location.href='login';");
				out.println("</script>");
			}
		}
	}

	// 회원가입
	@GetMapping("join")
	public String join(Model model, HttpServletRequest req, HttpServletResponse res) {
		// 약관 필요시 파일 불러와서 사용
//		ClassLoader classLoader = getClass().getClassLoader();
//		InputStream in = classLoader.getResourceAsStream("static/agreement.txt");
//
//		InputStreamReader inputStreamReader = new InputStreamReader(in);
//		Stream<String> streamOfString = new BufferedReader(inputStreamReader).lines();
//		String termsOfAgreement = streamOfString.collect(Collectors.joining());
//		model.addAttribute("termsOfAgreement", termsOfAgreement);

		
		
		return "join";

	}

	// 회원가입 전송
//	@PostMapping("signupPOST")
//	public void signupPOST(Model model, UserDTO user, HttpServletRequest req, HttpServletResponse res)
//			throws IOException {
//		res.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = res.getWriter();
//
//		UserDTO userCheck = userService.getUser(user);
//		if(userCheck == null){
//			user.setActivated(false);
//
//			if(userService.insert(user)) {
//				out.println("<script>");
//				out.println("alert('회원정보가 입력되었습니다. 이메일인증을 진행해주세요');");
//				out.println("location.href='emailcheck';");
//				out.println("</script>");
//			}else {
//				out.println("<script>");
//				out.println("alert('회원정보를 다시 입력해주세요');");
//				out.println("location.href='signup';");
//				out.println("</script>");
//			}
//		} else if(!userCheck.isActivated()) {
//			out.println("<script>");
//			out.println("alert('기록된 회원정보가 있습니다. 이메일 인증을 진행해 주세요');");
//			out.println("location.href='emailcheck';");
//			out.println("</script>");
//		} else{
//			out.println("<script>");
//			out.println("alert('이미 가입된 회원이 있습니다. 로그인을 진행해 주세요');");
//			out.println("location.href='login';");
//			out.println("</script>");
//		}
//
//
//	}

	//이메일 인증하기
	@PostMapping("sendEmail")
	public void certifiedEmail(Model model, UserDTO user, HttpServletRequest req, HttpServletResponse res) throws IOException{

		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		if(!emailService.selectActivated(user)) {
			String code = UUID.randomUUID().toString().substring(0, 6);

			user.setChecknum(code);
			emailService.mailSend(user);

			emailService.updateCheckNum(user);

			out.println("<script>");
			out.println("alert('인증코드가 발송되었습니다.');");
			out.println("location.href='emailcheck2?user_email=" +user.getUser_email() + "';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('회원가입정보가 없거나 이미 인증한 회원입니다.');");
			out.println("location.href='emailcheck';");
			out.println("</script>");
		}
	}

	// emailcheck2 화면이동
	@GetMapping("emailcheck2")
	public String emailcheck2(@RequestParam("user_email") String user_email, Model model) {
		model.addAttribute("user_email", user_email);
		return "emailcheck2";
	}

	//인증코드확인
	@PostMapping("checknumconfirm")
	public void checknumconfirm(Model model, UserDTO user
			, HttpServletRequest req, HttpServletResponse res) throws IOException{

		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		System.out.println("매칭 확인 checknum=" + user.getChecknum());
		if((emailService.selectCheckNum(user)).equals(user.getChecknum())) {
			user.setActivated(true);
			emailService.updateActivated(user);

			out.println("<script>");
			out.println("alert('이메일 인증이 완료되었습니다.');");
			out.println("location.href='login';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('인증코드를 다시 확인하세요.');");
			out.println("location.href='emailcheck2?user_email=" +user.getUser_email() + "';");
			out.println("</script>");
		}
	}

	// 이메일 중복 확인
	public String signupEmailCheck(UserDTO user, HttpServletRequest req, HttpServletResponse res) {
		int result = userService.emailCheck(user);
		if (result != 0) {
			return "fail"; // 중복 아이디가 존재

		} else {
			return "success"; // 중복 아이디 x
		}
	}

//	// 비밀번호 변경
//	@PostMapping("changepwdConfirm")
//	public void pwChangeConfirm(Model model, UserDTO user, HttpServletRequest req, HttpServletResponse res)
//			throws IOException {
//		res.setContentType("text/html; charset=utf-8");
//		session = req.getSession();
//		PrintWriter out = res.getWriter();
//		String pass = req.getParameter("pw-new");
//		String newPwConfirm = req.getParameter("pw-confirm");
//
//		if (pass.equals(newPwConfirm)) {
//
//			userService.modifyPassword(user);
//			session.invalidate();
//			out.println("<script>");
//			out.println("alert('비밀번호가 변경되었습니다');");
//			out.println("location.href='/';");
//			out.println("</script>");
//
//		} else {
//			out.println("<script>");
//			out.println("alert('입력한 비밀번호가 일치하지 않습니다');");
//			out.println("location.href='/changepwd';");
//			out.println("</script>");
//		}
//	}
//
//	//비밀번호찾기시 이메일인증문자 보내기
//	@PostMapping("sendEmailpw")
//	public void certifiedEmailpw(Model model, UserDTO user, HttpServletRequest req, HttpServletResponse res) throws IOException{
//
//		res.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = res.getWriter();
//
//		String code = UUID.randomUUID().toString().substring(0, 6);
//
//		user.setChecknum(code);
//		emailService.mailSend(user);
//
//		emailService.updateCheckNum(user);
//
//		out.println("<script>");
//		out.println("alert('인증코드가 발송되었습니다.');");
//		out.println("location.href='passwordfind2?user_email=" +user.getUser_email() + "';");
//		out.println("</script>");
//	}

	// passwordfind2로 화면이동
	@GetMapping("passwordfind2")
	public String passwordfind2(@RequestParam("user_email") String user_email, Model model) {
		model.addAttribute("user_email", user_email);
		return "passwordfind2";
	}

	// 비밀번호찾기시 인증코드확인
	@PostMapping("passwordfindPost")
	public void passwordfindPOST(Model model, UserDTO user, HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		if((emailService.selectCheckNum(user)).equals(user.getChecknum())) {
			out.println("<script>");
			out.println("alert('인증되셨습니다.');");
			out.println("location.href='passwordchange?user_email=" +user.getUser_email() + "';");
			out.println("</script>");

		} else {
			out.println("<script>");
			out.println("alert('인증코드를 확인하세요');");
			out.println("location.href='passwordfind2?user_email=" +user.getUser_email() + "';");
			out.println("</script>");
		}
	}

	// passwordchange 화면이동
	@GetMapping("passwordchange")
	public String passwordchange(@RequestParam("user_email") String user_email, Model model) {
		model.addAttribute("user_email", user_email);
		return "passwordchange";
	}

	// 비밀번호 변경 / 이메일 인증코드 확인후
	@PostMapping("passwordchangefin")
	public void passwordchangefin(Model model, ModifyPasswordDTO user, HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/html; charset=utf-8");
		PrintWriter out = res.getWriter();

		userService.modifyPassword(user);
		out.println("<script>");
		out.println("alert('비밀번호가 변경되었습니다');");
		out.println("location.href='/';");
		out.println("</script>");

	}

	// -----------------------------------------------------//
	// 마이페이지
	@GetMapping("mypage")
	public String mypage(Model model, HttpServletRequest req, HttpServletResponse res) {

		session = req.getSession();
		UserDTO userInfo = (UserDTO) session.getAttribute("mem");
		ArrayList<ProjectJoinDTO> joins = projectService.selectInviteGroup(userInfo);
		
		model.addAttribute("joins", joins);	
		
		return "mypage";
	}

	// 마이페이지 프로필 업데이트
	@PostMapping("mypageUpdateProfileImg")
	public String mypageUpdateProfileImg(Model model, UserDTO user, HttpServletRequest req, HttpServletResponse res,
			@RequestParam("upload-img") MultipartFile profile_img) throws IOException {
		UserDTO userInfo = (UserDTO) session.getAttribute("mem");

		// CDN 연결
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("", "", "", "", "", ""));

		// 최적의 프로필 이미지를 위해 이미지 편집 후 업로드
		@SuppressWarnings("rawtypes")
		Map result = cloudinary.uploader().upload(convert(profile_img), ObjectUtils.asMap("folder", "userprofile",
				"transformation", new Transformation().gravity("auto:classic").width(400).height(400).crop("thumb")));
		String profile_img_url = (String) result.get("secure_url");

		// 세션을 갱신하여 프로필 변경사항을 웹 뷰에서 즉시 적용
		userService.updateProfileImg(user);
		// userInfo.setProfile_image(profile_img_url);
		session.removeAttribute("mem");
		session.setAttribute("mem", userInfo);

		return "redirect:mypage";
	}

	// multipart -> 파일 변환(stream 사용. heroku에서 파일제어에 제약이 있기 때문)
	public File convert(MultipartFile file) throws IOException {
		// 파일명 충돌방지
		UUID uuid = UUID.randomUUID();
		String uuidFilename = uuid + "_" + file.getOriginalFilename();
		File convFile = new File(uuidFilename);
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	// 마이페이지 계정 수정
	@PostMapping("mypageModifyAccount")
	public void mypageModifyAccount(Model model, UserDTO user, HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		UserDTO user_no = (UserDTO) session.getAttribute("mem");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		user.setUser_email(user_no.getUser_email());

		if (userService.modifyAccount(user)) {
			UserDTO userInfo = (UserDTO) userService.getUser(user);
			session.setAttribute("mem", userInfo);
			System.out.println(userInfo);
			out.println("<script>");
			out.println("alert('회원 정보가 변경되었습니다.');");
			out.println("location.href='mypage';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('회원 정보 변경이 실패했습니다.');");
			out.println("location.href='/changepwd';");
			out.println("</script>");
		}
	}

	// 마이페이지 계정 삭제
	@GetMapping("mypageDeleteAccount")
	public void mypageDeleteAccount(Model model, HttpServletRequest req, HttpServletResponse res) throws IOException {
		UserDTO userInfo = (UserDTO) session.getAttribute("mem");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		if (userService.deleteAccount(userInfo)) {
			out.println("<script>");
			out.println("alert('회원 탈퇴되었습니다.');");
			out.println("location.href='logout';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('회원 탈퇴 실패했습니다. 다시 시도해주시기바랍니다.');");
			out.println("location.href='mypage';");
			out.println("</script>");
		}
	}

	// 비밀번호 중복 확인
	@PostMapping("newPassCheck")
	public String newPassCheck(String newPass, String passCheck) {
		boolean result = newPass.equals(passCheck);
		if (result != true) {
			return "fail"; // 비밀번호가 같지 않음

		} else {
			return "success"; // 비밀번호 동일
		}
	}

	// 회원 비밀번호 변경
	@PostMapping("modifyPassword")
	public void modifyPassword(Model model, ModifyPasswordDTO modifyPassword, HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		if(!modifyPassword.getNewPass().equals(modifyPassword.getNewPassCheck())){
			out.println("<script>");
			out.println("alert('새로운 비밀번호가 같지 않습니다.');");
			out.println("location.href='mypage';");
			out.println("</script>");
		}

		UserDTO userInfo = (UserDTO) session.getAttribute("mem");
	 	String beforePass =  req.getParameter("beforePass");
		modifyPassword.setUser_email(userInfo.getUser_email());
		if (bCryptPasswordEncoder.matches(beforePass, userService.userLogin(userInfo).getUser_pw())) {
			modifyPassword.setNewPass(bCryptPasswordEncoder.encode(modifyPassword.getNewPass()));
			userService.modifyPassword(modifyPassword);
			session.invalidate();
			out.println("<script>");
			out.println("alert('변경된 비밀번호로 다시 로그인바랍니다.');");
			out.println("location.href='login';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('이전 비밀번호가 틀렸습니다. 다시 확인바랍니다.');");
			out.println("location.href='mypage';");
			out.println("</script>");
		}
	}

	@GetMapping("logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	// 구글로그인

	@GetMapping("googleLogin")
	public String googleLogin(Model model) {
		return "googleLogin";
	}

}
