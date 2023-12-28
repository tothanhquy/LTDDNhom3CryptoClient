package com.example.nhom3_crypto_client.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nhom3_crypto_client.R;

public class QuyMainActivityTradingFragment extends Fragment {
    FragmentContainerView coinChartFragmentView;
    QuyCoinChartFragment coinChartFragment;
    TextView sumMoney, readyMoney, tradingCommandNumber;
    Context context;

    ActivityResultLauncher<Intent> changeCoinLauncher;
    public QuyMainActivityTradingFragment(Context context,ActivityResultLauncher<Intent> changeCoinLauncher) {
        this.context = context;
        this.changeCoinLauncher = changeCoinLauncher;
        // Required empty public constructor
    }
    public void changeCoinChartView(String coinId){
        coinChartFragment.changeCoinView(coinId);
    }
//
//    public static QuyMainActivityTradingFragment newInstance(String param1, String param2) {
//        QuyMainActivityTradingFragment fragment = new QuyMainActivityTradingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quy_main_activity_trading, container, false);
        coinChartFragmentView =view.findViewById(R.id.quyMainActivityTradingFragmentCoinChartFragmentContainer);
        sumMoney = view.findViewById(R.id.quyMainActivityTradingFragmentMiniInfoSum);
        readyMoney = view.findViewById(R.id.quyMainActivityTradingFragmentMiniInfoReadyMoney);
        tradingCommandNumber = view.findViewById(R.id.quyMainActivityTradingFragmentMiniInfoTradingCommandNumber);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        coinChartFragment = QuyCoinChartFragment.newInstance("bitcoin", context);
        coinChartFragment.setChangeCoinLauncher(changeCoinLauncher);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Replace the current fragment with the new one
        transaction.replace(R.id.quyMainActivityTradingFragmentCoinChartFragmentContainer, coinChartFragment);
        // Add the transaction to the back stack (optional)
//        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}