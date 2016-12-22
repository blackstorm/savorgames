package com.savorgames.service;

import java.util.List;

import com.savorgames.api.v1.dto.TagDto;
import com.savorgames.api.v1.dto.TopicDto;
import com.savorgames.dao.dto.TagDaoDto;
import com.savorgames.entity.Tag;
import com.savorgames.security.model.SGUser;

public interface TagService {
  public void saveTopicTags(TagDto.Create dto);
  public void updateTopicTags(TopicDto.Update dto, SGUser user);
  public List<Tag> getAll();
  public List<TagDaoDto> getTopicTagsByTid(long tid);
}
