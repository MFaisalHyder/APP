package com.spring.app.controller;

import com.spring.app.questions.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class APITest {
    private final RestTemplate restTemplate;

    @GetMapping(value = "/test", produces = "application/json")
    public String getPdfLink() {

        RequestEntity<Void> requestEntity = RequestEntity
                .get("https://kalilcamera.com.br/teste.json")
                .build();

        ResponseEntity<Response> responseEntity = restTemplate.exchange(requestEntity, Response.class);
        if (responseEntity.getBody() != null)
            return responseEntity.getBody().getData().get(0).getAttributes().getLink();
        else return "";
    }

}