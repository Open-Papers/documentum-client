package com.openpapers.documentumclient.resource.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.model.Tag;
import com.openpapers.documentumclient.resource.search.SearchParameter;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
       objectMapper  = new ObjectMapper();
    }

    @Test
    void BaseControllerTestOK() throws Exception{
        index();
        GreenDocument savedDoc = save();
        search(savedDoc);
        GreenDocument updatedDoc = update(savedDoc);
        //search(updatedDoc);
        delete(updatedDoc);
    }

    void search(GreenDocument doc) throws Exception{
        SearchParameter params = new SearchParameter();
        params.setEntityID(doc.getEntityID());
        params.setDescription(doc.getDescription());
        params.setTags(doc.getTags());
        params.setTitle(doc.getTitle());

        String json = objectMapper.writeValueAsString(params);
        MvcResult result = this.mockMvc.perform(post("/api/base/search").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Collection<GreenDocument> data = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<GreenDocument>>() {});
        assertTrue(data.contains(doc));

    }


    GreenDocument save() throws Exception{
        GreenDocument testDoc = new GreenDocument();
        testDoc.setContentKey("test-aws-key"+UUID.randomUUID());
        testDoc.setContentLink("test-aws-link"+UUID.randomUUID());
        testDoc.setContentSize("15mb");
        testDoc.setDescription("test-desc"+UUID.randomUUID());
        testDoc.setTitle("test"+UUID.randomUUID());
        testDoc.setDocAbstract("test-abstract"+UUID.randomUUID());
        Tag tag1 = new Tag();
        tag1.setType("ATTRIBUTE_TAG");
        tag1.setValue("random_value1");
        Tag tag2 = new Tag();

        tag2.setType("ATTRIBUTE_TAG");
        tag2.setValue("random_value2");

        testDoc.setTags(Arrays.asList(tag1,tag2));
        testDoc.setUpdatedBy("test_user");
        testDoc.setUploadedBy("test_user");
        testDoc.setUpdatedDate(new Date());
        testDoc.setUploadedDate(new Date());
        String json = objectMapper.writeValueAsString(testDoc);
        MvcResult result = this.mockMvc.perform(post("/api/base/save").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        GreenDocument data = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<GreenDocument>() {});
        return data;
    }


    GreenDocument update(GreenDocument document) throws Exception{
        document.setUploadedBy("System");
        document.setUpdatedBy("System");
        document.setUploadedDate(new Date());
        document.setUpdatedDate(new Date());
        document.setTitle("test_success#"+UUID.randomUUID());
        String json = objectMapper.writeValueAsString(document);
        this.mockMvc.perform(post("/api/base/update").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        return document;
    }


    void delete(GreenDocument doc) throws Exception{
        this.mockMvc.perform(get("/api/base/delete?entityID="+doc.getEntityID()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    void index() throws Exception{
        this.mockMvc.perform(get("/api/base/heartbeat"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    }
}