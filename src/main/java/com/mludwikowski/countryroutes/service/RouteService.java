package com.mludwikowski.countryroutes.service;

import java.util.List;

public interface RouteService {

    List<String> findRoute(String origin, String destination);
}
