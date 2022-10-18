package dongyang.spmis.service;

import dongyang.spmis.mapper.DiscordMapper;
import dongyang.spmis.mapper.ProjectMapper;
import dongyang.spmis.mapper.UserMapper;
import dongyang.spmis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DiscordMapper discordMapper;
	
	@Override
	public ArrayList<ProjectDTO> selectProjects(UserDTO user) {
		// TODO Auto-generated method stub
		return projectMapper.selectProjectList(user);
	}
	
	@Override
	public ArrayList<ProjectDTO> selectPublicProjects() {
		// TODO Auto-generated method stub
		return projectMapper.selectPublicProjectList();
	}
	
	@Override
	public int selectPublicProjectCnt() {
		return projectMapper.selectPublicProjectCnt();
	}
	
	
	
	@Override	
	public boolean createProject(ProjectDTO project) {
		// TODO Auto-generated method stub
		
		
		return projectMapper.createProject(project);
	}
	
	@Override
	// 프로젝트 조회
	public ProjectDTO selectOneProject(ProjectDTO project) {
		return projectMapper.selectOneProject(project);
	}
	
	// 프로젝트 조회
	@Override
	public ProjectDTO selectOneProjectToProjectJoin(ProjectJoinDTO join) {
		return projectMapper.selectOneProjectToProjectJoin(join);
	}
	
	@Override
	public ProjectDTO selectLatestProject() {
		return projectMapper.selectLatestProject();
	}

	@Override
	public boolean updatePrivacyScope(int project_id, String privacy_scope) {
		return projectMapper.updatePrivacyScope(project_id, privacy_scope);
	}

	public ArrayList<ProjectDTO> selectPagingProjects(int startIndex, int pageSize){
		
		return projectMapper.selectPagingProjectList(startIndex, pageSize);
	}
	
	@Override
	public boolean deleteProject(ProjectDTO project) {
		// TODO Auto-generated method stub
		return projectMapper.deleteProject(project);
	}
	
	@Override
	public boolean deleteProjectBoard(ProjectDTO project) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteProjectBoard(project);
	}
	
	@Override
	public boolean deleteProjectKanban(ProjectDTO project) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteProjectKanban(project);
	}
	
	@Override
	public boolean deleteProjectRule(ProjectDTO project) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteProjectRule(project);
	}
	
	@Override
	public boolean deleteProjectJoin(ProjectDTO project) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteProjectJoin(project);
	}

	@Override 
	public boolean createDefaultKanban(ProjectDTO project) {
		KanbanDTO kanban = new KanbanDTO();
		kanban.setProject_id(project.getProject_id());
		kanban.setKanban_status("TO DO");
		if(!projectMapper.createKanban(kanban)) return false;
		kanban.setKanban_status("DOING");
		if(!projectMapper.createKanban(kanban)) return false;
		kanban.setKanban_status("DONE");
		if(!projectMapper.createKanban(kanban)) return false;
		
		
		return true;
	}
	
	@Override
	public ArrayList<KanbanDTO> selectKanbanStatus(ProjectDTO project) {
		// TODO Auto-generated method stub
		return projectMapper.kanbanList(project);
	}

	@Override
	public boolean createKanbanStatus(KanbanDTO status) {
		// TODO Auto-generated method stub		
		return projectMapper.createKanban(status);
	}

	@Override
	public boolean deleteKanbanStatus(KanbanDTO status) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteKanban(status);
	}

	// 프로젝트 task(board)와 user_name 같이 불러오기
	@Override
	public ArrayList<TaskJoinUserDTO> selectTaskJoinUsers(ProjectDTO project){
		ArrayList<TaskJoinUserDTO> boards = projectMapper.selectTaskJoinUsers(project);
		
		for(int i =0; i < boards.size(); i++) {
			// 보드 한개 가져와서 객체에 넣음
			TaskJoinUserDTO board = boards.get(i);
			
			// 유저 객체 생성
			UserDTO userInfo = new UserDTO();
			
			// create유저 name 검색
			userInfo.setUser_email(board.getCreate_user_email());
			userInfo = userMapper.getUser(userInfo);
			// board 객체에 create 유저 name 값 넣어주기
			board.setCreate_user_name(userInfo.getUser_name());
			
			// start유저 name 검색
			userInfo.setUser_email(board.getStart_user_email());
			userInfo = userMapper.getUser(userInfo);
			// board 객체에 start 유저 name 값 넣어주기
			board.setStart_user_name(userInfo.getUser_name());
			
			// 원래 위치에 board 객체 수정해서 넣어주기 			
			boards.set(i, board);	
		}
		// boards 객체 반환
		return boards;
	}

	@Override
	public ArrayList<TaskJoinKanbanUserDTO> selectTaskJoinKanbanUser(int project_id) {
		return projectMapper.selectTaskJoinKanbanUser(project_id);
	}

	@Override
	public ArrayList<TaskDTO> selectBoards(ProjectDTO project) {
		// TODO Auto-generated method stub
		return projectMapper.taskList(project);
	}

	@Override
	public ArrayList<TaskDTO> selectKanbanTasks(KanbanDTO project) {
		// TODO Auto-generated method stub
		return projectMapper.taskByKanban(project);
	}
	
	@Override
	public boolean createTask(TaskDTO board) {
		// TODO Auto-generated method stub		
		return projectMapper.createTask(board);
	}

	@Override
	public boolean deleteTask(TaskDTO board) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteTask(board);
	}

	@Override
	public boolean updateTask(TaskDTO board) {
		// TODO Auto-generated method stub		
		return projectMapper.updateTask(board);
	}
	
	@Override
	public ArrayList<TaskJoinKanban> selectTaskJoinKanban(UserDTO user){
		return projectMapper.selectTaskJoinKanban(user);
	}

	@Override
	public ArrayList<UserDTO> getuserlist(ProjectJoinDTO user) {
		return projectMapper.getuserlist(user);
	}

	@Override
	public ArrayList<KanbanDTO> getkanbanlist(KanbanDTO kanban) {
		return projectMapper.getkanbanlist(kanban);
	}

	@Override
	public ArrayList<Integer> gettasklist(TaskDTO task) {
		return projectMapper.gettasklist(task);
	}

	@Override
	public ArrayList<ProjectRuleDTO> selectRule(ProjectDTO project) {
		// TODO Auto-generated method stub
		return projectMapper.rule(project);
	}

	@Override
	public boolean createRule(ProjectRuleDTO rule) {
		// TODO Auto-generated method stub		
		return projectMapper.createRule(rule);
	}

	
	
	@Override
	public boolean deleteRule(ProjectRuleDTO rule) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteRule(rule);
	}

	@Override
	public ArrayList<TaskCommentDTO> selectComment(TaskDTO board) {
		// TODO Auto-generated method stub
		return projectMapper.comment(board);
	}

	@Override
	public ArrayList<TaskCommentDTO> selectCommentByProjectID(int project_id) {

		return projectMapper.commentByProjectID(project_id);
	}

	@Override
	public boolean createComment(TaskCommentDTO comment) {
		// TODO Auto-generated method stub		
		return projectMapper.createComment(comment);
	}

	@Override
	public boolean deleteComment(TaskCommentDTO comment) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteComment(comment);
	}

	@Override
	public ProjectJoinDTO selectGroupCheck(ProjectDTO project, UserDTO user) {
		return projectMapper.selectGroupCheck(project.getProject_id(), user.getUser_email());
	}
	
	// 프로젝트의 admin(관리자)인지 체크
	@Override	
	public ProjectJoinDTO selectGroupAdmin(ProjectDTO project, UserDTO user){
		return projectMapper.selectGroupAdminCheck(project.getProject_id(), user.getUser_email());
	}
	
	@Override
	public ArrayList<ProjectJoinDTO> selectGroup(ProjectDTO project) {
		// TODO Auto-generated method stub		
		return projectMapper.group(project);
	}

	public ArrayList<ProjectJoinDTO> selectInviteGroup(UserDTO user){
		
		return projectMapper.selectInviteGroup(user);
	}
	
	@Override
	public boolean insertGroup(ProjectJoinDTO join) {
		// TODO Auto-generated method stub		
		if(projectMapper.checkJoin(join) != null) {
			System.out.println("이미 등록된 사용자 있음");
			return false;
		}
		return projectMapper.insertGroup(join);
	}

	@Override
	public boolean deleteProjectUser(ProjectJoinDTO join) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteGroup(join);
	}
	
	@Override
	public boolean updateGroup(ProjectJoinDTO join) {
		// TODO Auto-generated method stub		
		return projectMapper.updateGroup(join);
	}

	@Override
	public boolean updateRole(ProjectJoinDTO join) {
		
		return projectMapper.updateRole(join);
	}
	
	@Override
	public ArrayList<MeetingDTO> selectMeeting(ProjectDTO project) {
		// TODO Auto-generated method stub
		return projectMapper.meeting(project);
	}

	@Override
	public boolean createMeeting(MeetingDTO meeting) {
		// TODO Auto-generated method stub		
		return projectMapper.createMeeting(meeting);
	}

	@Override
	public boolean deleteMeeting(MeetingDTO meeting) {
		// TODO Auto-generated method stub
		
		return projectMapper.deleteMeeting(meeting);
	}

	@Override
	public ArrayList<MeetingLogDTO> selectMeetingLog(MeetingDTO meetingLog) {
		// TODO Auto-generated method stub		
		return projectMapper.meetingLog(meetingLog);
	}

	@Override
	public boolean insertMeetingLog(MeetingLogDTO meetingLog) {
		// TODO Auto-generated method stub
		
		return projectMapper.insertMeetingLog(meetingLog);
	}

	@Override
	public boolean deleteMeetingLog(MeetingLogDTO meetingLog) {
		return projectMapper.deleteMeetingLog(meetingLog);
	}

	@Override
	public ArrayList<MeetingLogChatDTO> selectMeetingLogChat(MeetingLogDTO meetinglogDTO) {
		return projectMapper.meetingLogChat(meetinglogDTO);
	}

	@Override
	public boolean insertMeetingChat(MeetingLogChatDTO meetinglogDTO) {
		return projectMapper.insertMeetingLogChat(meetinglogDTO);
	}

	@Override
	public boolean deleteMeetingChat(MeetingLogChatDTO meetinglogDTO) {
		// TODO Auto-generated method stub		
		return projectMapper.deleteMeetingChat(meetinglogDTO);
	}

	@Override
	public boolean addDiscordLink(DiscordLinkDTO discordLinkDTO) {
		return discordMapper.saveDiscordLink(discordLinkDTO);
	}

	@Override
	public DiscordLinkDTO FindDiscordLinkByProjectID(int project_id) {
		return discordMapper.discordLinkFindByProjectID(project_id);
	}

	@Override
	public ArrayList<DiscordLinkDTO> FindDiscordLinkByUserEmail(String user_email) {
		return discordMapper.discordLinkFindByUserEmail(user_email);
	}




	@Override
	public boolean addTaskComment(TaskCommentDTO taskCommentDTO) {
		return projectMapper.createComment(taskCommentDTO);
	}


	@Override
	public KanbanDTO selectKanbanStatus(TaskDTO board) {
		// TODO Auto-generated method stub
		
		return projectMapper.selectKanbanStatus(board);
	}

	@Override
	public boolean updateTaskKanbanID(TaskDTO board) {
		// TODO Auto-generated method stub
		return projectMapper.updateTaskKanbanID(board);
	}

	@Override
	public ProjectDTO selectOneProject(int project_id) {
		// TODO Auto-generated method stub
		return projectMapper.selectOneProjectForProject_id(project_id);
	}

}
