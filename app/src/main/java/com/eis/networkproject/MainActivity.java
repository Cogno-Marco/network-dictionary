package com.eis.networkproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.eis.communication.network.listeners.InviteListener;
import com.eis.smslibrary.SMSPeer;
import com.eis.smsnetwork.SMSFailReason;
import com.eis.smsnetwork.SMSJoinableNetManager;


public class MainActivity extends AppCompatActivity implements InviteListener<SMSPeer, SMSFailReason> {

    private EditText editText;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.phone_input);
        layout = (LinearLayout) findViewById(R.id.vertical_layout); // Your linear layout.
        updateLayout();
    }

    public void updateLayout(){
        ListAdapter adapter = ... // Your adapter.

        final int adapterCount = adapter.getCount();

        for (int i = 0; i < adapterCount; i++) {
            View item = adapter.getView(i, null, null);
            layout.addView(item);
        }
    }

    public SMSPeer getMessage(){
        String message = editText.getText().toString();
        return new SMSPeer(message);
    }

    public void buttonInput(View view){
        SMSJoinableNetManager.getInstance().invite(getMessage(), this);
    }

    @Override
    public void onInvitationSent(SMSPeer invitedPeer) {

    }

    @Override
    public void onInvitationNotSent(SMSPeer notInvitedPeer, SMSFailReason failReason) {

    }
}
