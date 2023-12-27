package com.example.nhom3_crypto_client.view_model;

import android.content.Context;

import com.example.nhom3_crypto_client.api.API;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoginViewModel extends BaseViewModel{
    private Context context;

    public LoginViewModel(Context context){
        this.context = context;
    }

    public void login(String sdt, String password, SystemNotificationModel.OkCallback okCallback){
        if(isLoading().getValue())return;
        _isLoading.postValue(true);
        new Thread(()->{
            loginAPI(sdt,password,okCallback);
        }).start();
    }
    private void loginAPI(String sdt, String password, SystemNotificationModel.OkCallback okCallback){
        try{
            System.out.println(sdt);
            System.out.println(password);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
//            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .addFormDataPart("numberPhone", sdt)
                    .addFormDataPart("password", password)
                    .build();

            API.ResponseAPI response = API.post(context,"/account/login",requestBody);
            if(response.status== API.ResponseAPI.Status.Fail){
                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,response.error));
            }else{
                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Info,okCallback));
            }
        }catch(Exception e){
            System.out.println(e);
            _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,"Lỗi hệ thống."));
        }finally {
            _isLoading.postValue(false);
        }
    }
    private void loginGetExample(String sdt, String password){
        try{
            API.RequestParams params = new API.RequestParams();
            params.add("numberPhone",sdt);
            params.add("password",password);

            API.ResponseAPI response = API.get(context,"/account/login",params);
            if(response.status== API.ResponseAPI.Status.Fail){
                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,response.error));
            }else{
                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Info,"ok"));
            }
        }catch(Exception e){
            System.out.println(e);
            _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,"Lỗi hệ thống."));
        }finally {
            _isLoading.postValue(false);
        }
    }
}
