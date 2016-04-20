package abhi.kurla.bloodpressure;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Gaurav on 20-04-2016.
 */
public class ReadSMS extends BroadcastReceiver {

    String number = "860936";
    String msg_from, msgBody;
    String[] array;


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                    }
                }catch(Exception e){
                    Log.d("Exception caught",e.getMessage());
                }
                if(msg_from.toLowerCase().contains(number.toLowerCase())){
                    array = msgBody.split(", ", 3);
                    String notif_msg = "Systolic:" + array[0] +
                            "Diastolic:" + array[1] +
                            "Heart Rate" + array[2];

                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(context)
                                    .setContentTitle("BP Monitor")
                                    .setContentText(notif_msg);

                    int mNotificationId = 001;
                    Intent myIntent = new Intent(context, MainActivity.class);
                    myIntent.putExtra("systolic",array[0]);
                    myIntent.putExtra("diastolic",array[1]);
                    myIntent.putExtra("heart_rate",array[2]);
                    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, myIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);

                    // Add as notification
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(mNotificationId, builder.build());


                }




            }
        }
    }
}
