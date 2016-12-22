package com.savorgames.dao.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public @ToString @Getter @Setter class TopicDaoDto {
  private long topicId;
  private String topicTitle;
  private long memberId;
  private String memberNickname;
  private String memberAvatar;
}
