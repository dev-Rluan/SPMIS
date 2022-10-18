package dongyang.spmis.mapper;

import dongyang.spmis.model.*;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface ProjectMapper extends DefaultDBInfo {
	
	// 프로젝트 
	// 참여 프로젝트 목록 불러오기
	@Select("SELECT p.project_id, project_name, project_des, project_create_date, project_final_date, project_final_expect_date, privacy_scope"
			+ " FROM " + PROJECT + " p, " + PROJECTJOIN + " pj WHERE p.project_id = pj.project_id and pj.user_email = #{user_email};")
	ArrayList<ProjectDTO> selectProjectList(UserDTO user);
	
	// 공개범위가 public인 프로젝트 목록 불러오기
	@Select("SELECT * FROM " + PROJECT + " where privacy_scope='public'")
	ArrayList<ProjectDTO> selectPublicProjectList();
	
	// 공개범위가 public인 프로젝트 갯수 불러오기
	@Select("SELECT count(*) FROM " + PROJECT + " where privacy_scope='public'")
	int selectPublicProjectCnt();	
	
	// 공개범위가 public이고 pagination처리가된 프로젝트 목록 불러오기
	@Select("SELECT * FROM " + PROJECT + " where privacy_scope='public' order by project_id desc limit ${startIndex}, ${pageSize}")
	ArrayList<ProjectDTO> selectPagingProjectList(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
	
	// 새로운 프로젝트 생성
	@Insert("INSERT INTO " + PROJECT + " VALUES( null, #{project_name}, #{project_des}, "
			+ "now(), null, null, #{privacy_scope} )")
	boolean createProject(ProjectDTO project);
	
	// 프로젝트 조회
	@Select("Select * from " + PROJECT + " where project_id=#{project_id}")
	ProjectDTO selectOneProject(ProjectDTO project);
	
	// 프로젝트 조회
	@Select("Select * from " + PROJECT + " where project_id=#{project_id}")
	ProjectDTO selectOneProjectForProject_id(int project_id);
	
	// 프로젝트 조회
	@Select("Select * from " + PROJECT + " where project_id=#{project_id}")
	ProjectDTO selectOneProjectToProjectJoin(ProjectJoinDTO project);
	
	// 가장 최신의 프로젝트 조회
	@Select("Select * from " + PROJECT + " order by project_id desc limit 1")
	ProjectDTO selectLatestProject();
	
	// 프로젝트 삭제
	@Delete("DELETE FROM " + PROJECT + " WHERE project_id=${project_id}")
	boolean deleteProject(ProjectDTO project);
	
	// 프로젝트 삭제시 task(board) 삭제
	@Delete("DELETE FROM " + TASK + " WHERE project_id=${project_id}")
	boolean deleteProjectBoard(ProjectDTO board);	
	
	// 프로젝트 삭제시 Kanban 삭제
	@Delete("DELETE FROM " + KANBAN + " WHERE project_id=${project_id}")
	boolean deleteProjectKanban(ProjectDTO board);	
	
	// 프로젝트 삭제시 rule 삭제
	@Delete("DELETE FROM " + PROJECTRULE + " WHERE project_id=${project_id}")
	boolean deleteProjectRule(ProjectDTO board);	
	
	// 프로젝트 삭제시 projectjoin 삭제
	@Delete("DELETE FROM " + PROJECTJOIN + " WHERE project_id=${project_id}")
	boolean deleteProjectJoin(ProjectDTO board);	
	
	// task
	// 보드로 칸반명 불러오기
	@Select("SELECT * FROM " + KANBAN + " where kanban_id=${kanban_id}")
	KanbanDTO selectKanbanStatus(TaskDTO board);
	
	// task 종류(칸반) 불러오기
	@Select("SELECT * FROM " + KANBAN + " where project_id=${project_id} ORDER BY kanban_id")
	ArrayList<KanbanDTO> kanbanList(ProjectDTO project);
	
	// 새로운 칸반 생성
	@Insert("INSERT INTO " + KANBAN + " VALUES(null, #{kanban_status}, ${project_id})")
	boolean createKanban(KanbanDTO status);
	
	// 칸반 삭제
	@Delete("DELETE FROM " + KANBAN + " WHERE kanban_id=${kanban_id}")
	boolean deleteKanban(KanbanDTO status);
	
	// task의 칸반 id 수정
	@Update("UPDATE " + TASK + " SET kanban_id =${kanban_id}  "
			+ " where task_id=#{task_id}")
	boolean updateTaskKanbanID(TaskDTO task);
	
	// 프로젝트 task(board) 불러오기 
	@Select("SELECT * FROM " + TASK + " where project_id=${project_id} ORDER BY task_id DESC")
	ArrayList<TaskDTO> taskList(ProjectDTO project);
	
	// 프로젝트 칸반아이디별 task(board) 불러오기
	@Select("SELECT * FROM " + TASK + " WHERE kanban_id=${kanban_id}")
	ArrayList<TaskDTO> taskByKanban(KanbanDTO project);

	// 새로운 task(board) 생성
	@Insert("INSERT INTO " + TASK
			+ " VALUES(null,${project_id},#{task_subject},#{content},now(),#{create_user_email}, #{start_user_email}, 0,"
			+ " null, null, #{final_expect_date}, #{kanban_id})")
	boolean createTask(TaskDTO task);
	
	// 유저별 보드와 kanban_name(board_status)불러오기
	@Select("SELECT kanban_status, task_id, pb.project_id, task_subject, content, task_create_date, "
			+ "start_user_email,  views, start_date, final_date, final_expect_date, pb.kanban_id "
			+ " FROM "+ TASK + " pb, " + KANBAN + " ps "
			+ " where pb.kanban_id = ps.kanban_id and start_user_email=#{user_email}")
	ArrayList<TaskJoinKanban> selectTaskJoinKanban(UserDTO user);

	// 유저별 보드와 kanban_name(board_status)불러오기
	@Select("SELECT kanban_status, task_id, pb.project_id, task_subject, content, task_create_date, "
			+ "start_user_email,  views, start_date, final_date, final_expect_date, pb.kanban_id, user_name"
			+ " FROM "+ TASK + " pb, " + KANBAN + " ps, " + PROJECTJOIN + " pj "
			+ " where pb.project_id=#{project_id}  and start_user_email = user_email ")
	ArrayList<TaskJoinKanbanUserDTO> selectTaskJoinKanbanUser(int project_id);

	@Select("SELECT * FROM " + TASK + " where project_id=${project_id} ORDER BY task_id DESC")
	ArrayList<TaskJoinUserDTO> selectTaskJoinUsers(ProjectDTO project);
	
	// task 삭제
	@Delete("DELETE FROM " + TASK + " WHERE task_id=#{task_id}")
	boolean deleteTask(TaskDTO task);
	
	// 작업 내용 수정
	@Update("UPDATE " + TASK
			+ " SET task_subject=#{task_subject}, content=#{content}, start_user_email=#{start_user_email},"
			+ "views=#{views}, start_date=#{start_date}, final_date=#{final_date}, final_expect_date=#{final_expect_date}, kanban_id=#{kanban_id}"
			+ " WHERE task_id=#{task_id}")
	boolean updateTask(TaskDTO task);

	@Update("UPDATE " + PROJECT
			+ " SET privacy_scope = #{privacy_scope}"
			+ " WHERE project_id = #{project_id} ")
	boolean updatePrivacyScope(@Param("project_id") int project_id, @Param("privacy_scope") String privacy_scope );

	//차트
	@Select("SELECT u.user_email, u.user_name FROM " + PROJECTJOIN + " p, " + USER + " u WHERE u.user_email = p.user_email and p.project_id=${project_id}")
	ArrayList<UserDTO> getuserlist(ProjectJoinDTO user);
	//차트
	@Select("SELECT kanban_id, kanban_status FROM " + KANBAN + " WHERE project_id=${project_id}")
	ArrayList<KanbanDTO> getkanbanlist(KanbanDTO kanban);
	//차트
	@Select("SELECT kanban_id FROM " + TASK + " WHERE project_id=${project_id} and start_user_email=#{start_user_email}")
	ArrayList<Integer> gettasklist(TaskDTO task);
	
	// 프로젝트 룰
	// 프로젝트 룰 불러오기
	@Select("SELECT * FROM " + PROJECTRULE + " WHERE project_id=${project_id}")
	ArrayList<ProjectRuleDTO> rule(ProjectDTO project);
	
	// 프로젝트 룰 생성
	@Insert("INSERT INTO " + PROJECTRULE + " VALUES (${project_id} ,#{rule}) ")
	boolean createRule(ProjectRuleDTO rule);
	
	// 프로젝트 룰 삭제
	@Delete("DELETE FROM " + PROJECTRULE + " WHERE project_id=${project_id} and rule=#{rule}")
	boolean deleteRule(ProjectRuleDTO rule);	
	
	// 댓글
	// 태스크별 댓글 불러오기
	@Select("Select * FROM " + TASKCOMMENT + " where task_id=#{task_id} ")
	ArrayList<TaskCommentDTO> comment (TaskDTO task);

	@Select("Select comment_id, tc.task_id, user_email, comment_content FROM "
			+ TASKCOMMENT + " as tc, " + TASK +" as ts " +
			" where tc.task_id = ts.task_id and project_id=#{project_id} ")
	ArrayList<TaskCommentDTO> commentByProjectID (int project_id);
	
	// 댓글 입력
	@Insert("INSERT INTO " + TASKCOMMENT + " VALUES (null, #{task_id} ,#{user_email}, #{comment_content}) ")
	boolean createComment(TaskCommentDTO comment);
	
	// 댓글 삭제
	@Delete("DELETE FROM " + TASKCOMMENT + " WHERE comment_id=#{comment_id}")
	boolean deleteComment(TaskCommentDTO comment);
	
	// 그룹원인지 체크하기위한 메서드
	@Select("SELECT * FROM " + PROJECTJOIN 
			+ " where project_id = #{project_id} and user_email= #{user_email} "
			+ " and join_status in('admin', 'member')" )
	ProjectJoinDTO selectGroupCheck(@Param("project_id") int project_id, @Param("user_email") String user_email);
	
	// 그룹원인지 체크하기위한 메서드
	@Select("SELECT * FROM " + PROJECTJOIN 
			+ " where project_id = #{project_id} and user_email= #{user_email} "
			+ " and join_status = 'admin' " )
	ProjectJoinDTO selectGroupAdminCheck(int project_id, String user_email);
	

	// 그룹원 목록 불러오기
	@Select("SELECT * FROM " + PROJECTJOIN + " where project_id=${project_id}") 
	ArrayList<ProjectJoinDTO> group(ProjectDTO project);
	
	// 그룹원 초대상태목록 불러오기
	@Select("SELECT pj.project_id, user_email, role, join_status, project_name FROM " 
	+ PROJECTJOIN + " pj, " + PROJECT 
	+ " p where user_email=#{user_email} AND pj.project_id=p.project_id") 
	ArrayList<ProjectJoinDTO> selectInviteGroup(UserDTO user);
	
	// 이미 그룹원이거나 초대 혹은 요청 상태인지 확인하기 위한 메서드
	@Select("SELECT * FROM " + PROJECTJOIN 
			+ " where project_id = #{project_id} and user_email= #{user_email} " )
	ProjectJoinDTO checkJoin(ProjectJoinDTO join);
	
	// 그룹원 추가하기
	@Insert("INSERT INTO " + PROJECTJOIN + " VALUES (${project_id}, #{user_email}, #{role}, #{join_status}, #{modifier} )")
	boolean insertGroup(ProjectJoinDTO join);
	
	// 그룹원 삭제
	@Delete("DELETE FROM " + PROJECTJOIN + " WHERE project_id=${project_id} and user_email=#{user_email}")
	boolean deleteGroup(ProjectJoinDTO join);
	
	// 그룹원 요청 처리
	@Update("UPDATE " + PROJECTJOIN + " SET join_status=#{join_status}, role=#{role} WHERE user_email=#{user_email} and project_id=${project_id}")		
	boolean updateGroup(ProjectJoinDTO join);

	@Update("UPDATE " + PROJECTJOIN + " SET role=#{role} WHERE user_email=#{user_email} and project_id=${project_id}")
	boolean updateRole(ProjectJoinDTO join);
	
	// 회의
	// 회의 목록 불러오기
	@Select("SELECT * FROM " + MEETING + " WHERE project_id=${project_id}")
	ArrayList<MeetingDTO> meeting(ProjectDTO project);
	
	// 회의 일정 생성
	@Insert("INSERT INTO " + MEETING
			+ " VALUES (null, #{meeting_title}, #{meeting_start_date}, #{meeting_end_date}, ${project_id})")
	boolean createMeeting(MeetingDTO meeting);
	
	// 회의 정보 삭제
	@Delete("DELETE FROM " + MEETING + " WHERE meeting_id=#{meeting_id}")
	boolean deleteMeeting(MeetingDTO meeting);
	
	// 회의 주제 불러오기
	@Select("SELECT * FROM " + MEETINGLOG + " WHERE meeting_id=#{meeting_id}")
	ArrayList<MeetingLogDTO> meetingLog(MeetingDTO meetingLog);
	
	// 회의 주제 추가
	@Insert("INSERT INTO " + MEETINGLOG + " VALUES (null, #{meeting_id}, #{meeting_log_subject}, #{user_email})")
	boolean insertMeetingLog(MeetingLogDTO meetingLog);
	
	// 회의 주제 삭제
	@Delete("DELETE FROM " + MEETINGLOG + " WHERE meeting_log_id=#{meeting_log_id}")
	boolean deleteMeetingLog(MeetingLogDTO meetingLog);
	
	// 주제에대한 의견 불러오기
	@Select("SELECT * FROM " + MEETINGLOGCHAT + " WHERE meeting_log_id=#{meeting_log_id} ")
	ArrayList<MeetingLogChatDTO> meetingLogChat(MeetingLogDTO meetinglogDTO);
	
	// 주제에대한 의견 생성
	@Insert("INSERT INTO " + MEETINGLOGCHAT + " VALUES (null, #{meeting_log_id}, #{meeting_log_opinion}, #{user_email})")
	boolean insertMeetingLogChat(MeetingLogChatDTO meetinglogDTO);
	
	// 회의 주제 삭제
	@Delete("DELETE FROM " + MEETINGLOGCHAT + " WHERE meeting_log_chat_id=#{meeting_log_chat_id}")
	boolean deleteMeetingChat(MeetingLogChatDTO meetingLog);
		

}
