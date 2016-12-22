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
import com.savorgames.cache.NodeCache;
//import com.savorgames.dao.NodeDao;
import com.savorgames.dao.TopicDao;
import com.savorgames.entity.Node;
import com.savorgames.service.NodeService;


@RestController
@RequestMapping(Sgv1.ROUTING + "/n")
@ResponseBody
public class NodeController{
  
//  @Autowired
//  private BaseResEntity resEntity;

  @Autowired
  private NodeService nodeService;
//  
//  @Autowired
//  private NodeDao ndao;

  @Autowired
  private NodeCache nodeCache;
  
  @Autowired
  private TopicDao tdao;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> show() {
    BaseResEntity.NodeRespEntity nodeRespEntity = new BaseResEntity.NodeRespEntity();
    
    /*
     * 查询缓存
     */
    List<Node> nodes = nodeCache.getNodes();
    
    /*
     * 缓存没有命中就去查数据库
     */
    ResponseEntity<?> res =
        nodes != null ? ResponseEntity.ok(nodeRespEntity.setStatusSucess("查询成功", nodes))
            : ResponseEntity.ok(nodeRespEntity.setStatusSucess("查询成功", nodeService.getNodes()));
    return res;
  }
  
  @RequestMapping(value = "/{nid}/t", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> topics(@PathVariable long nid, HttpServletRequest req) {
    if (nid == 0) return ResponseEntity.badRequest().build();
    long page;
    String pageStr = req.getParameter("page");
    if (pageStr == null) {
      page = 1;
    } else {
      page = Long.parseLong(pageStr);
    }
    List<Map<String, Object>> ts = tdao.selectTopicsByNodeIdByPage(nid, page, 25); // 主题S
//    Node node = ndao.findNodeByid(nid);
    BaseResEntity.ObjectData resEntity = new BaseResEntity.ObjectData();
    Map<String, Object> datas = new HashMap<>();
    datas.put("topics", ts);
//    datas.put("node", node);
    return ResponseEntity.ok(resEntity.setObjectDataStatusSucess(datas));
  }
}
