package dongyang.spmis.model;

import lombok.Builder;
import lombok.Data;

@Data
public class UserAutuorityDTO {
    private String user_email;
    String authority_name;


    @Builder
    public UserAutuorityDTO( String user_email, String authority_name) {
        this.authority_name = authority_name;
        this.user_email = user_email;
    }
}
