package com.simbirsoft.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokPostsGetRequests {

    private List<Post> posts;

    public class Post {

        private Integer id;
        private Integer userId;
        private String title;
        private String body;
    }
}
