package com.example.diary.view;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.diary.R;


/**
 * @author exampleZh
 * @brief description
 * @date 2022-11-07
 */
public class LoadingDialog extends Dialog {

    private TextView tvLoading;
    private ImageView ivLoading;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId, String message) {
        super(context, themeResId);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.loading_dialog);
        tvLoading = findViewById(R.id.tv_loading_tx);
//        tvLoading.setVisibility(View.VISIBLE);
        tvLoading.setText(message);
        ivLoading = findViewById(R.id.iv_loading);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        ivLoading.startAnimation(animation);

//        getWindow().getAttributes().gravity = Gravity.CENTER;//居中显示
//        getWindow().getAttributes().dimAmount = 0.5f;//背景透明度  取值范围 0 ~ 1
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
