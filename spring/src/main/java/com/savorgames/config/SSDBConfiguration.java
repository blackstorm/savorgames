package com.savorgames.config;

import org.nutz.ssdb4j.SSDBs;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.ssdb") // 从配置文件中读取
public class SSDBConfiguration {

  private static SSDB ssdb = null;

  private String host;

  private int port;

  private int timeout;

  /**
   * 必须要设置一个空的构造函数
   */
  public SSDBConfiguration() {

  }

  public SSDBConfiguration(String host, int port, int timeout) {
    this.host = host;
    this.port = port;
    this.timeout = timeout;
  }

  /**
   * 指定配置生成一个使用连接池的客户端
   * 
   * @return
   */
  @Bean
  public SSDB getSSDB() {
    if (ssdb == null) {
      System.out.println("初始化SSDB");
      ssdb = SSDBs.pool(this.host, this.port, this.timeout, null);
    }
    return ssdb;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }
}
