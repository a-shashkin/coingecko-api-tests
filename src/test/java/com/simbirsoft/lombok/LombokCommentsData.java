package com.simbirsoft.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokCommentsData {

    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;
}
