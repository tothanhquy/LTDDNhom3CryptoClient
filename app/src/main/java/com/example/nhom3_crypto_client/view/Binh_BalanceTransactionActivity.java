package com.example.nhom3_crypto_client.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import com.example.nhom3_crypto_client.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*import example.nhom3.newproject.adapter.Binh_TransactionAdapter;*/

public class Binh_BalanceTransactionActivity extends AppCompatActivity {

    private TextView txtBalanceExcessMoney;
    private TextView txtBalanceIncome;
    private TextView txtBalanceAvailable;
    private TextView txtBalanceSourceMoney;
    private RecyclerView recyclerView;
    /*private Binh_TransactionAdapter transactionAdapter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binh_activity_balance_transaction);

        ActionBar();

        initViews();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //List<Binh_Transaction> transactionList = createDummyData(); // Thay bằng dữ liệu thực tế của bạn
        // transactionAdapter = new Binh_TransactionAdapter(this, transactionList);
       // recyclerView.setAdapter(transactionAdapter);
    }

    /*  // Tạo dữ liệu giả lập
      private List<Binh_Transaction> createDummyData() {
          List<Binh_Transaction> dummyData = new ArrayList<>();

          // Thêm các mục vào danh sách
          dummyData.add(new Binh_Transaction("BTC/USD", "1000 x 5", "12-12-2023 19:48", "Đóng lệnh thủ công", "12.12.2023, 19:49", "-$ 1,56"));
          dummyData.add(new Binh_Transaction("BTC/USD", "100 x 5", "12-12-2023 19:48", "Đóng lệnh thủ công", "12.12.2023, 19:49", "-$ 1,76"));
          dummyData.add(new Binh_Transaction("BTC/USD", "100 x 5", "12-12-2023 19:48", "Đóng lệnh thủ công", "12.12.2023, 19:49", "-$ 1,76"));
          dummyData.add(new Binh_Transaction("BTC/USD", "100 x 5", "12-12-2023 19:48", "Đóng lệnh thủ công", "12.12.2023, 19:49", "-$ 1,76"));
          dummyData.add(new Binh_Transaction("BTC/USD", "100 x 5", "12-12-2023 19:48", "Đóng lệnh thủ công", "12.12.2023, 19:49", "-$ 1,76"));
          dummyData.add(new Binh_Transaction("BTC/USD", "100 x 5", "12-12-2023 19:48", "Đóng lệnh thủ công", "12.12.2023, 19:49", "-$ 1,76"));

          return dummyData;
      }*/
    private void initViews() {
        txtBalanceExcessMoney = findViewById(R.id.txt_balance_excess_money);
        txtBalanceIncome = findViewById(R.id.txt_balance_income);
        txtBalanceAvailable = findViewById(R.id.txt_balance_available);
        txtBalanceSourceMoney = findViewById(R.id.txt_balance_source_money);

        //
        recyclerView = findViewById(R.id.recycle_view_transaction_);

    }

    // Xử lý khi nút back được bấm
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
