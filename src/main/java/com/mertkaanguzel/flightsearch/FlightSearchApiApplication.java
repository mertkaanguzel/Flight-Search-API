package com.mertkaanguzel.flightsearch;

import com.mertkaanguzel.flightsearch.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.Clock;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class FlightSearchApiApplication {
	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}


	public static void main(String[] args) {
		SpringApplication.run(FlightSearchApiApplication.class, args);
	}

}
