package com.mludwikowski.countryroutes.helper;

import com.mludwikowski.countryroutes.dto.CountryDTO;
import com.mludwikowski.countryroutes.exception.NoRouteException;

import java.util.*;
import java.util.stream.Collectors;

public class BFSHelper {

    Queue<List<CountryDTO>> queue = new LinkedList<>();
    Set<CountryDTO> visited = new HashSet<>();

    public List<String> search(CountryDTO originCountry, CountryDTO destinationCountry, Map<String, CountryDTO> countryMap) {

        queue.add(Collections.singletonList(originCountry));
        visited.add(originCountry);

        while (!queue.isEmpty()) {
            List<CountryDTO> currentPath = queue.poll();
            CountryDTO lastCountry = currentPath.get(currentPath.size() - 1);

            if (lastCountry.equals(destinationCountry)) {
                return currentPath.stream().map(CountryDTO::getName).collect(Collectors.toList());
            }
            CountryDTO lastCountryObj = countryMap.get(lastCountry.getName());
            if (lastCountryObj == null || lastCountryObj.getBorders().isEmpty()) {
                continue;
            }

            for (String neighbor : lastCountryObj.getBorders()) {
                if (visited.contains(countryMap.get(neighbor))) {
                    continue;
                }

                CountryDTO neighborCountry = countryMap.get(neighbor);
                if (neighborCountry != null && neighborCountry.getRegion().connectedWith(originCountry.getRegion())) {
                    List<CountryDTO> newPath = new ArrayList<>(currentPath);
                    newPath.add(countryMap.get(neighbor));
                    queue.add(newPath);
                    visited.add(countryMap.get(neighbor));
                }
            }
        }
       return Collections.emptyList();
    }
}
