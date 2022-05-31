package main.java;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;


public class Connection {

    static MongoCollection<Document> artists;
    public static void connect() {
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("SD2022_projet");
        artists  = database.getCollection("artists", Document.class);

    }

    public static String getAPiKey() {
        return "f0f8ed2855eaf7851a62054c891d91a1";
    }

    public static void getTopArtists() throws ParseException {
        String res = HTTPTools.sendGet("http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key="+Connection.getAPiKey()+"&format=json&limit=10");
        Document parseRes = Document.parse(res);
        Document res2 = (Document) parseRes.get("artists");
        ArrayList<Document> res3 = (ArrayList<Document>) res2.get("artist");
        for (Document o : res3) {
            String name = (String) o.get("name");
            String listeners = (String) o.get("listeners");
            String playcount = (String) o.get("playcount");
            artists.insertOne(new Document("name", name).append("listeners", listeners).append("playcount", playcount));
        }
    }

    public static String getTopTags() {
        return HTTPTools.sendGet("http://ws.audioscrobbler.com/2.0/?method=chart.gettoptags&api_key="+Connection.getAPiKey()+"&format=json&limit=10");
    }

    public static String getTopTracks() {
        return HTTPTools.sendGet("http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key="+Connection.getAPiKey()+"&format=json&limit=10");
    }
}
