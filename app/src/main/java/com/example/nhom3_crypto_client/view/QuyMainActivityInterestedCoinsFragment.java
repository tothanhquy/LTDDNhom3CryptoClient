package com.example.nhom3_crypto_client.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nhom3_crypto_client.R;

public class QuyMainActivityInterestedCoinsFragment extends Fragment {

    public QuyMainActivityInterestedCoinsFragment() {
        // Required empty public constructor
    }

//    public static QuyMainActivityInterestedCoinsFragment newInstance(String param1, String param2) {
//        QuyMainActivityInterestedCoinsFragment fragment = new QuyMainActivityInterestedCoinsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quy_main_activity_interested_coins, container, false);
    }
}