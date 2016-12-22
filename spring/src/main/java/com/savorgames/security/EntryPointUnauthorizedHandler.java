package com.savorgames.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.savorgames.api.v1.resp.BaseResEntity;

/*
 * 认证失败的解决方案
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
  
  @Autowired
  private BaseResEntity baseResEntity;
  
  @Autowired
  private ObjectMapper mapper;

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
          throws IOException, ServletException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json; charset=utf-8");
    httpServletResponse.setStatus(401);
    PrintWriter out = httpServletResponse.getWriter();
    
    BaseResEntity res = baseResEntity.setStatusFailed("验证失败：Token失效或用户名密码错误 (:");
    out.print(mapper.writeValueAsString(res));
    out.flush();
    out.close();
//    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, mapper.writeValueAsString(res));
  }

}
