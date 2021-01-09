package com.openpapers.documentumclient.resource.orchestrator;


import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.search.SearchParameter;
import com.openpapers.documentumclient.resource.service.ContentService;
import com.openpapers.documentumclient.resource.service.ICoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


public class DocumentOrchestrator {

    private final Logger logger = LoggerFactory.getLogger(DocumentOrchestrator.class);

    @Autowired
    ContentService contentService;

    private ICoreService service;

    public DocumentOrchestrator(ConfigBuilder builder) {
        this.service = builder.builderService;
    }

    public static class ConfigBuilder {
        private ICoreService builderService;

        public ConfigBuilder setRepoIdentifier(ICoreService builderService){
            this.builderService = builderService;
            return this;
        }
        public DocumentOrchestrator build() {
            return new DocumentOrchestrator(this);
        }

    }

    public GreenDocument save(GreenDocument doc, MultipartFile file) {
        try{
            this.handleContent(doc,file);
        }catch (Exception ex){
            logger.error("Error at save, handle content",ex);
        }

        return service.save(doc);
    }

    public GreenDocument update(GreenDocument doc) {
        return service.update(doc);
    }

    public String delete(String entityID) {
        return service.delete(entityID);
    }

    public Collection<GreenDocument> search(SearchParameter searchparam) {
        return service.search(searchparam);
    }

    public GreenDocument handleContent(GreenDocument doc, MultipartFile file)throws IOException {

        Map map = contentService.initiateContentUpload(file);
        doc.setContentSize(String.valueOf(file.getBytes().length/1024*1024));
        doc.setContentLink((String)map.getOrDefault("file_url",""));
        doc.setContentKey((String)map.getOrDefault("file_key",""));
        return doc;
    }
}
