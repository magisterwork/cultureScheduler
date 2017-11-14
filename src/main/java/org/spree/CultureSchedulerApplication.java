package org.spree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CultureSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultureSchedulerApplication.class, args);
	}
}
