package com.moriarty.couchdb_ca;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@SpringBootApplication
@Configuration
public class CouchdbCaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouchdbCaApplication.class, args);
    }

}
