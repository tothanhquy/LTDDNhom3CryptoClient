package com.example.nhom3_crypto_client.core;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nhom3_crypto_client.api.API;
import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class General {
    public static void showNotification(Context context, SystemNotificationModel model){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(model.type.equals(SystemNotificationModel.Type.Info)){
            builder.setIcon(R.drawable.quy_info);
            builder.setTitle("Thông báo");
        }else if(model.type.equals(SystemNotificationModel.Type.Warning)){
            builder.setIcon(R.drawable.quy_warning);
            builder.setTitle("Cảnh báo");
        }else{
            builder.setIcon(R.drawable.quy_error);
            builder.setTitle("Error!");
        }

        builder.setMessage(model.content);
        builder.setCancelable(false);
        builder.setOnDismissListener(null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(model.okCallback!=null){
                    model.okCallback.handle();
                }
            }
        });

        builder.show();
    }
    public static void setImageUrl(Context context, ImageView image, String url){
        Picasso.with(context).load(url).into(image);
    }
    public static void setAvatarUrl(Context context, ImageView image, String name){
        String url = API.SERVER_URL_AND_PORT+"/resource/account/avatar/"+name;
        Picasso.with(context).load(url).into(image);
    }
    public static String convertTimeToDateTime(long milliSeconds){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
