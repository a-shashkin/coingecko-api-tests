package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokToDosData;
import com.simbirsoft.lombok.LombokUsersData;
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

@Feature("Users")
@Owner("allure8")
@Tag("users_tests")
public class UsersTests extends TestBase {

    @Test
    @AllureId("13959")
    @Story("Get users")
    @Tag("get_request_tests")
    void getAllUsersTest() {

        LombokUsersData[] usersGetRequestsData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/users").
                        then().
                        spec(responseSpecification).
                        log().body().
                        extract().as(LombokUsersData[].class);

        assertNotNull(usersGetRequestsData);
    }

    @Test
    @AllureId("13960")
    @Story("Get users")
    @Tag("get_request_tests")
    void getSpecificUserTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/usersRequests/getUserExpectedResult.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokUsersData expectedData = gson.fromJson(jsonReader, LombokToDosData.class);

        LombokUsersData usersGetRequestData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/users/" + expectedData.getId()).
                        then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokUsersData.class);

        assertEquals(expectedData.getId(), usersGetRequestData.getId());
        assertEquals(expectedData.getName(), usersGetRequestData.getName());
        assertEquals(expectedData.getUsername(), usersGetRequestData.getUsername());
        assertEquals(expectedData.getEmail(), usersGetRequestData.getEmail());
        assertEquals(expectedData.getAddress().getStreet(), usersGetRequestData.getAddress().getStreet());
        assertEquals(expectedData.getAddress().getSuite(), usersGetRequestData.getAddress().getSuite());
        assertEquals(expectedData.getAddress().getCity(), usersGetRequestData.getAddress().getCity());
        assertEquals(expectedData.getAddress().getZipcode(), usersGetRequestData.getAddress().getZipcode());
        assertEquals(expectedData.getAddress().getGeo().getLat(), usersGetRequestData.getAddress().getGeo().getLat());
        assertEquals(expectedData.getAddress().getGeo().getLng(), usersGetRequestData.getAddress().getGeo().getLng());
        assertEquals(expectedData.getPhone(), usersGetRequestData.getPhone());
        assertEquals(expectedData.getWebsite(), usersGetRequestData.getWebsite());
        assertEquals(expectedData.getCompany().getName(), usersGetRequestData.getCompany().getName());
        assertEquals(expectedData.getCompany().getCatchPhrase(), usersGetRequestData.getCompany().getCatchPhrase());
        assertEquals(expectedData.getCompany().getBs(), usersGetRequestData.getCompany().getBs());
    }

    @Test
    @AllureId("13962")
    @Story("Add users")
    @Tag("post_request_tests")
    void createUserRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/usersRequests/usersPostBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokUsersData sentData = gson.fromJson(jsonReader, LombokUsersData.class);

        LombokUsersData responseData =
                given().
                        spec(postRequestSpecification).
                        body(sentData).
                        when().
                        post("/users").
                        then().
                        spec(postResponseSpecification).
                        extract().as(LombokUsersData.class);

        assertThat(Arrays.asList(responseData)).
                extracting(LombokUsersData::getId).
                isNotNull();
        assertEquals(sentData.getName(), responseData.getName());
        assertEquals(sentData.getUsername(), responseData.getUsername());
        assertEquals(sentData.getEmail(), responseData.getEmail());
        assertEquals(sentData.getAddress().getStreet(), responseData.getAddress().getStreet());
        assertEquals(sentData.getAddress().getSuite(), responseData.getAddress().getSuite());
        assertEquals(sentData.getAddress().getCity(), responseData.getAddress().getCity());
        assertEquals(sentData.getAddress().getZipcode(), responseData.getAddress().getZipcode());
        assertEquals(sentData.getAddress().getGeo().getLat(), responseData.getAddress().getGeo().getLat());
        assertEquals(sentData.getAddress().getGeo().getLng(), responseData.getAddress().getGeo().getLng());
        assertEquals(sentData.getPhone(), responseData.getPhone());
        assertEquals(sentData.getWebsite(), responseData.getWebsite());
        assertEquals(sentData.getCompany().getName(), responseData.getCompany().getName());
        assertEquals(sentData.getCompany().getCatchPhrase(), responseData.getCompany().getCatchPhrase());
        assertEquals(sentData.getCompany().getBs(), responseData.getCompany().getBs());
    }

    @Test
    @AllureId("13963")
    @Story("Edit users")
    @Tag("put_request_tests")
    void editUserViaPutRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/usersRequests/usersPutBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokUsersData sentData = gson.fromJson(jsonReader, LombokUsersData.class);

        LombokUsersData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        put("/users/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokUsersData.class);

        assertEquals(sentData.getId(), responseData.getId());
        assertEquals(sentData.getName(), responseData.getName());
        assertEquals(sentData.getUsername(), responseData.getUsername());
        assertEquals(sentData.getEmail(), responseData.getEmail());
        assertEquals(sentData.getAddress().getStreet(), responseData.getAddress().getStreet());
        assertEquals(sentData.getAddress().getSuite(), responseData.getAddress().getSuite());
        assertEquals(sentData.getAddress().getCity(), responseData.getAddress().getCity());
        assertEquals(sentData.getAddress().getZipcode(), responseData.getAddress().getZipcode());
        assertEquals(sentData.getAddress().getGeo().getLat(), responseData.getAddress().getGeo().getLat());
        assertEquals(sentData.getAddress().getGeo().getLng(), responseData.getAddress().getGeo().getLng());
        assertEquals(sentData.getPhone(), responseData.getPhone());
        assertEquals(sentData.getWebsite(), responseData.getWebsite());
        assertEquals(sentData.getCompany().getName(), responseData.getCompany().getName());
        assertEquals(sentData.getCompany().getCatchPhrase(), responseData.getCompany().getCatchPhrase());
        assertEquals(sentData.getCompany().getBs(), responseData.getCompany().getBs());
    }

    @Test
    @AllureId("13964")
    @Story("Edit users")
    @Tag("patch_request_tests")
    void editUserViaPatchRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/usersRequests/usersPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokUsersData sentData = gson.fromJson(jsonReader, LombokUsersData.class);

        InputStream stream2 = classLoader.getResourceAsStream("testData/dataToSend/usersRequests/usersPatchBody.json");
        JsonReader jsonReader2 = new JsonReader(new InputStreamReader(stream2));
        JsonObject sentDataObject = gson.fromJson(jsonReader2, JsonObject.class);
        JsonObject company = sentDataObject.getAsJsonObject("company");
        JsonObject address = sentDataObject.getAsJsonObject("address");
        JsonObject geo = sentDataObject.getAsJsonObject("address").getAsJsonObject("geo");
        Set<String> sentKeysRoot = sentDataObject.keySet();
        Set<String> sentKeysCompany = company.keySet();
        Set<String> sentKeysAddress = address.keySet();
        Set<String> sentKeysGeo = geo.keySet();

        LombokUsersData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        patch("/users/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokUsersData.class);

        assertEquals(sentData.getId(), responseData.getId());
        if (sentKeysRoot.contains("name")) {
            assertEquals(responseData.getName(), sentData.getName());
        }
        if (sentKeysRoot.contains("username")) {
            assertEquals(responseData.getUsername(), sentData.getUsername());
        }
        if (sentKeysRoot.contains("email")) {
            assertEquals(responseData.getEmail(), sentData.getEmail());
        }
        if (sentKeysRoot.contains("address")) {
            if (sentKeysAddress.contains("street")) {
                assertEquals(responseData.getAddress().getStreet(), sentData.getAddress().getStreet());
            }
            if (sentKeysAddress.contains("suite")) {
                assertEquals(responseData.getAddress().getSuite(), sentData.getAddress().getSuite());
            }
            if (sentKeysAddress.contains("city")) {
                assertEquals(responseData.getAddress().getCity(), sentData.getAddress().getCity());
            }
            if (sentKeysAddress.contains("zipcode")) {
                assertEquals(responseData.getAddress().getZipcode(), sentData.getAddress().getZipcode());
            }
            if (sentKeysAddress.contains("geo")) {
                if (sentKeysGeo.contains("lat")) {
                    assertEquals(responseData.getAddress().getGeo().getLat(), sentData.getAddress().getGeo().getLat());
                }
                if (sentKeysGeo.contains("lng")) {
                    assertEquals(responseData.getAddress().getGeo().getLng(), sentData.getAddress().getGeo().getLng());
                }
            }
        }
        if (sentKeysRoot.contains("phone")) {
            assertEquals(responseData.getPhone(), sentData.getPhone());
        }
        if (sentKeysRoot.contains("website")) {
            assertEquals(responseData.getWebsite(), sentData.getWebsite());
        }
        if (sentKeysRoot.contains("company")) {
            if (sentKeysCompany.contains("name")) {
                assertEquals(responseData.getCompany().getName(), sentData.getCompany().getName());
            }
            if (sentKeysCompany.contains("catchPhrase")) {
                assertEquals(responseData.getCompany().getCatchPhrase(), sentData.getCompany().getCatchPhrase());
            }
            if (sentKeysCompany.contains("bs")) {
                assertEquals(responseData.getCompany().getBs(), sentData.getCompany().getBs());
            }
        }
    }

    @Test
    @AllureId("13965")
    @Story("Delete users")
    @Tag("delete_request_tests")
    void deleteUserRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/toDoRequests/todosPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokUsersData sentData = gson.fromJson(jsonReader, LombokUsersData.class);

        Response response =
                given().
                        spec(requestSpecification).
                        when().
                        delete("/users/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().response();
    }
}