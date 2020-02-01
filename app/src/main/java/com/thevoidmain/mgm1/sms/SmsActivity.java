package com.thevoidmain.mgm1.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thevoidmain.mgm1.R;
import com.thevoidmain.mgm1.implicit.MainActivity;

import java.util.List;

public class SmsActivity extends AppCompatActivity {


    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        textSMS = (EditText) findViewById(R.id.editTextSMS);

        //check for permission before sending sms
        ActivityCompat.requestPermissions(SmsActivity.this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                1);

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String phoneNo = textPhoneNo.getText().toString();
                String sms = textSMS.getText().toString();

                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        SubscriptionManager localSubscriptionManager = SubscriptionManager.from(SmsActivity.this);
                        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }


                        if (localSubscriptionManager.getActiveSubscriptionInfoCount() > 1) {
                            List localList = localSubscriptionManager.getActiveSubscriptionInfoList();

                            SubscriptionInfo simInfo1 = (SubscriptionInfo) localList.get(0);
                            SubscriptionInfo simInfo2 = (SubscriptionInfo) localList.get(1);

                            //SendSMS From SIM One
                            //SmsManager.getSmsManagerForSubscriptionId(simInfo1.getSubscriptionId()).sendTextMessage(customer.getMobile(), null, smsText, sentPI, deliveredPI);

                            //SendSMS From SIM Two
                            SmsManager.getSmsManagerForSubscriptionId(simInfo2.getSubscriptionId()).sendTextMessage(phoneNo, null, sms, null, null);
                        }
                    } else {
                        SmsManager.getDefault().sendTextMessage(phoneNo, null, sms, null, null);
                        Toast.makeText(getBaseContext(), "sent", Toast.LENGTH_SHORT).show();
                    }

//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(SmsActivity.this, "Permission denied to send sms", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void sendUsingBuiltInApp(View view){
        try {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", "write your message");
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendIntent);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
