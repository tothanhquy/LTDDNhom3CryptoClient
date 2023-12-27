package com.example.nhom3_crypto_client.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.nhom3_crypto_client.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuyMainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quy_main);


        String[] tabTitles = {"Home","Yêu thích" ,"Giao dịch", "Hồ sơ"};//put titles based on your need
        int[] tabIcons = {R.drawable.outline_info_24,R.drawable.outline_info_24, R.drawable.outline_info_24, R.drawable.outline_info_24};
        ArrayList<Fragment> fragments = new ArrayList<>(Arrays.asList(new QuyMainActivityHomeFragment(), new QuyMainActivityInterestedCoinsFragment(), new QuyMainActivityTradingFragment(), new QuyMainActivityProfileFragment()));
        ViewPager2 viewPager2 = findViewById(R.id.quyMainViewPager);
        viewPager2.setAdapter(new ViewPager2Adapter(getSupportFragmentManager(),getLifecycle(),viewPager2,fragments));
        TabLayout tabLayout = findViewById(R.id.quyMainTabLayout);

        new TabLayoutMediator(tabLayout, viewPager2,
                ((tab, position) -> {
                    tab.setText(tabTitles[position]);
                    tab.setIcon(tabIcons[position]);
                }
            )).attach();
    }
    private class ViewPager2Adapter extends FragmentStateAdapter {
        ViewPager2 viewPager2;
        List<Fragment> fragments;
        public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,ViewPager2 viewPager2,List<Fragment> fragments) {
            super(fragmentManager, lifecycle);
            this.viewPager2 = viewPager2;
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}