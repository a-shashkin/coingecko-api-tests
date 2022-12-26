package com.simbirsoft.config;

import org.aeonbits.owner.Config;

@TestConfig.Sources({"classpath:config/config.properties"})
public interface TestConfig extends Config {

    String baseUrl();
    String xRapidapiKey();
}
