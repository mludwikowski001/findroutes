package com.mludwikowski.countryroutes.api;

import com.mludwikowski.countryroutes.service.RouteService;
import com.mludwikowski.countryroutes.exception.NoRouteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteService routeService;

    @Test
    void testFindRoute_Success() throws Exception {
        List<String> route = Arrays.asList("A", "B", "C");
        String origin = "A";
        String destination = "C";

        when(routeService.findRoute(origin, destination)).thenReturn(route);

        mockMvc.perform(get("/routing/{origin}/{destination}", origin, destination))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0]").value("A"))
                .andExpect(jsonPath("$[1]").value("B"))
                .andExpect(jsonPath("$[2]").value("C"));

        verify(routeService).findRoute(origin, destination);
    }

    @Test
    void testFindRoute_NotFound() throws Exception {
        String origin = "X";
        String destination = "Y";

        when(routeService.findRoute(origin, destination)).thenThrow(new NoRouteException("No route found"));

        mockMvc.perform(get("/routing/{origin}/{destination}", origin, destination))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(routeService).findRoute(origin, destination);
    }

    @Test
    void testFindRoute_EmptyRoute() throws Exception {
        String origin = "A";
        String destination = "B";

        when(routeService.findRoute(origin, destination)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/routing/{origin}/{destination}", origin, destination))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isEmpty());

        verify(routeService).findRoute(origin, destination);
    }



}