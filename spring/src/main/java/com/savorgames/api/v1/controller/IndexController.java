package com.savorgames.api.v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.savorgames.api.v1.Sgv1;

@RestController
@RequestMapping(Sgv1.ROUTING)
@ResponseBody
public class IndexController {

  @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
  public ResponseEntity<?> home() {
    return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
  }
}
