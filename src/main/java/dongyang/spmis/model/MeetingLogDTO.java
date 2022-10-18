package dongyang.spmis.model;

import lombok.Data;

@Data
public class MeetingLogDTO {
	private int meeting_log_id;
	private int meeting_id;	
	private String meeting_log_subject;
	private String user_email;
	
}
