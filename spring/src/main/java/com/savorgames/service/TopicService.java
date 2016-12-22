package com.savorgames.service;

import java.util.List;

import com.savorgames.api.v1.dto.TopicDto;
import com.savorgames.dao.dto.TopicAndMemberAndNode;
import com.savorgames.entity.Topic;
import com.savorgames.security.model.SGUser;
import com.savorgames.service.exception.DeleteTopicException;
import com.savorgames.service.exception.ServiceException;
import com.savorgames.service.exception.UpdateTopicException;

public interface TopicService {
  public TopicAndMemberAndNode getTopic(long id);
  public Topic saveOne(TopicDto.Create dto);
  public void updateLooks(long id);
  public void updateReplys(long id);
  public void del(long id, SGUser sgUser) throws DeleteTopicException;
  public void del(long id) throws ServiceException;
  public void update(TopicDto.Update dto, SGUser sgUser) throws UpdateTopicException;
  public void updateTopicNid(long tid, long nid);
  public void updateTopicContent(long tid, String content);
  public void updateTopicRank(long tid) throws ServiceException;
  
  public List<Topic> getNodesLastTopic(); // 获获取node下最新发布
  public List<com.savorgames.dao.dto.TopicDaoDto> getRankTopics();
  public List<com.savorgames.dao.dto.TopicDaoDto> getLatestTopics();
  
}