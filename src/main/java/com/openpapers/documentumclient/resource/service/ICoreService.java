package com.openpapers.documentumclient.resource.service;


import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.search.SearchParameter;

import java.util.Collection;

public interface ICoreService {

    public GreenDocument save(GreenDocument doc);
    public GreenDocument update(GreenDocument doc);
    public Collection<GreenDocument> search(SearchParameter searchparam);
    public String delete(String entityID);
    Long generateEntityID();

}
