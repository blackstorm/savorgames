package com.savorgames;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.savorgames.api.v1.resp.BaseResEntity;
import com.savorgames.api.v1.valid.ReQBodyValid;

@SpringBootApplication
// TODO 与异步配置重复
@EnableAsync
@EnableScheduling // 开启异步
public class App implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) throws Exception {}

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
  
  @Bean
  public BaseResEntity baseResEntity(){
    return new BaseResEntity();
  }
  
  @Bean
  public ReQBodyValid reQBodyValid(){
    return new ReQBodyValid();
  }
}
