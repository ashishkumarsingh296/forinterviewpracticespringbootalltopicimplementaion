package com.example.forinterviewpracticespringbootalltopicimplementaion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAsync
@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy
public class ForinterviewpracticespringbootalltopicimplementaionApplication extends SpringBootServletInitializer {


	
		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
			return application.sources(ForinterviewpracticespringbootalltopicimplementaionApplication.class);
		}

	
	public static void main(String[] args) {
		SpringApplication.run(ForinterviewpracticespringbootalltopicimplementaionApplication.class, args);
	}


}
