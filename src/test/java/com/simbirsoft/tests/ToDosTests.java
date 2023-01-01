package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokAlbumsData;
import com.simbirsoft.lombok.LombokPhotosData;
import com.simbirsoft.lombok.LombokToDosData;
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


}
