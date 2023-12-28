package com.example.nhom3_crypto_client.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.nhom3_crypto_client.core.General;
import com.example.nhom3_crypto_client.model.QuyCoinChartModel;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.view_model.BaseViewModel;
import com.example.nhom3_crypto_client.view_model.QuyCoinChartViewModel;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.example.nhom3_crypto_client.R;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuyCoinChartFragment extends Fragment {
    private int CHANGE_COIN_ACTIVITY_CODE = 100;
    private String coinId;
    QuyCoinChartViewModel quyCoinChartViewModel;
    CandleStickChart candleStickChart;
    LineChart lineChart;

    Spinner interval, type;
    Button coinName;
    String intervalStr;
    boolean loadFirst=false, isChangeCoin = true;
    QuyCoinChartModel.CoinsHistoryCandleChart coinsHistoryCandleChart;
    QuyCoinChartModel.CoinsHistoryLineChart coinsHistoryLineChart;

    View loadingLayout;

    Context context;

    public void setIsChangeCoin(Boolean a){
        isChangeCoin = a;
    }
    ChangeCoinOkCallback changeCoinOkCallback;
    public void setChangeCoinOkCallback(ChangeCoinOkCallback a){
        changeCoinOkCallback = a;
    }

    public QuyCoinChartFragment(Context context) {
        this.context = context;
        quyCoinChartViewModel = new QuyCoinChartViewModel(context);
    }

    public static QuyCoinChartFragment newInstance(String coinId, Context context) {
        QuyCoinChartFragment fragment = new QuyCoinChartFragment(context);
        Bundle args = new Bundle();
        args.putString("coinId", coinId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            coinId = getArguments().getString("coinId");
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quy_coin_chart, container, false);
        candleStickChart = view.findViewById(R.id.quyCoinChartCandleStickChart);
        lineChart = view.findViewById(R.id.quyCoinChartLineChart);
        coinName = view.findViewById(R.id.quyCoinChartCoinNameSpinner);
        interval = view.findViewById(R.id.quyCoinChartCoinIntervalSpinner);
        type = view.findViewById(R.id.quyCoinChartCoinTypeSpinner);
        loadingLayout = view.findViewById(R.id.quyCoinChartLoadingLayout);

        return view;
    }

    private void setOptionView() {
        coinName.setText(coinId);
        coinName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChangeCoin)changeCoin();
            }
        });

        loadFirst=false;
        List<String> intervalLabels = new ArrayList<String>(Arrays.asList("M5","M15","M30","H1","H2","H6","H12","D1"));
        ArrayAdapter<String> intervalAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, intervalLabels);
        interval.setAdapter(intervalAdapter);
        interval.setSelection(0);
        interval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(loadFirst)loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> typeLabels = new ArrayList<String>(Arrays.asList("Candle","Line"));
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, typeLabels);
        type.setAdapter(typeAdapter);
        type.setSelection(0);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().toString().equals("Candle")){
                    candleStickChart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.GONE);
                }else{
                    candleStickChart.setVisibility(View.GONE);
                    lineChart.setVisibility(View.VISIBLE);
                }
                if(loadFirst)loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loadData();
        loadFirst=true;
    }

    private void changeCoin(){
        Intent intent = new Intent(context, QuyCoinListActivity.class);
        changeCoinLauncher.launch(intent);
//        startActivityForResult(intent,CHANGE_COIN_ACTIVITY_CODE);
    }
    public void changeCoinView(String coinId){
        coinName.setText(coinId);
        this.coinId = coinId;
        loadData();
    }
    ActivityResultLauncher<Intent> changeCoinLauncher;

    public void setChangeCoinLauncher(ActivityResultLauncher<Intent> changeCoinLauncher) {
        this.changeCoinLauncher = changeCoinLauncher;
    }

    public static interface ChangeCoinOkCallback{
        public void handle(String coinId);
    }

//    ActivityResultLauncher<Intent> changeCoinLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(), o -> {
//        if (o.getResultCode()== Activity.RESULT_OK){
//            Intent intent = o.getData();
//            coinId = intent.getStringExtra("coinId");
//            coinName.setText(coinId);
//            loadData();
//            //changeCoinOkCallback.handle(coinId);
//        }
//    });

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CHANGE_COIN_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {
//            coinId = data.getStringExtra("coinId");
//            coinName.setText(coinId);
//            loadData();
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        setOptionView();
        setObserve();
        setupCandleStickChart();
        setupLineChart();
//        loadData();
    }

    public void setObserve(){
        quyCoinChartViewModel.notification().observe(this, new Observer<SystemNotificationModel>() {
            @Override
            public void onChanged(SystemNotificationModel systemNotificationModel) {
                if(systemNotificationModel!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            General.showNotification(getContext(),systemNotificationModel);
                        }
                    });
                }
            }
        });
        quyCoinChartViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isLoading==true){
                            loadingLayout.setVisibility(View.VISIBLE);
                        }else{
                            loadingLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private void setupCandleStickChart() {
        // Customize chart
        candleStickChart.getDescription().setEnabled(false);
//        candleStickChart.setMaxVisibleValueCount(50);
        candleStickChart.setPinchZoom(true);

        // Customize X-axis
        XAxis xAxis = candleStickChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(5);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new CustomAxisValueFormatter());
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);

        // Customize Y-axis (left)
        YAxis rightAxis = candleStickChart.getAxisRight();
        rightAxis.setDrawGridLines(true);

        // Customize Y-axis (right)
        YAxis leftAxis = candleStickChart.getAxisLeft();
        leftAxis.setEnabled(false);

        // Customize legend
        Legend legend = candleStickChart.getLegend();
        legend.setEnabled(false);
    }
    private void setupLineChart() {
        // Customize chart
        lineChart.getDescription().setEnabled(false);
//        candleStickChart.setMaxVisibleValueCount(50);
        lineChart.setPinchZoom(true);

        // Customize X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(5);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new CustomAxisValueFormatter());
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);

        // Customize Y-axis (left)
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawGridLines(true);

        // Customize Y-axis (right)
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(false);

        // Customize legend
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
    }

    private void loadData() {
        String typeStr = type.getSelectedItem().toString().toLowerCase();
        intervalStr = interval.getSelectedItem().toString().toLowerCase();
        if(typeStr.equals("candle")){
            quyCoinChartViewModel.loadChart(typeStr,coinId,intervalStr,""+getStartTimeBaseInterval(intervalStr),"now", new HandleCandleChartResponse());
        }else{
            quyCoinChartViewModel.loadChart(typeStr,coinId,intervalStr,""+getStartTimeBaseInterval(intervalStr),"now", new HandleLineChartResponse());
        }
    }
    public long getStartTimeBaseInterval(String interval){
        long now = System.currentTimeMillis();
        long baseTime = 1000*60*60*3;//180 column
        if(interval.equals("m1")){
            return now - (baseTime);
        }else if(interval.equals("m5")){
            return now - (baseTime*5);
        }else if(interval.equals("m15")){
            return now - (baseTime*15);
        }else if(interval.equals("m30")){
            return now - (baseTime*30);
        }else if(interval.equals("h1")){
            return now - (baseTime*60);
        }else if(interval.equals("h2")){
            return now - (baseTime*120);
        }else if(interval.equals("h6")){
            return now - (baseTime*360);
        }else if(interval.equals("h12")){
            return now - (baseTime*720);
        }else{
            return now - (baseTime*365*10*8);
        }
    }
    class HandleCandleChartResponse implements BaseViewModel.OkCallback{
        @Override
        public void handle(String dataJson) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    QuyCoinChartModel.CoinsHistoryCandleChart coins = new Gson().fromJson(dataJson,QuyCoinChartModel.CoinsHistoryCandleChart.class);
                    coinsHistoryCandleChart = coins;
                    List<CandleEntry> entries = new ArrayList<>();

                    // Add sample data (replace this with your own data)
                    for (int i = 0; i < coins.data.size(); i++) {
                        entries.add(new CandleEntry(i, coins.data.get(i).height,
                                coins.data.get(i).low,
                                coins.data.get(i).open,
                                coins.data.get(i).close
                        ));
                    }

                    // Create a dataset with the entries
                    CandleDataSet dataSet = new CandleDataSet(entries,"");
//                    dataSet.setColor(Color.rgb(80, 80, 80));
                    dataSet.setDecreasingColor(Color.RED);
                    dataSet.setDecreasingPaintStyle(Paint.Style.FILL);
                    dataSet.setIncreasingColor(Color.GREEN);
                    dataSet.setIncreasingPaintStyle(Paint.Style.FILL);
                    dataSet.setNeutralColor(Color.BLUE);
                    dataSet.setShadowColorSameAsCandle(true);
                    dataSet.setDrawValues(false);
                    dataSet.setBarSpace(0.1f);

                    // Create a data object with the dataset
                    CandleData data = new CandleData(dataSet);

                    // Set data to the chart
                    if(candleStickChart.getData()!=null)candleStickChart.getData().clearValues();
                    candleStickChart.setData(data);

                    // Refresh chart
                    candleStickChart.invalidate();

                    int totalColumns = entries.size();
                    int visibleColumns = 20;

//                    candleStickChart.setVisibleXRangeMaximum(20);
//                    candleStickChart.moveViewToX(Integer.MAX_VALUE);

                    candleStickChart.fitScreen();
                    candleStickChart.zoom(9f, 1f, Integer.MAX_VALUE, (coins.data.get(coins.data.size()-1).height+coins.data.get(coins.data.size()-1).low)/2);
                }
            });
        }
    }

    class HandleLineChartResponse implements BaseViewModel.OkCallback{
        @Override
        public void handle(String dataJson) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    QuyCoinChartModel.CoinsHistoryLineChart coins = new Gson().fromJson(dataJson,QuyCoinChartModel.CoinsHistoryLineChart.class);
                    coinsHistoryLineChart = coins;
                    List<Entry> entries = new ArrayList<>();

                    // Add sample data (replace this with your own data)
                    for (int i = 0; i < coins.data.size(); i++) {
                        entries.add(new Entry(i, coins.data.get(i).priceUsd));
                    }

                    // Create a dataset with the entries
                    LineDataSet dataSet = new LineDataSet (entries,"");
                    dataSet.setColor(Color.GREEN);
                    dataSet.setDrawValues(false);
                    dataSet.setDrawCircles(false);

                    // Create a data object with the dataset
                    LineData data = new LineData(dataSet);

                    // Set data to the chart
                    lineChart.setData(data);

                    // Refresh chart
                    lineChart.invalidate();

                    lineChart.fitScreen();
//                    lineChart.zoom(9f, 1f, Integer.MAX_VALUE, coins.data.get(coins.data.size()-1).priceUsd);
                }
            });
        }
    }

    public class CustomAxisValueFormatter extends ValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            String pattern;
            if(intervalStr.startsWith("m")){
                pattern = "hh:mm";
            }else if(intervalStr.startsWith("h")){
                pattern = "dd/MM-hh";
            }else {
                pattern = "dd/MM/yyyy";
            }
            long millis=0;
            if(candleStickChart.getVisibility()==View.VISIBLE){
                if(coinsHistoryCandleChart.data.size()>(int)value)millis = coinsHistoryCandleChart.data.get((int)value).time;
            }else{
                if(coinsHistoryLineChart.data.size()>(int)value)millis = coinsHistoryLineChart.data.get((int)value).time;
            }
            Date date = new Date(millis);
            return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return getFormattedValue(value);
        }
    }
}