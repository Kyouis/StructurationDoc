package main.java;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bienvenue dans l'application, que voulez-vous faire : \n" +
                "1 - Top 10 des artistes\n" +
                "2 - Top 10 des Genres de musique\n" +
                "3 - Top 10 des musiques\n");

        String choix = sc.nextLine();
        switch (choix) {
            case "1" -> System.out.println(Connection.getTopArtists());
            case "2" -> System.out.println(Connection.getTopTags());
            case "3" -> System.out.println(Connection.getTopTracks());
            default -> System.out.println("Entrez un choix valide");
        }
    }
}
