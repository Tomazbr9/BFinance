package com.tomazbr9.bfinance;

import org.springframework.boot.SpringApplication;

public class TestBfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.from(BfinanceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
