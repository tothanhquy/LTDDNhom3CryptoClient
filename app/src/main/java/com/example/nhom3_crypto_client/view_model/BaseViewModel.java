package com.example.nhom3_crypto_client.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nhom3_crypto_client.model.SystemNotificationModel;

public class BaseViewModel extends ViewModel {
    protected MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    protected MutableLiveData<SystemNotificationModel> _notification = new MutableLiveData<>();

    // Getter method for the LiveData
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }
    public LiveData<SystemNotificationModel> notification() {
        return _notification;
    }

    // Method to update the data
    public void updateData(boolean isLoading) {
        _isLoading.setValue(isLoading);
    }
}
