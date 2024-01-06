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
                    if(setConfirmCreatePassword != setCreatePassword){
                        Toast.makeText(Thuc_SignUpActivity.this, "Xác nhận mật khẩu sai", Toast.LENGTH_LONG).show();
                    }
                } else {
                    loginViewModel.register(setPhonenumber, setCreatePassword, setConfirmCreatePassword, setName, new SystemNotificationModel.OkCallback() {
                        @Override
                        public void handle() {
                            Intent intent = new Intent(Thuc_SignUpActivity.this, Thuc_OTPActivity.class);
                            intent.putExtra("phonenumber", setPhonenumber);
                            startActivity(intent);
                        }
                    });
                }

            }
        });
    }
}