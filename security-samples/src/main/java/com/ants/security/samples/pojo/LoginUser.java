package com.ants.security.samples.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Author lyy
 * @Description
 * @Date 2022-05-26
 **/
@Data
@Builder
public class LoginUser implements UserDetails, CredentialsContainer {

	private User user;
	private List<String> roles;

	@Override
	public void eraseCredentials() {
		user.setPassword("******");
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return  AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",roles));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
