package com.example.nhom3_crypto_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import com.example.nhom3_crypto_client.api.API;
import com.example.nhom3_crypto_client.model.socket.SocketServiceEventsModel;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //test quy branch

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            Socket clientSocket = new Socket("192.168.1.10", 8081);
            PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStream inputStream = clientSocket.getInputStream();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}