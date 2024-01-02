package com.example.nhom3_crypto_client.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Ban_ViewCommand extends FragmentStateAdapter {

    public Ban_ViewCommand(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Ban_OpenFragment();
            case 1:
                return new Ban_CloseFragment();
            default:
                return new Ban_OpenFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
