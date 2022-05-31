package main.java;

import org.json.simple.parser.ParseException;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws ParseException {
        Connection.connect();
        System.out.println("Bienvenue dans l'application, que voulez-vous faire : \n" +
                "1 - Top 10 des artistes\n" +
                "2 - Top 10 des Genres de musique\n" +
                "3 - Top 10 des musiques\n" +
                "4 - Noter");

        String choix = sc.nextLine();
        switch (choix) {
            case "1" -> Connection.getTopArtists();//System.out.println(Connection.getTopArtists());
            case "2" -> Connection.getTopTags();
            case "3" -> Connection.getTopTracks();
            case "4" -> Connection.recom();
            default -> System.out.println("Entrez un choix valide");
        }


    }
}
