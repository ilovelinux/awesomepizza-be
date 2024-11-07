package com.awesomepizza.be;

import org.springframework.boot.SpringApplication;

public class TestAwesomepizzaBeApplication {

	public static void main(String[] args) {
		SpringApplication.from(AwesomepizzaBeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
