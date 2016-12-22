package com.savorgames.api.v1.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.BaseApiController;
import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.dto.TagDto;
import com.savorgames.api.v1.dto.TopicDto;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.api.v1.resp.ResValidFailEntity;
import com.savorgames.api.v1.resp.TopicResEntity;
import com.savorgames.dao.TopicDao;
import com.savorgames.dao.dto.TopicAndMemberAndNode;
import com.savorgames.entity.Topic;
import com.savorgames.security.model.SGUser;
import com.savorgames.service.NodeService;
import com.savorgames.service.TagService;
import com.savorgames.service.TopicReplyService;
import com.savorgames.service.TopicService;
import com.savorgames.service.exception.DeleteTopicException;
import com.savorgames.service.exception.UpdateTopicException;
import com.savorgames.utils.Sequence;

@RestController
@RequestMapping(Sgv1.ROUTING + "/t")
@ResponseBody
public class TopicController extends BaseApiController {

  @Autowired
  private BaseResEntity resEntity;

  @Autowired
  private TopicService topicService;

  @Autowired
  private TagService tagService;

  @Autowired
  private NodeService nodeService;
  
  @Autowired
  private TopicReplyService replyService;
  
  @Autowired
  private TopicDao tdao;


  /**
   * 获取
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = {"{id:\\d+}"}, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> get(@PathVariable long id) {
    TopicAndMemberAndNode t = topicService.getTopic(id);
    if (t == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    System.out.println(tagService.getTopicTagsByTid(id));
    TopicResEntity res = new TopicResEntity(t, replyService.getTopicReplys(id), tagService.getTopicTagsByTid(id));
    return ResponseEntity.ok(res);
  }

  /**
   * 发布
   * 
   * @param tc
   * @param result
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> save(@RequestBody @Valid TopicDto.Create tc, BindingResult result) {
    // 验证表单
    ResValidFailEntity rvfe = validHasErrors(result);
    if (rvfe != null) {
      rvfe.setStatusFailed("没有通过数据验证");
      return new ResponseEntity<>(rvfe, HttpStatus.OK);
    }

    // 验证节点是否存在 TODO 发布帖子时候需要验证节点是否存在，防止恶意发布，实现方式不是很完善 应该是返回 NULL 还是抛异常？
    String nodeName = nodeService.getNodeName(tc.getNid());
    if (nodeName == null) {
      return ResponseEntity.badRequest().body(resEntity.setStatusFailed("查无此节点"));
    }

    SGUser userDetails =
        (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // 封装用户ID
    tc.setUid(userDetails.getUid());
    Topic topic = topicService.saveOne(tc);

    // 如果用户有输入tag就进行保存，如果用户输入的TAGID不正确就不进行保存！BAD REQUEST！！！
    if (tc.getTags().length > 0) {
      TagDto.Create tagCreateDto = new TagDto.Create();
      tagCreateDto.setTags(tc.getTags());
      tagCreateDto.setUid(topic.getUid());
      tagCreateDto.setTopicId(topic.getTid());
//      System.out.println(topic);
      tagService.saveTopicTags(tagCreateDto);
    }


    BaseResEntity.ObjectData od = new BaseResEntity.ObjectData();
    // 发布成功 返回主题 TODO 这里返回一个ID或许比较合适
    return ResponseEntity.ok(od.setObjectDataStatusSucess(topic));
  }

  /**
   * 删除一个主题
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = {"{id:\\d+}"}, method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> del(@PathVariable long id) {
    SGUser userDetails =
        (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    try {
      topicService.del(id, userDetails);
    } catch (DeleteTopicException e) {
      return new ResponseEntity<>(baseResEntity.setStatusFailed(e.getMessage()),
          HttpStatus.UNAUTHORIZED);
    }
    return ResponseEntity.ok(baseResEntity.setStatusSucess());
  }


  /**
   * 更新主题
   * 
   * @param tu
   * @return
   */
  @RequestMapping(value = {"{id:\\d+}"}, method = RequestMethod.PATCH,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public ResponseEntity<?> update(@RequestBody TopicDto.Update tu) {
    SGUser userDetails =
        (SGUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (tu == null)
      return ResponseEntity.ok(baseResEntity.setStatusFailed("请填写更新内容"));

    try {
      topicService.update(tu, userDetails);
    } catch (UpdateTopicException e) {
      return new ResponseEntity<>(baseResEntity.setStatusFailed(e.getMessage()),
          HttpStatus.UNAUTHORIZED);
    }
    if (tu.getTags() != null && tu.getTags().length > 1)
      tagService.updateTopicTags(tu, userDetails);
    return ResponseEntity.ok(baseResEntity.setStatusSucess("更新成功"));
  }
  
  /**
   * 获取最后帖子
   * @param type
   */
  @RequestMapping(value = "/last", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> lastTopics(@RequestParam(value="type", required = false) String type) {
    if (type == null) {
      return ResponseEntity.badRequest().body(resEntity.setStatusFailed("BAD REQUEST!"));
    }
    BaseResEntity.ObjectData od = new BaseResEntity.ObjectData();
    switch (type) {
      case "node":
        return ResponseEntity.ok(od.setObjectDataStatusSucess(this.lastTopicByNode()));
      default:
        return ResponseEntity.badRequest().body(resEntity.setStatusFailed("BAD REQUEST!"));
    }
  }
  
  /**
   * 获取最后节点
   * @return
   */
  private List<Topic> lastTopicByNode() {
    return topicService.getNodesLastTopic();
  }
  
  /**
   * 获取rank主题
   * @return
   */
  private ResponseEntity<?> rank(){
//  List<com.savorgames.dao.dto.TopicDaoDto> res = topicService.getRankTopics();
    List<Map<String, Object>> day7Topics = tdao.select7Daytopis();
    Sequence seq = new Sequence();
    Map<String, Object>[] datas = seq.ordeyByRank(day7Topics);
    BaseResEntity.ObjectData od = new BaseResEntity.ObjectData();
    return ResponseEntity.ok(od.setObjectDataStatusSucess(datas));
  }
  
  /**
   * 获取最新主题
   * @return
   */
  private ResponseEntity<?> latest() {
    BaseResEntity.ObjectData od = new BaseResEntity.ObjectData();
    List<com.savorgames.dao.dto.TopicDaoDto> res = topicService.getLatestTopics();
    return ResponseEntity.ok(od.setObjectDataStatusSucess(res));
  }
  
  private ResponseEntity<?> nodeTopics(HttpServletRequest req) {
    BaseResEntity.ObjectData od = new BaseResEntity.ObjectData();
    String strid = req.getParameter("id");
    try {
      long id = Long.parseLong(strid);
      List<com.savorgames.dao.dto.TopicDaoDto> res = tdao.selectTopicsByNodeId(id);
      return ResponseEntity.ok(od.setObjectDataStatusSucess(res));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("bad req");
    }
  }
  
  /**
   * 按照获取主题
   * @param tab
   * @return
   */
  @RequestMapping(value="", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> key(@RequestParam String tab, HttpServletRequest req) {
    if (tab == null) {
      return ResponseEntity.badRequest().body(resEntity.setStatusFailed("Bad Request!!!!"));
    }
    switch (tab) {
      case "rank":
        return rank();
      case "latest":
        return latest();
      case "node":
        return nodeTopics(req);
      default:
        return ResponseEntity.badRequest().body(resEntity.setStatusFailed("Bad Request!!!!"));
    }
  }
  
  
}
