package dongyang.spmis.model;

import lombok.Data;

@Data
public class MemberDTO {
    private String user_email;
    private String user_name;
    private String profile;

    public MemberDTO(String user_email, String user_name, String profile) {
        this.user_email = user_email;
        this.user_name = user_name;
        this.profile = profile;
    }
}
