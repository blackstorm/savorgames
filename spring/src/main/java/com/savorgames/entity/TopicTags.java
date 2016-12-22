package com.savorgames.entity;

public class TopicTags {
  private long id;
  private long tagId;
  private long topicId;
  private long uid;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getTagId() {
    return tagId;
  }

  public void setTagId(long tagId) {
    this.tagId = tagId;
  }

  public long getTopicId() {
    return topicId;
  }

  public void setTopicId(long topicId) {
    this.topicId = topicId;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  @Override
  public String toString() {
    return "TopicTags [id=" + id + ", tagId=" + tagId + ", topicId=" + topicId + ", uid=" + uid
        + "]";
  }

}
