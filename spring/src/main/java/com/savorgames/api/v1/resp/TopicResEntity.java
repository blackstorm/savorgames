package com.savorgames.api.v1.resp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.savorgames.dao.dto.TopicAndMemberAndNode;
import com.savorgames.dao.dto.TopicReplyDaoDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public @ToString @Getter @Setter class TopicResEntity extends BaseResEntity{
  /**
   * 
   */
  private static final long serialVersionUID = 2808137850275706754L;
  
  private TopicAndMemberAndNode topic;
  private List<TopicReplyDaoDto> replys;
  private Object tags;
  
  public TopicResEntity(TopicAndMemberAndNode topic, List<TopicReplyDaoDto> replys, Object tags) {
    this.replys = replys;
    this.topic = topic;
    this.tags = tags;
    super.setStatusSucess();
  }
  
  public TopicResEntity setSuccess(TopicAndMemberAndNode topic, List<TopicReplyDaoDto> replys, Object tags){
    this.replys = replys;
    this.topic = topic;
    this.tags = tags;
    super.setStatusSucess();
    return this;
  }
}
