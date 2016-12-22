package com.savorgames.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savorgames.dao.NodeDao;
import com.savorgames.entity.Node;
import com.savorgames.service.NodeService;

@Service
public class NodeServiceImpl implements NodeService {

  @Autowired
  private NodeDao nodeDao;

  /**
   * 获取所有节点
   */
  @Override
  public List<Node> getNodes() {
    return nodeDao.findAllNode();
  }

  @Override
  public Node getNodeById(long nid) {
    return null;
  }

  /**
   * 获取node
   */
  @Override
  public String getNodeName(long nid) {
    return nodeDao.findNodeNameByid(nid);
  }

}
