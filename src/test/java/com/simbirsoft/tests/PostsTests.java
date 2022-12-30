package com.simbirsoft.tests;

import com.simbirsoft.lombok.LombokPostsData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static com.simbirsoft.specs.Specs.requestSpecification;
import static com.simbirsoft.specs.Specs.responseSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void getSpecificUserTest() throws IOException {

        properties.load(file);

        Response response =
                given().
                        log().all().
                        header("user-agent", " Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                                "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.6.1 Safari/605.1.15").
                        get("/posts/2").
                        then().
                        log().all().
                        statusCode(200).
                        extract().response();

        Integer id = response.path("id");

        assertEquals(2, id);
    }

    @Test
    void lombokGetAllPostsTest() {

        LombokPostsData[] postsGetRequestsData =
                given().
                        spec(requestSpecification).
                when().
                        get("/posts").
                then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokPostsData[].class);

    }

    @Test
    void lombokGetSpecificPostTest() {

        LombokPostsData postsGetRequestsData =
                given().
                        spec(requestSpecification).
                when().
                        get("/posts/2").
                then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokPostsData.class);

        assertEquals(2, postsGetRequestsData.getId());
    }
}
