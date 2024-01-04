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

    public void register(String sdt, String password, String confirmPassword, String name, SystemNotificationModel.OkCallback okCallback){
        if(isLoading().getValue())return;
        _isLoading.postValue(true);
        new Thread(()->{
            registerAPI(sdt,password, confirmPassword, name,okCallback);
        }).start();
    }
    private void registerAPI(String sdt, String password, String confirmPassword, String name ,SystemNotificationModel.OkCallback okCallback){
        try{
            System.out.println(sdt);
            System.out.println(password);
            System.out.println(confirmPassword);
            System.out.println(name);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
//            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .addFormDataPart("numberPhone", sdt)
                    .addFormDataPart("password", password)
                    .addFormDataPart("confirmPassword", confirmPassword)
                    .addFormDataPart("name", name)
                    .build();

            API.ResponseAPI response = API.post(context,"/account/registerStep1",requestBody);
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

    public void register2(String sdt, String sum,  SystemNotificationModel.OkCallback okCallback){
        if(isLoading().getValue())return;
        _isLoading.postValue(true);
        new Thread(()->{
            registerAPI2(sdt, sum ,okCallback);
        }).start();
    }
    private void registerAPI2(String sdt, String sum ,SystemNotificationModel.OkCallback okCallback){
        try{
            System.out.println(sdt);
            System.out.println(sum);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
//            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .addFormDataPart("numberPhone", sdt)
                    .addFormDataPart("code", sum)
                    .build();

            API.ResponseAPI response = API.post(context,"/account/registerStep2",requestBody);
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

    public void resetPassword(String sdt, String newPassword, String confirmNewPassword, SystemNotificationModel.OkCallback okCallback){
        if(isLoading().getValue())return;
        _isLoading.postValue(true);
        new Thread(()->{
            resetPasswordAPI(sdt, newPassword, confirmNewPassword,okCallback);
        }).start();
    }
    private void resetPasswordAPI(String sdt, String newPassword, String confirmNewPassword ,SystemNotificationModel.OkCallback okCallback){
        try{
            System.out.println(sdt);
            System.out.println(newPassword);
            System.out.println(confirmNewPassword);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
//            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .addFormDataPart("numberPhone", sdt)
                    .addFormDataPart("New password", newPassword)
                    .addFormDataPart("confirm new password", confirmNewPassword)
                    .build();

            API.ResponseAPI response = API.post(context,"/account/resetPasswordStep1",requestBody);
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

    public void resetPassword2(String sdt, String newPassword, String confirmNewPassword, SystemNotificationModel.OkCallback okCallback){
        if(isLoading().getValue())return;
        _isLoading.postValue(true);
        new Thread(()->{
            resetPasswordAPI(sdt, newPassword, confirmNewPassword,okCallback);
        }).start();
    }
    private void resetPasswordAPI2(String sdt, String newPassword, String confirmNewPassword ,SystemNotificationModel.OkCallback okCallback){
        try{
            System.out.println(sdt);
            System.out.println(newPassword);
            System.out.println(confirmNewPassword);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
//            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .addFormDataPart("numberPhone", sdt)
                    .addFormDataPart("New password", newPassword)
                    .addFormDataPart("confirm new password", confirmNewPassword)
                    .build();

            API.ResponseAPI response = API.post(context,"/account/resetPasswordStep2",requestBody);
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

}
