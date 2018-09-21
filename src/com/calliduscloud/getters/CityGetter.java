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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CityGetter {



    public String getCities(String key) throws Exception{

        String responseString;

        URI request = new URIBuilder()
                .setScheme("http")
                .setHost("api.meetup.com")
                .setPath("/2/cities")
                .setParameter("country", "RS")
                .setParameter("only", "city")
                .setParameter("key", key)
                .build();

        HttpGet get = new HttpGet(request);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(get);
        responseString = EntityUtils.toString(response.getEntity());

        return responseString;
    }

    public List<String> decodeCityJSON(JSONArray results) {

        List<String> cities = new ArrayList<>();

        ListIterator i = results.listIterator();
        while (i.hasNext()) {
            JSONObject jsonCityObject = (JSONObject) i.next();
            String city = (String) jsonCityObject.get("city");
            cities.add(city);
            System.out.println(i.nextIndex() + " " + city);
        }

        return cities;
    }
}
