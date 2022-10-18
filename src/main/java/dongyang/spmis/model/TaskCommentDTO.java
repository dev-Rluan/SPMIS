package dongyang.spmis.model;

import lombok.Data;

@Data
public class TaskCommentDTO {
	private int comment_id;
	private int task_id;
	private String user_email;
	private String comment_content;
}
