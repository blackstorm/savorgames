package com.savorgames.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.savorgames.cache.SGUserCache;
import com.savorgames.entity.Member;
import com.savorgames.entity.Role;
import com.savorgames.security.model.factory.SGUserFactory;
import com.savorgames.service.impl.MemberServiceImpl;

@Service
public class SGUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private MemberServiceImpl memberService;
  
  @Autowired
  private SGUserCache userCache;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member user;
    user = this.userCache.loadUserByUsername(username);
    
    /*
     * 缓存未命中查询数据库
     */
    if(user == null){
      user = this.memberService.getMemberByNameOrEmail(username);
    }
    
    /*
     * 无法查询到用户
     */
    if (user == null) {
      throw new UsernameNotFoundException(
          String.format("No user found with username '%s'.", username));
    } else {
      List<Role> roles = memberService.getMemberRole(user.getUid());// 获取用户权限
      return SGUserFactory.create(user, roles);
    }
  }
}
