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
import com.savorgames.entity.GameStyle;
import com.savorgames.service.GameStyleService;

@RestController
@RequestMapping(Sgv1.ROUTING + "/game/style")
@ResponseBody
public class GameStyleRest {
  
  @Autowired
  private GameStyleService gameStyleService;
  
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<?> getGameStyles(){
    List<GameStyle> styles = gameStyleService.getGameStyles();
    BaseResEntity.ObjectData od = new BaseResEntity.ObjectData();
    return ResponseEntity.ok(od.setObjectDataStatusSucess(styles));
  }
}
