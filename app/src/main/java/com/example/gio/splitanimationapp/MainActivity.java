package com.example.gio.splitanimationapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static int actionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.activity_main);


//        TypedValue tv = new TypedValue();
//        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
//        {
//            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//        }



        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                ActivitySplitAnimationUtil.startActivity(MainActivity.this, new Intent(MainActivity.this, SecondActivity.class), y);
                return false;
            }
        });


    }

//    public void clickNext(View v){
////        View root = this.getWindow().getDecorView().findViewById(android.R.id.content);
////        root.setDrawingCacheEnabled(true);
////        Bitmap bmp = root.getDrawingCache();
////
////        Bitmap mBmp1 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), 300);
////        Bitmap mBmp2 = Bitmap.createBitmap(bmp, 0, 300, bmp.getWidth(), bmp.getHeight() - 300);
//
//
//    }

//    public boolean onTouch(View view, MotionEvent event) {
//
//    }
}
