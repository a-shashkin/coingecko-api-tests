package com.simbirsoft.tests;

import com.simbirsoft.config.TestConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    public static void baseUrlSetup() {

        TestConfig testConfig = ConfigFactory.create(TestConfig.class);
        RestAssured.baseURI = testConfig.baseUrl();
    }
}
