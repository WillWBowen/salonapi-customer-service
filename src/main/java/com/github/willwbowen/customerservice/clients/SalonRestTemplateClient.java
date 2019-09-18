package com.github.willwbowen.customerservice.clients;

import com.github.willwbowen.customerservice.models.Salon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SalonRestTemplateClient {
    @Autowired
    RestTemplate restTemplate;

    public Salon getSalon(String salonId) {
        ResponseEntity<Salon> restExchange =
                restTemplate.exchange("http://salonservice/v1/salons/{salonId}",
                HttpMethod.GET,
                null, Salon.class, salonId);
        return restExchange.getBody();
    }
}
