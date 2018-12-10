package com.moriarty.couchdb_ca.db_repository;

import com.moriarty.couchdb_ca.entity.Post;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
// Defining views used by the repository through annotations
@View( name = "all", map = "function(doc) { if (doc.type == 'post') { emit(doc.name, 1) } }" )
public class PostRepository extends CouchDbRepositorySupport<Post> {
    // class extends CouchDbRepositorySupport and then creates a design document for the user class
    protected PostRepository(CouchDbConnector db) {
        super(Post.class, db);
            initStandardDesignDocument();
    }

    @View( name="all", map = "function(doc) { if (doc.type == 'post')  emit(doc.name, 1); }")
    @Override
    public List<Post> getAll() {
        ViewQuery q = createQuery("all")
                .descending(true)
                .includeDocs(true);
        return db.queryView(q, Post.class);
    }
    // mapreduce function definition to get the number of comments son a post
    @View( name="num_of_comments_on_post",
            map = "function (doc) { if (Array.isArray(doc.comments)) " +
                    "doc.comments.forEach(function (comment) { " +
                        "emit(doc._id, 1); " +
                        "if (Array.isArray(comment.replies)) " +
                            "comment.replies.forEach(function(replyL1) { " +
                                "emit(doc._id, 1); " +
                                "if (Array.isArray(replyL1.replies)) " +
                                        "replyL1.replies.forEach(function(replyL2) { " +
                                            "emit(replyL2.content, 1); " +
                                            "if (Array.isArray(replyL2.replies)) " +
                                                "replyL2.replies.forEach(function(replyL3) { " +
                                                "emit(doc._id, 1); " +
                                            "}); " +
                                    "}); " +
                            "}); " +
                        "}); " +
                    "}",
            reduce = "function (keys, values, rereduce) { if (rereduce) { return sum(values); } else { return values.length; }")
    public int getNumOfCommentsOnPost(String postId) {
        ViewQuery query = new ViewQuery()
                .designDocId("_design/Post")
                .viewName("num_of_comments_on_post")
                .key(postId);
        ViewResult r = db.queryView(query);
        try {
            return r.getRows().get(0).getValueAsInt();
        }
        catch (Exception e) {
            return 0;
        }

    }
    // annotation for the auto generation of views, only finder methods
    @GenerateView
    public List<Post> findByTitle(String name) {
        return queryView("by_title", name);
    }

    @GenerateView
    public List<Post> findBySubredditId(String subredditId) {
        return queryView("by_subredditId", subredditId);
    }
}



