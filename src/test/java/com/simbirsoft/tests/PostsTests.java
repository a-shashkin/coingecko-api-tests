package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokPostsData;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static com.simbirsoft.specs.Specs.requestSpecification;
import static com.simbirsoft.specs.Specs.responseSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void getAllPostsTest() {

        LombokPostsData[] postsGetRequestsData =
                given().
                        spec(requestSpecification).
                when().
                        get("/posts").
                then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokPostsData[].class);

        assertNotNull(postsGetRequestsData);
    }

    @Test
    void getSpecificPostTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/getPostExpectedResult.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPostsData expectedData = gson.fromJson(jsonReader, LombokPostsData.class);

        LombokPostsData postsGetRequestsData =
                given().
                        spec(requestSpecification).
                when().
                        get("/posts/" + expectedData.getId()).
                then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokPostsData.class);

        assertEquals(expectedData.getId(), postsGetRequestsData.getId());
        assertEquals(expectedData.getUserId(), postsGetRequestsData.getUserId());
        assertEquals(expectedData.getTitle(), postsGetRequestsData.getTitle());
        assertEquals(expectedData.getBody(), postsGetRequestsData.getBody());
    }
}
