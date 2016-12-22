package com.savorgames.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.savorgames.api.v1.dto.MemberDto.HashAndKey;
import com.savorgames.api.v1.dto.MemberDto.Me;
import com.savorgames.api.v1.dto.MemberDto.Signup;
import com.savorgames.api.v1.dto.MemberDto.UploadNickname;
import com.savorgames.dao.MemberDao;
import com.savorgames.entity.Member;
import com.savorgames.entity.Role;
import com.savorgames.service.MemberService;
import com.savorgames.utils.Tool;


@Service
public class MemberServiceImpl implements MemberService {
  
  @Value("${sg.qiniu.spaceLink}")
  private String qiniuSpaceLink;

  @Autowired
  PasswordEncoder bCrypt;

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * TODO 一个测试方法待删除
   */
  @Override
  public List<Member> test() {
    return memberDao.test();
  }


  /**
   * 注册用户并返回一个注册成功与否的布尔值
   * 
   * @param dto
   * @return
   */
  private boolean signup(Signup dto) {
    Member member = modelMapper.map(dto, Member.class); // dto to entity
    
    member.setPassword(bCrypt.encode(member.getPassword()));
    member.setNickname(member.getUsername()); // set default nickname
    member.setRegdate(new Date());
    String avatarPath = qiniuSpaceLink + "/default-avatars-0" + member.getAvatar() + ".png";
    member.setAvatar(avatarPath);
    Integer i = memberDao.save(member);
    return i > 0 ? true : false;
  }


  /**
   * 将用户保存再数据库中 并通过 findMemberByUsername 方法获取用户信息
   */
  @Override
  public Member signupAndGetMember(Signup dto) {
    if (!this.signup(dto)) {
      return null;
    }
    return memberDao.findMemberByUsername(dto.getUsername());
  }


  /**
   * 用户是否注册 参数类型：用户名、邮箱、手机号码
   */
  @Override
  public boolean isSignup(String value) {

    /*
     * TODO 邮箱验证有BUG 这里验证中文也通过
     */
    if (Tool.isEmail(value)) {
      if (memberDao.findUidByEmail(value) == null)
        return false;
      return true;
    }

    /*
     * 检测用户名
     */
    if (memberDao.findUidByUsername(value) == null)
      return false;
    return true;
  }


  /**
   * 通过用户名或者密码获取用户信息 主要应用与，安全框架拦截器查询
   */
  @Override
  public Member getMemberByNameOrEmail(String username) {
    return Tool.isEmail(username) ? memberDao.findMemberByEmail(username)
        : memberDao.findMemberByUsername(username);
  }


  /**
   * 通过用户名获取用户角色
   */
  @Override
  public List<Role> getMemberRole(String username) {
    return memberDao.findMemberRoleByUsername(username);
  }


  /**
   * 通过用户ID获取用户角色
   */
  @Override
  public List<Role> getMemberRole(long uid) {
    return memberDao.findMemberRoleByUid(uid);
  }


  /**
   * 通过ID获取用户信息
   */
  @Override
  public Me getMemberById(long uid) {
    Member me = memberDao.findMemberByUid(uid);
    return modelMapper.map(me, Me.class);
  }

  
  /**
   * 接受参数类型 MemberDto.HashAndKey
   * 一、方法逻辑如下：
   *    1.通过七牛空间地址和Key组成图片url
   *    2.获取DTO中的用户ID调用Dao层 保存至数据库
   */
  @Override
  public void uploadAvatarByQiniu(HashAndKey hashkey) {
    // TODO 更新需要将老头像删除
    String avatarLink = qiniuSpaceLink + "/" + hashkey.getKey();
    memberDao.uploadMemberAvatarByUid(hashkey.getUid(), avatarLink);
  }


  /**
   * 更新昵称
   */
  @Override
  public void uploadNickname(UploadNickname dto) {
    memberDao.uploadNicknameByUid(dto.getUid(), dto.getNickname());
  }


  @Override
  public String getQiniuAvatarAddress(long uid) {
    return memberDao.findMemberQiniuAvatarByUid(uid);
  }
}
