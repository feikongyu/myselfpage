package com.myself.demo.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


import java.io.IOException;

public class HttpClientTestController {

    public static void main(String[] args) {
        try {
            HttpContext httpContext = new BasicHttpContext();
            HttpClient httpClient = HttpClients.custom().build();
            HttpGet httpGet = new HttpGet("http://www.cshi.com");
            HttpResponse httpResponse = httpClient.execute(httpGet,httpContext);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = httpResponse.getEntity();
                String entityEntity = EntityUtils.toString(entity,"UTF-8");
                EntityUtils.consume(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
