package com.byteentropy.config_core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "spring.profiles.active=native",
    "spring.cloud.config.server.native.search-locations=classpath:/test-config-repo"
})
@ActiveProfiles("test")
class ConfigCoreApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {}

    @Test
    void healthEndpoint_isAvailable() {
        // Updated: Health is now permitAll() in SecurityConfig
        ResponseEntity<String> entity = restTemplate.getForEntity("/actuator/health", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void securedConfigEndpoint_requiresAuthentication() {
        // Now returns 401 because of our SecurityConfig
        ResponseEntity<String> entity = restTemplate.getForEntity("/test-app/default", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void virtualThreads_areEnabled() {
        // Check the Spring property instead of System property
        boolean enabled = Boolean.parseBoolean(environment.getProperty("spring.threads.virtual.enabled"));
        assertThat(enabled).isTrue();
    }
}