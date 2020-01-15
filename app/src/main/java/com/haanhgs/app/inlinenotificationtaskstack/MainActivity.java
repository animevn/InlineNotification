package com.haanhgs.app.inlinenotificationtaskstack;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//test
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bnSend)
    Button bnSend;

    public static final String RESULT = "result_code";
    public static final int NOTIFICATION_ID = 99999;

    private static final String CHANNELID = "channel_id";
    private static final int REQUEST = 999;
    private NotificationManager manager;
    private TaskStackBuilder builder;
    private RemoteInput remoteInput;
    private PendingIntent pendingIntent;
    private NotificationCompat.Action action;

    private void createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNELID,
                    "Notification", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Broadcast");
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createTaskStack(){
        builder = TaskStackBuilder.create(this);
        builder.addParentStack(MainActivity.class);
    }

    //if need to open MainActivity from notification, can remove this
    private void createIntentToOpenMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        builder.addNextIntent(intent);
    }

    //open notification activity when click reply
    private void createIntentToOpenNotificationActivity(){
        Intent intent = new Intent(this, NotificationReply.class);
        builder.addNextIntent(intent);
    }

    private void createRemoteInput(){
        remoteInput = new RemoteInput.Builder(RESULT).setLabel("Reply").build();
    }

    private void createPendingIntent(){
        pendingIntent = builder.getPendingIntent(REQUEST, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void addRemoteInputToNotificationAction(){
        action = new NotificationCompat.Action
                .Builder(R.drawable.icon_foreground, " Reply", pendingIntent)
                .addRemoteInput(remoteInput)
                .build();
    }

    private void buildNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNELID)
                .setSmallIcon(R.drawable.icon_foreground)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setContentTitle("Reply")
                .setContentText("Hi")
                .addAction(action);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    @OnClick(R.id.bnSend)
    public void onViewClicked(){
        createTaskStack();
        createIntentToOpenMainActivity();
        createIntentToOpenNotificationActivity();
        createRemoteInput();
        createPendingIntent();
        addRemoteInputToNotificationAction();
        buildNotification();
    }
}
