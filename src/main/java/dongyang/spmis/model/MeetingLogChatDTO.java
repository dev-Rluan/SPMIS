package dongyang.spmis.model;

import lombok.Data;

@Data
public class MeetingLogChatDTO {
	private int meeting_log_chat_id;
	private int meeting_log_id;
	private String meeting_log_opinion;
	private String user_email;
}
