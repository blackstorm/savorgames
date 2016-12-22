package com.savorgames.api.v1.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class ReplyDto {
  @Min(value = 1, message = "bad request")
  private long topicId;
  private int parentId;
  @NotEmpty
  @Size(min = 5, message = "字数小于5")
  private String content;
}
