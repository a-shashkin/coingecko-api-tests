package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokAlbumsData;
import com.simbirsoft.lombok.LombokPostsData;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static com.simbirsoft.specs.Specs.requestSpecification;
import static com.simbirsoft.specs.Specs.responseSpecification;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
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
    void getSpecificAlbumTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/albumsRequests/getAlbumExpectedResult.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokAlbumsData expectedData = gson.fromJson(jsonReader, LombokAlbumsData.class);

        LombokAlbumsData albumsGetRequestData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/albums/" + expectedData.getId()).
                        then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokAlbumsData.class);

        assertEquals(expectedData.getId(), albumsGetRequestData.getId());
        assertEquals(expectedData.getUserId(), albumsGetRequestData.getUserId());
        assertEquals(expectedData.getTitle(), albumsGetRequestData.getTitle());
    }

    @Test
    void getSpecificUserAlbums() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/albumsRequests/getSpecificUserAlbums.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokAlbumsData[] expectedData = gson.fromJson(jsonReader, LombokAlbumsData[].class);

        LombokAlbumsData[] albumsGetRequestsData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/users/" + expectedData[0].getUserId() + "/albums").
                        then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokAlbumsData[].class);

        for (int i = 0; i < expectedData.length; i++) {
            assertEquals(albumsGetRequestsData[i].getUserId(), expectedData[i].getUserId());
            assertThat(Arrays.asList(albumsGetRequestsData)).
                    extracting(LombokAlbumsData::getId).
                    isNotNull();
            assertThat(Arrays.asList(albumsGetRequestsData)).
                    extracting(LombokAlbumsData::getTitle).
                    isNotNull();
        }
    }
}
