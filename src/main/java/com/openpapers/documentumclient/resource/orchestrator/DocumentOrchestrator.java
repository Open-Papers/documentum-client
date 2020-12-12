package com.openpapers.documentumclient.resource.orchestrator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.search.SearchParameter;
import com.openpapers.documentumclient.resource.service.DynamoCoreService;
import com.openpapers.documentumclient.resource.service.ICoreService;
import com.openpapers.documentumclient.resource.service.MongoCoreService;

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

    public Collection<GreenDocument> search(SearchParameter searchparam) {
        return service.search(searchparam);
    }
}
