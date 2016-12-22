package com.savorgames.api.v1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.captcha.RegisterCaptcha;
import com.savorgames.api.v1.dto.MemberDto;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.api.v1.resp.ResValidFailEntity;
import com.savorgames.api.v1.valid.ReQBodyValid;
import com.savorgames.entity.Member;
import com.savorgames.mail.SGMailService;
import com.savorgames.service.MemberService;

@RestController
@RequestMapping(Sgv1.ROUTING + "/auth/register")
@ResponseBody
public class AuthRegisterController extends RegisterCaptcha {

  @Autowired
  private BaseResEntity baseResEntity;

  @Autowired
  private ReQBodyValid reQBodyValid;

  @Autowired
  private MemberService memberService;

  @Autowired
  private TemplateEngine te;

  @Autowired
  private SGMailService sgMailService;

  @Autowired
  private SSDB ssdb;

  /**
   * 返回一个注册使用的UUID
   *  TODO 注册验证码拉取有问题
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<?> getRegisterData() {
    String uuid = UUID.randomUUID().toString();
    String str = super.generateCreateText();
    Response rs = ssdb.setx(uuid, str, 180); // 设置三分钟后过期
    if (!rs.ok())
      return null;
    return ResponseEntity.ok(baseResEntity.setStatusSucess(uuid));
  }

  /**
   * 注册
   * @param sf
   * @param result
   * @param req
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> signup(@RequestBody @Valid MemberDto.Signup sf, BindingResult result,
      HttpServletRequest req) {

    /*
     * 验证表单
     */
    ResValidFailEntity rvfe = reQBodyValid.validHasErrors(result);
    if (rvfe != null) {
      rvfe.setStatusFailed("没有通过注册验证");
      return new ResponseEntity<>(rvfe, HttpStatus.OK);
    }

    /*
     * 验证验证码
     */
    Response re = ssdb.get(sf.getCaptchaid());
    if(!re.ok()){
      return new ResponseEntity<>(baseResEntity.setStatusFailed("拉取验证码失败，请刷新验证码"), HttpStatus.OK);
    }
    if(!re.asString().equals(sf.getCaptcha())){
      return new ResponseEntity<>(baseResEntity.setStatusFailed("验证码错误"), HttpStatus.OK);
    }
    ssdb.del(sf.getCaptchaid());

    /*
     * 检查用户名、邮箱等是否被注册
     */
    if (memberService.isSignup(sf.getEmail())) {
      if (memberService.isSignup(sf.getUsername())) {
        return new ResponseEntity<>(baseResEntity.setStatusFailed("邮箱和用户名已被注册"), HttpStatus.OK);
      }
      return new ResponseEntity<>(baseResEntity.setStatusFailed("邮箱已被注册"), HttpStatus.OK);
    } else if (memberService.isSignup(sf.getUsername())) {
      return new ResponseEntity<>(baseResEntity.setStatusFailed("用户名已被注册"), HttpStatus.OK);
    }

    /*
     * 调用service层注册用户，并将insert的用户返回回来
     */
    Member member = memberService.signupAndGetMember(sf);
    if (member == null) {
      return new ResponseEntity<>(baseResEntity.setStatusFailed("注册失败"), HttpStatus.OK);
    }

    /*
     * 准备发送邮件的主要内容：用户名，ID，主要填充内容等
     */
    Context context = new Context();
    context.setVariable("name", member.getNickname());
    context.setVariable("id", member.getUid());
    String emailContent = te.process("SignupSuccess", context);

    /*
     * 异步发送邮件
     */
    sgMailService.sendAsyncEmail(member.getEmail(), emailContent);

    return new ResponseEntity<>(baseResEntity.setStatusSucess(), HttpStatus.OK);
  }

  /**
   * 注册验证码
   */
  @RequestMapping(value = "/captcha", method = RequestMethod.GET)
  public void captcha(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String uuid = req.getParameter("id");

    /*
     * 判断 UUID是否为空
     */
    if (uuid == null || uuid.isEmpty())
      return;

    /*
     * 生成图片
     */
    Response res = ssdb.get(uuid);

    if (!res.ok())
      return;

    String text = res.asString();

    super.captcha(req, resp, text);
  }
}
