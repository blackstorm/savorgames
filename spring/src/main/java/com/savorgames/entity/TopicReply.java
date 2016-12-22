package com.savorgames.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public @ToString @Getter @Setter class TopicReply {
  private long id;
  private long topicId;
  private long parentId;
  private long memberId;
  private String content;
  private Date date;
  private int rank;
  private int status;
}
