package main.java;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.parser.ParseException;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Scanner;


public class Connection {
    static MongoCollection<Document> tags;
    static MongoCollection<Document> tracks;
    static MongoCollection<Document> artists;
    static MongoCollection<Document> recom;
    public static void connect() {
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("SD2022_projet");
        artists  = database.getCollection("artists", Document.class);
        tracks = database.getCollection("tracks", Document.class);
        tags  = database.getCollection("tags", Document.class);
        recom = database.getCollection("Recommandation", Document.class);
    }

    public static String getAPiKey() {
        return "f0f8ed2855eaf7851a62054c891d91a1";
    }

    public static void recom() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Inserez votre nom");
        String nom = sc.nextLine();
        String typeReco;
        String note;
        System.out.println("Que voulez-vous recommander ?\n" +
                "1 - Album\n" +
                "2 - Artiste\n" +
                "3 - Musique\n");
        String choix = sc.nextLine();
        switch (choix) {
            case "1" -> typeReco="Album";
            case "2" -> typeReco = "Artiste";
            case "3" -> typeReco = "Musique";
            default -> typeReco = "";
        }
        System.out.println("Quelle note sur 5 donnez-vous ?");
        choix = sc.nextLine();
        if (Integer.parseInt(choix) > 5 || Integer.parseInt(choix)<0) {
            System.out.println("Note non comprise entre 0 et 5");
        } else {
            recom.insertOne(new Document("utilisateur", nom).append("Oeuvre ou artiste noté", typeReco).append("note", choix));
        }

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

    public static void getTopTags() {
        String res = HTTPTools.sendGet("http://ws.audioscrobbler.com/2.0/?method=chart.gettoptags&api_key="+Connection.getAPiKey()+"&format=json&limit=10");
                Document parseRes = Document.parse(res);
        Document res2 = (Document) parseRes.get("tags");
        ArrayList<Document> res3 = (ArrayList<Document>) res2.get("tag");
        for (Document o : res3) {
            String name = (String) o.get("name");
            String reach= (String) o.get("reach");
            String taggings = (String) o.get("taggings");
            tags.insertOne(new Document("name", name).append("reach", reach).append("taggings", taggings));
        }
    }

    public static void getTopTracks() {
        String res = HTTPTools.sendGet("http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key="+Connection.getAPiKey()+"&format=json&limit=10");
        Document parseRes = Document.parse(res);
        Document res2 = (Document) parseRes.get("tracks");
        ArrayList<Document> res3 = (ArrayList<Document>) res2.get("track");
        for (Document o : res3) {
            String name = (String) o.get("name");
            String listeners = (String) o.get("listeners");
            String playcount = (String) o.get("playcount");
            tracks.insertOne(new Document("name", name).append("listeners", listeners).append("playcount", playcount));
        }
    }
}
