package com.savorgames.dao;

import java.util.List;

import com.savorgames.entity.Node;

public interface NodeDao {
  public List<Node> findAllNode();
  public Node findNodeByid(long id);
  public String findNodeNameByid(long id);
}
