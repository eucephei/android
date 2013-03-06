package com.example.notificationmanager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNotification extends Activity {
    private Button notifyButn;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        notifyButn = (Button)findViewById(R.id.notifyButn);
        notifyButn.setOnClickListener(new View.OnClickListener() {
           
            @Override
            public void onClick(View v) {
                //Obtain Notification Service
                NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
               
                // activity
                Intent notifyIntent = new Intent(CreateNotification.this,CreateNotification.class); 
                notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pIntent=PendingIntent.getActivity(CreateNotification.this,0,
                                                                  notifyIntent,0);
                String longText = "A PendingIntent is a token that you give to another application (e.g. Notification Manager, Alarm Manager or other 3rd party applications), which allows this other application to use the permissions of your application to execute a predefined piece of code.To perform a broadcast via a pending intent so get a PendingIntent via PendingIntent.getBroadcast(). To perform an activity via an pending intent you receive the activity via PendingIntent.getActivity().";
                
                // Build notification
                // Actions are just fake
                Notification n = new Notification.Builder(CreateNotification.this)
					.setContentTitle("New mail from " + "test@gmail.com")
					.setContentText("Subject")
					.setSmallIcon(R.drawable.icon)
					.setContentIntent(pIntent).setAutoCancel(true)
					.addAction(R.drawable.icon, "Call", pIntent)
					.addAction(R.drawable.icon, "More", pIntent)
					.addAction(R.drawable.icon, "And more", pIntent)
					.setStyle(new Notification.BigTextStyle().bigText(longText))
					.build();
                		
                notificationManager.notify(0,n );
               
            }
        });
    }
}
