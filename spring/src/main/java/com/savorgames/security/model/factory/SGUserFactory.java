package com.savorgames.security.model.factory;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.savorgames.entity.Member;
import com.savorgames.entity.Role;
import com.savorgames.security.model.SGUser;

public class SGUserFactory {
	public static SGUser create(Member user,List<Role> roles){
		String userRoles = "ROLE_USER"; // 登录用户都分配一个用户权限
		for(Role role : roles){
			userRoles = userRoles+","+role.getCode();
		}
		List<GrantedAuthority> listGrantedAuthority = AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles);
	    return new SGUser(
	    		Long.valueOf(user.getUid()),
	    		user.getUsername(),
	    		user.getPassword(),
	    		user.getEmail(),
	    		user.getLastpasswordreset(),
	    		listGrantedAuthority
	    		);
	}
}
