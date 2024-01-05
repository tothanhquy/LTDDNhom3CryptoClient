package com.example.nhom3_crypto_client.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.core.General;
import com.example.nhom3_crypto_client.service.CoinService;
import com.example.nhom3_crypto_client.service.ServiceConnections;
import com.example.nhom3_crypto_client.service.ServiceCreatedCallback;
import com.example.nhom3_crypto_client.view_model.BaseViewModel;
import com.example.nhom3_crypto_client.view_model.QuyAccountViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuyMainActivity extends BaseActivity {
    QuyMainActivityHomeFragment quyMainActivityHomeFragment;
    QuyMainActivityInterestedCoinsFragment quyMainActivityInterestedCoinsFragment;
    QuyMainActivityTradingFragment quyMainActivityTradingFragment;
    QuyMainActivityProfileFragment quyMainActivityProfileFragment;

    QuyAccountViewModel quyAccountViewModel;


    private String REGISTER_COIN_SERVICE_NAME = "main-activity";
    private CoinService coinService;
    private Boolean isBoundCoinService=false;
    private ServiceConnection serviceConnection;

    private class CoinServiceCreatedCallback implements ServiceCreatedCallback {
        @Override
        public void setService(Service service) {
            coinService = (CoinService) service;
        }
        @Override
        public void setIsBound(Boolean isBound) {
            isBoundCoinService = isBound;
        }
        @Override
        public void createdComplete() {

        }
    }

    private boolean interestedCoinsChangeStatus;
    public static interface InterestedCoinsChange{
        public void setStatus(boolean status);
        public boolean getStatus();
    }
    private InterestedCoinsChange InterestedCoinsChangeObject = new InterestedCoinsChange() {
        @Override
        public void setStatus(boolean status) {
            interestedCoinsChangeStatus = status;
            if(interestedCoinsChangeStatus==true){
                quyMainActivityInterestedCoinsFragment.getInterestedCoin();
            }
        }

        @Override
        public boolean getStatus() {
            return interestedCoinsChangeStatus;
        }
    };

    public static interface OpenViewCoin{
        public void open(String coinId);
    }
    private OpenViewCoin OpenViewCoinObject = new OpenViewCoin() {
        @Override
        public void open(String coinId) {
            quyMainActivityTradingFragment.changeCoinChartView(coinId);
            viewPager2Adapter.changeLayout(2);
        }
    };
    ViewPager2Adapter viewPager2Adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quy_main);
        getSupportActionBar().hide();

        quyAccountViewModel = new QuyAccountViewModel(getApplicationContext());


        View[] tabItem = {
                super.getCustomViewTabLayout(R.drawable.quy_home, "Home"),
                super.getCustomViewTabLayout(R.drawable.quy_interested_coin, "Yêu thích"),
                super.getCustomViewTabLayout(R.drawable.quy_trading_coin, "Giao dịch"),
                super.getCustomViewTabLayout(R.drawable.quy_profile, "Hồ sơ")
        };

        quyMainActivityHomeFragment = new QuyMainActivityHomeFragment();
        quyMainActivityInterestedCoinsFragment = new QuyMainActivityInterestedCoinsFragment(getApplicationContext(),InterestedCoinsChangeObject,OpenViewCoinObject);
        quyMainActivityTradingFragment = new QuyMainActivityTradingFragment(getApplicationContext(),changeCoinLauncher,InterestedCoinsChangeObject);
        quyMainActivityProfileFragment = new QuyMainActivityProfileFragment();

        ArrayList<Fragment> fragments = new ArrayList<>(Arrays.asList(quyMainActivityHomeFragment, quyMainActivityInterestedCoinsFragment,quyMainActivityTradingFragment, quyMainActivityProfileFragment));
        ViewPager2 viewPager2 = findViewById(R.id.quyMainViewPager);
        viewPager2Adapter = new ViewPager2Adapter(getSupportFragmentManager(),getLifecycle(),viewPager2,fragments);
        viewPager2.setAdapter(viewPager2Adapter);
        TabLayout tabLayout = findViewById(R.id.quyMainTabLayout);
        viewPager2.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager2,
                ((tab, position) -> {
                    tab.setCustomView(tabItem[position]);
                }
            )).attach();

        CoinServiceCreatedCallback serviceCreatedCallback = new CoinServiceCreatedCallback();
        serviceConnection = new ServiceConnections.CoinServiceConnection(serviceCreatedCallback);
        Intent intent = new Intent(QuyMainActivity.this, CoinService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        checkAuthAndContinue();
    }

    private void checkAuthAndContinue(){
        quyAccountViewModel.checkAuth(new BaseViewModel.OkCallback() {
            @Override
            public void handle(String data) {
                if(data.equals("false")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(QuyMainActivity.this, Thuc_MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBoundCoinService) {
            coinService.removeEventListener(REGISTER_COIN_SERVICE_NAME);
            unbindService(serviceConnection);
            isBoundCoinService = false;
        }
    }



    ActivityResultLauncher<Intent> changeCoinLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), o -> {
            if (o.getResultCode()== Activity.RESULT_OK){
                Intent intent = o.getData();
                String coinId = intent.getStringExtra("coinId");
                quyMainActivityTradingFragment.changeCoinChartView(coinId);
                //changeCoinOkCallback.handle(coinId);
            }
        });
    private class ViewPager2Adapter extends FragmentStateAdapter {
        ViewPager2 viewPager2;
        List<Fragment> fragments;
        public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,ViewPager2 viewPager2,List<Fragment> fragments) {
            super(fragmentManager, lifecycle);
            this.viewPager2 = viewPager2;
            this.fragments = fragments;
        }
        public void changeLayout(int position){
            viewPager2.setCurrentItem(position);
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