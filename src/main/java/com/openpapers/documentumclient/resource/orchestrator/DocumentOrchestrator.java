package com.openpapers.documentumclient.resource.orchestrator;


import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.search.SearchParameter;
import com.openpapers.documentumclient.resource.service.ICoreService;

import java.util.Collection;


public class DocumentOrchestrator {


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

    public GreenDocument save(GreenDocument doc) {
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
}
