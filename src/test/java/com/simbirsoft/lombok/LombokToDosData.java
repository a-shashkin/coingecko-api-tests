package com.simbirsoft.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokToDosData {

    private Integer id;
    private Integer userId;
    private String title;
    private Boolean completed;
}
