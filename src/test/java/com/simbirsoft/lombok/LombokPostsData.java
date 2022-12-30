package com.simbirsoft.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokPostsData {

        private Integer id;
        private Integer userId;
        private String title;
        private String body;
}
