package com.network;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.network"})
public class LetsNetworkApplication {
	
	public static void main(String[] args) throws Exception{
		SpringApplication.run(LetsNetworkApplication.class, args);
	}

}
