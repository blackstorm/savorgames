package com.savorgames.dao;

import java.util.List;

import com.savorgames.entity.GameStyle;

public interface GameStyleDao {
  public List<GameStyle> selectGameStyles();
}
