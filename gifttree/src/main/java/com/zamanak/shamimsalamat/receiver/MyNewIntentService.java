package com.zamanak.shamimsalamat.receiver;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.zamanak.shamimsalamat.activity.ScoreActivity;
import com.zamanak.shamimsalamat.R;


/**
 * Created by a.Raghibdoust on 2/7/2018.
 */

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("درخت شانس");
        builder.setContentText("سلام، میدونستی تا آخر امروز یه شانس داری که تو درخت شانس برنده باشی؟" +"\n"+"پس همین الان شانست رو امتحان کن!");
        builder.setSmallIcon(R.drawable.prize);
        Intent notifyIntent = new Intent(this, ScoreActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        //NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        //managerCompat.notify(NOTIFICATION_ID, notificationCompat);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0, notificationCompat);
        }
    }
}