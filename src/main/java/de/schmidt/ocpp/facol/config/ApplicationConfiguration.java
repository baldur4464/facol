package de.schmidt.ocpp.facol.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration ("applicationConfig")
@EnableConfigurationProperties
@Getter
public class ApplicationConfiguration {

        public ApplicationConfiguration() {
                System.out.println("ApplicationConfiguration geladen");
        }

        @Value("${server.port}")
        private Integer serverPort;
}

