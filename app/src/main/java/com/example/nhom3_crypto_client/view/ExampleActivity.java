package com.example.nhom3_crypto_client.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.service.ServiceConnections;
import com.example.nhom3_crypto_client.service.ServiceCreatedCallback;
import com.example.nhom3_crypto_client.service.SocketService;

public class ExampleActivity extends BaseActivity {
    private SocketService socketService;
    private Boolean isBoundSocketService;
    private ServiceConnection serviceConnection;

    private class SocketServiceCreatedCallback implements ServiceCreatedCallback{
        @Override
        public void setService(Service service) {
            socketService = (SocketService) service;
        }
        @Override
        public void setIsBound(Boolean isBound) {
            isBoundSocketService = isBound;
        }
        @Override
        public void createdComplete() {
            //
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        // Bind to the service
        SocketServiceCreatedCallback serviceCreatedCallback = new SocketServiceCreatedCallback();
        serviceConnection = new ServiceConnections.SocketServiceConnection(serviceCreatedCallback);
        Intent intent = new Intent(this, SocketService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBoundSocketService) {
            unbindService(serviceConnection);
            isBoundSocketService = false;
        }
    }
}