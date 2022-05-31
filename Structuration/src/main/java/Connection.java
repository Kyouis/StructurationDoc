package main.java;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Connection {

    public Connection() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("myMongoDb");
        mongoClient.getDatabaseNames().forEach(System.out::println);
    }
}
