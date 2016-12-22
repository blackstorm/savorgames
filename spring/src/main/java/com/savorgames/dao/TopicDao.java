package com.savorgames.dao;

import java.util.List;
import java.util.Map;

import com.savorgames.dao.dto.TopicAndMemberAndNode;
import com.savorgames.entity.Topic;

public interface TopicDao {
  public TopicAndMemberAndNode findOneTopicById(long tid);
  public void updateTopicLooksById(long id);
  public void updateTopicReplysByTid(long tid);
  public void updateTopicRankByTid(long tid);
  public long insertAndGetTid(Topic topic);
  public long updateTopicIsDeleteById(Topic topic);
  public long updateStatusIsDelByTid(long tid);
  public void updateNodeIdByTopicId(long tid, long nid);
  public long deleteTopicById(long tid);
  public List<Map<String, Object>> selectTopicsByUser(long uid);
  public List<Map<String, Object>> select7Daytopis();
  public Topic findTopicByTidAndUid(long tid, long uid);
  public List<Map<String, Object>> selectTopicsByNodeIdByPage(long nid, long page, long num);
  public List<Map<String, Object>> selectTopicsByTagIdByPage(long id, long page, long num);
  
  /*
   * TODO 可能会被遗弃的DAO层方法 updateTopicNilOrContent
   */
  public long updateTopicNilOrContent(Topic topic);
  public long updateTopicNidByTid(long tid, long nid);
  public long updateTopicContentByTid(long tid, String content);
  
  public List<Topic> findLastTopicByAllNodes();
  public List<com.savorgames.dao.dto.TopicDaoDto> find3Day20TopicByRank();
  public List<com.savorgames.dao.dto.TopicDaoDto> find20TopicByTidDesc();
  public List<com.savorgames.dao.dto.TopicDaoDto> selectTopicsByNodeId(long id);
  
  public List<Map<String, Object>> selectAdminTopicsByPage(long page, long num);
}