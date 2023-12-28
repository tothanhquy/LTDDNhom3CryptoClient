package com.example.nhom3_crypto_client.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.model.CoinServiceModel;
import com.example.nhom3_crypto_client.view.adapter.QuyCoinListAdapter;

import java.util.ArrayList;

public class QuyCoinListFragment extends Fragment {
    private RecyclerView recyclerView;
    private QuyCoinListAdapter adapter;
    private Context context;
    private ChooseCallback chooseCallback;

    private int sortStatus = SortStatus.None;

    public static class SortStatus{
        public static int Increase = 1;
        public static int Decrease = -1;
        public static int None = 0;
    }

    public void setChooseCallback(ChooseCallback chooseCallback) {
        this.chooseCallback = chooseCallback;
    }
    private class ClickOkCallback implements QuyCoinListAdapter.ClickCallback{
        @Override
        public void handle(String coinId) {
            if(chooseCallback!=null){
                chooseCallback.choose(coinId);
            }
        }
    }

    public static interface ChooseCallback{
        public void choose(String coinId);
    }

    public QuyCoinListFragment(Context context,int status) {
        this.context = context;
        this.sortStatus = status;
        adapter = new QuyCoinListAdapter(context, sortStatus);
        adapter.setClickCallback(new ClickOkCallback());
    }

    public void setList(ArrayList<CoinServiceModel.CoinNow> coins) {
        if(this.adapter!=null)this.adapter.setList(coins);
    }
    public void updateValues(ArrayList<CoinServiceModel.CoinNow> coins) {
        if(this.adapter!=null)this.adapter.updateList(coins);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quy_coin_list, container, false);

        recyclerView = view.findViewById(R.id.quyCoinListFragmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        return view;
    }
}