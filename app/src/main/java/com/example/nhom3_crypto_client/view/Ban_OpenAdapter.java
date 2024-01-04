package com.example.nhom3_crypto_client.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nhom3_crypto_client.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Ban_OpenAdapter extends RecyclerView.Adapter<Ban_OpenAdapter.ViewCustom> {
    private ArrayList<Ban_OpenCommand> opencommand;
    public void setList(ArrayList<Ban_OpenCommand> opencommand){
        this.opencommand= opencommand;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewCustom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewCustom(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ban_command_openitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCustom holder, int position) {
        holder.bindData(opencommand.get(position));
    }
    @Override
    public int getItemCount() {
        return opencommand != null ? opencommand.size() : 0;
    }
    class ViewCustom extends RecyclerView.ViewHolder {

        private TextView CoinName;
        private TextView Value;
        private TextView Leverage;

        public ViewCustom(View itemView) {
            super(itemView);
            CoinName = (TextView) itemView.findViewById(R.id.txtCoin);
            Value = (TextView) itemView.findViewById(R.id.txtValue);
            Leverage = (TextView) itemView.findViewById(R.id.txtLeverage);
        }

        public void bindData(Ban_OpenCommand item) {
            CoinName.setText(item.CoinName);
            Value.setText(item.vaule);
            Leverage.setText(item.Leverage);
        }
    }
}
