package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokToDosData;
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

public class ToDosTests {

    @Test
    void getAllToDosTest() {

        LombokToDosData[] toDosGetRequestsData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/todos").
                        then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokToDosData[].class);

        assertNotNull(toDosGetRequestsData);
    }

    @Test
    void getSpecificToDoTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/toDosRequests/getToDoExpectedResult.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokToDosData expectedData = gson.fromJson(jsonReader, LombokToDosData.class);

        LombokToDosData toDosGetRequestData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/todos/" + expectedData.getId()).
                        then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokToDosData.class);

        assertEquals(expectedData.getId(), toDosGetRequestData.getId());
        assertEquals(expectedData.getUserId(), toDosGetRequestData.getUserId());
        assertEquals(expectedData.getTitle(), toDosGetRequestData.getTitle());
        assertEquals(expectedData.getCompleted(), toDosGetRequestData.getCompleted());
    }

    @Test
    void getSpecificUserToDosTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/toDosRequests/getSpecificUserToDos.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokToDosData[] expectedData = gson.fromJson(jsonReader, LombokToDosData[].class);

        LombokToDosData[] toDosGetRequestsData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/users/" + expectedData[0].getUserId() + "/todos").
                        then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokToDosData[].class);

        for (int i = 0; i < expectedData.length; i++) {
            assertEquals(toDosGetRequestsData[i].getUserId(), expectedData[i].getUserId());
            assertThat(Arrays.asList(toDosGetRequestsData)).
                    extracting(LombokToDosData::getId).
                    isNotNull();
            assertThat(Arrays.asList(toDosGetRequestsData)).
                    extracting(LombokToDosData::getTitle).
                    isNotNull();
            assertThat(Arrays.asList(toDosGetRequestsData)).
                    extracting(LombokToDosData::getCompleted).
                    isNotNull();
        }
    }

    @Test
    void createToDoTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/todosRequests/todosPostBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokToDosData sentData = gson.fromJson(jsonReader, LombokToDosData.class);

        LombokToDosData responseData =
                given().
                        spec(postRequestSpecification).
                        body(sentData).
                        when().
                        post("/todos").
                        then().
                        spec(postResponseSpecification).
                        extract().as(LombokToDosData.class);

        assertThat(Arrays.asList(responseData)).
                extracting(LombokToDosData::getId).
                isNotNull();
        assertEquals(sentData.getUserId(), responseData.getUserId());
        assertEquals(sentData.getTitle(), responseData.getTitle());
        assertEquals(sentData.getCompleted(), responseData.getCompleted());
    }

    @Test
    void editToDoViaPutRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/todosRequests/todosPutBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokToDosData sentData = gson.fromJson(jsonReader, LombokToDosData.class);

        LombokToDosData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        put("/todos/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokToDosData.class);

        assertEquals(sentData.getId(), responseData.getId());
        assertEquals(sentData.getUserId(), responseData.getUserId());
        assertEquals(sentData.getTitle(), responseData.getTitle());
        assertEquals(sentData.getCompleted(), responseData.getCompleted());
    }

    @Test
    void editToDoViaPatchRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/todosRequests/todosPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokToDosData sentData = gson.fromJson(jsonReader, LombokToDosData.class);

        InputStream stream2 = classLoader.getResourceAsStream("testData/dataToSend/todosRequests/todosPatchBody.json");
        JsonReader jsonReader2 = new JsonReader(new InputStreamReader(stream2));
        JsonObject sentDataObject = gson.fromJson(jsonReader2, JsonObject.class);
        Set<String> sentKeys = sentDataObject.keySet();

        LombokToDosData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        patch("/todos/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokToDosData.class);

        assertEquals(sentData.getId(), responseData.getId());
        if (sentKeys.contains("userId")) {
            assertEquals(responseData.getUserId(), sentData.getUserId());
        }
        if (sentKeys.contains("title")) {
            assertEquals(responseData.getTitle(), sentData.getTitle());
        }
        if (sentKeys.contains("completed")) {
            assertEquals(responseData.getCompleted(), sentData.getCompleted());
        }
    }

    @Test
    void deleteToDoRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/toDoRequests/todosPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokToDosData sentData = gson.fromJson(jsonReader, LombokToDosData.class);

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
