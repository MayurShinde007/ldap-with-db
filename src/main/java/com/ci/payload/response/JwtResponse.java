package com.ci.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {

	private String token;

//	private String type = "Bearer";
//	private Long id;
//	private String username;
//	private String email;
//	private List<String> roles;
//	private List<String> privileges;

//	public JwtResponse(String accessToken) {
//		this.token = accessToken;
//	}

//	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles,
//			List<String> privileges) {
//		this.token = accessToken;
//		this.id = id;
//		this.username = username;
//		this.email = email;
//		this.roles = roles;
//		this.privileges = privileges;
//	}
}
