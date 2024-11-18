package com.mludwikowski.countryroutes.service;

import com.mludwikowski.countryroutes.api.CountryClient;
import com.mludwikowski.countryroutes.dto.CountryDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    @Autowired
    private final CountryClient countryClient;

    @Override
    public List<CountryDTO> fetchCountries(){

        return countryClient.getCountries();
    }
}
