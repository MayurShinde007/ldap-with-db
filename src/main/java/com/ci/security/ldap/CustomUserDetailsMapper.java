package com.ci.security.ldap;

import java.util.Collection;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import lombok.Getter;

@Getter
public class CustomUserDetailsMapper extends LdapUserDetailsMapper {

	private LdapUser ldapUser;
	private String commonName;

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authorities) {
		Attributes attributes = ctx.getAttributes();
		LdapUserDetails ldapUserDetails = (LdapUserDetails) super.mapUserFromContext(ctx, username, authorities);
		try {
			// Firstname and Lastname as stored in LDAP
			commonName = attributes.get("cn").get().toString();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		ldapUser = new LdapUser(ldapUserDetails, commonName);

		return ldapUser;
	}

//    @Override
//    public void mapUserToContext(UserDetails userDetails, DirContextAdapter dirContextAdapter) {
//
//    }
}
