package com.savorgames.entity;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonInclude;


@Repository
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 值为空不进行序列化
public class Tag {

  private long id;
  private String name;
  private String description;
  private String icon;
  private Date create;
  private long topicNum;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Date getCreate() {
    return create;
  }

  public void setCreate(Date create) {
    this.create = create;
  }

  public long getTopicNum() {
    return topicNum;
  }

  public void setTopicNum(long topicNum) {
    this.topicNum = topicNum;
  }

  @Override
  public String toString() {
    return "Tag [id=" + id + ", name=" + name + ", description=" + description + ", icon=" + icon
        + ", create=" + create + ", topicNum=" + topicNum + "]";
  }
}
