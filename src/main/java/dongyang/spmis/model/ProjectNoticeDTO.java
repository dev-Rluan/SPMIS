package dongyang.spmis.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectNoticeDTO {
    private int project_id;
    private String notice;
    private Date create_time;
}
