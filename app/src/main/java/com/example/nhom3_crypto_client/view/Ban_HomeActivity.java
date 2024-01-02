package com.example.nhom3_crypto_client.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.nhom3_crypto_client.R;
import androidx.appcompat.app.AppCompatActivity;

public class Ban_HomeActivity extends AppCompatActivity {
    private ImageView btnCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ban_activity_home);

        btnCommand = findViewById(R.id.btnCommand);
        btnCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Ban_HomeActivity.this,Ban_CommandActivity.class);
                startActivity(intent);
            }
        });
    }
}