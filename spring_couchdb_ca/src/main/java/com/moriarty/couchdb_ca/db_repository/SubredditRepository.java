package com.moriarty.couchdb_ca.db_repository;

import com.moriarty.couchdb_ca.entity.Post;
import com.moriarty.couchdb_ca.entity.Subreddit;
import com.moriarty.couchdb_ca.entity.User;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
// Defining views used by the repository through annotations
@View( name = "all", map = "function(doc) { emit(doc.name, 1) }" )
public class SubredditRepository extends CouchDbRepositorySupport<Subreddit> {
    // class extends CouchDbRepositorySupport and then creates a design document for the user class
    @Autowired
    private Subreddit subreddit;

    protected SubredditRepository(CouchDbConnector db) {
        super(Subreddit.class, db);
        initStandardDesignDocument();
    }

    @View( name="all", map = "function(doc) { if (doc.type == 'subreddit') emit(doc.name, 1); }")
    @Override
    public List<Subreddit> getAll() {
        ViewQuery q = createQuery("all")
                .descending(true)
                .includeDocs(true);
        return db.queryView(q, Subreddit.class);
    }
    // mapreduce function definition to get the number of Subreddits
    @View( name="num_of_subreddits",
            map = "function (doc) { emit(doc.name, 1); }",
            reduce = "function (keys, values, rereduce) { if (rereduce) { return sum(values); } else { return values.length; }")
    public int getSumOfSubreddits() {
        ViewResult r = db.queryView(createQuery("num_of_subreddits"));
        return r.getRows().get(0).getValueAsInt();
    }

    // add an attachment to a subreddit, i.e. image
    public void addSubredditFile(String base64EncodedData, String contentType, String name) {
        subreddit = db.get(Subreddit.class, "20e1ed2a41632e45ada34e3e57018a28");
        subreddit.setFile(base64EncodedData, contentType, name);
        db.update(subreddit);
    }

    // annotation for the auto generation of views, only finder methods
    // finds a subreddit by its name
    @GenerateView
    public List<Subreddit> findByName(String name) {
        return queryView("by_name", name);
    }

    @GenerateView
    public List<Subreddit> findByDescription(String description) {
        return queryView("by_description", description);
    }
}
