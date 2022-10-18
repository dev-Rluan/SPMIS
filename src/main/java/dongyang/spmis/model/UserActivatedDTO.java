package dongyang.spmis.model;

import lombok.Data;

@Data
public class UserActivatedDTO {
    private String user_email;
    private boolean activated;

    public UserActivatedDTO(String user_email, boolean activated) {
        this.user_email = user_email;
        this.activated = activated;
    }
}
