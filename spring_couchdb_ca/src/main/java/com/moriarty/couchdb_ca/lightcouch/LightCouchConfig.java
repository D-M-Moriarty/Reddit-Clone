package com.moriarty.couchdb_ca.lightcouch;

import com.google.gson.JsonObject;
import org.lightcouch.*;
import com.couchbase.client.deps.com.lmax.disruptor.Foo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LightCouchConfig {
    CouchDbClient dbClient = new CouchDbClient();
    JsonObject json = new JsonObject();

    public LightCouchConfig(CouchDbClient dbClient) {
        this.dbClient = dbClient;

        dbClient.save(json);

        dbClient.find(Foo.class,"6ebc91abf25ef1e8f3b8bd305700f27f",
                "1-211228c6e0d24c3d00c2700857e36d94");

        String baseURI = dbClient.getBaseUri().toString();
        String dbURI = dbClient.getDBUri().toString();

        json = dbClient.find(JsonObject.class,
                "6ebc91abf25ef1e8f3b8bd305700f27f");

        System.out.println(json.deepCopy());

        System.out.println(baseURI);
        System.out.println(dbURI);

        // View entries keys, values and documents
        View view = dbClient.view("Artists/byUserId")
                .reduce(false);

        System.out.println(view.toString());

        ViewResult<JsonObject, String, String> entries =
                view.queryView(JsonObject.class, String.class, String.class);

        System.out.println(entries.getRows().toArray());

        // _all_docs
        List<Document> allDocs =
                dbClient.view("_all_docs").query(Document.class);

        for (int i = 0; i < entries.getTotalRows(); i++) {
            System.out.println(entries.getRows().get(i).getKey());
        }

        // map reduce code
        DesignDocument album = new DesignDocument();
        album.setId("_design/Album");
        album.setLanguage("javascript");
        // Creating a mapreduce function method
        DesignDocument.MapReduce byAlbumName = new DesignDocument.MapReduce();
        // setting the mapper function
        byAlbumName.setMap(
                "function(doc) { emit(doc.name, 1); }");
        // setting the optional reduce
        byAlbumName.setReduce(
                "function (key, values, rereduce) {"
                        + "return sum(values)"
                        + "}");
        // Creating a view from the mapreduce function
        Map<String, DesignDocument.MapReduce> view2 = new HashMap<>();
        view2.put("byAlbumName", byAlbumName);
        //adding the view to the design document
        album.setViews(view2);
        // synchronise with the database
        dbClient.design().synchronizeWithDb(album);
        // query the view with the key "the Beatles" which returns and int
        int sum = dbClient.view("mydesign/byAlbumName").key("the Beatles").queryForInt();
        // output the result
        System.out.println(sum);
        // close the connection
        dbClient.shutdown();
    }
}
