package com.openpapers.documentumclient.resource.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openpapers.documentumclient.resource.controller.BaseController;
import com.openpapers.documentumclient.resource.util.AppConstants;
import com.openpapers.documentumclient.resource.util.MultipartInputStreamFileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class ContentService {

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(ContentService.class);

    public Map initiateContentUpload(MultipartFile file){
        String serverUrl = env.getProperty(AppConstants.ContentStorageClientApi.endPoint);
        Map response = null ;
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file",new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);

            response = restTemplate
                    .postForObject(serverUrl,requestEntity,Map.class);

        }catch (Exception ex){
            logger.error("Error at initiate ContentUpload",ex);
            response = new HashMap();
            response.put("file_url","");
            response.put("file_key","");
        }

        return response;

    }



}
