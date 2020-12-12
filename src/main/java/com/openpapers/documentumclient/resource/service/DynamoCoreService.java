package com.openpapers.documentumclient.resource.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.search.SearchParameter;

import java.util.Collection;


@Service
public class DynamoCoreService implements ICoreService{

    //Implementation for Dynamo APIs
    @Override
    public GreenDocument save(GreenDocument doc) {
        return null;
    }

    @Override
    public GreenDocument update(GreenDocument doc) {
        return null;
    }

    @Override
    public Collection<GreenDocument> search(SearchParameter searchparam) {
        return null;
    }

    @Override
    public Long generateEntityID(String collectionName) {
        return null;
    }


}
