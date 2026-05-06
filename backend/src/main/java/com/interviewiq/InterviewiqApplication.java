package com.interviewiq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterviewiqApplication {

	public static void main(String[] args) {
	    System.out.println("Checking for Flyway...");
	    try {
	        Class.forName("org.flywaydb.core.Flyway");
	        System.out.println("Flyway is on the classpath!");
	    } catch (ClassNotFoundException e) {
	        System.out.println("Flyway NOT found on classpath!");
	    }
	    SpringApplication.run(InterviewiqApplication.class, args);
	}

}
