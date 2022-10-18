package dongyang.spmis.model;

import lombok.Data;

@Data
public class ModifyPasswordDTO {
    private String newPass;
    private String newPassCheck;
    private String user_email;
}
