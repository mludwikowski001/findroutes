package com.mludwikowski.countryroutes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CountryRoutesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryRoutesApplication.class, args);
	}

}
