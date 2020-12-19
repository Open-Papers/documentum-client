package com.openpapers.documentumclient.resource.service;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.openpapers.documentumclient.resource.dao.MongoDAO;
import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.search.SearchParameter;
import com.openpapers.documentumclient.resource.util.AppConstants;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MongoCoreService implements ICoreService, ApplicationContextAware {

    @Autowired
    MongoDAO mongoDAO;


    MongoCoreService _self;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        _self = applicationContext.getBean(MongoCoreService.class);
    }

    @Override
    public GreenDocument save(GreenDocument doc) {
        doc.setEntityID(String.valueOf(generateEntityID()));
        mongoDAO.insert(doc);
        return doc;
    }

    @Override
    public GreenDocument update(GreenDocument doc) {
        if(null == doc.getEntityID() || Long.valueOf(doc.getEntityID()) <= 0) {
            throw new RuntimeException("Invalid document for update");
        }
        mongoDAO.update("{entityID : # }", doc);
        return doc;
    }

    @Override
    public String delete(String entityID) {
        if(null == entityID || Long.valueOf(entityID) <= 0) {
            throw new RuntimeException("Invalid document for delete");
        }
        SearchParameter params = new SearchParameter();
        params.setEntityID(entityID);
        Collection<GreenDocument> docs = _self.search(params);


        if (docs.iterator().hasNext()) {
            GreenDocument doc = docs.iterator().next();
            doc.setActive(false);
            mongoDAO.update("{entityID : # }", doc);
            return "Soft Delete Successful";
        }
        return "Soft Delete Failed";
    }

    @Override
    public Collection<GreenDocument> search(SearchParameter searchparam) {
        Collection<GreenDocument> result= new LinkedHashSet<>();
        List<String> queryParts = new LinkedList<>();
        Query query = new Query();
        String finalQuery = StringUtils.EMPTY;
        List<String> qAnd = new LinkedList<String>();
        List<String> qOr = new LinkedList<String>();
        query.addCriteria(Criteria.where("active").is(true));
        queryParts.add(query.getQueryObject().toString());
        if(null != searchparam.getEntityID())
            qAnd.add("'entityID':'"+searchparam.getEntityID()+"'");
        if(!StringUtils.isEmpty(searchparam.getTitle()))
            qAnd.add("'title': {$regex: '^"+searchparam.getTitle()+"'}");
        if(!StringUtils.isEmpty(searchparam.getDescription()))
            qAnd.add("'description':{$regex: '"+searchparam.getDescription()+"'}");
        if(!CollectionUtils.isEmpty(searchparam.getTags()))
            qAnd.add("'tags':{$elemMatch:{ "+searchparam.getTags().stream().map(t ->"'value':'"+t.getValue()+"'").
                    collect(Collectors.joining(","))+"} }");
        StringBuilder finalAnd = new StringBuilder(" { $and: [");
        finalAnd = finalAnd.append(qAnd.stream().map(q -> "{"+q+"}").collect(Collectors.joining(","))).append("] }");
        if(!CollectionUtils.isEmpty(qAnd)){
            queryParts.add(finalAnd.toString());
        }
        finalQuery = "{$and:["+queryParts.stream().map(i -> i.toString()).collect(Collectors.joining(","))+"]}";
        result = mongoDAO.find(finalQuery);
        return result;
    }


    @Override
    public Long generateEntityID() {
        Long entityId;
        entityId = mongoDAO.getDocumentCount();
        if(null == entityId)
            throw new RuntimeException("Unable to generate document ID");
        else
            entityId++;
        return entityId;
    }
}
