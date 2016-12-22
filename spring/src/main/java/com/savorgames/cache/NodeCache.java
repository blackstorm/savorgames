package com.savorgames.cache;

import java.io.IOException;
import java.util.List;

import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savorgames.entity.Node;
import com.savorgames.service.NodeService;

@Service
public class NodeCache {
  @Value("${sg.node.cache}")
  private String nodeCacheName;

  @Value("${sg.node.cachetime}")
  private int nodeCacheTime;
  @Autowired
  private SSDB ssdb;

  @Autowired
  private NodeService nodeService;

  @Autowired
  private ObjectMapper jacksonMapper;

  public List<Node> getNodes() {
    Response res = ssdb.get(nodeCacheName);
    /*
     * 缓存查询未命中
     */
    if (!res.ok()) {
      List<Node> listNode = nodeService.getNodes();
      try {
        String listNodeJson = jacksonMapper.writeValueAsString(listNode);
        ssdb.setx(nodeCacheName, listNodeJson, nodeCacheTime);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        System.out.println("序列化失败");
      }
      return listNode;
    }
    
    /*
     * 反序列化
     */
    String json = res.asString();
    List<Node> nodes;
    try {
      nodes = jacksonMapper.readValue(json, new TypeReference<List<Node>>(){});
      return nodes;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
