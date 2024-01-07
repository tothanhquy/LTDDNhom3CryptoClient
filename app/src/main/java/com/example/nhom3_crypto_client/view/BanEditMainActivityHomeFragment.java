package com.example.nhom3_crypto_client.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.core.General;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.model.response.QuyProfileResponseModel;
import com.example.nhom3_crypto_client.view_model.BaseViewModel;
import com.example.nhom3_crypto_client.view_model.BinhProfileViewModel;
import com.google.gson.Gson;

public class BanEditMainActivityHomeFragment extends Fragment {
    private ImageView btnCommand;
    private ImageView btnRating;
    private ImageView btnEditName;
    private ImageView btnSafe;
    private ImageView btnSetting;
    private ImageView imgAvatar;
    private float originalScale;
    private TextView txtName, txtMoney, txtMoneyAvailable, txtMoneyInvested, txtCommandNumber, txtRatingNumber;
    QuyProfileResponseModel.Profile profileDetails;
    private BinhProfileViewModel viewModel;

    public BanEditMainActivityHomeFragment() {
    }


    public static BanEditMainActivityHomeFragment newInstance(String param1, String param2) {
        BanEditMainActivityHomeFragment fragment = new BanEditMainActivityHomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.ban_activity_home, container, false);
        btnCommand = view.findViewById(R.id.btnCommand);
        btnRating = view.findViewById(R.id.btnRating);
        btnEditName = view.findViewById(R.id.btnEditName);
        btnSafe = view.findViewById(R.id.btnSafe);
        btnSetting = view.findViewById(R.id.btnSetting);
        txtName = view.findViewById(R.id.txtName);
        txtMoney = view.findViewById(R.id.txtMoney);
        txtMoneyAvailable = view.findViewById(R.id.txtMoneyAvailable);
        txtMoneyInvested = view.findViewById(R.id.txtMoneyInvested);
        txtCommandNumber = view.findViewById(R.id.txtCommandNumber);
        txtRatingNumber = view.findViewById(R.id.txtRattingNumber);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        viewModel = new BinhProfileViewModel(getContext());
        loadData();
        setObserve();
        setButtonClickAnimation(btnCommand, new ButtonClickAnimationAction() {
            @Override
            public void action() {
                startActivity(new Intent(getActivity(), Ban_CommandActivity.class));
            }
        });
        setButtonClickAnimation(btnRating, new ButtonClickAnimationAction() {
            @Override
            public void action() {
                startActivity(new Intent(getActivity(), Binh_RankActivity.class));
            }
        });
        setButtonClickAnimation(btnSafe, new ButtonClickAnimationAction() {
            @Override
            public void action() {
                startActivity(new Intent(getActivity(), Binh_BalanceTransactionActivity.class));
            }
        });
        setButtonClickAnimation(btnSetting, new ButtonClickAnimationAction() {
            @Override
            public void action() {
                startActivity(new Intent(getActivity(), Binh_SettingActivity.class));
            }
        });
        setButtonClickAnimation(btnEditName, new ButtonClickAnimationAction() {
            @Override
            public void action() {
                editInfoNavigationObject.navigation();
            }
        });
        setButtonClickAnimation(imgAvatar, new ButtonClickAnimationAction() {
            @Override
            public void action() {
                editInfoNavigationObject.navigation();
            }
        });

        return view;
    }

    public static interface EditInfoNavigation {
        public void navigation();
    }

    private EditInfoNavigation editInfoNavigationObject;

    public void setEditInfoNavigation(EditInfoNavigation editInfoNavigation) {
        this.editInfoNavigationObject = editInfoNavigation;
    }

    private interface ButtonClickAnimationAction {
        public void action();
    }

    private void setProfile() {

        //
        General.setAvatarUrl(getContext(), imgAvatar, profileDetails.avatar);
        txtName.setText(profileDetails.name);
        txtMoneyInvested.setText("$" + String.format("%.2f", profileDetails.moneyInvested) +" K");
        txtMoneyAvailable.setText("$" + String.format("%.2f", profileDetails.moneyNow)+" K");
        float sumMoney = profileDetails.moneyInvested + profileDetails.moneyProfitNow + profileDetails.moneyNow;
        txtMoney.setText("$" + String.format("%.2f", sumMoney)+ " K");
        txtRatingNumber.setText(">" + 100 * (profileDetails.totalNumber - profileDetails.topNumber) / profileDetails.totalNumber + "%");
        txtCommandNumber.setText("" + profileDetails.tradingCommandNumber);
    }

    private void loadData() {
     /*   Intent intent = getIntent();
        userId = intent.getStringExtra("id");*/
        viewModel.getInfo("mine", new BaseViewModel.OkCallback() {
            @Override
            public void handle(String data) {
                System.out.println(data);
                profileDetails = new Gson().fromJson(data, QuyProfileResponseModel.Profile.class);
                System.out.println(profileDetails);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setProfile();
                    }
                });

            }
        });
    }

    public void setObserve() {
        viewModel.notification().observe(getActivity(), new Observer<SystemNotificationModel>() {
            @Override
            public void onChanged(SystemNotificationModel systemNotificationModel) {
                if (systemNotificationModel != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            General.showNotification(getContext(), systemNotificationModel);
                        }
                    });
                }
            }
        });
    }

    private void setButtonClickAnimation(ImageView button, ButtonClickAnimationAction action) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a scale animation
                Animation anim = new ScaleAnimation(
                        1f, 0.6f, // Start and end values for the X axis scaling
                        1f, 0.6f, // Start and end values for the Y axis scaling
                        Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                        Animation.RELATIVE_TO_SELF, 0.5f // Pivot point of Y scaling
                );

                anim.setFillAfter(true); // Keeps the result of the animation
                anim.setDuration(200); // Duration of the animation in milliseconds

                // Set the animation listener to handle the animation end
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Do something when the animation ends (e.g., start a new activity)
                        button.startAnimation(getScaleAnimation(0.6f, 1f)); // Phóng to lại
                        action.action();

                    }

                    private Animation getScaleAnimation(float fromScale, float toScale) {
                        Animation anim = new ScaleAnimation(
                                fromScale, toScale, // Start and end values for the X axis scaling
                                fromScale, toScale, // Start and end values for the Y axis scaling
                                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point of Y scaling
                        );
                        anim.setFillAfter(true);
                        anim.setDuration(200);
                        return anim;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

                // Start the animation
                button.startAnimation(anim);
            }
        });
    }
}