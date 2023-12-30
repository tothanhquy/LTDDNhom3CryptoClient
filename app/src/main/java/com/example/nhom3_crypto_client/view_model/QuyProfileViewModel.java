package com.example.nhom3_crypto_client.view_model;

import android.content.Context;

import com.example.nhom3_crypto_client.api.API;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;

public class QuyProfileViewModel extends BaseViewModel{
    private Context context;

    public QuyProfileViewModel(Context context){
        this.context = context;
    }

    public void getMiniInfo(OkCallback okCallback){
//        if(isLoading().getValue())return;
//        _isLoading.postValue(true);
        new Thread(()->{
            getMiniInfoAPI(okCallback);
        }).start();
    }

    private void getMiniInfoAPI(OkCallback okCallback){
        try{
            API.RequestParams params = new API.RequestParams();

            API.ResponseAPI response = API.get(context,"/profile/miniDetails",params);
            if(response.status== API.ResponseAPI.Status.Fail){
                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,response.error));
            }else{
                okCallback.handle(response.data);
//                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Info,"ok"));
            }
        }catch(Exception e){
//            throw new RuntimeException(e);
            System.out.println(e);
            _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,"Lỗi hệ thống."));
        }finally {
//            _isLoading.postValue(false);
        }
    }
}
