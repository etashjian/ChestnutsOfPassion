package com.gimbal.android.sample;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by etashjian on 6/27/15.
 */
public class GimbalServerGet extends AsyncTask<String, Void, String> {

    GimbalServerGet() {}


    protected String doInBackground(String... place) {
        String get_val = "";

        try {
            HttpClient client = new DefaultHttpClient();
            String getURL = "https://manager.gimbal.com/api/v2/places/" + place[0];
            Log.e("PUT ", getURL);
            HttpGet get = new HttpGet(getURL);
            get.addHeader("Authorization","Token token=99095550f9da4f35dd14fd588abe68a6");
            HttpResponse responseGet = client.execute(get);
            HttpEntity resEntityGet = responseGet.getEntity();
            if (resEntityGet != null) {
                //do something with the response
                get_val = EntityUtils.toString(resEntityGet);
                Log.e("GET ", get_val);
            } else {
                Log.e("GET ", "FAILED");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GET ", e.toString());
        }

        return get_val;
    }
}
