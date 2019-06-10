package com.cred.register.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RootService {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    public String getMessageFromUserLocale(String message, Object... args) {
        return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }

    public ResponseEntity<?> request(Object map, String url, HttpMethod httpMethod, Class clazz){
        String content = "";
        try{
           content = objectMapper.writeValueAsString(map);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json");
        headers.setContentType(mediaType);
        HttpEntity entity = new HttpEntity<>(content, headers);
        return restTemplate.exchange(url, httpMethod, entity, clazz);
    }
}
