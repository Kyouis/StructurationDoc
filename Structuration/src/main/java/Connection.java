package main.java;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Connection {



    public static void connect() {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("SD2022_projet");
        }
    }

    public static String getAPiKey() {
        return "f0f8ed2855eaf7851a62054c891d91a1";
    }

    public static String getTopArtists() {
        return HTTPTools.sendGet("http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key="+Connection.getAPiKey()+"&format=json&limit=10");
    }

    public static String getTopTags() {
        return HTTPTools.sendGet("http://ws.audioscrobbler.com/2.0/?method=chart.gettoptags&api_key="+Connection.getAPiKey()+"&format=json&limit=10");
    }

    public static String getTopTracks() {
        return HTTPTools.sendGet("http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key="+Connection.getAPiKey()+"&format=json&limit=10");
    }
}
