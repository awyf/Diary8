package com.example.diary.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.example.diary.diaryApplication;
import com.example.diary.R;
import com.example.diary.controller.MeController;
import com.example.diary.controller.UserAdapter;
import com.example.diary.utils.BitmapUtils;
import com.example.diary.utils.FileUtils;
import com.example.diary.utils.GlideUtils;
import com.example.diary.utils.OnClick;
import com.example.diary.utils.PermissionUtils;
import com.example.diary.utils.Util;

import java.io.File;
import static android.app.Activity.RESULT_OK;

public class MeFragment extends Fragment implements View.OnClickListener {
    private static final int CHOOSE_PICTURE = 0;
    private static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;

    private TextView tv_update, tv_setting,tv_password;
    private ImageView imageView;
    private ShapeableImageView shapeableImageView;
    private String imaUrl = "http://pic2.zhimg.com/v2-696b347aa5b02a943706d5de13dc6ec1_r.jpg";
    private MeController meController;
    private boolean isShow = true;
    View tv_helpPhone;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meController = new MeController(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        imageView = root.findViewById(R.id.image_me);
        tv_update = root.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        tv_setting = root.findViewById(R.id.tv_setting);
        tv_setting.setOnClickListener(this);
        tv_password = root.findViewById(R.id.tv_password);
        shapeableImageView = root.findViewById(R.id.iv_avatar);
        tv_helpPhone=root.findViewById(R.id.tv_helpPhone);
        tv_helpPhone.setOnClickListener(this);
        shapeableImageView.setOnClickListener(this);
        initAvatarView();
        setTouch();
    }

    @SuppressLint("ClickableViewAccessibility")

    private void setTouch() {
        tv_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = tv_password.getCompoundDrawables()[2];
                if (drawable == null) {
                    return false;
                }
                if (tv_password.getWidth() - event.getX() < drawable.getBounds().width()) {
                    if (isShow) {
                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable on_drawable = diaryApplication.get().getDrawable(R.drawable.eye_on);
                        tv_password.setCompoundDrawablesWithIntrinsicBounds(null, null, on_drawable, null);
                        String password = FileUtils.readInfoByContext(getActivity());
                        tv_password.setText(password);
                        isShow = false;
                    } else {
                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable off_drawable = diaryApplication.get().getDrawable(R.drawable.eye_off);
                        tv_password.setCompoundDrawablesWithIntrinsicBounds(null, null, off_drawable, null);
                        tv_password.setText("********");
                        isShow = true;
                    }
                }
                return false;
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_helpPhone:{
                Util.showAlert(getActivity(), "拔打电话 400 600 000 ？", new OnClick() {
                    @Override
                    public void onDo() {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + "400 600 000");
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
                break;
            }
            case R.id.tv_update:
                GlideUtils.getBitmap(getActivity(), imaUrl, new GlideUtils.GlideLoadBitmapCallback() {
                    @Override
                    public void getBitmapCallback(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
                break;
            case R.id.tv_setting://第一个弹窗
                Util.showAlert(getActivity(), "前往设置页？", new OnClick() {
                    @Override
                    public void onDo() {
                        meController.toSelfSetting(getActivity());
                    }
                });

                break;
            case R.id.iv_avatar:
                PermissionUtils.checkPermission(getActivity());
                if (PermissionUtils.isPermissionGranted(getActivity())) {
                    meController.showSelectWindow();
                }
                break;
        }
    }

    public void initAvatarView() {
        File roundImageFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "roundImage.jpg");
        // 该路径为/storage/emulated/0/Android/data/yourPackageName/files/Picture
        if (roundImageFile.exists()) {
            shapeableImageView.setImageBitmap(BitmapUtils.getImg(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(), "roundImage"));
        }
    }

    @Override    
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    meController.startPhotoZoom(meController.getTempUri()); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    meController.startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    @Override   //第二个弹窗
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission","授权失败！");
                    // 授权失败，退出应用
                    getActivity().finish();
                    return;
                }
            }
        }
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data 图片
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            BitmapUtils.storeImageToSDCARD(photo, "roundImage", getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
//            photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            shapeableImageView.setImageBitmap(photo);
        }
    }

}
