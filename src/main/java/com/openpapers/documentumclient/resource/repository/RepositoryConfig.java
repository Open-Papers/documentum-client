package com.openpapers.documentumclient.resource.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

@Service
public class RepositoryConfig {

    private final Logger log = LoggerFactory.getLogger(RepositoryConfig.class);

    private MongoClient mongoClient = null;
    private DB db;



    public void MongoConnector(String host, int port)
    {
        this.mongoClient = new MongoClient(host, port);
    }

    public void MongoConnector(String host, Integer port, String username, String password)
    {
        username = URLEncoder.encode(username);
        password = URLEncoder.encode(password);
        StringBuilder uri = new StringBuilder("mongodb://");
        uri.append(username.toString())
                .append(":")
                .append(password.toString())
                .append("@")
                .append(host.toString())
                .append(":")
                .append(port.toString())
                .append("/test?ssl=true&replicaSet=")
                .append("AppCluster0-shard-0")
                .append("&authSource=admin&retryWrites=true&maxIdleTimeMS=5000");

        log.info(uri.toString());
        MongoClientURI mongoUri = new MongoClientURI(uri.toString());
        this.mongoClient = new MongoClient(mongoUri);
    }

    @SuppressWarnings("deprecation")
    public DB connectToDB(String dbName)
    {
        this.db = mongoClient.getDB(dbName);
        return this.db;

    }


    public List<String> listAllDB()
    {
        List<String> ret = new LinkedList<>();
        MongoIterable<String> x = mongoClient.listDatabaseNames();
        for(String t : x)
        {
            ret.add(t.toString());
        }
        return ret;
    }
}
