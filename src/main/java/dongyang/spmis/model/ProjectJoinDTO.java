package dongyang.spmis.model;

import lombok.Data;

@Data
public class ProjectJoinDTO {
	private int project_id;
	private String user_email;
	private String role;
	private String join_status;
	private int modifier;
	
	// table에는 없는 값, default=null로 받고 필요할때만 join 해서 사용할 값
	private String project_name;
}
