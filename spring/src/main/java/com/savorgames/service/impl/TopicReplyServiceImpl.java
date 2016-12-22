package com.savorgames.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.savorgames.api.v1.dto.ReplyDto;
import com.savorgames.dao.RankRecordDao;
import com.savorgames.dao.TopicReplyDao;
import com.savorgames.dao.dto.TopicReplyDaoDto;
import com.savorgames.entity.TopicReply;
import com.savorgames.security.model.SGUser;
import com.savorgames.service.TopicReplyService;
import com.savorgames.service.exception.ServiceException;

@Service
public class TopicReplyServiceImpl implements TopicReplyService{
  
  @Autowired
  private TopicReplyDao replyDao;
  
  @Autowired
  private RankRecordDao ranRecordDao;
  
  
  @Override
  public List<TopicReplyDaoDto> getTopicReplys(long tid) {
    return replyDao.selectReplysByTopicId(tid);
  }


  /**
   * 保存评论并返回评论列表
   */
  @Override
  public List<TopicReplyDaoDto> saveReply(ReplyDto dto) {
//    TopicReply replyContent = modelMapper.map(dto, TopicReply.class); // dto to entity
    SGUser userDetails = (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    TopicReply replyContent = new TopicReply();
    replyContent.setContent(dto.getContent());
    replyContent.setMemberId(userDetails.getUid());
    replyContent.setDate(new Date());
    replyContent.setTopicId(dto.getTopicId());
    replyContent.setParentId(dto.getParentId());
//    System.out.println(replyContent);
    replyDao.insterReply(replyContent);
    return this.getTopicReplys(replyContent.getTopicId());
  }


  /**
   *  rank + 1
   */
  @Override
  public void upReplyRank(long replyId) throws ServiceException {
    SGUser userDetails = (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String type = "reply";
    long uid = userDetails.getUid();
    Integer is = ranRecordDao.selectMemberIsRank(type, uid, replyId);
//    System.out.println(is);
    if (is != null) {
      throw new ServiceException("已经称赞过这个回复");
    }
    ranRecordDao.insert(type, uid, replyId);
    replyDao.updateRank(replyId);
  }


  /**
   *  查询评论
   */
  @Override
  public TopicReplyDaoDto getReply(long id) {
    return replyDao.selectReplyById(id);
  }

}
