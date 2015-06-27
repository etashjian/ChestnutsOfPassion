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

    public static boolean is_food_available(Place place) {

        boolean avail = false;

        try
        {
            String retval = new GimbalServerGet().execute(place.getIdentifier()).get();
            avail = retval.contains("Food\":\"TRUE");
        } catch (Exception e) {
            Log.e("AppService", e.toString());
        }

        return avail;
    }

    public static void set_food_available(boolean available, Place place) {
        try
        {
            String retval = new GimbalServerGet().execute(place.getIdentifier()).get();
            if(available) {
                retval = retval.replaceAll("FALSE", "TRUE");
            } else {
                retval = retval.replaceAll("TRUE", "FALSE");
            }

            String in[] = {place.getIdentifier(), retval};
            new GimbalServerPut().execute(in).get();
        } catch (Exception e) {
            Log.e("AppService", e.toString());
        }
    }



}
