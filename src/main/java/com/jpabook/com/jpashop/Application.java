package com.jpabook.com.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		Hello hello = new Hello();
		hello.setData("hello");
		String data = hello.getData();

		System.out.println("data = " + data);

	}





}
