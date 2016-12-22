package com.savorgames.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.service.TopicService;
import com.savorgames.service.exception.ServiceException;

@RestController
@RequestMapping(Sgv1.ROUTING + "/t/rank")
@ResponseBody
public class TopicRankRest {
  
  @Autowired
  private BaseResEntity resEntity;
  
  @Autowired
  private TopicService topicService;
  
  @RequestMapping(value = {"{id:\\d+}"}, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> rankUp(@PathVariable long id) {
    try {
      topicService.updateTopicRank(id);
    } catch (ServiceException e) {
      return ResponseEntity.badRequest().body(resEntity.setStatusFailed(e.getMessage()));
    }
    return ResponseEntity.ok(resEntity.setStatusSucess());
  }
}
