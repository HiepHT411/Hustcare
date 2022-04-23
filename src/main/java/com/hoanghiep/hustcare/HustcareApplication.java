package com.hoanghiep.hustcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

@SpringBootApplication
public class HustcareApplication {

	public static void main(String[] args) {
		//SpringApplication.run(HustcareApplication.class, args);
		Application.launch(MainApp.class, args);
	}

}
