package com.savorgames.entity;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_EMPTY) // 值为空不进行序列化
public @ToString @Getter @Setter class Topic {
  
  private long tid;
  private long uid;
  private long nid;
  private long tagId;
  private Date created;
  private String title;
  private String content;
  private int rank;
  private int looks;
  private int replys;
  private int status;
}
