package com.simbirsoft.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokUserAddress {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private LombokUserAddressGeo geo;
}
