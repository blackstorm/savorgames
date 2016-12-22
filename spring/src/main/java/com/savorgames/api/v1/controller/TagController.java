package com.savorgames.api.v1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.dao.TopicDao;
import com.savorgames.entity.Tag;
import com.savorgames.service.TagService;

@RestController
@RequestMapping(Sgv1.ROUTING + "/tag")
@ResponseBody
public class TagController{
  
  @Autowired
  private TagService tagService;
  
  @Autowired
  private TopicDao tdao;
  
  @RequestMapping( method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> show (){
    List<Tag> listTags = tagService.getAll();
    BaseResEntity.ObjectData resEntity = new BaseResEntity.ObjectData();
    return ResponseEntity.ok(resEntity.setObjectDataStatusSucess(listTags));
  }
  
  @RequestMapping(value = "/{id}/t", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> topics(@PathVariable long id, HttpServletRequest req) {
    if (id == 0) return ResponseEntity.badRequest().build();
    long page;
    String pageStr = req.getParameter("page");
    if (pageStr == null) {
      page = 1;
    } else {
      page = Long.parseLong(pageStr);
    }
    List<Map<String, Object>> ts = tdao.selectTopicsByTagIdByPage(id, page, 25);
    if (ts == null) return ResponseEntity.notFound().build();
    BaseResEntity.ObjectData resEntity = new BaseResEntity.ObjectData();
    Map<String, Object> datas = new HashMap<>();
    datas.put("topics", ts);
    return ResponseEntity.ok(resEntity.setObjectDataStatusSucess(datas));
  }
}
