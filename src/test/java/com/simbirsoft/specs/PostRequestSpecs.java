package com.simbirsoft.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class PostRequestSpecs {

    public static RequestSpecification postRequestSpecification =
            with().
                    log().all().
                    contentType(ContentType.JSON);

    public static ResponseSpecification postResponseSpecification =
            new ResponseSpecBuilder().
                    expectStatusCode(201).
                    build();
}
