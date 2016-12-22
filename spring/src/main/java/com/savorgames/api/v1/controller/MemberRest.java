package com.savorgames.api.v1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.dao.MemberDao;
import com.savorgames.dao.TopicDao;

@RestController
@RequestMapping(Sgv1.ROUTING + "/member")
@ResponseBody
public class MemberRest {
  
  @Autowired
  private MemberDao mdao;
  
  @Autowired
  private TopicDao tdao;
  
  @RequestMapping(value = "/{username}", method = RequestMethod.GET)
  public ResponseEntity<?> getMember(@PathVariable String username) {
    Map<String, Object> member = mdao.selectMemberByUsername(username);
    return this.getMemberResEntity(member);
  }
  
  
  @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getMember(@PathVariable long id) {
    Map<String, Object> member = mdao.selectMemberByUid(id);
    return this.getMemberResEntity(member);
  }
  
  
  private ResponseEntity<?> getMemberResEntity(Map<String, Object> member) {
    if (member == null) {
      return ResponseEntity.notFound().build();
    }
    long uid = (long) member.get("uid");
    List<Map<String, Object>> memberTopics = tdao.selectTopicsByUser(uid);
    Map<String, Object> res = new HashMap<>();
    res.put("information", member);
    res.put("topics", memberTopics);
    res.put("status", "ok");
    BaseResEntity.ObjectData entity = new BaseResEntity.ObjectData();
    return ResponseEntity.ok(entity.setObjectDataStatusSucess(res));
  }
}
