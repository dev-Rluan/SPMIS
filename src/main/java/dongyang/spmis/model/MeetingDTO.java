package dongyang.spmis.model;

import lombok.Data;

import java.util.Date;

@Data
public class MeetingDTO {
	private int meeting_id;
	private String meeting_title;
	private Date meeting_start_date;
	private Date meeting_end_date;
	private int project_id;
	
}
