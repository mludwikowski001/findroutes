package com.mludwikowski.countryroutes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CountryDTO {

    @JsonProperty(value="cca3")
    private String name;

    @JsonProperty(value = "region")
    private Region region;

    private List<String> borders;
}
