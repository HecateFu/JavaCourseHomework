package com.example.homework2;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;

@SpringBootApplication
public class Homework2Application {

	public static void main(String[] args) throws Exception {
		Class<?> helloClass = new HelloClassLoader().loadClass("Hello");
		Method method = helloClass.getMethod("hello");
		method.invoke(helloClass.newInstance());
	}

}
