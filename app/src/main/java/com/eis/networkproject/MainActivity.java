package com.eis.networkproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.eis.communication.network.listeners.InviteListener;
import com.eis.smslibrary.SMSHandler;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSFailReason;
import com.eis.smsnetwork.SMSJoinableNetManager;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements InviteListener<SMSPeer, SMSFailReason> {

    private EditText editText;
    private ListView subsList;
    private SMSPeer[] subsToNet;
    private ArrayAdapter<SMSPeer> adapter;

    private static final String[] PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.phone_input);
        subsList = findViewById(R.id.listView);

        ActivityCompat.requestPermissions(this, PERMISSIONS, 1);

        SMSHandler.getInstance().setup(this);

        setupLayout();
        Timer timer = new Timer();
        timer.schedule(new UpdateList(this), 0, 200);
    }

    public void setupLayout(){
        subsToNet = SMSJoinableNetManager.getInstance().
                getNetSubscriberList().getSubscribers().toArray(new SMSPeer[] {});
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subsToNet);
        subsList.setAdapter(adapter);
    }

    public SMSPeer getMessage(){
        String message = editText.getText().toString();
        return new SMSPeer(message);
    }

    public void buttonInput(View view){
        SMSJoinableNetManager.getInstance().invite(getMessage(), this);
    }

    public void updateList(){
        subsToNet = SMSJoinableNetManager.getInstance().
                getNetSubscriberList().getSubscribers().toArray(new SMSPeer[] {});
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onInvitationSent(SMSPeer invitedPeer) {
        Log.d("NET_DEMO", "Invitation was sent to: " + invitedPeer);
    }

    @Override
    public void onInvitationNotSent(SMSPeer notInvitedPeer, SMSFailReason failReason) {
        Log.e("NET_DEMO", "Invitation was NOT sent to: " + notInvitedPeer + " error was: " + failReason);
    }

    class UpdateList extends TimerTask{

        private MainActivity activity;

        public UpdateList(MainActivity activity){
            this.activity = activity;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.updateList();
                }
            });
        }
    }
}
