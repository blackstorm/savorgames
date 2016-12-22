package com.savorgames.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.savorgames.api.v1.dto.TopicDto.Create;
import com.savorgames.api.v1.dto.TopicDto.Update;
import com.savorgames.dao.RankRecordDao;
import com.savorgames.dao.TopicDao;
import com.savorgames.dao.dto.TopicAndMemberAndNode;
import com.savorgames.dao.dto.TopicDaoDto;
import com.savorgames.entity.Topic;
import com.savorgames.security.model.SGUser;
import com.savorgames.service.TopicService;
import com.savorgames.service.exception.DeleteTopicException;
import com.savorgames.service.exception.ServiceException;
import com.savorgames.service.exception.UpdateTopicException;

@Service
public class TopicServiceImpl implements TopicService {
  
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private TopicDao topicDao;
  
  @Autowired
  private RankRecordDao ranRecordDao;


  /**
   * 获取一个主题
   */
  @Override
  public TopicAndMemberAndNode getTopic(long id) {
    this.updateLooks(id);
    return topicDao.findOneTopicById(id);
  }


  /**
   * 保存一个主题
   */
  @Override
  public Topic saveOne(Create dto) {
    Topic topic = modelMapper.map(dto, Topic.class); // dto to entity
    topic.setCreated(new Date());
    topicDao.insertAndGetTid(topic);
    return topic;
  }


  /**
   * 更新主题查看次数
   */
  @Override
  public void updateLooks(long id) {
    topicDao.updateTopicLooksById(id);
  }


  /**
   * 标记帖子为删除状态
   */
  @Override
  public void del(long id,SGUser user) throws DeleteTopicException{
//    SGUser userDetails = (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    long userId = userDetails.getUid();
    Topic topic = topicDao.findTopicByTidAndUid(id, user.getUid());
    if (topic == null)
      throw new DeleteTopicException("没有权限删除此内容");
    topic.setStatus(1);
    topicDao.updateTopicIsDeleteById(topic);
  }
  
  /**
   * 帖子删除
   */
  @Override
  public void del(long id) throws ServiceException{
    try {
      topicDao.updateStatusIsDelByTid(id);
    } catch (Exception e) {
      throw new ServiceException("Service delete error");
    }
  }


  /**
   * 更新主题
   */
  @Override
  public void update(Update dto, SGUser user) throws UpdateTopicException {
    long tid = dto.getTid();
    
    /*
     * 判断用户是否有此权限
     */
    Topic topic = topicDao.findTopicByTidAndUid(tid, user.getUid());
    if(topic == null)
      throw new UpdateTopicException("没有权限更新此内容");
    
    /*
     * 获取准备数据
     */
    String content = dto.getContent();
    long nid = dto.getNid();
    
    /*
     * 动态更新判读
     */
    if(content != null) 
      this.updateTopicContent(tid, content);
    if (nid != topic.getNid() && nid > 0)
      this.updateTopicNid(tid, dto.getNid());
  }


  /**
   * 更新nid
   */
  @Override
  public void updateTopicNid(long tid, long nid) {
    topicDao.updateTopicNidByTid(tid, nid);
  }


  /**
   * 更新content
   */
  @Override
  public void updateTopicContent(long tid, String content) {
    topicDao.updateTopicContentByTid(tid, content);
  }

  /**
   * 查询node下最新帖子
   */
  @Override
  public List<Topic> getNodesLastTopic() {
    return topicDao.findLastTopicByAllNodes();
  }


  /**
   * 获取rank帖子
   */
  @Override
  public List<TopicDaoDto> getRankTopics() {
    return topicDao.find3Day20TopicByRank();
  }


  /**
   * 获取最新的20个帖子
   */
  @Override
  public List<TopicDaoDto> getLatestTopics() {
    return topicDao.find20TopicByTidDesc();
  }


  /**
   * 异步更新评论过条数
   */
  @Async
  @Override
  public void updateReplys(long id) {
    topicDao.updateTopicReplysByTid(id);
  }


  @Override
  public void updateTopicRank(long tid) throws ServiceException {
    SGUser userDetails = (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String type = "post";
    long uid = userDetails.getUid();
    Integer is = ranRecordDao.selectMemberIsRank(type, uid, tid);
    if (is != null) {
      throw new ServiceException("已经称赞过这个主题");
    }
    ranRecordDao.insert(type, uid, tid);
    topicDao.updateTopicRankByTid(tid);
  }

}