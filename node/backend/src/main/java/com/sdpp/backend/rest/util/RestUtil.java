package com.sdpp.backend.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/****************************
 *                          *
 * @author : Franco Catania *
 *                          *
 ****************************/
public class RestUtil {

    public static Object getObjectForUrl(String url){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response
                = restTemplate.getForEntity(url, Object.class);
        if(!response.getStatusCode().equals(HttpStatus.OK))
            throw new RuntimeException("Couldn't retrieve information from URL " + url + " Response: " + response.toString() );

        return response.getBody();
    }

}