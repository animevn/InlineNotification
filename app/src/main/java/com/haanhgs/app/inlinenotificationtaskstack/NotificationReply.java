package com.haanhgs.app.inlinenotificationtaskstack;

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.haanhgs.app.inlinenotificationtaskstack.MainActivity.NOTIFICATION_ID;
import static com.haanhgs.app.inlinenotificationtaskstack.MainActivity.RESULT;

public class NotificationReply extends AppCompatActivity {

    @BindView(R.id.tvReceived)
    TextView tvReceived;

    private void getCharFromInline(Bundle bundle){
        CharSequence message = "";
        if (bundle != null){
            message = bundle.getCharSequence(RESULT);
        }
        tvReceived.setText(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_reply);
        ButterKnife.bind(this);

        Bundle remote = RemoteInput.getResultsFromIntent(getIntent());
        getCharFromInline(remote);
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) manager.cancel(NOTIFICATION_ID);
    }

}
