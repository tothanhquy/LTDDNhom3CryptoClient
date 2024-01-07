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

import androidx.fragment.app.Fragment;

import com.example.nhom3_crypto_client.R;

public class BanEditMainActivityHomeFragment extends Fragment {
    private ImageView btnCommand;
    private ImageView btnRating;
    private ImageView btnEditName;
    private float originalScale;
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
        setButtonClickAnimation(btnEditName, new ButtonClickAnimationAction() {
            @Override
            public void action() {
                editInfoNavigationObject.navigation();
            }
        });
        return view;
    }

    public static interface EditInfoNavigation{
        public void navigation();
    }
    private EditInfoNavigation editInfoNavigationObject;
    public void setEditInfoNavigation(EditInfoNavigation editInfoNavigation){
        this.editInfoNavigationObject = editInfoNavigation;
    }
    private interface ButtonClickAnimationAction{
        public void action();
    }
    private void setButtonClickAnimation( ImageView button, ButtonClickAnimationAction action) {
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