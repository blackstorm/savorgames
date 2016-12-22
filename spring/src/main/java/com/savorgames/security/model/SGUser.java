package com.savorgames.security.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 实现 UserDetails 接口 自定义授权用户
 * @author Haozi
 *
 */
public class SGUser implements UserDetails{
	private static final long serialVersionUID = -2156757081444288632L;
	
	private long uid;
	private String username;
	private String password;
	private String email;
	private String userAgent;
	private String ip;
	private Date lastPasswordReset;
	private Collection<? extends GrantedAuthority> authorities;
	private Boolean accountNonExpired = true;
	private Boolean accountNonLocked = true;
	private Boolean credentialsNonExpired = true;
	private Boolean enabled = true;
	
	public SGUser() {
		super();
	}
	
	public SGUser(long uid, String username, String password, String email, Date lastPasswordReset, Collection<? extends GrantedAuthority> authorities){
		this.setUid(uid);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setAuthorities(authorities);
		this.setLastPasswordReset(lastPasswordReset);
	}
	
	public Date getLastPasswordReset() {
		return lastPasswordReset;
	}

	public void setLastPasswordReset(Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

  @Override
  public String toString() {
    return "SGUser [uid=" + uid + ", username=" + username + ", password=" + password + ", email="
        + email + ", userAgent=" + userAgent + ", ip=" + ip + ", lastPasswordReset="
        + lastPasswordReset + ", authorities=" + authorities + ", accountNonExpired="
        + accountNonExpired + ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired="
        + credentialsNonExpired + ", enabled=" + enabled + "]";
  }
}
