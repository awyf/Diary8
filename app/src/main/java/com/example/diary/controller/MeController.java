package com.example.diary.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.example.diary.R;
import com.example.diary.utils.BitmapUtils;
import com.example.diary.utils.FileUtils;
import com.example.diary.view.MeFragment;
import com.example.diary.view.SelectPicPopupWindow;

import java.io.File;

public class MeController {
    private static final int CHOOSE_PICTURE = 0;
    private static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;

    private Fragment mView;

    public static Uri getTempUri() {
        return tempUri;
    }

    protected static Uri tempUri;
    private SelectPicPopupWindow menuWindow;

    public MeController(MeFragment meFragment) {
        this.mView = meFragment;
    }

    // 为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.tv_take_pictures:
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File( mView.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image.jpg");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tempUri = FileProvider.getUriForFile(mView.getActivity().getApplicationContext(), "com.sunyard.mytest.fileprovider", file);
                    } else {
                        tempUri = Uri.fromFile(file);
                    }
                    Log.d("11111111", tempUri.toString());
                    // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    mView.startActivityForResult(openCameraIntent, TAKE_PICTURE);
                    break;
                case R.id.tv_open_album:
                    Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
                    openAlbumIntent.setType("image/*");
                    mView.startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 跳转应用系统设置界面
     * @param context
     */
    public void toSelfSetting(Context context) {

        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }

    /**
     * 弹出头像选择框
     */
    public void showSelectWindow() {
        menuWindow = new SelectPicPopupWindow(mView.getActivity(), itemsOnClick);
        // 获取当前窗口的像素
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mView.getActivity().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        // 弹窗为屏幕宽度的一半
        menuWindow.setWidth(dm.widthPixels);
        // 从底部显示
        menuWindow.showAtLocation(mView.getActivity().findViewById(R.id.layout_update), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0 ,0);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        mView.startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    public static void setToolbarVisibility(Activity activity) {
        View toolbar = activity.findViewById(R.id.toolbar);
        if (toolbar.getVisibility() != View.VISIBLE) {
            toolbar.setVisibility(View.VISIBLE);
        }
    }
}
