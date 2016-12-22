package com.savorgames.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_EMPTY) // 值为空不进行序列化
public @ToString @Getter @Setter class Game {
  private long gid;
  private String name;
  private String otherName;
  private String des;
  private String frontcoverPath;
  private int status;
  private Date releaseDate;
  private long uid;
  private Date created;
}
