package com.mertkaanguzel.flightsearch;

import com.mertkaanguzel.flightsearch.config.RsaKeyProperties;
import com.mertkaanguzel.flightsearch.model.Airport;
import com.mertkaanguzel.flightsearch.repository.AirportRepository;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;


@SpringBootApplication
@SecurityScheme(
		name = "Flight Search Api", scheme = "bearer", type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER, bearerFormat =  "JWT")
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableScheduling
public class FlightSearchApiApplication {

	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("Flight Search API")
				.description("""
    All routes except the login and the register requires authentication.
    To create or update or delete resource, a user must have an admin role.
    After the login, a user must click on the Authorize button and paste the JWT token in the required place.
    Admin_username: jane, Admin_password: doe 
				"""));
	}


	@Bean
	CommandLineRunner initEntities(AirportRepository repository) {

		return args -> {
			Airport ankara = Airport.builder()
					.city("Ankara")
					.build();

			Airport istanbul = Airport.builder()
					.city("Istanbul")
					.build();

			repository.save(ankara);
			repository.save(istanbul);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(FlightSearchApiApplication.class, args);
	}

}
