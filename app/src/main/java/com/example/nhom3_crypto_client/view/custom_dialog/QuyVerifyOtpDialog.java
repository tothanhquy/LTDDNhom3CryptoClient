package com.example.nhom3_crypto_client.view.custom_dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhom3_crypto_client.R;

import java.util.regex.Pattern;

public class QuyVerifyOtpDialog extends Dialog {
    EditText input;
    Button btnVerify, btnCancel;
    OkCallback okCallback;

    public void setHandleCallback(OkCallback okCallback) {
        this.okCallback = okCallback;
    }

    public QuyVerifyOtpDialog(Context context) {
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }
    public static interface OkCallback{
        public void handle(String otp);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quy_dialog_verify_otp);
        input = findViewById(R.id.quyDialogVerifyOtpInput);
        btnCancel = findViewById(R.id.quyDialogVerifyOtpCancelBtn);
        btnVerify = findViewById(R.id.quyDialogVerifyOtpVerifyBtn);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDataValid()) {
                        okCallback.handle(input.getText().toString());
                    }
                }
            });
    }
    public static boolean checkValidOtp(String otp){
        String regex = "^(\\d{6})$";
        return Pattern.matches(regex, otp);
    }
    private boolean isDataValid() {
        String otp = input.getText().toString().trim();
        if(otp.isEmpty()){
            Toast.makeText(getContext(), "Otp không được để trống",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(!checkValidOtp(otp)){
                Toast.makeText(getContext(), "Otp không hợp lệ",Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }
}
