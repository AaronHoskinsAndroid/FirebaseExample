package examples.aaronhoskins.com.firebaseexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
public static final String TAG = "TAG_FCM";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        displayPushNotification(remoteMessage);

    }

    public void displayPushNotification(RemoteMessage remoteMessage){
        //Base intent to start the activity
        Intent intent = new Intent(this, MainActivity.class);
        //Pending intent so the intent stays around pending an action
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT);
        //Get the user selected ring tone for notifications
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //build the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                .setContentIntent(pendingIntent)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_disabled)
                .setSound(soundUri);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


        //update user notifications
        notificationManager.notify(0, notificationBuilder.build());

    }
}
