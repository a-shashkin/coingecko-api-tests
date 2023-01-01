package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokAlbumsData;
import com.simbirsoft.lombok.LombokPhotosData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Set;

import static com.simbirsoft.specs.PostRequestSpecs.postRequestSpecification;
import static com.simbirsoft.specs.PostRequestSpecs.postResponseSpecification;
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
    void getSpecificUserAlbumsTest() {

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

    @Test
    void getAlbumPhotosTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/albumsRequests/getSpecificAlbumPhotos.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPhotosData[] expectedData = gson.fromJson(jsonReader, LombokPhotosData[].class);

        LombokPhotosData[] photosData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/albums/" + expectedData[0].getAlbumId() + "/photos").
                        then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokPhotosData[].class);

        for (int i = 0; i < expectedData.length; i++) {
            assertEquals(photosData[i].getAlbumId(), expectedData[i].getAlbumId());
            assertThat(Arrays.asList(photosData)).
                    extracting(LombokPhotosData::getId).
                    isNotNull();
            assertThat(Arrays.asList(photosData)).
                    extracting(LombokPhotosData::getTitle).
                    isNotNull();
            assertThat(Arrays.asList(photosData)).
                    extracting(LombokPhotosData::getUrl).
                    isNotNull();
            assertThat(Arrays.asList(photosData)).
                    extracting(LombokPhotosData::getThumbnailUrl).
                    isNotNull();
        }
    }

    @Test
    void createAlbumTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/albumsRequests/albumsPostBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokAlbumsData sentData = gson.fromJson(jsonReader, LombokAlbumsData.class);

        LombokAlbumsData responseData =
                given().
                        spec(postRequestSpecification).
                        body(sentData).
                        when().
                        post("/albums").
                        then().
                        spec(postResponseSpecification).
                        extract().as(LombokAlbumsData.class);

        assertThat(Arrays.asList(responseData)).
                extracting(LombokAlbumsData::getId).
                isNotNull();
        assertEquals(sentData.getUserId(), responseData.getUserId());
        assertEquals(sentData.getTitle(), responseData.getTitle());
    }

    @Test
    void editAlbumViaPutRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/albumsRequests/albumsPutBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokAlbumsData sentData = gson.fromJson(jsonReader, LombokAlbumsData.class);

        LombokAlbumsData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        put("/albums/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokAlbumsData.class);

        assertEquals(sentData.getId(), responseData.getId());
        assertEquals(sentData.getUserId(), responseData.getUserId());
        assertEquals(sentData.getTitle(), responseData.getTitle());
    }

    @Test
    void editAlbumViaPatchRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/albumsRequests/albumsPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokAlbumsData sentData = gson.fromJson(jsonReader, LombokAlbumsData.class);

        InputStream stream2 = classLoader.getResourceAsStream("testData/dataToSend/albumsRequests/albumsPatchBody.json");
        JsonReader jsonReader2 = new JsonReader(new InputStreamReader(stream2));
        JsonObject sentDataObject = gson.fromJson(jsonReader2, JsonObject.class);
        Set<String> sentKeys = sentDataObject.keySet();

        LombokAlbumsData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        patch("/albums/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokAlbumsData.class);

        assertEquals(sentData.getId(), responseData.getId());
        if (sentKeys.contains("userId")) {
            assertEquals(responseData.getUserId(), sentData.getUserId());
        }
        if (sentKeys.contains("title")) {
            assertEquals(responseData.getTitle(), sentData.getTitle());
        }
    }

    @Test
    void deleteAlbumRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/albumsRequests/albumsPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokAlbumsData sentData = gson.fromJson(jsonReader, LombokAlbumsData.class);

        Response response =
                given().
                        spec(requestSpecification).
                        when().
                        delete("/posts/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().response();
    }
}
