package com.example.nhom3_crypto_client.service;

import android.app.Service;

public interface ServiceCreatedCallback {
    public void setService(Service service);
    public void setIsBound(Boolean isBound);
    public void createdComplete();
}
