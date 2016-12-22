package com.savorgames.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY) // 值为空不进行序列化
public class Node{
  private long nid;
  private String name;
  private String description;
  private String icon;
  private Date create;
  public long getNid() {
    return nid;
  }
  public void setNid(long nid) {
    this.nid = nid;
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
  @Override
  public String toString() {
    return "Node [nid=" + nid + ", name=" + name + ", description=" + description + ", icon=" + icon
        + ", create=" + create + "]";
  }
}
