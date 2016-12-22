package com.savorgames.service;

import java.util.List;

import com.savorgames.api.v1.dto.ReplyDto;
import com.savorgames.dao.dto.TopicReplyDaoDto;
import com.savorgames.service.exception.ServiceException;

public interface TopicReplyService {
  public List<TopicReplyDaoDto> getTopicReplys(long tid);
  public List<TopicReplyDaoDto> saveReply(ReplyDto dto);
  public void upReplyRank(long id) throws ServiceException;
  public TopicReplyDaoDto getReply(long id);
}
