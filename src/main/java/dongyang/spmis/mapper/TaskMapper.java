package dongyang.spmis.mapper;

public interface TaskMapper extends DefaultDBInfo {

//	// 글 등록 메서드
//	@Insert("INSERT INTO " + PROJECTBOARD
//			+ " VALUES(null,#{project_id},#{board_subject},#{board_content},#{board_create_date},#{create_user_id}, #{start_user_id})")
//	void regist(ProjectBoardDTO projectBoardDTO);
//	
//	// 글 목록을 가지고 오는 메서드(페이징 처리를 안하고 목록전체 보여주기)
//	@Select("SELECT * FROM " + PROJECTBOARD + " ORDER BY board_id DESC")
//	ArrayList<ProjectBoardDTO> listBoard();
//
//	// 글 상세보기 메서드
//	@Select("SELECT * FROM " + PROJECTBOARD + " WHERE board_id=#{board_id}")
//	ProjectBoardDTO contentBoard(ProjectBoardDTO projectBoardDTO);
//
//	// 게시글 삭제 메서드
//	@Delete("DELETE FROM " + PROJECTBOARD + " WHERE board_id=#{board_id}")
//	void deleteBoard(ProjectBoardDTO projectBoardDTO);
//
//	// 글 수정 메서드
//	@Update("UPDATE " + PROJECTBOARD + " SET board_subject=#{board_subject}, board_content=#{board_content} WHERE board_id=#{board_id}")
//	void updateBoard(ProjectBoardDTO projectBoardDTO);
//
//	// 댓글 불러오는 메서드
//	@Select("SELECT * FROM " + BOARDCOMMENT + " WHERE board_id=${board_id}")
//	ArrayList<BoardCommentDTO> getComment(BoardCommentDTO boardCommentDTO);
//	
//	// 댓글 추가하는 메서드
//	@Insert("INSERT INTO " + BOARDCOMMENT + " VALUES(null, ${board_id}, ${user_id}, #{comment_content})")
//	void addComment(BoardCommentDTO boardCommentDTO);
//	
//	// 댓글 삭제하는 메서드
//	@Delete("DELETE FROM " + BOARDCOMMENT + " WHERE comment_id=${comment_id} AND board_id=${board_id}")
//	void removeComment(BoardCommentDTO boardCommentDTO);
}
