package com.byteentropy.config_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * config-core: A high-performance centralized configuration service.
 * Built with Java 21 Virtual Threads and Spring Cloud Config.
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigCoreApplication {

    public static void main(String[] args) {
        // Enable Virtual Threads for handling incoming config requests
        System.setProperty("jdk.virtualThreadScheduler.parallelism", "10");
        SpringApplication.run(ConfigCoreApplication.class, args);
    }
}