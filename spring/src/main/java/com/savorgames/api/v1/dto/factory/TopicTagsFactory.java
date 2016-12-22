package com.savorgames.api.v1.dto.factory;

import java.util.ArrayList;
import java.util.List;

import com.savorgames.api.v1.dto.TagDto;
import com.savorgames.api.v1.dto.TopicDto;
import com.savorgames.entity.TopicTags;

public class TopicTagsFactory {
  
  /**
   * TagDto.Create 生成 List<TopicTags>
   * @param dto
   * @return
   */
  public static List<TopicTags> createListTopicTag(TagDto.Create dto){
//    List<TopicTags> lt = new ArrayList<>();
//    long[] tags = dto.getTags();
//    for(long id : tags){
//      TopicTags tts = new TopicTags();
//      tts.setTopicId(dto.getTopicId());
//      tts.setTagId(id);
//      tts.setUid(dto.getUid());
//      lt.add(tts);
//    }
//    return lt;
    return tagsToListTopicTags(dto.getTags(),dto.getTopicId(),dto.getUid());
  }
  
  public static List<TopicTags> createListTopicTag(TopicDto.Update dto, long uid){
    return tagsToListTopicTags(dto.getTags(),dto.getTid(),uid);
  }
  
  /**
   * 
   * @param tags
   * @param topicId
   * @param uid
   * @return
   */
  public static List<TopicTags> tagsToListTopicTags(long[] tags, long topicId, long uid){
    List<TopicTags> lt = new ArrayList<>();
    for(long id : tags){
      TopicTags tts = new TopicTags();
      tts.setTopicId(topicId);
      tts.setUid(uid);
      tts.setTagId(id);
      lt.add(tts);
    }
    return lt;
  }
}
