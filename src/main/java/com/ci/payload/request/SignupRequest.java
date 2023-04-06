package com.ci.payload.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

	@NotBlank
//	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
//	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
//	@Size(min = 6, max = 40)
	private String password;

	private Set<String> role;

	private Set<String> privilege;

	public SignupRequest(@NotBlank String username, @NotBlank String password, @NotBlank @Email String email,
			Set<String> role, Set<String> privilege) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.privilege = privilege;
	}

}
