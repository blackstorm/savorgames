package com.savorgames.dao;

import java.util.List;

import com.savorgames.entity.GamePlatform;

public interface GamePlatformDao {
  public List<GamePlatform> selectGamePlatforms();
}
