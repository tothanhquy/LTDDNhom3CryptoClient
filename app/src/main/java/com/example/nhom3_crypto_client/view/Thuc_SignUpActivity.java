package com.example.nhom3_crypto_client.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.view.custom_dialog.QuyVerifyOtpDialog;
import com.example.nhom3_crypto_client.view_model.LoginViewModel;

public class Thuc_SignUpActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private void setRender() {
        // Set alert error
        loginViewModel.notification().observe(this, new Observer<SystemNotificationModel>() {
            @Override
            public void onChanged(SystemNotificationModel it) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (it != null) {
                            Toast.makeText(Thuc_SignUpActivity.this, it.content, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.thuc_activity_signup);
        loginViewModel = new LoginViewModel(getApplicationContext());
        Signup();
        setRender();
    }

    public void Signup(){
        final EditText phonenumber = findViewById(R.id.phonenumber);
        final EditText createpassword = findViewById(R.id.createpassword);
        final EditText confirmcreatepassword = findViewById(R.id.confirmcreatepassword);
        final EditText text = findViewById(R.id.text);
        final Button btnSignup = findViewById(R.id.btnSignup);
        final TextView signin = findViewById(R.id.signin);
        final TextView resetpassword = findViewById((R.id.resetpassword));

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thuc_SignUpActivity.this, Thuc_MainActivity.class);
                startActivity(intent);
            }
        });

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thuc_SignUpActivity.this, Thuc_ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String setPhonenumber = phonenumber.getText().toString();
                String setCreatePassword = createpassword.getText().toString();
                String setConfirmCreatePassword = confirmcreatepassword.getText().toString();
                String setName = text.getText().toString();

                if (setCreatePassword.isEmpty() || setPhonenumber.isEmpty() || setConfirmCreatePassword.isEmpty() || setName.isEmpty()) {
                    Toast.makeText(Thuc_SignUpActivity.this, "Bạn đang bỏ trống", Toast.LENGTH_SHORT).show();

                } else {
                    if(!setConfirmCreatePassword.equals(setCreatePassword) ){
                        Toast.makeText(Thuc_SignUpActivity.this, "Xác nhận mật khẩu sai", Toast.LENGTH_LONG).show();
                    }else {
                        openOTP();
                    }

                }

            }
        });

    }

    public void openOTP(){
        QuyVerifyOtpDialog quyVerifyOtpDialog = new QuyVerifyOtpDialog(this);
        quyVerifyOtpDialog.setHandleCallback(new QuyVerifyOtpDialog.OkCallback() {
            @Override
            public void handle(String otp) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        quyVerifyOtpDialog.hide();
                        final EditText createPassword = findViewById(R.id.createpassword);
                        final EditText phonenumber = findViewById(R.id.phonenumber);
                        final EditText name = findViewById(R.id.text);
                        String setCreatePassword = createPassword.getText().toString();
                        String setNumberphone = phonenumber.getText().toString();
                        String setName = name.getText().toString();
                        loginViewModel.register2(setNumberphone, otp, new SystemNotificationModel.OkCallback() {
                            @Override
                            public void handle() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                });
                            }
                        }, new SystemNotificationModel.OkCallback() {
                            @Override
                            public void handle() {
                                quyVerifyOtpDialog.show();
                            }
                        });
                    }
                });

            }
        });
        quyVerifyOtpDialog.setResendCallback(new QuyVerifyOtpDialog.OkCallback() {
            @Override
            public void handle(String otp) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        quyVerifyOtpDialog.hide();
                        final EditText phonenumber = findViewById(R.id.phonenumber);
                        String setNumberphone = phonenumber.getText().toString();
                        loginViewModel.resend(setNumberphone, new SystemNotificationModel.OkCallback() {
                            @Override
                            public void handle() {
                                quyVerifyOtpDialog.show();
                            }
                        }, new SystemNotificationModel.OkCallback() {
                            @Override
                            public void handle() {
                                quyVerifyOtpDialog.show();
                            }
                        })
                        ;
                    }
                });

            }
        });
        quyVerifyOtpDialog.show();
    }
}