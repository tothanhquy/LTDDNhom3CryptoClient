package com.example.nhom3_crypto_client.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.nhom3_crypto_client.R;
public class Binh_SettingActivity extends AppCompatActivity {

    private Button btnLogout;
    private Button btnChangePin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binh_activity_setting);


        btnLogout = findViewById(R.id.btn_setting_logout);
        btnChangePin = findViewById(R.id.btn_setting_change_pin);

        btnChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangPinDiaLog();
            }
        });

        getSupportActionBar().setTitle("Cài đặt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //dialog mã pin
    private void showChangPinDiaLog() {
        // new dialog
        Dialog editPinDialog = new Dialog(this);
        editPinDialog.setContentView(R.layout.binh_dialog_pin_code);
        LinearLayout pinCodeLayout = editPinDialog.findViewById(R.id.pin_code_input_layout);

        // mã pin
        View[] pinCodeViews = new View[]{
                editPinDialog.findViewById(R.id.pin_code_1),
                editPinDialog.findViewById(R.id.pin_code_2),
                editPinDialog.findViewById(R.id.pin_code_3),
                editPinDialog.findViewById(R.id.pin_code_4)
        };

        //  Button 0->9
        for (int i = 0; i <= 9; i++) {
            int btnId = getResources().getIdentifier("btn" + i, "id", getPackageName());
            Button button = editPinDialog.findViewById(btnId);
            int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handlePinButtonClick(finalI, pinCodeViews, pinCodeLayout);
                }
            });
        }

        // btnClear
        Button btnClear = editPinDialog.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClearButtonClick(pinCodeViews, pinCodeLayout);
            }
        });


        Button btnClearAll = editPinDialog.findViewById(R.id.btn_clear_all);
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEnteredPin(pinCodeViews);
            }
        });

        // Hiển thị dialog
        editPinDialog.show();
    }

    // Xử lý khi Button 0-9 được nhấn
    private void handlePinButtonClick(int pin, View[] pinCodeViews, LinearLayout pinCodeLayout) {
        for (View pinCodeView : pinCodeViews) {
            if (pinCodeView.getTag() == null) {
                pinCodeView.setBackgroundResource(R.drawable.binh_view_circle_pin_code2);
                pinCodeView.setTag(pin);
                checkAndShowToast(pinCodeViews);
                break;
            }
        }

    }

    // btnClear
    private void handleClearButtonClick(View[] pinCodeViews, LinearLayout pinCodeLayout) {
        for (int i = pinCodeViews.length - 1; i >= 0; i--) {
            if (pinCodeViews[i].getTag() != null) {
                pinCodeViews[i].setBackgroundResource(R.drawable.binh_view_circle_pin_code);
                pinCodeViews[i].setTag(null);
                break;
            }
        }
    }

    // Kiểm tra
    private void checkAndShowToast(View[] pinCodeViews) {
        StringBuilder enteredPin = new StringBuilder();
        for (View pinCodeView : pinCodeViews) {
            if (pinCodeView.getTag() != null) {
                enteredPin.append(pinCodeView.getTag());
            }
        }
        // Replace "1234" with your desired correct PIN
        String correctPin = "1234";
        if (enteredPin.length() == 4 && enteredPin.toString().equals(correctPin)) {
            Toast.makeText(this, "Correct PIN", Toast.LENGTH_SHORT).show();
        } else if (enteredPin.length() == 4) {
            Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show();

        }
    }

    // Clear all pin
    private void clearEnteredPin(View[] pinCodeViews) {
        for (View pinCodeView : pinCodeViews) {
            pinCodeView.setBackgroundResource(R.drawable.binh_view_circle_pin_code);
            pinCodeView.setTag(null);
        }
    }

    // Xử lý sự kiện khi nút quay lại được bấm (actionBar)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // quay lại activity trc đó
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
