package com.example.nhom3_crypto_client.core;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.squareup.picasso.Picasso;

public class General {
    public static void showNotification(Context context, SystemNotificationModel model){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(model.content);
        builder.show();
    }
    public static void setImageUrl(Context context, ImageView image, String url){
        Picasso.with(context).load(url).into(image);
    }


}
