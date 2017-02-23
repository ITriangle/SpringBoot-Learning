package com.wangl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		Application application = new Application();

		application.logger.debug("This is a debug message");
		application.logger.info("This is an info message");
		application.logger.warn("This is a warn message");
		application.logger.error("This is an error message");

		application.logger.error("error {}", "param");

	}

}
