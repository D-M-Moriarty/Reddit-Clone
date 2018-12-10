package com.moriarty.couchdb_ca.db_repository;

import com.moriarty.couchdb_ca.entity.Comment;
import com.moriarty.couchdb_ca.entity.Post;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@View( name = "all", map = "function(doc) { if (doc.type == 'comment') { emit(doc.name, 1) } }" )
public class CommentRepository extends CouchDbRepositorySupport<Comment> {
    protected CommentRepository(CouchDbConnector db) {
         super(Comment.class, db);
        initStandardDesignDocument();
    }

    @View( name = "all", map = "function(doc) { if (doc.type == 'comment') { emit(doc.name, 1) } }" )
    @Override
    public List<Comment> getAll() {
        ViewQuery q = createQuery("all")
                .descending(true)
                .includeDocs(true);
        return db.queryView(q, Comment.class);
    }

    @GenerateView
    public List<Comment> findByContent(String content) {
        return queryView("by_content", content);
    }
    @GenerateView
    @View(name = "by_postId", reduce = "function (keys, values, rereduce) { if (rereduce) { return count(values); } else { return values.length; }")
    public List<Comment> findByPostId(String postId) {
        return queryView("by_postId", postId);
    }


}
