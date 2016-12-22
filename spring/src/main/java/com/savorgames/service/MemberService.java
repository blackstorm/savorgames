package com.savorgames.service;

import java.util.List;

import com.savorgames.api.v1.dto.MemberDto;
import com.savorgames.entity.Member;
import com.savorgames.entity.Role;

public interface MemberService {
  List<Member> test();

  boolean isSignup(String value);

  Member signupAndGetMember(MemberDto.Signup dto);

  Member getMemberByNameOrEmail(String username);
  
  MemberDto.Me getMemberById(long uid);

  List<Role> getMemberRole(String username);

  List<Role> getMemberRole(long uid);
  
  void uploadAvatarByQiniu(MemberDto.HashAndKey hashkey);
  
  void uploadNickname(MemberDto.UploadNickname dto);
  
  String getQiniuAvatarAddress(long uid); // 获取七牛云存储的头像信息
}
