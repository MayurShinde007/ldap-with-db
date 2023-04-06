package com.ci.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ci.models.EPrivilege;
import com.ci.models.ERole;
import com.ci.models.Privilege;
import com.ci.models.Role;
import com.ci.models.User;
import com.ci.payload.request.LoginRequest;
import com.ci.payload.request.SignupRequest;
import com.ci.payload.response.JwtResponse;
import com.ci.payload.response.MessageResponse;
import com.ci.repository.PrivilegeRepository;
import com.ci.repository.RoleRepository;
import com.ci.repository.UserRepository;
import com.ci.security.jwt.JwtUtils;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

//	@Autowired
//	private UserDetailsServiceImpl userDetailsService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_1)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_2":
					Role adminRole = roleRepository.findByName(ERole.ROLE_2)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "ROLE_3":
					Role modRole = roleRepository.findByName(ERole.ROLE_3)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_1)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		Set<String> strPrivileges = signUpRequest.getPrivilege();
		Set<Privilege> privileges = new HashSet<>();

		if (strPrivileges == null) {
			Privilege privs = privilegeRepository.findByPrivilege(EPrivilege.PRIV_1)
					.orElseThrow(() -> new RuntimeException("Error: Privilege is not found."));
			privileges.add(privs);
		} else {
			strPrivileges.forEach(priv -> {
				switch (priv) {
				case "PRIV_2":
					Privilege priv2 = privilegeRepository.findByPrivilege(EPrivilege.PRIV_2)
							.orElseThrow(() -> new RuntimeException("Error: Privilege is not found."));
					privileges.add(priv2);
					break;
				case "PRIV_3":
					Privilege priv3 = privilegeRepository.findByPrivilege(EPrivilege.PRIV_3)
							.orElseThrow(() -> new RuntimeException("Error: Privilege is not found."));
					privileges.add(priv3);

					break;
				default:
					Privilege priv1 = privilegeRepository.findByPrivilege(EPrivilege.PRIV_1)
							.orElseThrow(() -> new RuntimeException("Error: Privilege is not found."));
					privileges.add(priv1);
				}
			});
		}

		user.setRoles(roles);
		user.setPrivileges(privileges);

		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
