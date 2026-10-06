package com.nivasasignal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NivasaSignalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NivasaSignalBackendApplication.class, args);
	}

}
