package com.example;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONObject;


public class SuperHeroFigther {

public static void main(String[] args) throws IOException, InterruptedException {
    Scanner input = new Scanner(System.in);

    System.out.println("View the superhero list at: https://www.superheroapi.com/ids.html");

    System.out.print("Enter your SuperHero API access token: ");
    String token = input.nextLine();

    System.out.print("Enter the ID of the first Superhero: ");
    String hero1 = input.nextLine();

    System.out.print("Enter the ID of the second Superhero: ");
    String hero2 = input.nextLine();

    String[] heroNames = new String[2];
    int hero1Power = getSuperheroInfo(hero1,token,heroNames,0);
    int hero2Power = getSuperheroInfo(hero2,token,heroNames,1);

    if (hero1Power == -1 || hero2Power == -1) {
        System.out.println("Error retrieving superhero information.");
    }
     else if (hero1Power > hero2Power) {
        System.out.println(heroNames[0] + " is stronger than " + heroNames[1]);
    } else if (hero2Power > hero1Power) {
        System.out.println(heroNames[1] + " is stronger than " + heroNames[0]);
    } else {
        System.out.println(heroNames[0] + " and " + heroNames[1] + " it's a tie!");
    }
   
}
public static int getSuperheroInfo(String superHeroID, String token, String[] heroNames, int index) throws IOException, InterruptedException {
   String url="https://www.superheroapi.com/api.php/" + token + "/" + superHeroID;
   HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build(); 
HttpResponse<String> response = client.send(
    request, HttpResponse.BodyHandlers.ofString());
    
    if (response.statusCode()== 200){
       JSONObject heroData = new JSONObject(response.body());

       if (!heroData.has("powerstats")) {
        System.out.println("No superhero found with ID: " + superHeroID);
        return -1;
       }
       String heroName= heroData.getString("name");
       heroNames[index] = heroName;
       JSONObject powerStats = heroData.getJSONObject("powerstats");
     
        String[] stats = {"intelligence", "strength", "speed", "durability", "power", "combat"};
        int totalPower = 0;

        for (int i = 0; i < stats.length; i++) {
            String statName = stats[i];
            String value = powerStats.getString(statName);

            if(!value.equals("null")){
                int statValue = Integer.parseInt(value);
                totalPower += statValue;
            }
        }
        System.out.println(heroName + " has a total power of: " + totalPower);
        return totalPower;
    } else {
        System.out.println("Error: " + response.statusCode());
        return -1;
    }

}

}

