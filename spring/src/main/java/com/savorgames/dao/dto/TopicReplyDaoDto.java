package com.savorgames.dao.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public @ToString @Setter @Getter class TopicReplyDaoDto {
  private long id;
  private long parentId;
  private long memberId;
  private String memberAvatar;
  private String memberNickname;
  private String content;
  private Date date;
  private int rank;
}
