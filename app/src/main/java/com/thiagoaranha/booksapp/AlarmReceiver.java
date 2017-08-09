package com.thiagoaranha.booksapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Thiago on 05/08/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final int SIMPLE_NOTIFICATION_ID =  1001;
    private static final String IS_BIG_NOTIFICATION = "isBigNotification";
    NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        String bookId = null;

        Bundle bundle = intent.getExtras();
        if(bundle != null){
            if(bundle.containsKey("bookId")){
                bookId = (String) bundle.get("bookId");
            }
        }


        createSimpleNotification(context, bookId);

    }

    private void createSimpleNotification(Context context, String bookId) {

        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getString(R.string.notification_title))
                        .setAutoCancel(true)
                        .setContentText(context.getString(R.string.notification_text));


        //Intent resultIntent = new Intent(context, MainActivity.class);   //-- Livro abrindo através da MainActivity
        Intent resultIntent = new Intent("NOTIFICATION_BOOK");             //-- Livro abrind através da BookActivity com intent filter

        resultIntent.putExtra(IS_BIG_NOTIFICATION,  false);
        resultIntent.putExtra("bookId", bookId);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);


        mNotificationManager.notify(SIMPLE_NOTIFICATION_ID, mBuilder.build());
    }


}
