package com.example;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONArray;

public class SuperHeroFinder {

public static void main(String[] args) throws IOException, InterruptedException {
    getSuperheroInfo();
}
public static void getSuperheroInfo() throws IOException, InterruptedException {
   String url="https://www.superheroapi.com/api.php/Token/search/spider-man";
   HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build(); 
HttpResponse<String> response = client.send(
    request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode()== 200){
        System.out.println(response.body());
    } else {
        System.out.println("Error: " + response.statusCode());
    }

}

}

