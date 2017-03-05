package com.example.gio.splitanimationapp;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;



public class ActivitySplitAnimationUtil {

    private static float mTopImageHeight;
    private static float mBottomImageHeight;

    public static Bitmap mBitmap = null;
    private static int[] mLoc1;
    private static int[] mLoc2;
    private static ImageView mTopImage;
    private static ImageView mBottomImage;
    private static AnimatorSet mSetAnim;
    public  static AppCompatActivity act;

    /**
     * Start a new Activity with a Split animation
     *
     * @param currActivity The current Activity
     * @param intent       The Intent needed tot start the new Activity
     * @param splitYCoord  The Y coordinate where we want to split the Activity on the animation. -1 will split the Activity equally
     */
    public static void startActivity(Activity currActivity, Intent intent, int splitYCoord) {

        // Preparing the bitmaps that we need to show
        prepare(currActivity, splitYCoord);

        currActivity.startActivity(intent);
        currActivity.overridePendingTransition(0, 0);
    }

    /**
     * Start a new Activity with a Split animation right in the middle of the Activity
     *
     * @param currActivity The current Activity
     * @param intent       The Intent needed tot start the new Activity
     */

    /**
     * Preparing the graphics on the destination Activity.
     * Should be called on the destination activity on Activity#onCreate() BEFORE setContentView()
     *
     * @param destActivity the destination Activity
     */
    public static void prepareAnimation(final Activity destActivity) {
        mTopImage = createImageView(destActivity, mBitmap, mLoc1);
        mBottomImage = createImageView(destActivity, mBitmap, mLoc2);
    }

    public static void prepareAnimation2(final Activity destActivity) {
        mTopImage = createImageView2(destActivity,  mLoc1);
        mBottomImage = createImageView2(destActivity, mLoc2);
    }

    /**
     * Start the animation the reveals the destination Activity
     * Should be called on the destination activity on Activity#onCreate() AFTER setContentView()
     *
     * @param destActivity the destination Activity
     * @param duration The duration of the animation
     * @param interpolator The interpulator to use for the animation. null for no interpulation.
     */
    public static void animate(final Activity destActivity, final int duration, final TimeInterpolator interpolator) {


        // Post this on the UI thread's message queue. It's needed for the items to be already measured
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                mSetAnim = new AnimatorSet();


                mTopImage.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mBottomImage.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mTopImageHeight=mTopImage.getHeight();
                mBottomImageHeight=mBottomImage.getHeight();
                mSetAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        clean(destActivity);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        clean(destActivity);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                // Animating the 2 parts away from each other
                Animator anim1 = ObjectAnimator.ofFloat(mTopImage, "translationY", mTopImage.getHeight() * -1);
                Animator anim2 = ObjectAnimator.ofFloat(mBottomImage, "translationY", mBottomImage.getHeight());

                if (interpolator != null) {
                    anim1.setInterpolator(interpolator);
                    anim2.setInterpolator(interpolator);
                }

                mSetAnim.setDuration(duration);
                mSetAnim.playTogether(anim1, anim2);
                mSetAnim.start();
            }
        });

    }

    public static void givikuna(){

//        mTopImage.animate().y(-200).setDuration(0).start();

        Animator anim1 = ObjectAnimator.ofFloat(mTopImage, "translationY",  -mTopImageHeight);
        Animator anim2 = ObjectAnimator.ofFloat(mBottomImage, "translationY", mBottomImageHeight);


            anim1.setInterpolator(new DecelerateInterpolator());
            anim2.setInterpolator(new DecelerateInterpolator());


//        mSetAnim.setDuration(0);
//        mSetAnim.playTogether(anim1, anim2);
//        mSetAnim.start();

        anim1 = ObjectAnimator.ofFloat(mTopImage, "translationY",  -mTopImageHeight, 0);
        anim2 = ObjectAnimator.ofFloat(mBottomImage, "translationY", mBottomImageHeight, 0);

        mSetAnim.setDuration(5000);
        mSetAnim.playTogether(anim1, anim2);
        mSetAnim.start();
    }







    /**
     * Start the animation that reveals the destination Activity
     * Should be called on the destination activity on Activity#onCreate() AFTER setContentView()
     *
     * @param destActivity the destination Activity
     * @param duration The duration of the animation
     */
    public static void animate(final Activity destActivity, final int duration) {
        animate(destActivity, duration, new DecelerateInterpolator());
    }

    /**
     * Cancel an in progress animation
     */
    public static void cancel() {
        if (mSetAnim != null)
            mSetAnim.cancel();
    }

    /**
     * Clean stuff
     *
     * @param activity The Activity where the animation is occurring
     */
    private static void clean(Activity activity) {
        if (mTopImage != null) {
            mTopImage.setLayerType(View.LAYER_TYPE_NONE, null);
            try {
                // If we use the regular removeView() we'll get a small UI glitch
                activity.getWindowManager().removeViewImmediate(mBottomImage);
            } catch (Exception ignored) {
            }
        }
        if (mBottomImage != null) {
            mBottomImage.setLayerType(View.LAYER_TYPE_NONE, null);
            try {
                activity.getWindowManager().removeViewImmediate(mTopImage);
            } catch (Exception ignored) {
            }
        }

//        mBitmap = null;
    }

    /**
     * Preparing the graphics for the animation
     *
     * @param currActivity the current Activity from where we start the new one
     * @param splitYCoord  The Y coordinate where we want to split the activity. -1 will split the activity equally
     */
    public static void prepare(Activity currActivity, int splitYCoord) {

        // Get the content of the activity and put in a bitmap
        View root = currActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        root.setDrawingCacheEnabled(true);
        mBitmap = root.getDrawingCache();

        // If the split Y coordinate is -1 - We'll split the activity equally
        splitYCoord = (splitYCoord != -1 ? splitYCoord : mBitmap.getHeight() / 2);


        if (splitYCoord > mBitmap.getHeight())
            throw new IllegalArgumentException("Split Y coordinate [" + splitYCoord + "] exceeds the activity's height [" + mBitmap.getHeight() + "]");

        // Set the location to put the 2 bitmaps on the destination activity
        mLoc1 = new int[]{0, splitYCoord, 240};
        mLoc2 = new int[]{splitYCoord, mBitmap.getHeight(), 240};   // aqaa shesacvleli
    }

    /**
     * Creating the an image, containing one part of the animation on the destination activity
     *
     * @param destActivity The destination activity
     * @param bmp          The Bitmap of the part we want to add to the destination activity
     * @param loc          The location this part should be on the screen
     * @return
     */
    private static ImageView createImageView(Activity destActivity, Bitmap bmp, int loc[]) {
        MyImageView imageView = new MyImageView(destActivity);
        imageView.setImageBitmap(bmp);
        imageView.setImageOffsets(bmp.getWidth(), loc[0], loc[1]);

        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP;
        windowParams.x = 0;
        windowParams.y = loc[2] + loc[0];
        windowParams.height = loc[1] - loc[0];
        windowParams.width = bmp.getWidth();
        windowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;
        destActivity.getWindowManager().addView(imageView, windowParams);

        return imageView;
    }


    private static ImageView createImageView2(Activity destActivity,  int loc[]) {
        MyImageView imageView = new MyImageView(destActivity);
        imageView.setImageBitmap(mBitmap);
        imageView.setImageOffsets(mBitmap.getWidth(), loc[0], loc[1]);

        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP;
        windowParams.x = 0;
        windowParams.y = loc[2] + loc[0];
        windowParams.height = loc[1] - loc[0];
        windowParams.width = mBitmap.getWidth();
        windowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;
        destActivity.getWindowManager().addView(imageView, windowParams);

        return imageView;
    }



    /**
     * MyImageView
     * Extended ImageView that draws just part of an image, base on start/end position
     */
    private static class MyImageView extends ImageView
    {
        private Rect mSrcRect;
        private Rect mDstRect;
        private Paint mPaint;

        public MyImageView(Context context)
        {
            super(context);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }

        /**
         * Setting the bitmap offests to control the visible area
         *
         * @param width		   The bitmap image
//         * @param bmp          The start Y position
//         * @param loc          The end Y position
         * @return
         */
        public void setImageOffsets(int width, int startY, int endY)
        {
            mSrcRect = new Rect(0, startY, width, endY);
            mDstRect = new Rect(0, 0, width, endY - startY);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            Bitmap bm = null;
            Drawable drawable = getDrawable();
            if (null != drawable && drawable instanceof BitmapDrawable)
            {
                bm = ((BitmapDrawable)drawable).getBitmap();
            }

            if (null == bm)
            {
                super.onDraw(canvas);
            }
            else
            {

                canvas.drawBitmap(bm, mSrcRect, mDstRect, mPaint);
            }
        }
    }
}