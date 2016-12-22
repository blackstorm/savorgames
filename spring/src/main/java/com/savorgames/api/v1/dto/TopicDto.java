package com.savorgames.api.v1.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 如果一个成员变量为私有属性，并且具有GETSET方法，且GETSET 方法为public 那么他的继承类也会拥有此方法
 * 
 * @author inf2
 *
 */
@Data
public class TopicDto {
  private long tid;
  @NotEmpty
  @Size(min = 3, max = 80, message = "标题长度必须在 3-80 之间")
  private String title;
  @NotEmpty(message = "内容不能为空")
  private String content;

  /**
   * Create 创建主题
   * 
   * @author inf2
   *
   */
  public @ToString static class Create extends TopicDto {
    private @Getter @Setter long uid;

    @Min(value = 1, message = "您需要选择一个节点")
    private @Getter @Setter long nid;
    private @Getter @Setter long[] tags;
    private @Getter @Setter boolean remindMe = false;
  }

  @Data
  public static class Update {
    @Min(value = 1, message = "[update] bad request")
    private long tid;
    private String content;
    private long nid;
    private long[] tags;
  }
}
