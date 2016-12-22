package com.savorgames.api.v1.dto;

import lombok.Data;

@Data
public class TagDto {
  
  @Data
  public static class Create{
    private long topicId;
    private long uid;
    private long[] tags;
  }
}
