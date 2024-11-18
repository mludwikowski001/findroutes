package com.mludwikowski.countryroutes.api;

import com.mludwikowski.countryroutes.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/routing/{origin}/{destination}")
     public ResponseEntity<List<String>> findRoute(@PathVariable String origin, @PathVariable String destination){

      return ResponseEntity.ok(routeService.findRoute(origin, destination));

     }

}
