package com.example.gio.splitanimationapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import javax.net.ssl.HandshakeCompletedEvent;

import static java.lang.Thread.sleep;

public class SecondActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second);



            findViewById(R.id.activity_second).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            ActivitySplitAnimationUtil.prepareAnimation2(SecondActivity.this);
                    ActivitySplitAnimationUtil.givikuna();

                }
            });

            // Preparing the 2 images to be split
            ActivitySplitAnimationUtil.prepareAnimation(this);


            // Animating the items to be open, revealing the new activity
            ActivitySplitAnimationUtil.animate(this, 1000);
        }

        @Override
        protected void onStop() {
            // If we're currently running the entrance animation - cancel it
            ActivitySplitAnimationUtil.cancel();

            super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        }



//    @Override
//    public void onBackPressed() {
//        ActivitySplitAnimationUtil.givikuna();
//        ActivitySplitAnimationUtil.act = this.getParent();
//       new Handler().postDelayed(new Runnable() {
//           @Override
//           public void run() {
//               this.super.onBackPressed();
//
//           }
//       }, 5000);
//    }
}