package com.akshay.mypicsumapplication.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.akshay.mypicsumapplication.R;

public class CustomProgressDialog extends Dialog {

    private AppCompatImageView iv;

    private Runnable mTimeout = new Runnable() {
        @Override
        public void run() {
            try {
                setCancelable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public CustomProgressDialog(Context context) {
        this(context, R.mipmap.progress_loader);
    }

    public CustomProgressDialog(Context context, int resourceIdOfImage) {
        super(context, R.style.custom_progress_dialog_style);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv = new AppCompatImageView(context);
        iv.setImageResource(resourceIdOfImage);
        layout.addView(iv, params);
        addContentView(layout, params);
        iv.postDelayed(mTimeout, 1000 * 10);
    }

    @Override
    public void show() {
        super.show();
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(2500);
        iv.setAnimation(anim);
        iv.startAnimation(anim);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        iv.removeCallbacks(mTimeout);
    }
}