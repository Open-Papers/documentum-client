package com.openpapers.documentumclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.openpapers.documentumclient.resource.orchestrator.DocumentOrchestrator;
import com.openpapers.documentumclient.resource.service.DynamoCoreService;
import com.openpapers.documentumclient.resource.service.MongoCoreService;

@SpringBootApplication
@Configuration
public class DocumentumClientApplication {

    @Autowired
    MongoCoreService mongoCoreService;


    @Autowired
    DynamoCoreService dynamoCoreService;

    public static void main(String[] args) {
        SpringApplication.run(DocumentumClientApplication.class, args);
    }

    @Bean
    @Qualifier("Mongo_Client")
    public DocumentOrchestrator getMongoDocumentOrchestrator() {
        return new DocumentOrchestrator.ConfigBuilder().setRepoIdentifier(mongoCoreService).build();
    }


    @Bean
    @Qualifier("Dynamo_Client")
    public DocumentOrchestrator getDynamoDocumentOrchestrator() {
        return new DocumentOrchestrator.ConfigBuilder().setRepoIdentifier(dynamoCoreService).build();
    }

}
