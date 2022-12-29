package com.simbirsoft.tests;

import com.simbirsoft.lombok.LombokPostsGetRequests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static com.simbirsoft.specs.Specs.requestSpecification;
import static com.simbirsoft.specs.Specs.responseSpecification;

public class PostsTests extends TestBase {

    Properties properties = new Properties();
    FileInputStream file;
    {
        try
        {
            file = new FileInputStream("./src/test/resources/config/config.properties");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllPostsTest() throws IOException {

        properties.load(file);

        Response response =
                given().
                        log().all().
                        header("user-agent", " Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                                "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.6.1 Safari/605.1.15").
                        get("/posts").
                then().
                        log().all().
                        statusCode(200).
                        extract().response();

    }

    @Test
    void lombokGetAllPostsTest() {

        LombokPostsGetRequests[] postsGetRequestsData =
                given().
                        spec(requestSpecification).
                when().
                        get("/posts").
                then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokPostsGetRequests[].class);
    }
}
