package dongyang.spmis.service;

import dongyang.spmis.model.*;

import java.util.ArrayList;


public interface ProjectService {
	
	// 프로젝트
	
	// 프로젝트 목록 불러오기
	public ArrayList<ProjectDTO> selectProjects(UserDTO user);
	
	// 공개범위가 public인 프로젝트 목록 불러오기
	public ArrayList<ProjectDTO> selectPublicProjects();
	
	// 공개범위가 public인 프로젝트 갯수  불러오기
	public int selectPublicProjectCnt();
	
	// 공개범위가 public이고 pagination처리가된 프로젝트 목록 불러오기
	public ArrayList<ProjectDTO> selectPagingProjects(int startIndex, int pageSize);
	
	// 새로운 프로젝트 생성
	public boolean createProject(ProjectDTO project); 
	
	// 프로젝트 삭제
	public boolean deleteProject(ProjectDTO project);
	
	// 프로젝트 삭제 시 task 삭제
	public boolean deleteProjectBoard(ProjectDTO project);	
	
	// 프로젝트 삭제 시 kanban 삭제
	public boolean deleteProjectKanban(ProjectDTO project);
	
	// 프로젝트 삭제 시 Rule 삭제
	public boolean deleteProjectRule(ProjectDTO project);
	
	// 프로젝트 삭제 시 ProjectJoin 삭제
	public boolean deleteProjectJoin(ProjectDTO project);
	
	// 프로젝트 조회
	public ProjectDTO selectOneProject(ProjectDTO project);
	
	// 프로젝트 조회 (String형으로 Project_id하나만 받는경우)
	public ProjectDTO selectOneProject(int project_id);
	
	// 프로젝트 조회
	public ProjectDTO selectOneProjectToProjectJoin(ProjectJoinDTO join);
	
	// 가장 최신의 프로젝트 조회
	public ProjectDTO selectLatestProject();

	//
	public boolean updatePrivacyScope(int project_id, String privacy_scope);
	
	// default칸반 TO DO, DOING, DONE 칸반 생성
	public boolean createDefaultKanban(ProjectDTO project);
	
	// board의 kanban_id로 projectStatus테이블의 project_status 가져오기
	public KanbanDTO selectKanbanStatus(TaskDTO board);
	
	// task
	// task 종류(칸반) 불러오기
	public ArrayList<KanbanDTO> selectKanbanStatus(ProjectDTO project);
	
	// 새로운 칸반 생성
	public boolean createKanbanStatus(KanbanDTO status);
	
	// 칸반 삭제
	public boolean deleteKanbanStatus(KanbanDTO status);
	
	// board의 칸반 id 수정
	public boolean updateTaskKanbanID(TaskDTO board);
	
	// 프로젝트 task(board) 불러오기 
	public ArrayList<TaskDTO> selectBoards(ProjectDTO project);
	
	// 프로젝트 칸반아이디별 task(board) 불러오기 
	public ArrayList<TaskDTO> selectKanbanTasks(KanbanDTO project);

	// 프로젝트 task(board)와 user_name 같이 불러오기
	public ArrayList<TaskJoinUserDTO> selectTaskJoinUsers(ProjectDTO project);


	public ArrayList<TaskJoinKanbanUserDTO> selectTaskJoinKanbanUser(int project_id);

	// 새로운 task(board) 생성
	public boolean createTask(TaskDTO board);
	
	// task 삭제
	public boolean deleteTask(TaskDTO board);
	
	// task 작업 내용 수정
	public boolean updateTask(TaskDTO board);
	
	// 유저별 작업 목록 가져오기 (board + boardstatus) 
	public ArrayList<TaskJoinKanban> selectTaskJoinKanban(UserDTO user);

	//차트
	//프로젝트별 유저리스트 불러오기
	public ArrayList<UserDTO> getuserlist(ProjectJoinDTO user);

	//프로젝트별 칸반아이디, 칸반상태 불러오기
	public ArrayList<KanbanDTO> getkanbanlist(KanbanDTO kanban);

	//프로젝트, 유저이메일별 칸반아이디 불러오기 / task수량체크용
	public ArrayList<Integer> gettasklist(TaskDTO task);
	
	
	// 프로젝트 룰
	// 프로젝트 룰 불러오기
	public ArrayList<ProjectRuleDTO> selectRule(ProjectDTO project);
	
	// 프로젝트 룰 생성
	public boolean createRule(ProjectRuleDTO rule);
	
	// 프로젝트 룰 삭제
	public boolean deleteRule(ProjectRuleDTO rule);


	// 댓글
	// 태스크별 댓글 불러오기
	public ArrayList<TaskCommentDTO> selectComment(TaskDTO board);

	public ArrayList<TaskCommentDTO> selectCommentByProjectID(int task);
	
	// 댓글 입력
	public boolean createComment(TaskCommentDTO comment);
	
	// 댓글 삭제
	public boolean deleteComment(TaskCommentDTO comment);
	
	
	// 그룹
	// 그룹원인지 체크해서 제대로 가져오면 인증 null 값이면 되돌리기
	public ProjectJoinDTO selectGroupCheck(ProjectDTO project, UserDTO user);
	
	// 초대상태의 그룹원 목록 불러오기
	public ArrayList<ProjectJoinDTO> selectInviteGroup(UserDTO user);
	
	// 그룹원 목록 불러오기
	public ArrayList<ProjectJoinDTO> selectGroup(ProjectDTO project);
	
	// 프로젝트의 admin(관리자)인지 체크
	public ProjectJoinDTO selectGroupAdmin(ProjectDTO project, UserDTO user);
	
	// 그룹원 추가
	public boolean insertGroup(ProjectJoinDTO join); 
	
	// 그룹원 삭제
	public boolean deleteProjectUser(ProjectJoinDTO join);
	
	// 그룹원 요청 처리
	public boolean updateGroup(ProjectJoinDTO join);
	
	// 그룹원 역할명 변경 처리
	public boolean updateRole(ProjectJoinDTO join);
	
	// 회의
	// 회의 목록 불러오기
	public ArrayList<MeetingDTO> selectMeeting(ProjectDTO project);
	
	// 미팅 일정 생성
	public boolean createMeeting(MeetingDTO meeting);
	
	// 회의 정보
	public boolean deleteMeeting(MeetingDTO meeting);
	
	// 회의 로그 불러오기
	public ArrayList<MeetingLogDTO> selectMeetingLog(MeetingDTO meetingLog);
	
	// 회의 주제 추가
	public boolean insertMeetingLog(MeetingLogDTO meetingLog);
	
	// 회의 주제 삭제
	public boolean deleteMeetingLog(MeetingLogDTO meetingLog);
	
	// 주제에대한 의견 불러오기
	public ArrayList<MeetingLogChatDTO> selectMeetingLogChat(MeetingLogDTO meetinglogDTO);
	
	// 주제에 대한 의견 생성
	public boolean insertMeetingChat(MeetingLogChatDTO meetinglogDTO);
	
	// 의견 삭제
	public boolean deleteMeetingChat(MeetingLogChatDTO meetinglogDTO);


	// 디스코드 링크 추가
	public boolean addDiscordLink(DiscordLinkDTO discordLinkDTO);

	// 디스코드 링크 찾기
	public DiscordLinkDTO FindDiscordLinkByProjectID(int project_id);

	public ArrayList<DiscordLinkDTO> FindDiscordLinkByUserEmail(String user_email);





public boolean addTaskComment(TaskCommentDTO taskCommentDTO);
}
