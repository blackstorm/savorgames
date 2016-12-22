package com.savorgames.service;

import java.util.List;

import com.savorgames.entity.Node;

public interface NodeService {
  public List<Node> getNodes();
  public Node getNodeById(long nid);
  public String getNodeName(long nid);
}
