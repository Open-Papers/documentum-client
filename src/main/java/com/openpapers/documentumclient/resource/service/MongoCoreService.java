package com.openpapers.documentumclient.resource.service;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class MongoCoreService implements ICoreService{

    @Autowired
    MongoDAO mongoDAO;

    @Override
    public GreenDocument save(GreenDocument doc) {
        doc.setEntityID(generateEntityID(AppConstants.MongoApi.mongoDBCollection));
        mongoDAO.insert(doc);
        return doc;
    }

    @Override
    public GreenDocument update(GreenDocument doc) {
        if(null == doc.getEntityID() || doc.getEntityID() <= 0) {
            throw new RuntimeException("Invalid document for update");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("entityId").is(doc.getEntityID()));
        mongoDAO.update(query.getQueryObject().toString(), doc);
        return doc;
    }

    @Override
    public Collection<GreenDocument> search(SearchParameter searchparam) {
        Collection<GreenDocument> result= new LinkedHashSet<>();
        List<String> queryParts = new LinkedList<>();
        Query query = new Query();
        String finalQuery = StringUtils.EMPTY;
        List<String> qAnd = new LinkedList<String>();
        List<String> qOr = new LinkedList<String>();
        query.addCriteria(Criteria.where("isActive").is(true));
        queryParts.add(query.getQueryObject().toString());
        if(null != searchparam.getEntityID())
            qAnd.add("'entityId':'"+searchparam.getEntityID()+"'");
        if(!StringUtils.isEmpty(searchparam.getTitle()))
            qAnd.add("'title': {$regex: '^"+searchparam.getTitle()+"'}");
        if(!StringUtils.isEmpty(searchparam.getDescription()))
            qAnd.add("'description':{$regex: '"+searchparam.getDescription()+"'}");
        if(!CollectionUtils.isEmpty(searchparam.getTags()))
            qAnd.add("'tags':{$in:[ "+searchparam.getTags().stream().map(t -> t.toString()).collect(Collectors.joining(",","'","'"))+"]}");
        StringBuilder finalAnd = new StringBuilder(" { $and: [");
        qAnd.stream().map(q ->"{"+q+"}").forEach(qc->{
            finalAnd.append(qc);
        });
        finalAnd.append("] }");
        queryParts.add(finalAnd.toString());
        finalQuery = "{$and:["+queryParts.stream().map(i -> i.toString()).collect(Collectors.joining(","))+"]}";
        result = mongoDAO.find(finalQuery);
        return result;
    }

    @Override
    public Long generateEntityID(String collectionName) {
        Long entityId;
        entityId = mongoDAO.getDocumentCount(collectionName);
        if(null == entityId)
            throw new RuntimeException("Unable to generate document ID");
        else
            entityId++;
        return entityId;
    }
}
