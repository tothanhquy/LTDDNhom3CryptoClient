package com.example.nhom3_crypto_client.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.core.General;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.model.response.BinhTransactionHistoryResponseModel;
import com.example.nhom3_crypto_client.model.response.QuyProfileResponseModel;
import com.example.nhom3_crypto_client.model.response.QuyTradingCommandResponseModel;
import com.example.nhom3_crypto_client.view.adapter.BinhRankTopUserAdapter;
import com.example.nhom3_crypto_client.view.adapter.BinhTransactionHistoryAdapter;
import com.example.nhom3_crypto_client.view_model.BaseViewModel;
import com.example.nhom3_crypto_client.view_model.BinhProfileViewModel;
import com.example.nhom3_crypto_client.view_model.BinhRankViewModel;
import com.example.nhom3_crypto_client.view_model.BinhTransactionHistoryViewModel;
import com.google.gson.Gson;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Binh_BalanceTransactionActivity extends AppCompatActivity {

    private TextView txtBalanceExcessMoney;
    private TextView txtBalanceIncome;
    private TextView txtBalanceAvailable;
    private TextView txtBalanceSourceMoney;
    private RecyclerView recyclerView;

    private ArrayList<BinhTransactionHistoryResponseModel.Item> items;
    private BinhTransactionHistoryViewModel viewModel;

    BinhTransactionHistoryAdapter binhTransactionHistoryAdapter;

    //
    private BinhProfileViewModel profileViewModel;
    QuyProfileResponseModel.Profile profileDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binh_activity_balance_transaction);

        ActionBar();

        recyclerView = findViewById(R.id.recycle_view_transaction_);
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binhTransactionHistoryAdapter = new BinhTransactionHistoryAdapter(getApplicationContext());
        recyclerView.setAdapter(binhTransactionHistoryAdapter);


        initViews();

        viewModel = new BinhTransactionHistoryViewModel(getApplicationContext());

        profileViewModel = new BinhProfileViewModel(getApplicationContext());


        setObserve();
        loadData();

        loadData2();
    }

    private void initViews() {

        txtBalanceExcessMoney = findViewById(R.id.txt_balance_excess_money);
        txtBalanceIncome = findViewById(R.id.txt_balance_income);
        txtBalanceAvailable = findViewById(R.id.txt_balance_available);
        txtBalanceSourceMoney = findViewById(R.id.txt_balance_source_money);

        //

    }

    private void setProfile() {
        float sumMoney = profileDetails.moneyInvested + profileDetails.moneyProfitNow + profileDetails.moneyNow;
        txtBalanceExcessMoney.setText("$" + String.format("%.2f", sumMoney) + " K");
//        String.format("%.2f", sumMoney)

        txtBalanceAvailable.setText("$" + String.format("%.2f", profileDetails.moneyNow));
        txtBalanceIncome.setText("$" + String.format("%.2f", profileDetails.moneyProfitNow));

        float sourceMoney = profileDetails.moneyNow + profileDetails.moneyInvested;
        txtBalanceSourceMoney.setText("$" + String.format("%.2f", sourceMoney));

    }

    private void setObserve() {
        // Set alert error
        viewModel.notification().observe(this, new Observer<SystemNotificationModel>() {
            @Override
            public void onChanged(SystemNotificationModel it) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (it != null) {
                            Toast.makeText(Binh_BalanceTransactionActivity.this, it.content, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Set alert notification
    }

    //data lịch sử giao dịch
    private void loadData() {
        viewModel.getTransactionHistory(new BaseViewModel.OkCallback() {
            @Override
            public void handle(String data) {
                System.out.println(data);
                BinhTransactionHistoryResponseModel.Lists parseOj = new Gson().fromJson(data, BinhTransactionHistoryResponseModel.Lists.class);
                setData(parseOj);
            }
        });
    }

    //data money user
    private void loadData2() {
       /* Intent intent = getIntent();
        userId = intent.getStringExtra("id");*/
        profileViewModel.getInfo("mine", new BaseViewModel.OkCallback() {
            @Override
            public void handle(String data) {
                System.out.println(data);
                profileDetails = new Gson().fromJson(data, QuyProfileResponseModel.Profile.class);
                System.out.println(profileDetails);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setProfile();
                    }
                });

            }
        });
    }


    private void setData(BinhTransactionHistoryResponseModel.Lists data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binhTransactionHistoryAdapter.setList(new ArrayList<>(data.items));
            }
        });

    }


    // Xử lý khi nút back được bấm
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ActionBar() {

        // Hiển thị nút back trên action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Hiển thị nút back
            actionBar.setDisplayHomeAsUpEnabled(true);

            // Đặt màu chữ
            int colorWhite = getResources().getColor(android.R.color.black);
            actionBar.setTitle(Html.fromHtml("<font color='" + colorWhite + "'>SỐ DƯ GIAO DỊCH</font>"));

            // Đặt màu của nút back
            Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.binh_ic_arrow_back);
            upArrow.setColorFilter(colorWhite, PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }
}
