package com.openpapers.documentumclient.resource.controller;


import com.openpapers.documentumclient.resource.model.HealthMetric;
import com.openpapers.documentumclient.resource.service.UtilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.orchestrator.DocumentOrchestrator;
import com.openpapers.documentumclient.resource.search.SearchParameter;

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



    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ApiOperation("Search APi for Workflow")
    @RequestMapping(value = "/com/search", method = RequestMethod.POST)
    public ResponseEntity<Collection<GreenDocument>> search(@RequestBody SearchParameter searchParams) {
        return ResponseEntity.ok(orchestrator.search(searchParams));
    }

    @ApiOperation("Save APi for saving WorkFlow")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<GreenDocument> save(@RequestBody GreenDocument doc) {
        return ResponseEntity.ok(orchestrator.save(doc));
    }

    @ApiOperation("HeartBeat")
    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity<HealthMetric> index() {
        return ResponseEntity.ok(utilityService.heartBeat());
    }
}
