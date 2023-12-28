package com.example.nhom3_crypto_client.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.api.API;
import com.example.nhom3_crypto_client.model.CoinServiceModel;
import com.example.nhom3_crypto_client.model.socket.SocketServiceEventsModel;
import com.example.nhom3_crypto_client.view.BaseActivity;
import com.example.nhom3_crypto_client.view.ExampleActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CoinService extends Service {
    private String socketName = "coin-service";
    private SocketService socketService;
    private Boolean isBoundSocketService;
    private ServiceConnection serviceConnection;
    private CoinServiceModel.CoinServiceListenerManager coinServiceListenerManager;
    private CoinServiceModel.CoinsNow coinsNow = null;

    public void addEventListener(ArrayList<String> coinsId, String author, CoinServiceModel.EventCallbackInterface callback){
        coinServiceListenerManager.addListener(coinsId,author,callback);
    }
    public void removeEventListener(ArrayList<String> coinsId, String author){
        coinServiceListenerManager.removeListener(coinsId,author);
    }
    public void removeEventListener(String author){
        coinServiceListenerManager.removeListener(author);
    }


    public static interface GetAllWaitCallback{
        public void handle(ArrayList<CoinServiceModel.CoinNow> coins);
    }
    public void getAllCoins(GetAllWaitCallback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    while(true){
                        if(i++>20)break;
                        if(coinsNow!=null)
                        {
                            callback.handle(coinsNow.data);
                            return;
                        };
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                }
                callback.handle(new ArrayList<>());
            }
        }).start();
    }

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
            socketService.addEventListener(SocketServiceEventsModel.EventNames.Receive.CoinsPriceNow,socketName,new UpdateValue());
        }
    }
    private class UpdateValue implements SocketServiceEventsModel.EventCallbackInterface {
        @Override
        public void handle(String data) {
            try{
                coinsNow = new Gson().fromJson(data,CoinServiceModel.CoinsNow.class);
                coinServiceListenerManager.handleEvent(coinsNow.data);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        coinServiceListenerManager = new CoinServiceModel.CoinServiceListenerManager();
        // Bind to the service
        SocketServiceCreatedCallback serviceCreatedCallback = new SocketServiceCreatedCallback();
        serviceConnection = new ServiceConnections.SocketServiceConnection(serviceCreatedCallback);
        Intent intent = new Intent(this, SocketService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new CoinService.MyBinder();
    }

    // Binder class to provide access to the service
    public class MyBinder extends Binder {
        CoinService getService() {
            return CoinService.this;
        }
    }

    @Override
    public void onDestroy() {
        socketService.removeEventListener(socketName);
        if (isBoundSocketService) {
            unbindService(serviceConnection);
            isBoundSocketService = false;
        }
        super.onDestroy();
    }
}