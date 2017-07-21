package com.platform.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author Gunnar Hillert
 *
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConfigApplication.class, args);
	}

}
