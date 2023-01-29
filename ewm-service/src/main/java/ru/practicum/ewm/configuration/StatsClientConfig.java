package ru.practicum.ewm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.client.StatsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsClientConfig {
    @Value("${stats-server.url}")
    private String server;

    @Bean
    StatsClient client() {
        return new StatsClient(WebClient.create(server));
    }
}
