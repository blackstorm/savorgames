package com.savorgames.api.v1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qiniu.util.Auth;
import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.dto.MemberDto;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.dao.MemberDao;
import com.savorgames.security.model.SGUser;
import com.savorgames.service.MemberService;

@RestController
@RequestMapping(Sgv1.ROUTING + "/user")
@ResponseBody
public class UserController {

  @Value("${sg.qiniu.bucketName}")
  private String bucket;

  @Autowired
  private Auth qiniuAuth;

  @Autowired
  private BaseResEntity resEntity;

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberDao mdao;

  @RequestMapping(value = "", method = RequestMethod.GET)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> me() {
    SGUser userDetails =
        (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    long uid = userDetails.getUid();
    Map<String, Object> user = new HashMap<>();
    user.put("user", memberService.getMemberById(uid));
    // TODO 用户权限也可以从缓存中直接获取
    List<com.savorgames.entity.Role> roles = mdao.findMemberRoleByUid(uid);
    int rolesSize = roles.size();
    String[] userRoles = new String[rolesSize];
    for (int i = 0; i < rolesSize; i++) {
      String roleName = roles.get(i).getCode().replace("ROLE_", "").toLowerCase();
      userRoles[i] = roleName;
    }
    user.put("status", userRoles);
    BaseResEntity.ObjectData res = new BaseResEntity.ObjectData();
    return ResponseEntity.ok(res.setObjectDataStatusSucess(user));
  }

  /**
   * 获取上传token
   * 
   * @return
   */
  @RequestMapping(value = "/avatar/upload", method = RequestMethod.GET)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> avatarUploadToken() {
    String avatarKey = UUID.randomUUID().toString().replaceAll("-", "") + ".png";
    String token = qiniuAuth.uploadToken(bucket, avatarKey);// 返回 上传 TOKEN
    BaseResEntity.UploadAvatarToen tokenRes = new BaseResEntity.UploadAvatarToen();
    return ResponseEntity.ok(tokenRes.setUploadAvatarToenStatusSucess(token, avatarKey));
  }

  /**
   * 图片上传七牛成功回调地址 Method: POST
   * 
   * @param hashkey
   * @param result
   * @return
   */
  @RequestMapping(value = "/avatar/upload", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> avatarUploadSuccess(@RequestBody @Valid MemberDto.HashAndKey hashAndkey,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    SGUser userDetails =
        (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    long uid = userDetails.getUid();
    hashAndkey.setUid(uid);
    memberService.uploadAvatarByQiniu(hashAndkey);
    BaseResEntity.ObjectData res = new BaseResEntity.ObjectData();
    String QiniuAvatarAddr = memberService.getQiniuAvatarAddress(uid);
    return ResponseEntity.ok(res.setObjectDataStatusSucess(QiniuAvatarAddr));
  }

  /**
   * 更新昵称
   * 
   * @param dto
   * @param result
   * @return
   */
  @RequestMapping(value = "/nickname", method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> nicknameUpload(@RequestBody @Valid MemberDto.UploadNickname dto,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    SGUser userDetails =
        (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    dto.setUid(userDetails.getUid());
    memberService.uploadNickname(dto);
    return ResponseEntity.ok(resEntity.setStatusSucess("更新昵称成功"));
  }
}
