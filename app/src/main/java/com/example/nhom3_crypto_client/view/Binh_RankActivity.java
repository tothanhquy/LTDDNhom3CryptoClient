package com.example.nhom3_crypto_client.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nhom3_crypto_client.R;

import java.util.List;

/*import example.nhom3.newproject.adapter.Binh_RankAdapter;
import example.nhom3.newproject.model.Binh_UserRank;*/

public class Binh_RankActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtRankPercent;
    /*private Binh_RankAdapter rankAdapter;
    private List<Binh_UserRank> userRankList;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binh_activity_rank);

        // back
        ActionBar();
        initViews();
       // initData();

    }
 /*   private void initData()
    {
        // Tạo danh sách giả định
        userRankList = new ArrayList<>();
        userRankList.add(new Binh_UserRank("Trần Xuân Bình", "$ 1234738328", 1));
        userRankList.add(new Binh_UserRank("Nguyễn Văn A", "$ 987654321", 2));
        userRankList.add(new Binh_UserRank("Nguyễn Thị C", "$ 987654321", 3));
        userRankList.add(new Binh_UserRank("Nguyễn Thị N", "$ 987654321", 4));
        userRankList.add(new Binh_UserRank("Nguyễn Văn S", "$ 987654321", 5));
        userRankList.add(new Binh_UserRank("Nguyễn Quang X", "$ 987654321", 6));
        userRankList.add(new Binh_UserRank("Nguyễn Quang X", "$ 987654321", 7));

        // Khởi tạo Adapter và set Adapter cho RecyclerView
        rankAdapter = new Binh_RankAdapter(userRankList);
        recyclerView.setAdapter(rankAdapter);

        // Set LayoutManager cho RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }*/

    private void initViews()
    {
        recyclerView = findViewById(R.id.recycle_list_rank);
        txtRankPercent = findViewById(R.id.txt_rank_percent);

    }

    public void ActionBar(){

        // Hiển thị nút back trên action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Hiển thị nút back
            actionBar.setDisplayHomeAsUpEnabled(true);

            // Đặt màu chữ
            int colorWhite = getResources().getColor(android.R.color.black);
            actionBar.setTitle(Html.fromHtml("<font color='" + colorWhite + "'>XẾP HẠNG</font>"));

            // Đặt màu của nút back
            Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.binh_ic_arrow_back);
            upArrow.setColorFilter(colorWhite, PorterDuff.Mode.SRC_ATOP);
            actionBar.setHomeAsUpIndicator(upArrow);

            // Đặt màu nền
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}