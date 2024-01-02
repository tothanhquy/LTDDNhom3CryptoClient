package com.example.nhom3_crypto_client.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.nhom3_crypto_client.R;
import com.google.android.material.tabs.TabLayout;
public class Ban_CommandActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Ban_ViewCommand viewCommand;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ban_command_main);
        tabLayout= findViewById(R.id.tabLayout);
        viewPager2= findViewById((R.id.ViewPage2));
        viewCommand= new Ban_ViewCommand(this);
        button= findViewById(R.id.btnContinue);
        viewPager2.setAdapter(viewCommand);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}