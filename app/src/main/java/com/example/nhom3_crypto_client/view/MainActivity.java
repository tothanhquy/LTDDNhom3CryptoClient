package com.example.nhom3_crypto_client.view;

import android.content.Intent;
import android.content.ServiceConnection;
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
import com.example.nhom3_crypto_client.service.SocketService;
import com.example.nhom3_crypto_client.view_model.LoginViewModel;


public class MainActivity extends AppCompatActivity {

    private SocketService socketService;
    private Boolean isBoundSocketService;
    private ServiceConnection serviceConnection;

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
                            Toast.makeText(MainActivity.this, it.content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Set alert notification
    }

        // Set loading
    public boolean check(String phonenumber, String password){
        if(phonenumber.equals(phonenumber) && password.equals(password)){
            loginViewModel.login(phonenumber, password, new Login1());
            return true;
        }
        return false;
    }

    public class Login1 implements SystemNotificationModel.OkCallback{

        @Override
        public void handle() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginViewModel = new LoginViewModel(getApplicationContext());
        Signin();
        setRender();
    }

    public void Signin () {
        final EditText password = findViewById(R.id.password);
        final EditText phonenumber = findViewById(R.id.phonenumber);
        Button btnLogin = findViewById(R.id.btnLogin);
        final TextView signup = findViewById(R.id.signup);
        final TextView resetpassword = findViewById((R.id.resetpassword));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setPassword = password.getText().toString();
                String setPhonenumber = phonenumber.getText().toString();

                if (setPassword.isEmpty() || setPhonenumber.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Bạn đang bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    check(setPhonenumber, setPassword);
                    // Xử lý đăng
                    // Ở đây có thể thêm logic xác thực đăng nhập
                   // Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Boolean check123 =  check(phonenumber.getText().toString(), password.getText().toString());
//                if(check123) {
//                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(MainActivity.this, Home.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }

}