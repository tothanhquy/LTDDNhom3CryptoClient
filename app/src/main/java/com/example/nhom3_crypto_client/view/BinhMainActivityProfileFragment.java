package com.example.nhom3_crypto_client.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.core.General;
import com.example.nhom3_crypto_client.model.SystemNotificationModel;
import com.example.nhom3_crypto_client.model.response.QuyProfileResponseModel;
import com.example.nhom3_crypto_client.view_model.BaseViewModel;
import com.example.nhom3_crypto_client.view_model.BinhProfileViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BinhMainActivityProfileFragment extends Fragment {

    private CardView cardBrief;
    private TextView txtSolenh;
    private TextView txtPercent;
    private SeekBar seekBar;
    private ImageView userImageView;
    private ImageView dialogImageView;
    private ImageButton changeImageButton;
    private int solenhValue = 0;
    public static int selectImageChooseOption = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private PieChart pieChart;
    private TextView txtBriefIncome, txtBriefInvested, txtBriefAvailable;
    private TextView txtBriefMoneyMax, txtBriefMoneyAverage, txtBriefMoneyProfitMax, txtBriefMoneyLosstMax;
    private TextView txtBriefRate;

    private ImageView btnEditName;
    private ImageView btnSetting;
    private TextView txtBriefUserName;

    private BinhProfileViewModel profileViewModel;
    private String userId;
    QuyProfileResponseModel.Profile profileDetails;
    LinearLayout loadingLayout;

    //
    ImageView btnUserRank;
    public int betterPercent;

    public BinhMainActivityProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        // percent
        txtPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Trước khi thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Trong quá trình thay đổi văn bản
//                updatePercentAndSeekBar();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Sau khi thay đổi văn bản
                updatePercentAndSeekBar();
            }
        });

        //Click image
        cardBrief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });

        //   updatePieChart();
        editUserName();

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Binh_SettingActivity.class);
                startActivity(intent);
            }
        });


        profileViewModel = new BinhProfileViewModel(getContext());

        //
        setObserve();
        loadData();

        //
        btnUserRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Binh_RankActivity.class);
                intent.putExtra("betterPercent", betterPercent);
                startActivity(intent);
            }
        });
    }


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

        View view = inflater.inflate(R.layout.binh_activity_brief_user, container, false);
        loadingLayout = view.findViewById(R.id.loadingLayout);

        seekBar = view.findViewById(R.id.seekbar_brief_percent);
        seekBar.setEnabled(false);
        userImageView = view.findViewById(R.id.image_brief_user);


        btnSetting = view.findViewById(R.id.btn_brief_setting);

        // pie
        pieChart = view.findViewById(R.id.pieChart);

        //editname
        btnEditName = view.findViewById(R.id.btn_brief_edit_name);
        txtBriefUserName = view.findViewById(R.id.txt_brief_user_name);

        //
        txtBriefIncome = view.findViewById(R.id.txt_brief_income);
        txtBriefAvailable = view.findViewById(R.id.txt_brief_available);
        txtBriefInvested = view.findViewById(R.id.txt_brief_invested);
        txtSolenh = view.findViewById(R.id.txt_brief_solenh);
        txtPercent = view.findViewById(R.id.txt_brief_percent);

        //
        txtBriefMoneyMax = view.findViewById(R.id.txt_brief_money_max);
        txtBriefMoneyProfitMax = view.findViewById(R.id.txt_brief_profit_max);
        txtBriefMoneyLosstMax = view.findViewById(R.id.txt_brief_money_loss_max);
        txtBriefMoneyAverage = view.findViewById(R.id.txt_brief_money_average);
        //
        txtBriefRate = view.findViewById(R.id.txt__brief_rate);

        cardBrief = view.findViewById(R.id.cardView_brief_user_image);

        //
        btnUserRank = view.findViewById(R.id.btn_brief_rank);

        return view;
    }

    private void setProfile() {
        betterPercent = 100 * (profileDetails.totalNumber - profileDetails.topNumber) / profileDetails.totalNumber;
        //
        General.setAvatarUrl(getContext(), userImageView, profileDetails.avatar);
        txtBriefUserName.setText(profileDetails.name);
        txtBriefIncome.setText("$" + String.format("%.2f", profileDetails.moneyProfitNow));

        txtBriefInvested.setText("$" + String.format("%.2f", profileDetails.moneyInvested));
        txtBriefAvailable.setText("$" + String.format("%.2f", profileDetails.moneyNow));

        txtBriefRate.setText(">" + 100 * (profileDetails.totalNumber - profileDetails.topNumber) / profileDetails.totalNumber + "%");

        txtBriefMoneyAverage.setText("$" + String.format("%.2f", profileDetails.tradingCommandMoneyAvg));
        txtBriefMoneyMax.setText("$" + String.format("%.2f", profileDetails.tradingCommandMoneyMaximum));
        txtBriefMoneyProfitMax.setText("$" + String.format("%.2f", profileDetails.tradingCommandProfitMaximum));
        txtBriefMoneyLosstMax.setText("$" + String.format("%.2f", profileDetails.tradingCommandLossMaximum));
        if (profileDetails.tradingCommandNumber == 0) {
            txtPercent.setText("0");
        } else {
            txtPercent.setText((100 * profileDetails.tradingCommandProfitNumber / profileDetails.tradingCommandNumber) + "%");
        }


        txtSolenh.setText("" + profileDetails.tradingCommandNumber);

        //Pie Chart
        ArrayList<PieEntry> entries = new ArrayList<>();
        //tổng tiền trong chart
        float sumMoney = profileDetails.moneyInvested + profileDetails.moneyProfitNow + profileDetails.moneyNow;
        if (profileDetails.moneyProfitNow > 0) {
            entries.add(new PieEntry(profileDetails.moneyProfitNow, ""));
        }
        if (profileDetails.moneyNow > 0) {
            entries.add(new PieEntry(profileDetails.moneyNow, ""));
        }
        if (profileDetails.moneyInvested > 0) {
            entries.add(new PieEntry(profileDetails.moneyInvested, ""));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(new int[]{R.color.incomeColor, R.color.availableColor, R.color.investedColor}, getContext());
        pieDataSet.setValueTextColor(Color.WHITE); // Đặt màu cho label
        pieDataSet.setValueTextSize(9f);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);

        pieChart.animateY(1000);

        // Đặt văn bản và làm đậm màu sắc
        pieChart.setCenterText("$" + String.valueOf((int) sumMoney) + "K");
        pieChart.setCenterTextColor(Color.parseColor("#FF000000"));
        pieChart.setCenterTextSize(16f);
        // Trước hết, lấy đối tượng Legend từ PieChart
        Legend legend = pieChart.getLegend();
        // Tắt hiển thị Legend (bảng chú giải)
        legend.setEnabled(false);
        // Cập nhật biểu đồ
        pieChart.invalidate();

    }

    private void loadData() {
     /*   Intent intent = getIntent();
        userId = intent.getStringExtra("id");*/
        profileViewModel.getInfo("mine", new BaseViewModel.OkCallback() {
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
        profileViewModel.notification().observe(this, new Observer<SystemNotificationModel>() {
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
        profileViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                System.out.println(loadingLayout);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isLoading == true) {
                            loadingLayout.setVisibility(View.VISIBLE);
                        } else {
                            loadingLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }


    //edit name
    public void editUserName() {
        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hiển thị Dialog chỉnh sửa tên
                showEditNameDialog();
            }
        });
    }

    // dialog username
    private void showEditNameDialog() {
        // Tạo Dialog
        Dialog editNameDialog = new Dialog(getContext());
        editNameDialog.setContentView(R.layout.binh_dialog_edit_name);

        // Lấy tham chiếu đến các thành phần trong Dialog
        EditText editTextNewName = editNameDialog.findViewById(R.id.edit_brief_dialog_name);
        Button btnConfirm = editNameDialog.findViewById(R.id.btnConfirm);

        // Xử lý sự kiện khi nhấn nút "Đồng ý"
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editTextNewName.getText().toString();
                if (!newName.trim().isEmpty()) {
                    profileViewModel.postChangeName(newName, new BaseViewModel.OkCallback() {
                        @Override
                        public void handle(String data) {
                            txtBriefUserName.setText(newName);
                            editNameDialog.dismiss();
                        }
                    });
                }
                editNameDialog.dismiss();
            }
        });

        // Hiển thị Dialog
        editNameDialog.show();
    }


    private void updatePercentAndSeekBar() {
        try {
           /* txtPercent.setText((profileDetails.tradingCommandProfitNumber / profileDetails.tradingCommandNumber) * 100 + "%");
            txtSolenh.setText(profileDetails.tradingCommandNumber);*/


            // Cập nhật giá tị cho SeekBar
            if (profileDetails.tradingCommandNumber == 0) {
                seekBar.setProgress(0);
            } else {
                seekBar.setProgress((100 * profileDetails.tradingCommandProfitNumber / profileDetails.tradingCommandNumber));
            }
            System.out.println(profileDetails.tradingCommandProfitNumber);
            seekBar.setMin(0);
            seekBar.setMax(100);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    private void showImageDialog() {
        // Tạo Dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.binh_dialog_edit_image_user);

        // Lấy tham chiếu đến các thành phần trong Dialog
        dialogImageView = dialog.findViewById(R.id.image_item_rank_user);
        changeImageButton = dialog.findViewById(R.id.btn_brief_change_img_user);

        // Đặt ảnh từ image_brief_user vào ImageView trong Dialog
        dialogImageView.setImageDrawable(userImageView.getDrawable());

        // Xử lý sự kiện khi nhấn nút thay đổi ảnh
        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageChooseOption = 2;
                openChooseImageActivity.open(2);


            }
        });
        dialog.show();
    }

    public void setChooseAvatarImage(Bitmap bitmap) {
//        System.out.println(bitmap.getByteCount());
//        userImageView.setImageBitmap(bitmap);

        userImageView.setBackground(new BitmapDrawable(getResources(), bitmap));
        dialogImageView.setImageBitmap(bitmap);
        changeAvatar(bitmap);
    }

    private void changeAvatar(Bitmap bitmap) {
        profileViewModel.postImage(bitmap, new BaseViewModel.OkCallback() {
            @Override
            public void handle(String data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        General.showNotification(getContext(), new SystemNotificationModel(SystemNotificationModel.Type.Info, "", "Up ảnh thành công", null));
                    }
                });
            }
        });
    }

    ActivityResultLauncher<Intent> profileOpenChoseLauncher;

    public void setProfileOpenChose(ActivityResultLauncher<Intent> profileOpenChoseLauncher) {
        this.profileOpenChoseLauncher = profileOpenChoseLauncher;
    }

    public static interface OpenChooseImageActivity {
        public void open(int choose);
    }

    OpenChooseImageActivity openChooseImageActivity;

    public void setOpenChooseImageActivity(OpenChooseImageActivity openChooseImageActivity) {
        this.openChooseImageActivity = openChooseImageActivity;
    }


}