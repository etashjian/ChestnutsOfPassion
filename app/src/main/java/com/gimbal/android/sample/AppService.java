/**
 * Copyright (C) 2014 Gimbal, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of Gimbal, Inc.
 *
 * The following sample code illustrates various aspects of the Gimbal SDK.
 *
 * The sample code herein is provided for your convenience, and has not been
 * tested or designed to work on any particular system configuration. It is
 * provided AS IS and your use of this sample code, whether as provided or
 * with any modification, is at your own risk. Neither Gimbal, Inc.
 * nor any affiliate takes any liability nor responsibility with respect
 * to the sample code, and disclaims all warranties, express and
 * implied, including without limitation warranties on merchantability,
 * fitness for a specified purpose, and against infringement.
 */
package com.gimbal.android.sample;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import android.app.*;
import android.support.v4.app.NotificationCompat;

import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Communication;
import com.gimbal.android.CommunicationListener;
import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.Place;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Push;
import com.gimbal.android.Visit;
import com.gimbal.android.sample.GimbalEvent.TYPE;

public class AppService extends Service {
    private static final int MAX_NUM_EVENTS = 100;
    private LinkedList<GimbalEvent> events;
    private PlaceEventListener placeEventListener;
    private CommunicationListener communicationListener;

    @Override
    public void onCreate() {
        events = new LinkedList<GimbalEvent>(GimbalDAO.getEvents(getApplicationContext()));

        Gimbal.setApiKey(this.getApplication(), "17c9242e-b249-475b-8b57-64e3184eb9d9");

        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello world, I AM KAVYA!");
        placeEventListener = new PlaceEventListener() {

            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting, List<Visit> list) {
//                Toast.makeText(getApplicationContext(), beaconSighting.getBeacon().getTemperature()+"", Toast.LENGTH_LONG).show();

                super.onBeaconSighting(beaconSighting, list);
            }

            @Override
            public void onVisitStart(Visit visit) {
                Place p = visit.getPlace();
//                MyApp appState = ((MyApp)getApplicationContext());
//                appState.addBeacon(p);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(1, mBuilder.build());

                addEvent(new GimbalEvent(TYPE.PLACE_ENTER, visit.getPlace().getName(), new Date(visit.getArrivalTimeInMillis())));
            }

            @Override
            public void onVisitEnd(Visit visit) {
//                Place p = visit.getPlace();
//                MyApp appState = ((MyApp)getApplicationContext());
//                appState.removeBeacon(p);

                Toast.makeText(getApplicationContext(), "Bye Kavya :(", Toast.LENGTH_LONG).show();
                addEvent(new GimbalEvent(TYPE.PLACE_EXIT, visit.getPlace().getName(), new Date(visit.getDepartureTimeInMillis())));
            }
        };
        PlaceManager.getInstance().addListener(placeEventListener);

        // Setup CommunicationListener
        communicationListener = new CommunicationListener() {
            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Visit visit) {
            	
                for (Communication communication : communications) {
                    addEvent(new GimbalEvent(TYPE.NOTIFICATION_CLICKED, communication.getTitle(), new Date(communication.getDeliveryDate())));
                }

                // let the SDK post notifications for the communicates
                return communications;
            }

            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Push push) {
            	
                for (Communication communication : communications) {
                    addEvent(new GimbalEvent(TYPE.NOTIFICATION_CLICKED, communication.getTitle(), new Date(communication.getDeliveryDate())));
                }

                // let the SDK post notifications for the communicates
                return communications;
            }

            @Override
            public void onNotificationClicked(List<Communication> communications) {
                for (Communication communication : communications) {
                    addEvent(new GimbalEvent(TYPE.NOTIFICATION_CLICKED, communication.getTitle(), new Date()));
                }
            }
        };
        CommunicationManager.getInstance().addListener(communicationListener);
    }

    private void addEvent(GimbalEvent event) {
        while (events.size() >= MAX_NUM_EVENTS) {
            events.removeLast();
        }
        events.add(0, event);
        GimbalDAO.setEvents(getApplicationContext(), events);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        PlaceManager.getInstance().removeListener(placeEventListener);
        CommunicationManager.getInstance().removeListener(communicationListener);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
