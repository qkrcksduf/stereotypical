package io.wisoft.virtual.cnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class CncApplication {

  public static void main(String[] args) {
    SpringApplication.run(CncApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  ThreadPoolTaskExecutor tp() {
    ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
    te.setCorePoolSize(10);
    te.setQueueCapacity(50);
    te.setMaxPoolSize(100);
    te.setThreadNamePrefix("myThread");
    return te;
  }

}
