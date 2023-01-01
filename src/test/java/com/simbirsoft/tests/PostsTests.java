package com.simbirsoft.tests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.simbirsoft.lombok.LombokCommentsData;
import com.simbirsoft.lombok.LombokPostsData;
import com.simbirsoft.specs.Specs;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

import static com.simbirsoft.specs.PostRequestSpecs.postRequestSpecification;
import static com.simbirsoft.specs.PostRequestSpecs.postResponseSpecification;
import static io.restassured.RestAssured.*;
import static com.simbirsoft.specs.Specs.requestSpecification;
import static com.simbirsoft.specs.Specs.responseSpecification;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void getCommentsForPostViaPostsRequest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/getCommentExpectedResult.json");
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
    void getCommentsForPostViaCommentsPostIdRequest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/expectedResults/getCommentExpectedResult.json");
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
    void createPostTest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/postsPostBody.json");
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
    void editPostViaPutRequest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/postsPutBody.json");
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
    void editPostViaPatchRequest() {

        Gson gson = new Gson();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("testData/dataToSend/postsPatchBody.json");
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
        jsonReader.setLenient(true);
        LombokPostsData sentData = gson.fromJson(jsonReader, LombokPostsData.class);
    }
}
