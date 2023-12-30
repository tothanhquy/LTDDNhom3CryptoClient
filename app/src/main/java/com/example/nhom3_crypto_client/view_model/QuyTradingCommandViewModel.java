package com.example.nhom3_crypto_client.view_model;

import android.content.Context;

import com.example.nhom3_crypto_client.api.API;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class QuyTradingCommandViewModel extends BaseViewModel{
    private Context context;

    public QuyTradingCommandViewModel(Context context){
        this.context = context;
    }

    public void openCommand(String optCode, String buyOrSell, String coinId, float moneyNumber, int leverage, boolean enableTpSl, float takeProfit, float stopLoss,OkCallback okCallback){
        if(isLoading().getValue())return;
        _isLoading.postValue(true);
        new Thread(()->{
            openCommandAPI(optCode,buyOrSell,coinId,moneyNumber,leverage,enableTpSl,takeProfit,stopLoss,okCallback);
        }).start();
    }

    private void openCommandAPI(String optCode, String buyOrSell, String coinId, float moneyNumber, int leverage, boolean enableTpSl, float takeProfit, float stopLoss, OkCallback okCallback){
        try{
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
//            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .addFormDataPart("optCode", optCode)
                    .addFormDataPart("buyOrSell", buyOrSell)
                    .addFormDataPart("coinId", coinId)
                    .addFormDataPart("moneyNumber", ""+moneyNumber)
                    .addFormDataPart("leverage", ""+leverage)
                    .addFormDataPart("enableTpSl", ""+enableTpSl)
                    .addFormDataPart("takeProfit", ""+takeProfit)
                    .addFormDataPart("stopLoss", ""+stopLoss)
                    .build();



            API.ResponseAPI response = API.post(context,"/profile/miniDetails",requestBody);
            if(response.status== API.ResponseAPI.Status.Fail){
                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,response.error));
            }else{
//                okCallback.handle(response.data);
                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Info, "Success", "Mở lệnh thành công",new SystemNotificationModel.OkCallback() {
                    @Override
                    public void handle() {
                        okCallback.handle(response.data);
                    }
                }));
            }
        }catch(Exception e){
            System.out.println(e);
            _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,"Lỗi hệ thống."));
        }finally {
            _isLoading.postValue(false);
        }
    }

    public void checkTradePinStatus(OkCallback okCallback){
//        if(isLoading().getValue())return;
//        _isLoading.postValue(true);
        new Thread(()->{
            checkTradePinStatusAPI(okCallback);
        }).start();
    }
    private void checkTradePinStatusAPI(OkCallback okCallback){
        try{
            API.RequestParams params = new API.RequestParams();

            API.ResponseAPI response = API.get(context,"/account/getTradeAuthStatus",params);
            if(response.status== API.ResponseAPI.Status.Fail){
                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,response.error));
            }else{
                okCallback.handle(response.data);
//                _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Info,"ok"));
            }
        }catch(Exception e){
            System.out.println(e);
            _notification.postValue(new SystemNotificationModel(SystemNotificationModel.Type.Error,"Lỗi hệ thống."));
        }finally {
//            _isLoading.postValue(false);
        }
    }
}
