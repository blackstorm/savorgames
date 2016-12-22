package com.savorgames.api.v1.controller;

import java.util.UUID;

import javax.validation.Valid;
import org.nutz.ssdb4j.spi.Response;

import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.dto.ForgetPassword;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.api.v1.resp.ResValidFailEntity;
import com.savorgames.api.v1.valid.ReQBodyValid;
import com.savorgames.dao.MemberDao;
import com.savorgames.entity.Member;
import com.savorgames.mail.SGMailService;

@RestController
@RequestMapping(Sgv1.ROUTING + "/auth/forget")
@ResponseBody
public class ForgetPasswordRest {
  
  @Autowired
  private BaseResEntity resEntity;
  
  @Autowired
  private ReQBodyValid reQBodyValid;
  
  @Autowired
  private MemberDao mdao;
  
  @Autowired
  private SSDB ssdb;
  
  @Autowired
  private TemplateEngine te;
  
  @Autowired
  private SGMailService sgMailService;
  
  @Autowired
  private PasswordEncoder bCrypt;
  
  /**
   * 发送忘记密码邮件
   * @param form
   * @param result
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> forgetPasswordByMail(@RequestBody @Valid ForgetPassword.Rest form, BindingResult result) {
    ResValidFailEntity rvfe = reQBodyValid.validHasErrors(result);
    if (rvfe != null) {
      rvfe.setStatusFailed("表单错误");
      return ResponseEntity.badRequest().body(rvfe);
    }
    Member member = mdao.findMemberByEmail(form.getEmail());
    
    if (member == null) {
      return ResponseEntity.badRequest().build();
    }
    
    String ssdbForgetPasswordKey = UUID.randomUUID().toString().replace("-", "");
    ssdb.setx(ssdbForgetPasswordKey, member.getUid(),300); // 失效时间300秒
    
    Context context = new Context();
    context.setVariable("key", ssdbForgetPasswordKey);
    String emailContent = te.process("forget_password", context);
    
    sgMailService.sendForgetPasswordMail(member.getEmail(), emailContent);
    
    return ResponseEntity.ok(resEntity.setStatusSucess());
  }
  
  
  /**
   * 重置密码
   * @param form
   * @param result
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> setNewPassword(@RequestBody @Valid ForgetPassword.NewPassword form, BindingResult result) {
    ResValidFailEntity rvfe = reQBodyValid.validHasErrors(result);
    if (rvfe != null) {
      rvfe.setStatusFailed("表单错误");
      return ResponseEntity.badRequest().body(rvfe);
    }
    String key = form.getKey();
    Response ssdbResponse = ssdb.get(key);
    if (!ssdbResponse.ok()) {
      return ResponseEntity.badRequest().body(resEntity.setStatusFailed("获取验证码失败，请重新发送邮件"));
    }
    long uid = ssdbResponse.asLong();
    String password = bCrypt.encode(form.getNewPassword());
    mdao.uploadPasswordByUid(uid, password);
    ssdb.del(key);
    return ResponseEntity.ok(resEntity.setStatusSucess("重置密码成功"));
  }
}
