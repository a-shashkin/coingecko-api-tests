package com.simbirsoft.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokUsersData {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private LombokUserAddress address;
    private String phone;
    private String website;
    private LombokUserCompany company;
}
