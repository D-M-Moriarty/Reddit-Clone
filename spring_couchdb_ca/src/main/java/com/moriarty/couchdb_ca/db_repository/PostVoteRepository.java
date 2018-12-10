package com.moriarty.couchdb_ca.db_repository;

import com.moriarty.couchdb_ca.entity.Post;
import com.moriarty.couchdb_ca.entity.PostVote;
import com.moriarty.couchdb_ca.entity.User;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PostVoteRepository extends CouchDbRepositorySupport<PostVote> {

    protected PostVoteRepository(CouchDbConnector db) {
        super(PostVote.class, db);
    }

    // This generates the ( all ) view for each Design Document
    @View( name="all", map = "function(doc) { if (doc.type == 'postVote')  emit(doc.name, 1); }")
    @Override
    public List<PostVote> getAll() {
        ViewQuery q = createQuery("all")
                .descending(true)
                .includeDocs(true);
        return db.queryView(q, PostVote.class);
    }

    @View( name="find_chicken", map = "function(doc) { emit(doc.name, 1); }")
    public List<PostVote> getAllChicken() {
        ViewQuery q = createQuery("find_chicken")
                .descending(true)
                .includeDocs(true);
        return db.queryView(q, PostVote.class);
    }
}
