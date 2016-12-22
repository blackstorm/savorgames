package com.savorgames.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_EMPTY) // 值为空不进行序列化
public @ToString @Getter @Setter class GamePlatform {
  private long platformId;
  private String platformName;
}
