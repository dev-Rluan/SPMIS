package dongyang.spmis.model;

import lombok.Data;

@Data
public class JoinUserDTO {
    private String user_email;
    private String user_pw;
    private String user_name;
}
