package dev.pr.habittracker.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record ApiAspects(String header, String key) {
}
