package com.moriarty.couchdb_ca.db_repository;

import com.moriarty.couchdb_ca.entity.Message;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageRepository extends CouchDbRepositorySupport<Message> {

    protected MessageRepository(CouchDbConnector db) {
        super(Message.class, db);
    }
}
