package com.savorgames.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.service.TopicReplyService;
import com.savorgames.service.exception.ServiceException;

@RestController
@RequestMapping(Sgv1.ROUTING + "/r/rank")
@ResponseBody
public class ReplyRankRest {
  
  @Autowired
  private BaseResEntity resEntity;
  
  @Autowired
  private TopicReplyService replyService;
  
  @RequestMapping(value = {"{id:\\d+}"}, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> up(@PathVariable long id) {
    if(id < 1) {
      return ResponseEntity.badRequest().body(resEntity.setStatusFailed("bad request"));
    }
    try {
      replyService.upReplyRank(id);
      return ResponseEntity.ok(resEntity.setStatusSucess("成功"));
    } catch (ServiceException e) {
      return ResponseEntity.badRequest().body(resEntity.setStatusFailed(e.getMessage()));
    }
  }
}
