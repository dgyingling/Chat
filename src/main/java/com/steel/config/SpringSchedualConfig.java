package com.steel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.steel.scheduler.UpdateTask;

@Configuration
@EnableScheduling
public class SpringSchedualConfig {
	@Bean
	public UpdateTask getTask(){
		return new UpdateTask();
	}
}
