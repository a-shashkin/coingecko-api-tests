package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokCommentsData;
import com.simbirsoft.lombok.LombokPostsData;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static com.simbirsoft.specs.PostRequestSpecs.postRequestSpecification;
import static com.simbirsoft.specs.PostRequestSpecs.postResponseSpecification;
import static io.restassured.RestAssured.*;
import static com.simbirsoft.specs.Specs.requestSpecification;
import static com.simbirsoft.specs.Specs.responseSpecification;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Posts")
@Owner("allure8")
@Tag("posts_tests")
public class PostsTests extends TestBase {

    @Test
    @AllureId("13918")
    @Story("Getting posts")
    @Tag("get_request_tests")
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
    @AllureId("13919")
    @Story("Getting posts")
    @Tag("get_request_tests")
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

    @Test
    @AllureId("13920")
    @Story("Getting comments on posts")
    @Tag("get_request_tests")
    void getCommentsForPostViaPostsRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/postsRequests/getCommentExpectedResult.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokCommentsData expectedData = gson.fromJson(jsonReader, LombokCommentsData.class);

        LombokCommentsData[] commentsGetRequestsData =
                given().
                        spec(requestSpecification).
                when().
                        get("/posts/" + expectedData.getPostId() + "/comments").
                then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokCommentsData[].class);

        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getPostId).
                contains(expectedData.getPostId());
        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getId).
                isNotNull();
        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getBody).
                isNotNull();
        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getName).
                isNotNull();
        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getEmail).
                isNotNull();
    }

    @Test
    @AllureId("13921")
    @Story("Getting comments on posts")
    @Tag("get_request_tests")
    void getCommentsForPostViaCommentsPostIdRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/postsRequests/getCommentExpectedResult.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokCommentsData expectedData = gson.fromJson(jsonReader, LombokCommentsData.class);

        LombokCommentsData[] commentsGetRequestsData =
                given().
                        spec(requestSpecification).
                        when().
                        get("/comments?postId=" + expectedData.getPostId()).
                        then().
                        spec(responseSpecification).
                        log().all().
                        extract().as(LombokCommentsData[].class);

        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getPostId).
                contains(expectedData.getPostId());
        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getId).
                isNotNull();
        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getBody).
                isNotNull();
        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getName).
                isNotNull();
        assertThat(Arrays.asList(commentsGetRequestsData)).
                extracting(LombokCommentsData::getEmail).
                isNotNull();
    }

    @Test
    @AllureId("13922")
    @Story("Creating posts")
    @Tag("post_request_tests")
    void createPostTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/postsRequests/postsPostBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPostsData sentData = gson.fromJson(jsonReader, LombokPostsData.class);

        LombokPostsData responseData =
                given().
                        spec(postRequestSpecification).
                        body(sentData).
                        when().
                        post("/posts").
                        then().
                        spec(postResponseSpecification).
                        extract().as(LombokPostsData.class);

        assertThat(Arrays.asList(responseData)).
                extracting(LombokPostsData::getId).
                isNotNull();
        assertEquals(sentData.getUserId(), responseData.getUserId());
        assertEquals(sentData.getTitle(), responseData.getTitle());
        assertEquals(sentData.getBody(), responseData.getBody());
    }

    @Test
    @AllureId("13923")
    @Story("Updating posts")
    @Tag("put_request_tests")
    void editPostViaPutRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/postsRequests/postsPutBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPostsData sentData = gson.fromJson(jsonReader, LombokPostsData.class);

        LombokPostsData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        put("/posts/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokPostsData.class);

        assertEquals(responseData.getId(), sentData.getId());
        assertEquals(responseData.getUserId(), sentData.getUserId());
        assertEquals(responseData.getTitle(), sentData.getTitle());
        assertEquals(responseData.getBody(), sentData.getBody());
    }

    @Test
    @AllureId("13924")
    @Story("Updating posts")
    @Tag("patch_request_tests")
    void editPostViaPatchRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/postsRequests/postsPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPostsData sentData = gson.fromJson(jsonReader, LombokPostsData.class);

        InputStream stream2 = classLoader.getResourceAsStream("testData/dataToSend/postsRequests/postsPatchBody.json");
        JsonReader jsonReader2 = new JsonReader(new InputStreamReader(stream2));
        JsonObject sentDataObject = gson.fromJson(jsonReader2, JsonObject.class);
        Set<String> sentKeys = sentDataObject.keySet();

        LombokPostsData responseData =
                given().
                        spec(requestSpecification).
                        body(sentData).
                        when().
                        patch("/posts/" + sentData.getId()).
                        then().
                        spec(responseSpecification).
                        extract().as(LombokPostsData.class);

        assertEquals(responseData.getId(), sentData.getId());
        if (sentKeys.contains("userId")) {
            assertEquals(responseData.getUserId(), sentData.getUserId());
        }
        if (sentKeys.contains("title")) {
            assertEquals(responseData.getTitle(), sentData.getTitle());
        }
        if (sentKeys.contains("body")) {
            assertEquals(responseData.getBody(), sentData.getBody());
        }
    }

    @Test
    @AllureId("13927")
    @Story("Deleting posts")
    @Tag("delete_request_tests")
    void deletePostRequestTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/postsRequests/postsPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPostsData sentData = gson.fromJson(jsonReader, LombokPostsData.class);

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
