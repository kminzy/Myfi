package com.MyFi.MyFridge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;

import static android.content.Context.MODE_PRIVATE;

public class Broadcast extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences prefs = context.getSharedPreferences("Alarm", MODE_PRIVATE);


        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), ViewIngredientActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle("유통기한이 만료 임박 알림.");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.splash_logo);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("유통기한이 임박한 재료가 있습니다. 지금 확인하세요");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

//===removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);

        }

        SharedPreferences alram = PreferenceManager.getDefaultSharedPreferences((MainActivity)MainActivity.mContext);
        int beforeDay = alram.getInt("pushAlarmDay",3);

        if(alram.getBoolean("pushAlarm",true))
        {

            if(((MainActivity)MainActivity.mContext).myIngredientList!=null) {
                for (int i = 0; i < ((MainActivity) MainActivity.mContext).myIngredientList.size(); i++) {
                    if (((MainActivity) MainActivity.mContext).myIngredientList.get(i).getDday() <= beforeDay) {
                        mNotificationManager.notify(0, mBuilder.build());
                        break;
                    }
                }
            }
        }

        //if(isAlram) {

        //}

    }
}
