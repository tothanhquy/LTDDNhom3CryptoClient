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

public class Thuc_SignUpActivity extends AppCompatActivity {

    public boolean check(String phonenumber, String createpassword, String confirmcreatepassword){
        String hashPhoneNumber = "0123";
        String hashPassword = "123";
        if(phonenumber.equals(hashPhoneNumber) && createpassword.equals(hashPassword) && hashPassword.equals(confirmcreatepassword)){

            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thuc_activity_signup);
        Signup();
    }

    public void Signup(){
        final EditText phonenumber = findViewById(R.id.phonenumber);
        final EditText createpassword = findViewById(R.id.createpassword);
        final EditText confirmcreatepassword = findViewById(R.id.confirmcreatepassword);
        final EditText text = findViewById(R.id.text    );
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
                Boolean check123 =  check(phonenumber.getText().toString(), createpassword.getText().toString(), confirmcreatepassword.getText().toString());
                if(check123) {
                    Toast.makeText(Thuc_SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Thuc_SignUpActivity.this, Thuc_OTPActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}