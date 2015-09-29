package com.monit.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class JSONRest{

    private final static String ENDPOINT = "http://10.129.187.163/render?target=stats.teste&format=json&from=-3min";

    public String getJson() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(ENDPOINT);
        HttpResponse response;

        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            return EntityUtils.toString(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
