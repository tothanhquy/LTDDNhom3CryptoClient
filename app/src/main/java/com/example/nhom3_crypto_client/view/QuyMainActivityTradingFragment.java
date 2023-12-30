package com.example.nhom3_crypto_client.view;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.core.General;
import com.example.nhom3_crypto_client.model.CoinServiceModel;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.model.response.QuyProfileResponseModel;
import com.example.nhom3_crypto_client.service.CoinService;
import com.example.nhom3_crypto_client.service.ServiceConnections;
import com.example.nhom3_crypto_client.service.ServiceCreatedCallback;
import com.example.nhom3_crypto_client.view_model.BaseViewModel;
import com.example.nhom3_crypto_client.view_model.QuyProfileViewModel;
import com.example.nhom3_crypto_client.view_model.QuyTradingCommandViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QuyMainActivityTradingFragment extends Fragment {
    FragmentContainerView coinChartFragmentView;
    QuyCoinChartFragment coinChartFragment;
    TextView miniInfoSumMoney, miniInfoReadyMoney, miniInfoTradingCommandNumber;
    Context context;

    TextView quyMainActivityTradingFragmentCoinInfoName;
    ImageView quyMainActivityTradingFragmentCoinInfoIcon;
    TextView quyMainActivityTradingFragmentCoinInfoPrice;
    TextView quyMainActivityTradingFragmentCoinInfoChange24h;
    Button quyMainActivityTradingFragmentCoinInfoSellBtn;
    Button quyMainActivityTradingFragmentCoinInfoBuyBtn;
    LinearLayout quyMainActivityTradingFragmentCreateCommandContainer;
    View quyMainActivityTradingFragmentCreateCommandContainerCloseView;
    TextView quyMainActivityTradingFragmentCreateCommandContainerSwitchSellLabel;
    Switch quyMainActivityTradingFragmentCreateCommandContainerSwitchBuySell;
    TextView quyMainActivityTradingFragmentCreateCommandContainerSwitchBuyLabel;
    EditText quyMainActivityTradingFragmentCreateCommandContainerInputMoney;
    SeekBar quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney;
    Spinner quyMainActivityTradingFragmentCreateCommandContainerLeverage;
    EditText quyMainActivityTradingFragmentCreateCommandContainerTakeProfit;
    Switch quyMainActivityTradingFragmentCreateCommandContainerEnableTPSL;
    EditText quyMainActivityTradingFragmentCreateCommandContainerStopLoss;
    TextView quyMainActivityTradingFragmentCreateCommandContainerSumMoney;
    TextView quyMainActivityTradingFragmentCreateCommandContainerCommission;
    View quyMainActivityTradingFragmentCreateCommandContainerOpenCommandButton;
    LinearLayout loadingLayout;


    QuyProfileResponseModel.MiniProfile miniProfile;

    QuyProfileViewModel quyProfileViewModel;
    QuyTradingCommandViewModel quyTradingCommandViewModel;

    ActivityResultLauncher<Intent> changeCoinLauncher;

    private String REGISTER_COIN_SERVICE_NAME = "main-activity-trading-fragment";
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

    private String coinId = "bitcoin";

    public QuyMainActivityTradingFragment(Context context,ActivityResultLauncher<Intent> changeCoinLauncher) {
        this.context = context;
        this.changeCoinLauncher = changeCoinLauncher;
        quyProfileViewModel = new QuyProfileViewModel(context);
        quyTradingCommandViewModel = new QuyTradingCommandViewModel(context);

        CoinServiceCreatedCallback serviceCreatedCallback = new CoinServiceCreatedCallback();
        serviceConnection = new ServiceConnections.CoinServiceConnection(serviceCreatedCallback);
        Intent intent = new Intent(getActivity(), CoinService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    public void changeCoinChartView(String coinId){
        this.coinId = coinId;
        loadCoinInfo();
        coinChartFragment.changeCoinView(coinId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBoundCoinService) {
            coinService.removeEventListener(REGISTER_COIN_SERVICE_NAME);
            getActivity().unbindService(serviceConnection);
            isBoundCoinService = false;
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quy_main_activity_trading, container, false);
        coinChartFragmentView =view.findViewById(R.id.quyMainActivityTradingFragmentCoinChartFragmentContainer);
        miniInfoSumMoney = view.findViewById(R.id.quyMainActivityTradingFragmentMiniInfoSum);
        miniInfoReadyMoney = view.findViewById(R.id.quyMainActivityTradingFragmentMiniInfoReadyMoney);
        miniInfoTradingCommandNumber = view.findViewById(R.id.quyMainActivityTradingFragmentMiniInfoTradingCommandNumber);

        quyMainActivityTradingFragmentCoinInfoName = view.findViewById(R.id.quyMainActivityTradingFragmentCoinInfoName);
        quyMainActivityTradingFragmentCoinInfoIcon = view.findViewById(R.id.quyMainActivityTradingFragmentCoinInfoIcon);
        quyMainActivityTradingFragmentCoinInfoPrice = view.findViewById(R.id.quyMainActivityTradingFragmentCoinInfoPrice);
        quyMainActivityTradingFragmentCoinInfoChange24h = view.findViewById(R.id.quyMainActivityTradingFragmentCoinInfoChange24h);
        quyMainActivityTradingFragmentCoinInfoSellBtn = view.findViewById(R.id.quyMainActivityTradingFragmentCoinInfoSellBtn);
        quyMainActivityTradingFragmentCoinInfoBuyBtn = view.findViewById(R.id.quyMainActivityTradingFragmentCoinInfoBuyBtn);
        quyMainActivityTradingFragmentCreateCommandContainer = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainer);
        quyMainActivityTradingFragmentCreateCommandContainerCloseView = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerCloseView);
        quyMainActivityTradingFragmentCreateCommandContainerSwitchSellLabel = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerSwitchSellLabel);
        quyMainActivityTradingFragmentCreateCommandContainerSwitchBuySell = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerSwitchBuySell);
        quyMainActivityTradingFragmentCreateCommandContainerSwitchBuyLabel = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerSwitchBuyLabel);
        quyMainActivityTradingFragmentCreateCommandContainerInputMoney = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerInputMoney);
        quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney);
        quyMainActivityTradingFragmentCreateCommandContainerLeverage = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerLeverage);
        quyMainActivityTradingFragmentCreateCommandContainerTakeProfit = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerTakeProfit);
        quyMainActivityTradingFragmentCreateCommandContainerEnableTPSL = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerEnableTPSL);
        quyMainActivityTradingFragmentCreateCommandContainerStopLoss = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerStopLoss);
        quyMainActivityTradingFragmentCreateCommandContainerSumMoney = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerSumMoney);
        quyMainActivityTradingFragmentCreateCommandContainerCommission = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerCommission);
        quyMainActivityTradingFragmentCreateCommandContainerOpenCommandButton = view.findViewById(R.id.quyMainActivityTradingFragmentCreateCommandContainerOpenCommandButton);

        loadingLayout = view.findViewById(R.id.loadingLayout);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        coinChartFragment = QuyCoinChartFragment.newInstance(this.coinId, context);
        coinChartFragment.setChangeCoinLauncher(changeCoinLauncher);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Replace the current fragment with the new one
        transaction.replace(R.id.quyMainActivityTradingFragmentCoinChartFragmentContainer, coinChartFragment);
        // Add the transaction to the back stack (optional)
//        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        setObserve();
        setInitView();
        setEvents();
        loadMiniProfile();
        loadCoinInfo();
    }

    private void setInitView(){
        String leverageLabel[] = {"1","5","8","10","13","20"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, leverageLabel);
        quyMainActivityTradingFragmentCreateCommandContainerLeverage.setAdapter(adapter);
        quyMainActivityTradingFragmentCreateCommandContainerLeverage.setSelection(0);
        quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney.setMin(0);
        quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney.setMax(100);
    }

    public void setObserve(){
        quyProfileViewModel.notification().observe(this, new Observer<SystemNotificationModel>() {
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
        quyProfileViewModel.isLoading().observe(this, new Observer<Boolean>() {
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
        quyTradingCommandViewModel.notification().observe(this, new Observer<SystemNotificationModel>() {
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
        quyTradingCommandViewModel.isLoading().observe(this, new Observer<Boolean>() {
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
    private void setEvents(){
        quyMainActivityTradingFragmentCreateCommandContainerCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quyMainActivityTradingFragmentCreateCommandContainer.setVisibility(View.GONE);
            }
        });
        quyMainActivityTradingFragmentCoinInfoSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateCommandContainer(false);
            }
        });
        quyMainActivityTradingFragmentCoinInfoBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateCommandContainer(true);
            }
        });
        quyMainActivityTradingFragmentCreateCommandContainerSwitchBuySell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    quyMainActivityTradingFragmentCreateCommandContainerSwitchSellLabel.setTextColor(Color.GRAY);
                    quyMainActivityTradingFragmentCreateCommandContainerSwitchBuyLabel.setTextColor(Color.parseColor("#3bd391"));
                }else{
                    quyMainActivityTradingFragmentCreateCommandContainerSwitchBuyLabel.setTextColor(Color.GRAY);
                    quyMainActivityTradingFragmentCreateCommandContainerSwitchSellLabel.setTextColor(Color.parseColor("#FF3333"));
                }
            }
        });
        quyMainActivityTradingFragmentCreateCommandContainerOpenCommandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCommandAndContinue();
            }
        });
    }
    private void setConstrainEvents(){
        quyMainActivityTradingFragmentCreateCommandContainerInputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                long money=0;
                try{
                    money = Long.parseLong(editable.toString());
                }catch (Exception e){}
                long maximumMoney = (long) (miniProfile.moneyNow*0.99f);
                if(money>maximumMoney){
                    quyMainActivityTradingFragmentCreateCommandContainerInputMoney.setText(""+maximumMoney);
                }
                if(quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney.getProgress()!=(int)(money/maximumMoney)){
                    quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney.setProgress((int)(money/maximumMoney));
                }
                setTempSumMoney();
            }
        });
        quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long money=0l;
                try{
                    money = Long.parseLong(quyMainActivityTradingFragmentCreateCommandContainerInputMoney.getText().toString());
                }catch (Exception e){}
                if(money!=seekBar.getProgress()*0.99f*miniProfile.moneyNow){
                    quyMainActivityTradingFragmentCreateCommandContainerInputMoney.setText(seekBar.getProgress()*0.99f*miniProfile.moneyNow+"");
                }
                setTempSumMoney();
            }
        });
    }
    private void resetInputValueGeneral(){
        quyMainActivityTradingFragmentCreateCommandContainerLeverage.setSelection(0);
        quyMainActivityTradingFragmentCreateCommandContainerInputMoney.setText("0");
        quyMainActivityTradingFragmentCreateCommandContainerSeekBarMoney.setProgress(0);
        quyMainActivityTradingFragmentCreateCommandContainerEnableTPSL.setChecked(false);
        quyMainActivityTradingFragmentCreateCommandContainerTakeProfit.setText("0");
        quyMainActivityTradingFragmentCreateCommandContainerStopLoss.setText("0");
        setTPSLInputStatus();
        setTempSumMoney();
    }
    private void openCreateCommandContainer(boolean isBuy){
        quyMainActivityTradingFragmentCreateCommandContainerSwitchBuySell.setChecked(isBuy);
        resetInputValueGeneral();
        quyMainActivityTradingFragmentCreateCommandContainer.setVisibility(View.VISIBLE);
    }

    private void setTPSLInputStatus(){
        if(quyMainActivityTradingFragmentCreateCommandContainerEnableTPSL.isChecked()){
            quyMainActivityTradingFragmentCreateCommandContainerTakeProfit.setEnabled(true);
            quyMainActivityTradingFragmentCreateCommandContainerStopLoss.setEnabled(true);
        }else{
            quyMainActivityTradingFragmentCreateCommandContainerTakeProfit.setEnabled(false);
            quyMainActivityTradingFragmentCreateCommandContainerStopLoss.setEnabled(false);
        }
    }
    private void setTempSumMoney(){
        long money = 0L;
        int leverage = 1;
        long commission = 0;
        try{
            leverage = Integer.parseInt(quyMainActivityTradingFragmentCreateCommandContainerLeverage.getSelectedItem().toString());
            money = Long.parseLong(quyMainActivityTradingFragmentCreateCommandContainerInputMoney.getText().toString());
            if(leverage!=1){
                commission = getCommission(money,leverage);//0.01%
            }
        }catch(Exception e){}
        quyMainActivityTradingFragmentCreateCommandContainerSumMoney.setText("Khối lượng: "+money*leverage);
        quyMainActivityTradingFragmentCreateCommandContainerCommission.setText("Hoa hồng: "+commission+"$");
    }

    private class LoadMiniProfileOk implements BaseViewModel.OkCallback{
        @Override
        public void handle(String data) {
            try{
                miniProfile = new Gson().fromJson(data,QuyProfileResponseModel.MiniProfile.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setMiniInfo();
                    }
                });

                ArrayList<String> coinIds = new ArrayList<>(miniProfile.openCommandItems.stream().map(e->e.coinId).collect(Collectors.toList()));
                if(coinIds.size()!=0){
                    coinService.addEventListener(coinIds, REGISTER_COIN_SERVICE_NAME, new CoinServiceModel.EventCallbackInterface() {
                        @Override
                        public void handle(ArrayList<CoinServiceModel.CoinNow> coins) {
                            updateMiniInfoSumMoney(coins);
                        }
                    });
                }
                setConstrainEvents();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    private void loadMiniProfile(){
        quyProfileViewModel.getMiniInfo(new LoadMiniProfileOk());
    }
    private void setMiniInfo(){
        miniInfoSumMoney.setText(miniProfile.moneyNow+miniProfile.moneyInvested+miniProfile.moneyProfitNow+"");
        miniInfoReadyMoney.setText(miniProfile.moneyNow+"");
        miniInfoTradingCommandNumber.setText(miniProfile.openTradingCommandNumber+"");
    }
    private void updateMiniInfoSumMoney(ArrayList<CoinServiceModel.CoinNow> coins){
        miniProfile.moneyProfitNow = 0f;
        for (int i = 0; i < miniProfile.openCommandItems.size(); i++) {
            int indCoin = -1;
            for (int j = 0; j < coins.size(); j++) {
                if(coins.get(j).id.equals(miniProfile.openCommandItems.get(i))){
                    indCoin=j;
                    break;
                }
            }
            if(indCoin!=-1){
                miniProfile.moneyProfitNow+=(coins.get(indCoin).priceUsd-miniProfile.openCommandItems.get(i).openPrice)*miniProfile.openCommandItems.get(i).coinNumber*(miniProfile.openCommandItems.get(i).buyOrSell.equals("buy")?1f:-1f);
            }
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                miniInfoSumMoney.setText(miniProfile.moneyNow+miniProfile.moneyInvested+miniProfile.moneyProfitNow+"");
            }
        });
    }

    private void loadCoinInfo(){
        coinService.getCoinById(this.coinId, new CoinService.GetOneWaitCallback() {
            @Override
            public void handle(CoinServiceModel.CoinNow coin) {
                if(coin!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setCoinInfo(coin);
                        }
                    });
                }
            }
        });
        coinService.addEventListener(new ArrayList<>(Arrays.asList(this.coinId)), REGISTER_COIN_SERVICE_NAME, new CoinServiceModel.EventCallbackInterface() {
            @Override
            public void handle(ArrayList<CoinServiceModel.CoinNow> coins) {
                for (int i = 0; i < coins.size(); i++) {
                    updateCoinInfo(coins.get(i));
                }
            }
        });

    }
    private void setCoinInfo(CoinServiceModel.CoinNow coin){
        quyMainActivityTradingFragmentCoinInfoName.setText(coin.name);
        General.setImageUrl(context,quyMainActivityTradingFragmentCoinInfoIcon,coin.icon);
        quyMainActivityTradingFragmentCoinInfoPrice.setText(""+coin.priceUsd);
        quyMainActivityTradingFragmentCoinInfoChange24h.setText(""+coin.changePercent24Hr);
    }
    private void updateCoinInfo(CoinServiceModel.CoinNow coin){
        if(coin.id.equals(this.coinId)){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    quyMainActivityTradingFragmentCoinInfoPrice.setText(""+coin.priceUsd);
                    quyMainActivityTradingFragmentCoinInfoChange24h.setText(""+coin.changePercent24Hr);
                }
            });
        }
    }

    private long getCommission(long money, int leverage){
        if(leverage==1)return 0L;
        return (long)(money*leverage*0.0001f);
    }
    private void checkCommandAndContinue(){
        long money = 0L;
        int leverage = 1;
        long commission = 0;
        try{
            leverage = Integer.parseInt(quyMainActivityTradingFragmentCreateCommandContainerLeverage.getSelectedItem().toString());
            money = Long.parseLong(quyMainActivityTradingFragmentCreateCommandContainerInputMoney.getText().toString());
            if(leverage!=1){
                commission = getCommission(money,leverage);//0.01%
            }
        }catch(Exception e){}

        if(money==0L){
            General.showNotification(context,new SystemNotificationModel(SystemNotificationModel.Type.Warning,"Chưa nhập số tiền."));
        }else if(money<10L){
            General.showNotification(context,new SystemNotificationModel(SystemNotificationModel.Type.Warning,"Số tiền không ít hơn 10$."));
        }

        if(quyMainActivityTradingFragmentCreateCommandContainerEnableTPSL.isChecked()){
            long tp = 0L;
            long sl = 0L;
            try{
                tp = Long.parseLong(quyMainActivityTradingFragmentCreateCommandContainerTakeProfit.getText().toString());
                sl = Long.parseLong(quyMainActivityTradingFragmentCreateCommandContainerStopLoss.getText().toString());
            }catch(Exception e){}
            if(tp<money-commission){
                General.showNotification(context,new SystemNotificationModel(SystemNotificationModel.Type.Warning,"Chốt lời không ít hơn giá trị hiện tại."));
            }
            if(sl>money-commission){
                General.showNotification(context,new SystemNotificationModel(SystemNotificationModel.Type.Warning,"Cắt lỗ không lớn hơn giá trị hiện tại."));
            }
        }
        checkTracePinStatusAndContinue();
    }
    private void checkTracePinStatusAndContinue(){
        quyTradingCommandViewModel.checkTradePinStatus(new BaseViewModel.OkCallback() {
            @Override
            public void handle(String data) {
                if(data.equals("true")){
                    openVerifyPinAndContinue();
                }else{
                    General.showNotification(context,new SystemNotificationModel(SystemNotificationModel.Type.Warning,"Bạn chưa thiết lập mã pin giao dịch. Hãy vào hồ sơ > cài đặt để thiết lập."));
                }
            }
        });
    }

    private void openVerifyPinAndContinue(){


    }


}