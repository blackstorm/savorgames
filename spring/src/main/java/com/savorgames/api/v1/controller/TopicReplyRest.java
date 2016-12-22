package com.savorgames.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.dto.ReplyDto;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.api.v1.resp.ResValidFailEntity;
import com.savorgames.api.v1.valid.ReQBodyValid;
import com.savorgames.dao.dto.TopicReplyDaoDto;
import com.savorgames.service.TopicReplyService;
import com.savorgames.service.TopicService;

@RestController
@RequestMapping(Sgv1.ROUTING + "/t/reply")
@ResponseBody
public class TopicReplyRest {
  
  @Autowired
  private ReQBodyValid reQBodyValid;
  
  @Autowired
  private TopicReplyService replyService;
  
  @Autowired
  private TopicService topicService;
  
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> pushReply(@RequestBody @Valid ReplyDto reply, BindingResult result) {
    ResValidFailEntity rvfe = reQBodyValid.validHasErrors(result);
    if (rvfe != null) {
      rvfe.setStatusFailed("表单错误");
      return ResponseEntity.badRequest().body(rvfe);
    }
    
    BaseResEntity.ObjectData od = new BaseResEntity.ObjectData();
    List<TopicReplyDaoDto> replys = replyService.saveReply(reply);
    topicService.updateReplys(reply.getTopicId()); // 异步
    // TODO 回复消息提醒---同时需要异步处理
    return ResponseEntity.ok(od.setObjectDataStatusSucess(replys));
  }
}
