package com.simbirsoft.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class Specs {

    public static RequestSpecification requestSpecification =
            with().
                    log().all().
                    contentType(ContentType.JSON);

    public static ResponseSpecification responseSpecification =
            new ResponseSpecBuilder().
                    expectStatusCode(200).
                    build();
}
