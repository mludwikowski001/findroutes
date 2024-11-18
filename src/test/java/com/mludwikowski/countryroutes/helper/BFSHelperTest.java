package com.mludwikowski.countryroutes.helper;

import com.mludwikowski.countryroutes.dto.CountryDTO;
import com.mludwikowski.countryroutes.dto.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BFSHelperTest {

    private BFSHelper bfsHelper;

    private static final Map<String, CountryDTO> COUNTRY_MAP = new HashMap<>();

    static {
        COUNTRY_MAP.put("A", new CountryDTO("A", Region.EUROPE, List.of("B")));
        COUNTRY_MAP.put("B", new CountryDTO("B", Region.EUROPE, List.of("C", "D")));
        COUNTRY_MAP.put("C", new CountryDTO("C", Region.EUROPE, Collections.emptyList()));
        COUNTRY_MAP.put("D", new CountryDTO("D", Region.ASIA, List.of("E", "A")));
        COUNTRY_MAP.put("E", new CountryDTO("E", Region.ASIA, List.of("D")));
        COUNTRY_MAP.put("F", new CountryDTO("E", Region.OCEANIA, Collections.emptyList()));
        COUNTRY_MAP.put("G", new CountryDTO("E", Region.EUROPE, Collections.emptyList()));
    }

    @BeforeEach
    void setUp() {
        bfsHelper = new BFSHelper();
    }

    @Test
    void testSearch_FoundRoute() {
        List<String> result = bfsHelper.search(COUNTRY_MAP.get("A"), COUNTRY_MAP.get("C"), COUNTRY_MAP);

        assertEquals(Arrays.asList("A", "B", "C"), result);
    }

    @Test
    void testSearch_NoRoute() {
        List<String> result = bfsHelper.search(COUNTRY_MAP.get("A"), COUNTRY_MAP.get("G"), COUNTRY_MAP);

        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch_SingleCountryRoute() {
        List<String> result = bfsHelper.search(COUNTRY_MAP.get("A"), COUNTRY_MAP.get("A"), COUNTRY_MAP);

        assertEquals(Collections.singletonList("A"), result);
    }

    @Test
    void testSearch_CyclicRoute() {
        List<String> result = bfsHelper.search(COUNTRY_MAP.get("A"), COUNTRY_MAP.get("D"), COUNTRY_MAP);

        assertEquals(Arrays.asList("A", "B", "D"), result);
    }

    @Test
    void testSearch_NoConnectionBetweenRegions() {
        List<String> result = bfsHelper.search(COUNTRY_MAP.get("E"), COUNTRY_MAP.get("F"), COUNTRY_MAP);

        assertTrue(result.isEmpty());
    }

    @Test
    void testSearch_EmptyBorders() {
        List<String> result = bfsHelper.search(COUNTRY_MAP.get("C"), COUNTRY_MAP.get("A"), COUNTRY_MAP);

        assertTrue(result.isEmpty());
    }

}