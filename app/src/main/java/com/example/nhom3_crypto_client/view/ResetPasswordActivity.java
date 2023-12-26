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

public class ResetPasswordActivity extends AppCompatActivity {
    public boolean check(String phonenumber, String newPassword, String confirmPassword){
        String hashPhoneNumber = "0123";
        String hashnewPassword = "123";
        String hashConfirmPassword  = "123";
        if(phonenumber.equals(hashPhoneNumber) && newPassword.equals(hashnewPassword) && newPassword.equals(confirmPassword)){

            return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("gegeegegeg");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
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
                Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check123 =  check(phonenumber.getText().toString(), newpassword.getText().toString(), confirmnewpassword.getText().toString());
                if(check123) {
                    Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, OTPActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}