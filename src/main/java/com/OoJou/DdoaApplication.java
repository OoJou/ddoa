package com.OoJou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class DdoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdoaApplication.class, args);
	}

}

