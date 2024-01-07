package com.example.nhom3_crypto_client.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom3_crypto_client.R;
import com.example.nhom3_crypto_client.core.General;
import com.example.nhom3_crypto_client.model.response.QuyProfileResponseModel;

import java.io.IOException;
import java.util.ArrayList;

public class BinhRankTopUserAdapter extends RecyclerView.Adapter<BinhRankTopUserAdapter.ViewHolderCustom> {

    private ArrayList<QuyProfileResponseModel.TopUser> topUsers = new ArrayList<>();
    Context context;

    public void setList(ArrayList<QuyProfileResponseModel.TopUser> topUsers) {
        this.topUsers = topUsers;
        notifyDataSetChanged();
        System.out.println("ublic void setList(ArrayList<QuyProfileResponseModel");
    }

    public BinhRankTopUserAdapter(Context context) {
        this.context = context;
    }

    public static interface OpenCallback {
        public void open(String userId);
    }

    OpenCallback openCallback;

    public void setOpenCallback(OpenCallback openCallback) {
        this.openCallback = openCallback;
    }

    @NonNull
    @Override
    public BinhRankTopUserAdapter.ViewHolderCustom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderCustom(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.binh_item_list_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BinhRankTopUserAdapter.ViewHolderCustom holder, int position) {
        try {
            holder.bindData(topUsers.get(position), context, position);
        } catch (IOException e) {

        }
    }

    @Override
    public int getItemCount() {
        return topUsers.size();
    }

    public class ViewHolderCustom extends RecyclerView.ViewHolder {


        private ImageView imageRankUser;
        private TextView txtRankUserName;
        private TextView txtRankMoneyProfit;
        private TextView txtRankUserPercent;
        private TextView txtRankTopNumber;
        private TextView txtRankCommandNumber;

        public ViewHolderCustom(@NonNull View view) {
            super(view);
            imageRankUser = view.findViewById(R.id.image_item_rank_user);
            txtRankUserName = view.findViewById(R.id.txt_item_rank_user_name);
            txtRankTopNumber = view.findViewById(R.id.txt_item_rank_top_number);
            txtRankUserPercent = view.findViewById(R.id.txt_item_rank_percent);
            txtRankMoneyProfit = view.findViewById(R.id.txt_item_rank_money_profit);
            txtRankCommandNumber = view.findViewById(R.id.txt_item_trading_command_number);
        }

        public void bindData(QuyProfileResponseModel.TopUser topUser, Context context, int position) throws IOException {
            General.setAvatarUrl(context, imageRankUser, topUser.avatar);
            txtRankUserName.setText(topUser.name);
            txtRankTopNumber.setText("" + topUser.topNumber);
            txtRankMoneyProfit.setText("$" + topUser.moneyNow);
            txtRankCommandNumber.setText("" + topUser.tradingCommandNumber);
            //kiểm tra lại dòng này

            if (topUser.tradingCommandNumber == 0) {
                txtRankUserPercent.setText("0" + "%");
            } else {
                txtRankUserPercent.setText((100 * topUser.tradingCommandProfitNumber / topUser.tradingCommandNumber) + "%");

            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openCallback.open(topUser.id);
                }
            });
        }
    }
}
