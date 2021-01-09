package com.openpapers.documentumclient.resource.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSONParseException;
import com.openpapers.documentumclient.resource.model.HealthMetric;
import com.openpapers.documentumclient.resource.service.UtilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.orchestrator.DocumentOrchestrator;
import com.openpapers.documentumclient.resource.search.SearchParameter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Controller
@Api("Base com.controller")
@RequestMapping("api/base")
public class BaseController {

  
    @Autowired
    @Qualifier("Mongo_Client")
    DocumentOrchestrator orchestrator;

    @Autowired
    UtilityService utilityService;


    @Autowired
    ObjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ApiOperation("Search APi for Document")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<Collection<GreenDocument>> search(@RequestBody SearchParameter searchParams) {
        return ResponseEntity.ok(orchestrator.search(searchParams));
    }

    @ApiOperation("Save APi for Document")
    @RequestMapping(value = "/save", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = "application/json")
    public ResponseEntity<GreenDocument> save(@RequestParam(name = "doc") String doc,
                                              @RequestParam(name = "file") MultipartFile file) throws JsonProcessingException {
        return ResponseEntity.ok(orchestrator.save(mapper.readValue(doc,GreenDocument.class),file));
    }

    @ApiOperation("Update APi for Document")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<GreenDocument> update(@RequestBody GreenDocument doc) {
        return ResponseEntity.ok(orchestrator.update(doc));
    }

    @ApiOperation("Delete APi for Document")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<String> update(@RequestParam String entityID) {
        return ResponseEntity.ok(orchestrator.delete(entityID));
    }

    @ApiOperation("HeartBeat")
    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity<HealthMetric> index() {
        return ResponseEntity.ok(utilityService.heartBeat());
    }
}
