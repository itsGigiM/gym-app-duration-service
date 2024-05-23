package com.example.totalDuration;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
@EnableFeignClients
public class WorkHoursApplication {
	@Autowired
	@Lazy
	private EurekaClient eurekaClient;

	@Value("${spring.application.name}")
	private String appName;
	public static void main(String[] args) {
		SpringApplication.run(WorkHoursApplication.class, args);
	}

}
