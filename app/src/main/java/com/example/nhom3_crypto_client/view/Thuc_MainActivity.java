package com.example.nhom3_crypto_client.view;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.api.API;
import com.example.nhom3_crypto_client.core.General;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.service.SocketService;
import com.example.nhom3_crypto_client.view.custom_dialog.QuyVerifyOtpDialog;
import com.example.nhom3_crypto_client.view_model.BaseViewModel;
import com.example.nhom3_crypto_client.view_model.LoginViewModel;
import com.example.nhom3_crypto_client.view_model.QuyAccountViewModel;


public class Thuc_MainActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private QuyAccountViewModel quyAccountViewModel;

    private void setRender() {
        // Set alert error
        loginViewModel.notification().observe(this, new Observer<SystemNotificationModel>() {
            @Override
            public void onChanged(SystemNotificationModel it) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (it != null) {
                            General.showNotification(Thuc_MainActivity.this, it);
                        }
                    }
                });
            }
        });
        quyAccountViewModel.notification().observe(this, new Observer<SystemNotificationModel>() {
            @Override
            public void onChanged(SystemNotificationModel it) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (it != null) {
                            General.showNotification(Thuc_MainActivity.this, it);
                        }
                    }
                });
            }
        });

        // Set alert notification
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thuc_activity_main);
        loginViewModel = new LoginViewModel(getApplicationContext());
        quyAccountViewModel = new QuyAccountViewModel(getApplicationContext());
        Signin();
        setRender();
        chechAuth();
    }

    private void chechAuth(){
        quyAccountViewModel.checkAuth(new BaseViewModel.OkCallback() {
            @Override
            public void handle(String data) {
                if(data.equals("true")){

                gotoMain();
                }
            }
        });
    }


    public void Signin () {
        final EditText password = findViewById(R.id.password);
        final EditText phonenumber = findViewById(R.id.phonenumber);
        Button btnLogin = findViewById(R.id.btnLogin);
        final TextView signup = findViewById(R.id.signup);
        final TextView resetpassword = findViewById((R.id.resetpassword));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thuc_MainActivity.this, Thuc_SignUpActivity.class);
                startActivity(intent);
            }
        });
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thuc_MainActivity.this, Thuc_ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setPassword = password.getText().toString();
                String setPhonenumber = phonenumber.getText().toString();

                if (setPassword.isEmpty() || setPhonenumber.isEmpty()) {
                    Toast.makeText(Thuc_MainActivity.this, "Bạn đang bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    login(setPhonenumber,setPassword);
                }
            }
        });
    }

    public void login(String phonenumber, String password){
        loginViewModel.login(phonenumber, password, new SystemNotificationModel.OkCallback() {
            @Override
            public void handle() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gotoMain();
                    }
                });
            }
        }, new SystemNotificationModel.OkCallback() {
            @Override
            public void handle() {

            }
        });
    }

    private void gotoMain(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Thuc_MainActivity.this, QuyMainActivity.class);
                gotoMainActivity.launch(intent);
            }
        });

    }

    ActivityResultLauncher<Intent> gotoMainActivity = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), o -> {
            Intent intent = o.getData();
            if(intent!=null){
                boolean isLogout = intent.getBooleanExtra("isLogout",false);
                if(!isLogout)finishAffinity();
            }else{
                finishAffinity();
            }
        });
}