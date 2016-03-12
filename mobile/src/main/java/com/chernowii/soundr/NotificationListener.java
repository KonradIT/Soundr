package com.chernowii.soundr;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

/**
 * Created by Konrad Iturbe on 03/12/16.
 */
public class NotificationListener extends NotificationListenerService {
    static final String TAG = "Soundr Debug";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if(sbn.getPackageName().equals("com.whatsapp")){
            Toast.makeText(getApplicationContext(),"WhatsApp Message Received!",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }
}