package com.example.nhom3_crypto_client.view_model;

import android.content.Context;

import com.example.nhom3_crypto_client.api.API;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;

public class QuyAccountViewModel extends BaseViewModel{
    private Context context;

    public QuyAccountViewModel(Context context){
        this.context = context;
    }

    public void checkAuth(OkCallback okCallback){
//        if(isLoading().getValue())return;
//        _isLoading.postValue(true);
        new Thread(()->{
            checkAuthAPI(okCallback);
        }).start();
    }

    private void checkAuthAPI(OkCallback okCallback){
        try{
            API.RequestParams params = new API.RequestParams();
            params.add("userId","123");

            API.ResponseAPI response = API.get(context,"/coins/getAll",params);
            if(response.status== API.ResponseAPI.Status.Fail){
                okCallback.handle("false");
//                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,response.error));
            }else{
                okCallback.handle("true");
//                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Info,"ok"));
            }
        }catch(Exception e){
//            throw new RuntimeException(e);
            okCallback.handle("false");
            System.out.println(e);
//            _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,"Lỗi hệ thống."));
        }finally {
//            _isLoading.postValue(false);
        }
    }


}
