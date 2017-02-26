package com.example.gio.splitanimationapp;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setTitle("givi");

            // Preparing the 2 images to be split
            ActivitySplitAnimationUtil.prepareAnimation(this);

            setContentView(R.layout.activity_second);

            // Animating the items to be open, revealing the new activity
            ActivitySplitAnimationUtil.animate(this, 1000);
        }

        @Override
        protected void onStop() {
            // If we're currently running the entrance animation - cancel it
            ActivitySplitAnimationUtil.cancel();

            super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        }
    }