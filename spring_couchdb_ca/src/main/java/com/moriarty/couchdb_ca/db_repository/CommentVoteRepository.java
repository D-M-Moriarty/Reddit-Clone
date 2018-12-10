package com.moriarty.couchdb_ca.db_repository;

import com.moriarty.couchdb_ca.entity.CommentVote;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommentVoteRepository extends CouchDbRepositorySupport<CommentVote> {

    protected CommentVoteRepository(CouchDbConnector db) {
        super(CommentVote.class, db);
    }
}
