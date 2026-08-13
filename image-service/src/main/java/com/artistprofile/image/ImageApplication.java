package com.artistprofile.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
//@SpringBootApplication(scanBasePackages = "com.artistprofile") // so that classes in sibling packages will also be scanned
@SpringBootApplication
public class ImageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageApplication.class, args);
	}

}
