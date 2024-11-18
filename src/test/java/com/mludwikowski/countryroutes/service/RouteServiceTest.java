package com.mludwikowski.countryroutes.service;

import com.mludwikowski.countryroutes.api.CountryClient;
import com.mludwikowski.countryroutes.dto.CountryDTO;
import com.mludwikowski.countryroutes.dto.Region;
import com.mludwikowski.countryroutes.exception.NoRouteException;
import com.mludwikowski.countryroutes.helper.BFSHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RouteServiceTest {

    @Mock
    private CountryClient countryClient; // Mockujemy CountryClient

    @Mock
    private BFSHelper bfsHelper; // Mockujemy BFSHelper

    @InjectMocks
    private RouteServiceImpl routeService; // Nasza testowana klasa

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindRoute_Success() {
        CountryDTO originCountry = new CountryDTO("Poland", Region.EUROPE, Arrays.asList("Germany", "Czechia"));
        CountryDTO destinationCountry = new CountryDTO("Germany", Region.EUROPE, Arrays.asList("Poland", "France"));

        Map<String, CountryDTO> countryMap = new HashMap<>();
        countryMap.put("Poland", originCountry);
        countryMap.put("Germany", destinationCountry);

        when(countryClient.getCountries()).thenReturn(Arrays.asList(originCountry, destinationCountry));
        when(bfsHelper.search(any(CountryDTO.class), any(CountryDTO.class), eq(countryMap))).thenReturn(Arrays.asList("Poland", "Germany"));

        List<String> route = routeService.findRoute("Poland", "Germany");

        assertEquals(Arrays.asList("Poland", "Germany"), route);
    }

    @Test
    void testFindRoute_UnknownOriginCountry() {
        CountryDTO destinationCountry = new CountryDTO("Germany", Region.EUROPE, Arrays.asList("Poland", "France"));

        when(countryClient.getCountries()).thenReturn(Collections.singletonList(destinationCountry));

        NoRouteException exception = assertThrows(NoRouteException.class, () -> routeService.findRoute("Poland", "Germany"));

        assertEquals("Unknown origin country Poland", exception.getMessage());
    }

    @Test
    void testFindRoute_UnknownDestinationCountry() {
        CountryDTO originCountry = new CountryDTO("Poland", Region.EUROPE, Arrays.asList("Germany", "Czech"));

        when(countryClient.getCountries()).thenReturn(Collections.singletonList(originCountry));

        NoRouteException exception = assertThrows(NoRouteException.class, () -> routeService.findRoute("Poland", "Germany"));

        assertEquals("Unknown destination country Germany", exception.getMessage());
    }

    @Test
    void testFindRoute_CountriesNotConnected() {
        CountryDTO originCountry = new CountryDTO("Poland", Region.EUROPE, Arrays.asList("Germany", "Czech"));
        CountryDTO destinationCountry = new CountryDTO("Japan", Region.ASIA, Arrays.asList("China", "Korea"));

        when(countryClient.getCountries()).thenReturn(Arrays.asList(originCountry, destinationCountry));

        NoRouteException exception = assertThrows(NoRouteException.class, () -> routeService.findRoute("Poland", "Japan"));

        assertEquals("Poland is not connected with Japan by land", exception.getMessage());
    }

    @Test
    void testFindRoute_OriginHasNoNeighbours() {
        CountryDTO originCountry = new CountryDTO("Poland", Region.EUROPE, Collections.emptyList());
        CountryDTO destinationCountry = new CountryDTO("Germany", Region.EUROPE, Arrays.asList("Poland", "France"));

        when(countryClient.getCountries()).thenReturn(Arrays.asList(originCountry, destinationCountry));

        NoRouteException exception = assertThrows(NoRouteException.class, () -> routeService.findRoute("Poland", "Germany"));

        assertEquals("Origin country Poland has no neighbours", exception.getMessage());
    }

    @Test
    void testFindRoute_DestinationHasNoNeighbours() {
        CountryDTO originCountry = new CountryDTO("Poland", Region.EUROPE, Arrays.asList("Germany", "Czechia"));
        CountryDTO destinationCountry = new CountryDTO("Germany", Region.EUROPE, Collections.emptyList());

        when(countryClient.getCountries()).thenReturn(Arrays.asList(originCountry, destinationCountry));

        NoRouteException exception = assertThrows(NoRouteException.class, () -> routeService.findRoute("Poland", "Germany"));

        assertEquals("Destination country Germany has no neighbours", exception.getMessage());
    }

    @Test
    void testFindRoute_EmptyRoute() {
        CountryDTO originCountry = new CountryDTO("Poland", Region.EUROPE, Arrays.asList("Germany", "Czechia"));
        CountryDTO destinationCountry = new CountryDTO("Malta", Region.EUROPE, Arrays.asList("Slovakia", "France"));

        Map<String, CountryDTO> countryMap = new HashMap<>();
        countryMap.put("Poland", originCountry);
        countryMap.put("Malta", destinationCountry);

        when(countryClient.getCountries()).thenReturn(Arrays.asList(originCountry, destinationCountry));
        when(bfsHelper.search(any(CountryDTO.class), any(CountryDTO.class), eq(countryMap))).thenReturn(Collections.emptyList());

        NoRouteException exception = assertThrows(NoRouteException.class, () -> routeService.findRoute("Poland", "Malta"));

        assertEquals("Poland is not connected with Malta by land", exception.getMessage());
    }

}