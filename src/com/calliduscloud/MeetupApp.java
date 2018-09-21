package com.calliduscloud;

import com.calliduscloud.getters.CityGetter;
import com.calliduscloud.getters.EventGetter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Scanner;

public class MeetupApp {

    public static void main(String[] args) {

        //API key ne bi trebalo da bude ovde (iz sigurnosnih razloga), ali sam ga ostavio u slucaju zelite da pokrenete aplikaciju
        String key = "4992f2e3417d24712b4e6e242c2778";
        CityGetter cityGetter = new CityGetter();
        String jsonCities = null;

        try {
            jsonCities = cityGetter.getCities(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        JSONObject root = null;
        try {
            root = (JSONObject) parser.parse(jsonCities);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray results = (JSONArray) root.get("results");

        List<String> cities = cityGetter.decodeCityJSON(results);

        Scanner scan = new Scanner(System.in);

        System.out.print("\nUnesite redni broj grada za koji zelite da pogledate dogadjaje: ");
        Integer num;
        while (true) {
            try {
                num = Integer.parseInt(scan.nextLine());
                if (num > cities.size() || num < 1) throw new IndexOutOfBoundsException();
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.print("Broj koji ste uneli je izvan opsega, molimo unesite broj od 1 do " + cities.size() + ": ");
            } catch (Exception e) {
                System.out.print("Podatak koji ste uneli nije broj, pokusajte ponovo: ");
            }
        }

        EventGetter eventGetter = new EventGetter(cities.get(num - 1));
        String jsonEvents = null;
        try {
            jsonEvents = eventGetter.getEvents(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            root = (JSONObject) parser.parse(jsonEvents);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        results = (JSONArray) root.get("results");

        if(results.isEmpty()) {
            System.out.println("Nema podataka za dogadjaje u ovom gradu");
        } else {
            eventGetter.decodeEventJSON(results);
        }


    }

}
