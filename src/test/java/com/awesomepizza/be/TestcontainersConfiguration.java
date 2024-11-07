package com.awesomepizza.be;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.CockroachContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	CockroachContainer cockroachContainer() {
		return new CockroachContainer(DockerImageName.parse("cockroachdb/cockroach:v24.2.4"));
	}

}
