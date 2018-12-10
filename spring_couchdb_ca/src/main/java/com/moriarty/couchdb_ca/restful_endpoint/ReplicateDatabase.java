package com.moriarty.couchdb_ca.restful_endpoint;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ReplicationCommand;
import org.ektorp.ReplicationStatus;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("replicate")
public class ReplicateDatabase {
    @Autowired
    private CouchDbConnector dbConnector;

    @PostMapping("/{replicationDb}")
    public ReplicationStatus replicateDB(@PathVariable String replicationDb,
                                         @RequestHeader("continuous") String continuous) {
        HttpClient httpClient = new StdHttpClient.Builder().build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        // if the second parameter is true, the database will be created if it doesn't exists
        CouchDbConnector db = dbInstance.createConnector(replicationDb, true);
        // Perform Replication from current database to the new database
        boolean isContinuous = continuous.equals("true");
        ReplicationCommand cmd = new ReplicationCommand.Builder()
                .source(dbConnector.getDatabaseName())
                .target(replicationDb)
                .continuous(isContinuous)
                .createTarget(true)
                .build();
        return dbInstance.replicate(cmd);
    }

}
