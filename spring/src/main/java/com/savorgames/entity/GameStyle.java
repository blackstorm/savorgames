package com.savorgames.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_EMPTY) // 值为空不进行序列化
public @ToString @Getter @Setter class GameStyle {
  private long id;
  private String code;
  private String codeName;
}
