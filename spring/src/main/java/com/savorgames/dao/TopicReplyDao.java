package com.savorgames.dao;

import java.util.List;

import com.savorgames.dao.dto.TopicReplyDaoDto;
import com.savorgames.entity.TopicReply;

public interface TopicReplyDao {
  public List<TopicReplyDaoDto> selectReplysByTopicId(long tid);
  public Integer insterReply(TopicReply reply);
  public void updateRank(long rid);
  public TopicReplyDaoDto selectReplyById(long rid);
}
