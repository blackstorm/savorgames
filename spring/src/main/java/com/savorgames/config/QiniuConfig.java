package com.savorgames.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qiniu.util.Auth;

@Configuration
@ConfigurationProperties(prefix = "sg.qiniu") //从配置文件中读取
public class QiniuConfig {
  
  private String accessKey;
  private String secretKey;
  
  @Bean
  public Auth qiniuAuth(){
    return Auth.create(accessKey, secretKey);
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
}
