package com.safety.net.alerts.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class ActuatorHttpExchangesConfiguration {

    @Bean
    public HttpExchangeRepository customHttpExchangeRepository() {
        return new FileLoggingHttpExchangeRepository();
    }

    public static class FileLoggingHttpExchangeRepository implements HttpExchangeRepository {

        private static final Logger successLogger = LoggerFactory.getLogger("http.success");
        private static final Logger errorLogger = LoggerFactory.getLogger("http.error");

        private static final Logger debugLogger = LoggerFactory.getLogger("application.debug");

        @Override
        public List<HttpExchange> findAll() {
            return null;
        }

        @Override
        public void add(HttpExchange exchange) {

            if (exchange.getResponse().getStatus() <= 299) {
                successLogger.info("Request: " + exchange.getRequest().getUri() + " - Response: " + exchange.getResponse().getStatus());
            }
            else if (exchange.getResponse().getStatus() > 399) {
                errorLogger.error("Request: " + exchange.getRequest().getUri() + " - Response: " + exchange.getResponse().getStatus());
            }
            debugLogger.atDebug().log();
            debugLogger.debug(exchange.getResponse().toString());
        }
    }
}


