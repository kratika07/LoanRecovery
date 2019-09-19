package com.example.myapplication.Helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.myapplication.Activities.SplashScreen;
import com.example.myapplication.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Random;

/**
 * Created by admin18 on 7/6/19.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";
    int random = new Random().nextInt(61) + 20;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("<><><msg   "+remoteMessage.getNotification().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            long[] v = {500,1000};
            //Uri sound = Uri.parse("android.resource://" + this.getPackageName() + "/raw/plucky");
            String CHANNEL_ID = "my_channel_001";// The id of the channel.
            CharSequence name = "Loan";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setSound(null,null);

            Intent intent = new Intent(this, SplashScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setChannelId(CHANNEL_ID)
                    .setSound(null)
                    .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);;
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(1, builder.build());

        }else {
            sendNotification(remoteMessage);
        }


    }


    private void sendNotification(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        //Uri sound = Uri.parse("android.resource://" + this.getPackageName() + "/raw/plucky");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
        notificationBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(null);
        long[] v = {500,1000};
        notificationBuilder.setVibrate(v);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(random, notificationBuilder.build());

    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             notificationBuilder.setColor(Color.parseColor("#3DBAAA"));
             return R.mipmap.ic_launcher;
        } else {
            return R.mipmap.ic_launcher;
        }
    }




}
