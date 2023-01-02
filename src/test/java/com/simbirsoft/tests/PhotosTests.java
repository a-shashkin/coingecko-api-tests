package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokPhotosData;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
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

@Feature("Photos")
@Owner("allure8")
@Tag("photos_tests")
public class PhotosTests extends TestBase {

    @Test
    @AllureId("13936")
    @Story("Getting photos")
    @Tag("get_request_tests")
    void getAllPhotosTest() {

        LombokPhotosData[] photosGetRequestsData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/photos").
                        then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokPhotosData[].class);

        assertNotNull(photosGetRequestsData);
    }

    @Test
    @AllureId("13937")
    @Story("Getting photos")
    @Tag("get_request_tests")
    void getSpecificPhotoTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/photosRequests/getPhotoExpectedResult.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPhotosData expectedData = gson.fromJson(jsonReader, LombokPhotosData.class);

        LombokPhotosData photosGetRequestData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/photos/" + expectedData.getId()).
                        then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokPhotosData.class);

        assertEquals(expectedData.getId(), photosGetRequestData.getId());
        assertEquals(expectedData.getAlbumId(), photosGetRequestData.getAlbumId());
        assertEquals(expectedData.getTitle(), photosGetRequestData.getTitle());
        assertEquals(expectedData.getUrl(), photosGetRequestData.getUrl());
        assertEquals(expectedData.getThumbnailUrl(), photosGetRequestData.getThumbnailUrl());
    }

    @Test
    @AllureId("13938")
    @Story("Posting photos")
    @Tag("post_request_tests")
    void postPhotoTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/photosRequests/photosPostBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPhotosData sentData = gson.fromJson(jsonReader, LombokPhotosData.class);

        LombokPhotosData responseData =
                given().
                        spec(postRequestSpecification).
                        body(sentData).
                        when().
                        post("/photos").
                        then().
                        spec(postResponseSpecification).
                        extract().as(LombokPhotosData.class);

        assertThat(Arrays.asList(responseData)).
                extracting(LombokPhotosData::getId).
                isNotNull();
        assertEquals(sentData.getAlbumId(), responseData.getAlbumId());
        assertEquals(sentData.getTitle(), responseData.getTitle());
        assertEquals(sentData.getUrl(), responseData.getUrl());
        assertEquals(sentData.getThumbnailUrl(), responseData.getThumbnailUrl());
    }

    @Test
    @AllureId("13939")
    @Story("Posting photos")
    @Tag("put_request_tests")
    void editPhotoViaPutRequest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/photosRequests/photosPutBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPhotosData sentData = gson.fromJson(jsonReader, LombokPhotosData.class);

        LombokPhotosData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        put("/photos/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokPhotosData.class);

        assertEquals(sentData.getId(), responseData.getId());
        assertEquals(sentData.getAlbumId(), responseData.getAlbumId());
        assertEquals(sentData.getTitle(), responseData.getTitle());
        assertEquals(sentData.getUrl(), responseData.getUrl());
        assertEquals(sentData.getThumbnailUrl(), responseData.getThumbnailUrl());
    }

    @Test
    @AllureId("13940")
    @Story("Posting photos")
    @Tag("patch_request_tests")
    void editPhotoViaPatchRequest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/photosRequests/photosPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPhotosData sentData = gson.fromJson(jsonReader, LombokPhotosData.class);

        InputStream stream2 = classLoader.getResourceAsStream("testData/dataToSend/photosRequests/photosPatchBody.json");
        JsonReader jsonReader2 = new JsonReader(new InputStreamReader(stream2));
        JsonObject sentDataObject = gson.fromJson(jsonReader2, JsonObject.class);
        Set<String> sentKeys = sentDataObject.keySet();

        LombokPhotosData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        patch("/photos/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokPhotosData.class);

        assertEquals(sentData.getId(), responseData.getId());
        if (sentKeys.contains("albumId")) {
            assertEquals(sentData.getAlbumId(), responseData.getAlbumId());
        }
        if (sentKeys.contains("title")) {
            assertEquals(sentData.getTitle(), responseData.getTitle());
        }
        if (sentKeys.contains("url")) {
            assertEquals(sentData.getUrl(), responseData.getUrl());
        }
        if (sentKeys.contains("thumbnailUrl")) {
            assertEquals(sentData.getThumbnailUrl(), responseData.getThumbnailUrl());
        }
    }

    @Test
    @AllureId("13941")
    @Story("Deleting photos")
    @Tag("delete_request_tests")
    void deletePhotoRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/photosRequests/photosPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPhotosData sentData = gson.fromJson(jsonReader, LombokPhotosData.class);

        Response response =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        delete("/photos/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().response();
    }
}
