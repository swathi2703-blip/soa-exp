package com.example.config_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConfigServerApplicationTests {

	@Autowired
	private EnvironmentRepository environmentRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void servesEmployeeServiceConfiguration() {
		Environment environment = environmentRepository.findOne("employee-service", "default", null);

		assertThat(environment.getPropertySources())
			.anySatisfy(source -> {
				assertThat(source.getSource().get("server.port")).isEqualTo("8081");
				assertThat(source.getSource().get("spring.datasource.url"))
					.isEqualTo("jdbc:mysql://localhost:3306/employee_db");
			});
	}

}
