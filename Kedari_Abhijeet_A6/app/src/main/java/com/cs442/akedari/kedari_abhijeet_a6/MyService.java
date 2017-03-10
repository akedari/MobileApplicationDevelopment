package com.cs442.akedari.kedari_abhijeet_a6;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Abhishek on 3/9/2016.
 */

//Here we can implement any subclass of Service class or we can directly extends Service class here

public class MyService extends Service{
    int _count=0;
    int var;
    boolean flag;
    Notification note;
    boolean functionCalledRecently = false;

    //Android will call this method on created on service
    //We can use this method for some initialization part we need that
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //Android will call this method on start of services
    //this method has to return the the integer value so we are using Start_Sticky those are predefined constant
    // These constant values are available with Service class and are get used in case services get restarted by the System
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        flag=true;
        int i=0;
        int cnt = intent.getIntExtra("value",0);
        var= cnt;
        Toast.makeText(this,"Service Started with Count "+cnt,Toast.LENGTH_LONG).show();
        Log.i("Mylogs", "Service Started !!!");
        showNotification(cnt);

        return START_STICKY;
    }

    public void showNotification(int count1)
    {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.timeimg);
            builder.setContentTitle("My first Notification");
            builder.setContentText("Count value is " + count1);
            Log.i("Mylogs","Count value started with: "+count1);
            Intent intent = new Intent(this, ServiceActivity.class);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addParentStack(ServiceActivity.class);
            taskStackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            builder.setVibrate(new long[] {1000,1000});
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

            final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int delay = 1000; // delay for 1 sec.
            int period = 12000; // repeat every 10 sec.
            final int notificationID =0;
            _count=count1;
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask()
            {
                public void run()
                {
                    if(flag==true) {
                        builder.setContentText("Count value is " + _count);
                        Log.i("Mylogs", "Count value incresed to: " + _count);
                        notificationManager.notify(notificationID, builder.build()); // display the notification
                        _count += 10;
                    }
                }
            }, delay, period);

    }
    //android call this service method onDestroy Activity phase
    @Override
    public void onDestroy() {
        //super.onDestroy();
        Toast.makeText(this,"Service Destroyed when Count was "+_count,Toast.LENGTH_LONG).show();
        Log.i("Mylogs", "Service onDestroy !!!");
        flag=false;
        //flag =false;
        //showNotification(0);
    }

    //this is mandatory service method we have to implement for types of services bind Services and Started Services
    //In case if we are working on Started services will return null from this method.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
