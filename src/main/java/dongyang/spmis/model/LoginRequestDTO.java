package dongyang.spmis.model;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String user_email;
    private String user_pw;
}
