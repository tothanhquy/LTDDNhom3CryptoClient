package com.example.nhom3_crypto_client.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.view_model.LoginViewModel;

public class Thuc_ResetPasswordActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thuc_activity_reset_password);
        ResetPassword();
    }
    public void ResetPassword(){
        final TextView signin = findViewById(R.id.signin);
        final TextView signup = findViewById(R.id.signup);
        final EditText phonenumber = findViewById(R.id.phonenumber);
        final EditText newpassword = findViewById(R.id.newpassword);
        final EditText confirmnewpassword = findViewById(R.id.confirmnewpassword);
        final Button btn = findViewById(R.id.btn);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thuc_ResetPasswordActivity.this, Thuc_MainActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Thuc_ResetPasswordActivity.this, Thuc_SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String setPhonenumber = phonenumber.getText().toString();
                String setNewPassword = newpassword.getText().toString();
                String setConfirmNewPassword = confirmnewpassword.getText().toString();

                if (setNewPassword.isEmpty() || setPhonenumber.isEmpty() || setConfirmNewPassword.isEmpty()) {
                    Toast.makeText(Thuc_ResetPasswordActivity.this, "Bạn đang bỏ trống", Toast.LENGTH_SHORT).show();
                    if (setConfirmNewPassword != setNewPassword){
                        Toast.makeText(Thuc_ResetPasswordActivity.this, "Xác nhận mật khẩu sai", Toast.LENGTH_LONG).show();

                    }
                } else {
                    loginViewModel.resetPassword(setPhonenumber, setNewPassword, setConfirmNewPassword, new SystemNotificationModel.OkCallback() {
                        @Override
                        public void handle() {
                            Intent intent = new Intent(Thuc_ResetPasswordActivity.this, Thuc_OTPActivity.class);
                            intent.putExtra("phonenumber", setPhonenumber);
                            startActivity(intent);
                        }
                    });
                }

            }
        });
    }
}