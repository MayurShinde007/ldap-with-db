package com.ci.security.ldap;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import com.ci.models.Role;
import com.ci.models.User;
import com.ci.repository.RoleRepository;
import com.ci.repository.UserRepository;

public class CustomRolesPopulator implements LdapAuthoritiesPopulator {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	public CustomRolesPopulator(RoleRepository roleRepository, UserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations dirContextOperations,
			String username) {

		Collection<GrantedAuthority> roles = new HashSet<>();
		final Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			final List<Role> rolesList = roleRepository.findAll();
			roles = rolesList.stream().map(role -> new SimpleGrantedAuthority(role.getName().toString()))
					.collect(Collectors.toList());
		}
		return roles;
	}
}
