package com.example.nhom3_crypto_client.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.service.ServiceConnections;
import com.example.nhom3_crypto_client.service.ServiceCreatedCallback;
import com.example.nhom3_crypto_client.service.SocketService;
import com.example.nhom3_crypto_client.view_model.LoginViewModel;

public class ExampleActivity extends BaseActivity {
    private SocketService socketService;
    private Boolean isBoundSocketService;
    private ServiceConnection serviceConnection;

    private LoginViewModel loginViewModel;

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

    public void Login(View view){
//        loginViewModel.login("123", "abc", new Login1());
    }

    public class Login1 implements SystemNotificationModel.OkCallback{

        @Override
        public void handle() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ExampleActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
            loginViewModel = new LoginViewModel(getApplicationContext());
        System.out.println("abc");

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