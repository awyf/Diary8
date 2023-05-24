package com.example.diary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.diary.R;
import com.example.diary.view.LoadingDialog;


public class GlideUtils {
    private static Context context;
    private static final String TAG = "GlideUtil";
    private static LoadingDialog loadingDialog;

    private static RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            Log.e(TAG, "网络访问失败,请检查是否开启网络和http许可");
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            Log.i(TAG, "网络访问成功,可以显示图片");
            return false;
        }
    };

    private static ImageViewTarget<Drawable> getImageViewTarget(final ImageView imageView) {
        final ImageViewTarget<Drawable>  imageViewTarget = new ImageViewTarget<Drawable>(imageView) {

            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                if (loadingDialog != null) {
                    loadingDialog.show();
                }
                Log.d(TAG, "开始加载图片");
            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                super.onResourceReady(resource, transition);
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                Log.d(TAG, "加载图片完成");
            }

            @Override
            protected void setResource(@Nullable Drawable resource) {
                imageView.setImageDrawable(resource);
            }
        };
        return imageViewTarget;
    }

    public static void init(Context context) {
        GlideUtils.context = context;
        Log.i(TAG, "Glide初始化成功");
    }

    public static void loadImage(String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public interface GlideLoadBitmapCallback{
        void getBitmapCallback(Bitmap bitmap);
    }

    public static void getBitmap(Context context, String uri, final GlideLoadBitmapCallback callback) {
        loadingDialog = new LoadingDialog(context, R.layout.loading_dialog, null);
        Glide.with(context).asBitmap().load(uri).centerCrop()
                .override(200, 200)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        if (loadingDialog != null) {
                            loadingDialog.show();
                        }
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (loadingDialog != null) {
                            loadingDialog.dismiss();
                        }
                        callback.getBitmapCallback(resource);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                });
    }



    public static void loadImageListener(String url, ImageView imageView, boolean needNetListener, boolean needResourceListener) {
        if (needResourceListener) {
            Glide.with(context).load(url).addListener(needNetListener ? requestListener : null).into(getImageViewTarget(imageView));
        } else {
            Glide.with(context).load(url).addListener(needNetListener ? requestListener : null).into(imageView);
        }
    }

    /**
     * 显示网络Url图片 附带加载网络监听和设置资源监听 显示加载弹窗
     * @param context 显示在哪个Activity/Fragment上
     * @param url  网络图片url
     * @param imageView 图片控件
     * @param needNetListener 是否需要网络监听
     * @param needResourceListener 是否需要设置资源监听
     */
    public static void loadImageListenerWithDialog(Context context, String url, ImageView imageView, boolean needNetListener, boolean needResourceListener) {
        loadingDialog = new LoadingDialog(context, R.layout.loading_dialog, null);
        if (needResourceListener) {
            Glide.with(context).load(url).addListener(needNetListener ? requestListener : null).into(getImageViewTarget(imageView));
        } else {
            Glide.with(context).load(url).addListener(needNetListener ? requestListener : null).into(imageView);
        }
    }
}
