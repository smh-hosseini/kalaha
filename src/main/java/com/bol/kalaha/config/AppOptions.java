package com.bol.kalaha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kalaha")
public class AppOptions {
    private Integer pitSize;
    private Integer pitNumsPerPlayer;
}
