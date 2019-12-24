package com.haanhgs.app.inlinenotificationtaskstack;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationReply extends AppCompatActivity {

    @BindView(R.id.tvReceived)
    TextView tvReceived;

    private void getCharFromInline(Bundle bundle){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_reply);
        ButterKnife.bind(this);

    }

}
