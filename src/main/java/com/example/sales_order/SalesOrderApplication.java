package com.example.sales_order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SalesOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesOrderApplication.class, args);
	}

}
