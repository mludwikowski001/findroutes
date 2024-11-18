package com.mludwikowski.countryroutes.api;

import com.mludwikowski.countryroutes.dto.CountryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "${feign.country-client.name}", url = "${feign.country-client.url}")
public interface CountryClient {

    @GetMapping
    List<CountryDTO> getCountries();
}
