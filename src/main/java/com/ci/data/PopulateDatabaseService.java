package com.ci.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ci.models.EPrivilege;
import com.ci.models.ERole;
import com.ci.models.Privilege;
import com.ci.models.Role;
import com.ci.models.User;
import com.ci.repository.PrivilegeRepository;
import com.ci.repository.RoleRepository;
import com.ci.repository.UserRepository;

//@Service
public class PopulateDatabaseService {

	// @Bean
	public CommandLineRunner populate(final UserRepository userRepository, final RoleRepository roleRepository,
			final PrivilegeRepository privilegeRepository, final PasswordEncoder encoder) {
		return args -> {

			List<Role> role = new ArrayList<>();
			role.add(new Role(ERole.ROLE_1));
			role.add(new Role(ERole.ROLE_2));
			role.add(new Role(ERole.ROLE_3));
			roleRepository.saveAll(role);

			List<Privilege> privilege = new ArrayList<>();
			privilege.add(new Privilege(EPrivilege.PRIV_1));
			privilege.add(new Privilege(EPrivilege.PRIV_2));
			privilege.add(new Privilege(EPrivilege.PRIV_3));
			privilegeRepository.saveAll(privilege);

			Set<Role> roles = new HashSet<>();
			roles.add(new Role(ERole.ROLE_1));

			Set<Privilege> privileges = new HashSet<>();
			privileges.add(new Privilege(EPrivilege.PRIV_1));
			privileges.add(new Privilege(EPrivilege.PRIV_2));

			User user = new User("user1", "user1@gmail.com", encoder.encode("user1"));
			user.setRoles(roles);
			user.setPrivileges(privileges);
			userRepository.save(user);
		};
	}
}
