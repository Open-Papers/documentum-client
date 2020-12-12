package com.openpapers.documentumclient.resource.dao;


import com.mongodb.DB;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import com.openpapers.documentumclient.resource.model.GreenDocument;
import com.openpapers.documentumclient.resource.repository.RepositoryConfig;
import com.openpapers.documentumclient.resource.util.AppConstants;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.LinkedList;

@Repository
public class MongoDAO {
    private DB db;
    private Jongo jongo;

    @Autowired
    private RepositoryConfig repository;

    @Autowired
    private Environment env;

    @PostConstruct
    private void init() {
        repository.MongoConnector(env.getProperty("mongo.host"), Integer.parseInt(env.getProperty("mongo.port")),
                env.getProperty("mongo.userid"), env.getProperty("mongo.password"));
        this.db = repository.connectToDB(AppConstants.MongoApi.mongoDB);
        this.jongo = new Jongo(db);
    }


    public void insert(GreenDocument document) {
        MongoCollection refData  = jongo.getCollection(AppConstants.MongoApi.mongoDBCollection);
        refData.save(document);
    }


    public void update(String query,GreenDocument document) {
        MongoCollection  refData = jongo.getCollection(AppConstants.MongoApi.mongoDBCollection);
        refData.update(query, document);
    }


    public Collection<GreenDocument> find(String query) {
        Collection<GreenDocument> payload = new LinkedList<>();
        MongoCollection  refData = jongo.getCollection(AppConstants.MongoApi.mongoDBCollection);
        MongoCursor<GreenDocument> cursor = refData.find(query).as(GreenDocument.class);
        while(cursor.hasNext()) {
            payload.add(cursor.next());
        }
        return payload;
    }

    public Long getDocumentCount(String collectionName) {
        MongoCollection  collection  = jongo.getCollection(collectionName);
        return collection.count();
    }


}
