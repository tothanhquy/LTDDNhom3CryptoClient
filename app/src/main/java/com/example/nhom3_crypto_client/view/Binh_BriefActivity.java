package com.example.nhom3_crypto_client.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.example.nhom3_crypto_client.R;
public class Binh_BriefActivity extends AppCompatActivity {

    private TextView txtSolenh;
    private TextView txtPercent;
    private SeekBar seekBar;
    private ImageView userImageView;
    private ImageView dialogImageView;
    private ImageButton changeImageButton;
    private int solenhValue = 0;
    private int choose = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private PieChart pieChart;
    private TextView txtBriefIncome, txtBriefInvested, txtBriefAvailable;

    private ImageView btnEditName;
    private ImageView btnSetting;
    private TextView txtBriefUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binh_activity_brief_user);
        //
        initView();

        // Đặt sự kiện lắng nghe cho txt_solenh
        txtSolenh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Trước khi thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Trong quá trình thay đổi văn bản
                updatePercentAndSeekBar();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Sau khi thay đổi văn bản
            }
        });

        // Lên lịch trình nhiệm vụ cập nhật giá trị
        handler.postDelayed(updateTask, 5000);

        //Click image
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });

        //
        updatePieChart();

        //
        editUserName();

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang SettingActivity khi nút được click
                Intent intent = new Intent(Binh_BriefActivity.this, Binh_SettingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initView() {
        txtSolenh = findViewById(R.id.txt_brief_solenh);
        txtPercent = findViewById(R.id.txt_brief_percent);
        seekBar = findViewById(R.id.seekbar_brief_percent);
        userImageView = findViewById(R.id.image_brief_user);

        txtBriefIncome = findViewById(R.id.txt_brief_income);
        txtBriefAvailable = findViewById(R.id.txt_brief_available);
        txtBriefInvested = findViewById(R.id.txt_brief_invested);
        btnSetting = findViewById(R.id.btn_brief_setting);

        // pie
        pieChart = findViewById(R.id.pieChart);

        //editname
        btnEditName = findViewById(R.id.btn_brief_edit_name);
        txtBriefUserName = findViewById(R.id.txt_brief_user_name);

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
        Dialog editNameDialog = new Dialog(this);
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
                    // Cập nhật TextView txt_brief_user_name
                    txtBriefUserName.setText(newName);
                }
                editNameDialog.dismiss();
            }
        });

        // Hiển thị Dialog
        editNameDialog.show();
    }


    //
    private void updatePieChart() {

        ArrayList<PieEntry> entries = new ArrayList<>();

        int incomeValue = getRandomValue();
        int availableValue = getRandomValue();
        int investedValue = getRandomValue();

        int sumMoney = investedValue + incomeValue + availableValue;

        // Đặt giá trị vào các TextView tương ứng
        txtBriefIncome.setText("$ " + String.valueOf(incomeValue));
        txtBriefInvested.setText("$ " + String.valueOf(investedValue));
        txtBriefAvailable.setText("$ " + String.valueOf(availableValue) + "K");

        entries.add(new PieEntry(incomeValue, ""));
        entries.add(new PieEntry(availableValue, ""));
        entries.add(new PieEntry(investedValue, ""));

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(new int[]{R.color.incomeColor, R.color.availableColor, R.color.investedColor}, getApplicationContext());
        pieDataSet.setValueTextColor(Color.WHITE); // Đặt màu cho label
        pieDataSet.setValueTextSize(10f);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);

        pieChart.animateY(1000);

        // Đặt văn bản và làm đậm màu sắc
        pieChart.setCenterText("$" + String.valueOf(sumMoney) + "K");
        pieChart.setCenterTextColor(Color.parseColor("#FF000000"));
        pieChart.setCenterTextSize(16f);
        // Trước hết, lấy đối tượng Legend từ PieChart
        Legend legend = pieChart.getLegend();
        // Tắt hiển thị Legend (bảng chú giải)
        legend.setEnabled(false);
        // Cập nhật biểu đồ
        pieChart.invalidate();

    }

    private int getRandomValue() {
        return (int) (Math.random() * 100);
    }


    private void updatePercentAndSeekBar() {
        try {
            // Lấy giá trị từ txt_solenh
            int solenhValue = Integer.parseInt(txtSolenh.getText().toString());

            // Tính toán giá trị percent
            int percentValue = solenhValue * 1;

            // Cập nhật giá trị cho txt_percent
            txtPercent.setText(String.valueOf(percentValue) + "%");

            // Cập nhật giá trị cho SeekBar
            seekBar.setProgress(percentValue);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //
    private Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            solenhValue += 5;
            // Cập nhật giá trị cho txt_solenh
            txtSolenh.setText(String.valueOf(solenhValue));
            handler.postDelayed(this, 5000);
        }
    };

    private void showImageDialog() {
        // Tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.binh_dialog_edit_image_user);

        // Lấy tham chiếu đến các thành phần trong Dialog
        dialogImageView = dialog.findViewById(R.id.image_rank_user);
        changeImageButton = dialog.findViewById(R.id.btn_brief_change_img_user);

        // Đặt ảnh từ image_brief_user vào ImageView trong Dialog
        dialogImageView.setImageDrawable(userImageView.getDrawable());

        // Xử lý sự kiện khi nhấn nút thay đổi ảnh
        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence optionMenu[] = {"Chụp ảnh", "Chọn ảnh", "Thoát"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Binh_BriefActivity.this);
                builder.setItems(optionMenu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (optionMenu[i].equals("Chụp ảnh")) {
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            choose = 1;
                            getData.launch(takePicture);
                        } else if (optionMenu[i].equals("Chọn ảnh")) {
                            Intent pickPhoto = new Intent();
                            pickPhoto.setType("image/*");
                            pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                            choose = 2;
                            getData.launch(pickPhoto);
                        } else if (optionMenu[i].equals("Thoát")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });


        dialog.show();
    }

    ActivityResultLauncher<Intent> getData = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    Intent data = o.getData();
                    Bitmap selectedImage = null;
                    if (choose == 1) {
                        selectedImage = (Bitmap) data.getExtras().get("data");
                    } else if (choose == 2) {
                        Uri selectedImageUrl = data.getData();
                        try {
                            selectedImage = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(), selectedImageUrl);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // Cập nhật ảnh cho cả userImageView và dialogImageView
                    userImageView.setImageBitmap(selectedImage);
                    dialogImageView.setImageBitmap(selectedImage);
                }
            }
    );
}
