package com.ci.security.ldap;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

public class LdapUser implements LdapUserDetails {

	private static final long serialVersionUID = 1L;

	private final LdapUserDetails ldapUserDetails;
	private final String commonName;

	public LdapUser(LdapUserDetails ldapUserDetails, String commonName) {
		this.ldapUserDetails = ldapUserDetails;
		this.commonName = commonName;
	}

	@Override
	public String getDn() {
		return ldapUserDetails.getDn();
	}

	@Override
	public void eraseCredentials() {
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return ldapUserDetails.getAuthorities();
	}

	@Override
	public String getPassword() {
		return ldapUserDetails.getPassword();
	}

	@Override
	public String getUsername() {
		return ldapUserDetails.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return ldapUserDetails.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return ldapUserDetails.isAccountNonExpired();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return ldapUserDetails.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return ldapUserDetails.isEnabled();
	}

	public String getCommonName() {
		return commonName;
	}
}
