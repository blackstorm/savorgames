package com.savorgames.api.v1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;

@RestController
@RequestMapping(Sgv1.ROUTING + "/game")
@ResponseBody
public class GameController {
  
}