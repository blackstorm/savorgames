package com.savorgames.dao;

import java.util.List;

import com.savorgames.dao.dto.TagDaoDto;
import com.savorgames.entity.Tag;

public interface TagDao {
  public List<Tag> findAllTag();
  public Integer findTagIsExistById(long id);
  public List<TagDaoDto> selectTagsByTid(long tid);
}
