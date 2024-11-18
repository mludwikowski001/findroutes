package com.mludwikowski.countryroutes.service;

import com.mludwikowski.countryroutes.dto.CountryDTO;

import java.util.List;

public interface CountryService {

    List<CountryDTO> fetchCountries();
}
