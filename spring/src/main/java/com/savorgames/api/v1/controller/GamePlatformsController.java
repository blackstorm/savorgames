package com.savorgames.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.entity.GamePlatform;
import com.savorgames.service.GamePlatformService;

@RestController
@RequestMapping(Sgv1.ROUTING + "/game/platform")
@ResponseBody
public class GamePlatformsController {
  
  @Autowired
  private GamePlatformService gamePlatformService;
  
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<?> getGamePlatforms() {
    List<GamePlatform> platforms = gamePlatformService.getGamePlatforms();
    BaseResEntity.ObjectData od = new BaseResEntity.ObjectData();
    return ResponseEntity.ok(od.setObjectDataStatusSucess(platforms));
  }
}
