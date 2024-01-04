package com.example.nhom3_crypto_client.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.nhom3_crypto_client.R;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Ban_OpenFragment extends Fragment {
    private ArrayList<Ban_OpenCommand> openCommands = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Ban_OpenAdapter openAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ban_fragment_open, container, false);
        recyclerView = view.findViewById(R.id.listOpenItem);
        progressBar = view.findViewById(R.id.LoadBar);
        openAdapter = new Ban_OpenAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(openAdapter);
        Custom custom = new Custom();
        fetchData(custom);
        return view;
    }
    boolean fetchData(FetchDataOKComeback comeback){
        new Thread(()->{
            try {
                Thread.sleep(3000);
                comeback.handle();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();
        return true;
    }
    public interface FetchDataOKComeback {
        void handle();
    }
    public class Custom implements FetchDataOKComeback{
        @Override
        public void handle() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    ArrayList<Ban_OpenCommand> openCommandArrayList = new ArrayList<>();
                    openCommandArrayList.add(new Ban_OpenCommand("adaw","dwad","dwadx"));
                    openAdapter.setList(openCommandArrayList);

                }
            });
        }
    }
}
