package com.savorgames.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.savorgames.api.v1.dto.TagDto.Create;
import com.savorgames.api.v1.dto.TopicDto.Update;
import com.savorgames.api.v1.dto.factory.TopicTagsFactory;
import com.savorgames.dao.TagDao;
import com.savorgames.dao.TopicTagsDao;
import com.savorgames.dao.dto.TagDaoDto;
import com.savorgames.entity.Tag;
import com.savorgames.entity.TopicTags;
import com.savorgames.security.model.SGUser;
import com.savorgames.service.TagService;

@Service
public class TagServiceImpl implements TagService {

  @Autowired
  private TopicTagsDao topicTagsDao;

  @Autowired
  private TagDao tagDao;

  /**
   * 批量插入topicTags
   */
  @Override
  @Async
  public void saveTopicTags(Create dto) {
    List<TopicTags> lt = new ArrayList<>();
    for (long id : dto.getTags()) {
      if (tagDao.findTagIsExistById(id) == 1) {
        TopicTags tts = new TopicTags();
        tts.setTopicId(dto.getTopicId());
        tts.setTagId(id);
        tts.setUid(dto.getUid());
        lt.add(tts);
      }
    }

    // List<TopicTags> listTopicTags = TopicTagsFactory.createListTopicTag(dto); TODO TopicTagsFactory.createListTopicTag 这个方法可能会被遗弃！
    if (lt.size() > 0) {
      topicTagsDao.batchInsert(lt);
    }
  }

  /**
   * 更新topic tags 1.删除老旧的tags 2.插入新的tag
   */
  @Override
  @Async
  public void updateTopicTags(Update dto, SGUser user) {
    List<TopicTags> listTopicTags = TopicTagsFactory.createListTopicTag(dto, user.getUid());
    topicTagsDao.deleteAllTopicTagsByTopicId(dto.getTid());
    topicTagsDao.batchInsert(listTopicTags);
  }

  /**
   * 查询所有tag
   */
  @Override
  public List<Tag> getAll() {
    return tagDao.findAllTag();
  }

  @Override
  public List<TagDaoDto> getTopicTagsByTid(long tid) {
    return tagDao.selectTagsByTid(tid);
  }

}
