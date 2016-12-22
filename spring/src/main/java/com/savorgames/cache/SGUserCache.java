package com.savorgames.cache;

import java.io.IOException;

import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savorgames.entity.Member;
import com.savorgames.service.MemberService;

@Service
public class SGUserCache {
  
  @Value("${sg.user.cachetime}")
  private int cacheTime;
  
  @Autowired
  private MemberService memberService;
  
  @Autowired
  private ObjectMapper jacksonMapper;
  
  @Autowired
  private SSDB ssdb;
  
  public Member loadUserByUsername(String usernameOrEmail){
    Response res = ssdb.get(usernameOrEmail);
    /*
     * 缓存未命中
     */
    if(!res.ok()){
      Member user = memberService.getMemberByNameOrEmail(usernameOrEmail);
      try {
        String userJson = jacksonMapper.writeValueAsString(user);
        ssdb.setx(usernameOrEmail, userJson, cacheTime);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return user;
    }
    /*
     * 缓存命中
     */
    String userJson = res.asString();
    Member user;
    try {
      user = jacksonMapper.readValue(userJson, Member.class);
      return user;
    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
