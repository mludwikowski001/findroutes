package com.mludwikowski.countryroutes.service;

import com.mludwikowski.countryroutes.api.CountryClient;
import com.mludwikowski.countryroutes.dto.CountryDTO;
import com.mludwikowski.countryroutes.exception.NoRouteException;
import com.mludwikowski.countryroutes.helper.BFSHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RouteServiceImpl implements RouteService{

    @Autowired
    private final CountryClient countryClient;
    @Override
    public List<String> findRoute(String origin, String destination) {

        Map<String, CountryDTO> countryMap = countryClient.getCountries().stream().collect(Collectors.toMap(CountryDTO::getName, Function.identity()));

        CountryDTO originCountry = Optional.ofNullable(countryMap.get(origin))
                .orElseThrow(() -> new NoRouteException(String.format("Unknown origin country %s", origin)));

        CountryDTO destinationCountry = Optional.ofNullable(countryMap.get(destination))
                .orElseThrow(() -> new NoRouteException(String.format("Unknown destination country %s", destination)));

        validateCountries(origin, destination, originCountry, destinationCountry);

        List<String> route = new BFSHelper().search(originCountry, destinationCountry, countryMap);

        if(route.isEmpty()){
            throw new NoRouteException(String.format(
                    "%s is not connected with %s by land",
                    originCountry.getName(),
                    destinationCountry.getName()));
        }

        return route;
    }

    private static void validateCountries(String origin, String destination, CountryDTO originCountry, CountryDTO destinationCountry) {
        if (!originCountry.getRegion().connectedWith(destinationCountry.getRegion())) {
            throw new NoRouteException(String.format(
                    "%s (%s) is not connected with %s (%s) by land",
                    originCountry.getRegion(), origin,
                    destinationCountry.getRegion(), destination));
        }

        if (!origin.equals(destination)) {
            if (originCountry.getBorders().isEmpty()) {
                throw new NoRouteException(String.format("Origin country %s has no neighbours", origin));
            }

            if (destinationCountry.getBorders().isEmpty()) {
                throw new NoRouteException(String.format("Destination country %s has no neighbours", destination));
            }
        }
    }
}
