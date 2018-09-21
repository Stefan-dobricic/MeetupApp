package com.calliduscloud.getters;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URI;

public class EventGetter {

    private String city;

    public EventGetter(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEvents(String key) throws Exception {

        String responseString;

        URI request = new URIBuilder()
                .setScheme("http")
                .setHost("api.meetup.com")
                .setPath("/2/open_events")
                .setParameter("country", "RS")
                .setParameter("city", city)
                .setParameter("key", key)
                .build();

        HttpGet get = new HttpGet(request);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(get);
        responseString = EntityUtils.toString(response.getEntity());

        return responseString;
    }

    public void decodeEventJSON(JSONArray results) {
        for (Object result : results) {
            JSONObject jsonEventObject = (JSONObject) result;

            JSONObject venue = (JSONObject) jsonEventObject.get("venue");
            System.out.println("\nName: " + jsonEventObject.get("name"));

            System.out.print("Adresa: ");
            if (venue != null) {
                System.out.println(venue.get("address_1") + ", " + venue.get("city"));
            } else {
                System.out.println("Adresa nije dostupna");
            }

            System.out.print("Opis: ");
            String description = (String) jsonEventObject.get("description");
            if (description != null) {
                System.out.println((description).replaceAll("<p>", "").replaceAll("</p>", ""));
            } else {
                System.out.println("Opis nije dostupna");
            }
        }
    }
}
