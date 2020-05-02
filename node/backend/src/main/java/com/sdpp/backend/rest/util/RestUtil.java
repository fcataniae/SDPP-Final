package com.sdpp.backend.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/****************************
 *                          *
 * @author : Franco Catania *
 *                          *
 ****************************/
public class RestUtil {

    public static Object getObjectForUrl(String url, LinkedMultiValueMap<String, String> params){

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParams(params);
        UriComponents uriComponents = builder.build().encode();
        ResponseEntity<Object> response
                = restTemplate.getForEntity(uriComponents.toUriString(), Object.class, params);
        if(!response.getStatusCode().equals(HttpStatus.OK))
            throw new RuntimeException("Couldn't retrieve information from URL " + url + " Response: " + response.toString() );

        return response.getBody();
    }

}
