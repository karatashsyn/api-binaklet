package com.binaklet.binaklet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
@SpringBootApplication
public class BinakletApplication {
	LoggingOptions options = LoggingOptions.getDefaultInstance();
	Logging logging = options.getService();
	public static void main(String[] args) {
		SpringApplication.run(BinakletApplication.class, args);
	}

}
