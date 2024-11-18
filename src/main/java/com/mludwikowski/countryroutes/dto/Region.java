package com.mludwikowski.countryroutes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Set;

public enum Region {

    AFRICA("Africa"),
    AMERICAS("Americas"),
    ASIA("Asia"),
    EUROPE("Europe"),
    OCEANIA("Oceania"),
    ANTARCTIC("Antarctic");

    private final String value;

    Region(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Region fromValue(String value) {
        for (Region region : Region.values()) {
            if (region.value.equalsIgnoreCase(value)) {
                return region;
            }
        }
        return null;
    }

    private static final Set<Region> CONNECTED_CONTINENTS = Set.of(AFRICA, ASIA, EUROPE);

    public boolean connectedWith(Region region) {

        return this == region || (CONNECTED_CONTINENTS.contains(this) && CONNECTED_CONTINENTS.contains(region));
    }
}
