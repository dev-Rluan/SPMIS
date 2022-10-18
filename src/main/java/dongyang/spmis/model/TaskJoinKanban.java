package dongyang.spmis.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaskJoinKanban {
		private int task_id;
		private int project_id;
		private String task_subject;
		private String content;
		private Date task_create_date;
		private String create_user_email;
		private String start_user_email;
		private int views;
		private String kanban_status;
		private Date start_date;
		private Date final_date;
		private Date final_expect_date;
		private int kanban_id;
	

}
