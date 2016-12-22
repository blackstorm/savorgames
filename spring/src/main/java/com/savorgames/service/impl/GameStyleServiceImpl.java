package com.savorgames.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savorgames.dao.GameStyleDao;
import com.savorgames.entity.GameStyle;
import com.savorgames.service.GameStyleService;

@Service
public class GameStyleServiceImpl implements GameStyleService {
  
  @Autowired
  private GameStyleDao gameStyleDao;

  @Override
  public List<GameStyle> getGameStyles() {
    return gameStyleDao.selectGameStyles();
  }

}
