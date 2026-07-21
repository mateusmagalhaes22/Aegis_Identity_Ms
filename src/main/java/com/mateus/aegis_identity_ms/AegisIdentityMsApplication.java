package com.mateus.aegis_identity_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AegisIdentityMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AegisIdentityMsApplication.class, args);
	}

}
