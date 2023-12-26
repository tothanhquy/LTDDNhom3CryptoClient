package com.example.nhom3_crypto_client.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom3_crypto_client.R;

public class OTPActivity extends AppCompatActivity {

    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        OTP();
        setupOtpEditText(otp1, otp2, otp1);
        setupOtpEditText(otp2, otp3, otp1);
        setupOtpEditText(otp3, otp4, otp2);
        setupOtpEditText(otp4, otp5, otp3);
        setupOtpEditText(otp5, otp6, otp4);
        setupOtpEditText(otp6, otp6, otp5);

    }

    public void OTP(){
        final ImageView back = findViewById(R.id.back);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupOtpEditText(final EditText currentEditText, final EditText nextEditText, final EditText previousEditText) {
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    nextEditText.requestFocus();
                } else if (editable.length() == 0) {
                    previousEditText.requestFocus();
                }
            }
        });

        currentEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // Handle delete key event
                    if (currentEditText.getText().length() == 0 && previousEditText != null) {
                        // If the current EditText is empty, move focus to the previous EditText
                        previousEditText.requestFocus();
                    } else {
                        // Clear the text if it's not empty
                        currentEditText.getText().clear();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}