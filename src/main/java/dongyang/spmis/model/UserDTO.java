package dongyang.spmis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO   {
	private String user_email;
	private String user_pw;
	private String user_name;
	private Date create_time;
	private String profile;
	private String checknum;
	private boolean activated;
	private String provider;
	private String provider_id;

	@Builder.Default
	private ArrayList<String> authorities = new ArrayList<>();

	@Builder
	public UserDTO(String user_email, String user_pw,
				   String user_name, Date create_time,
				   String profile, String checknum,
				   boolean activated, String provider,
				   String provider_id) {
		this.user_email = user_email;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.create_time = create_time;
		this.profile = profile;
		this.checknum = checknum;
		this.activated = activated;
		this.provider = provider;
		this.provider_id = provider_id;
	}
}
