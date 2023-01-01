package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokAlbumsData;
import com.simbirsoft.lombok.LombokPostsData;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static com.simbirsoft.specs.Specs.requestSpecification;
import static com.simbirsoft.specs.Specs.responseSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AlbumsTests extends TestBase {

    @Test
    void getAllAlbumsTest() {

        LombokAlbumsData[] albumsGetRequestsData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/albums").
                        then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokAlbumsData[].class);

        assertNotNull(albumsGetRequestsData);
    }

    @Test
    void getSpecificPostTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/postsRequests/getPostExpectedResult.json");
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
