package de.schmidt.ocpp.facol.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@Getter
public class ApplicationConfiguration {
        @Value("${server.testport}")
        private Integer serverPort;

        @Value("${server.address}")
        private String serverAddress;
}

