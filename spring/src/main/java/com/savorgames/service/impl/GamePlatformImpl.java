package com.savorgames.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savorgames.dao.GamePlatformDao;
import com.savorgames.entity.GamePlatform;
import com.savorgames.service.GamePlatformService;

@Service
public class GamePlatformImpl implements GamePlatformService {
  
  @Autowired
  private GamePlatformDao gamePlatformDao;

  @Override
  public List<GamePlatform> getGamePlatforms() {
    return gamePlatformDao.selectGamePlatforms();
  }

}
