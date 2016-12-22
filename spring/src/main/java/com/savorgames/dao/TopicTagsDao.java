package com.savorgames.dao;

import java.util.List;

import com.savorgames.entity.TopicTags;

public interface TopicTagsDao {
  public void insert(TopicTags topicTags);
  public void batchInsert(List<TopicTags> listTopicTags);
  public void deleteAllTopicTagsByTopicId(long tid);
}
