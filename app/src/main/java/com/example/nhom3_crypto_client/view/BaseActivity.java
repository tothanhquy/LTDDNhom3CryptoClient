package com.example.nhom3_crypto_client.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhom3_crypto_client.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected View getCustomViewTabLayout(int drawableId, String text){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.quy_tablayout_item, null);
        view.findViewById(R.id.quyTablayoutItemIcon).setBackgroundResource(drawableId);
        ((TextView)view.findViewById(R.id.quyTablayoutItemText)).setText(text);
        return view;
    }
    public static View getCustomViewTabLayout(Context context, int drawableId, String text){
        View view = LayoutInflater.from(context).inflate(R.layout.quy_tablayout_item, null);
        view.findViewById(R.id.quyTablayoutItemIcon).setBackgroundResource(drawableId);
        ((TextView)view.findViewById(R.id.quyTablayoutItemText)).setText(text);
        return view;
    }
}