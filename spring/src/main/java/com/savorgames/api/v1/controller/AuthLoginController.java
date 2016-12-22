package com.savorgames.api.v1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.dto.MemberDto;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.api.v1.resp.ResValidFailEntity;
import com.savorgames.api.v1.valid.ReQBodyValid;
import com.savorgames.security.SGTokenUtils;
import com.savorgames.security.model.SGUser;

@RestController
@RequestMapping(Sgv1.ROUTING + "/auth/login")
@ResponseBody
public class AuthLoginController {
  
  @Autowired
  private ReQBodyValid reQBodyValid;
  
  @Autowired
  private AuthenticationManager authenticationManager; // 通过config中注入依赖

  @Autowired
  private UserDetailsService userDetailsService;
  
  @Autowired
  private SGTokenUtils tokenUtils;
  
  /**
   * 登录函数
   * TODO 如果标记记住我 就为起生成30的token否则生成2小时
   * @param sf
   * @param result
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> signin(@RequestBody @Valid MemberDto.Signin sf, HttpServletRequest req,
      BindingResult result) throws AuthenticationException {
    
    /*
     * 验证表单
     */
    ResValidFailEntity rvfe = reQBodyValid.validHasErrors(result); 
    if (rvfe != null) {
      rvfe.setStatusFailed("没有通过登录验证");
      return new ResponseEntity<>(rvfe, HttpStatus.BAD_REQUEST);
    }
   
    /*
     * 构造未认证的UsernamePasswordAuthenticationToken
     * 通过AuthenticationManager:ProviderManager完成认证任务
     */
    UsernamePasswordAuthenticationToken upat =
        new UsernamePasswordAuthenticationToken(sf.getUsername(), sf.getPassword());
    Authentication authentication = this.authenticationManager.authenticate(upat);

    /*
     * 保存认证 
     */
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    /*
     * 刷新密码后认证，这样我们就可以生成令牌
     */
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(sf.getUsername());
    SGUser sguser = (SGUser) userDetails;
    sguser.setIp(req.getRemoteAddr());
    sguser.setUserAgent(req.getHeader("User-Agent"));
    String token = this.tokenUtils.generateToken(userDetails);
    
    BaseResEntity.Token tokenRes = new BaseResEntity.Token();
    return ResponseEntity.ok(tokenRes.setStatusSucess("登录成功", token));
  }
}
