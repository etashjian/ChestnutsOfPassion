package com.gimbal.android.sample;

import android.os.AsyncTask;
import android.util.Log;

import com.gimbal.android.Place;

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
import org.json.JSONArray;

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
public class GimbalServer {

    public static boolean is_food_available(String id) {

        boolean avail = false;

        try
        {
            String retval = new GimbalServerGet().execute(id).get();
            avail = retval.contains("Food\":\"TRUE");
        } catch (Exception e) {
            Log.e("AppService", e.toString());
        }

        return avail;
    }

    public static void set_server_pair(String key, String value, String id) {
        try
        {
            String retval = new GimbalServerGet().execute(id).get();
            // I'm lazy. assuming that key map already has key.
            retval = retval.replaceAll(key + "\":\"[^\"]+", key + "\":\"" + value);

            String in[] = {id, retval};
            new GimbalServerPut().execute(in).get();
        } catch (Exception e) {
            Log.e("AppService", e.toString());
        }
    }



}
