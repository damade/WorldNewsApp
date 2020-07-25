package com.example.android.worldnewsapp.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.android.worldnewsapp.Activity.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {


    public NotificationReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        MainActivity mainActivity = new MainActivity();
        //mainActivity.updateNotification();
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
