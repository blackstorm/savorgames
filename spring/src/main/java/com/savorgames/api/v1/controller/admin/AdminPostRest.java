package com.savorgames.api.v1.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.dao.TopicDao;
import com.savorgames.service.TopicService;
import com.savorgames.service.exception.ServiceException;

@RestController
@RequestMapping(Sgv1.ROUTING + "/admin/post")
@ResponseBody
public class AdminPostRest {
  @Autowired
  private TopicDao tdao;
  
  @Autowired
  private TopicService topicService;
  
  @Autowired
  private BaseResEntity resEntity;
  
  /**
   * 分页帖子
   * @param page
   * @return
   */
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public ResponseEntity<?> defaultSelect(@RequestParam long page) {
    BaseResEntity.ObjectData resEntity = new BaseResEntity.ObjectData();
    if (page == 0 || page == 1) {
      List<Map<String, Object>> res = tdao.selectAdminTopicsByPage(0,20);
      return ResponseEntity.ok(resEntity.setObjectDataStatusSucess(res));
    }
    List<Map<String, Object>> res = tdao.selectAdminTopicsByPage(20 * (page - 1),20);
    return ResponseEntity.ok(resEntity.setObjectDataStatusSucess(res));
  }
  
  /**
   * 删除
   * @param id
   * @return
   */
  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public ResponseEntity<?> delete(@PathVariable long id) {
    try {
      topicService.del(id);
      return ResponseEntity.ok(resEntity.setStatusSucess("删除成功"));
    } catch (ServiceException e) {
      return ResponseEntity.badRequest().body(resEntity.setStatusFailed("删除失败" + e.getMessage()));
    }
  }
  
  @RequestMapping(value = {"{id:\\d+}"}, method = RequestMethod.PATCH,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public ResponseEntity<?> changeNode(@PathVariable long id, @RequestBody long nid) {
    if (nid < 1) {
      return ResponseEntity.badRequest().body("Bad Request");
    }
    tdao.updateTopicNidByTid(id, nid);
    return ResponseEntity.ok(resEntity.setStatusSucess());
  }
}
