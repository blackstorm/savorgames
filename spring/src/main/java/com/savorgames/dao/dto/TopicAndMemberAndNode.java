package com.savorgames.dao.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public @ToString @Getter @Setter class TopicAndMemberAndNode extends TopicDaoDto{
  private String topicContent;
  private Date topicDate;
  private long topicRank;
  private long topicLooks;
  private long topicReplys;
  private long nodeId;
  private String nodeName;
}
