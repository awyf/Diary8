package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.diary.controller.MeController;
import com.example.diary.model.DiaryHelper;
import com.example.diary.utils.ActivityUtils;
import com.example.diary.view.AddDiaryFragment;
import com.example.diary.view.DiariesFragment;
import com.example.diary.view.MeFragment;
//主界面1
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFragment();
        initNavigationBottom();
    }
//底部导航
    @SuppressLint("ResourceAsColor")
    private void initNavigationBottom() {
        bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }
//初始化底部导航栏的。
// 首先通过findViewById()方法获取到布局文件中定义的底部导航栏控件，
// 然后调用setItemIconTintList()方法将图标的着色设置为null，这样可以保证图标的原本颜色不被改变。
// 最后设置底部导航栏的选中监听器，当用户点击某个选项时，会触发相应的回调方法。
    @Override//创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void initFragment() {//初始化Fragment
        DiariesFragment diariesFragment = getDiariesFragment();
        if (diariesFragment == null) {
            diariesFragment = new DiariesFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), diariesFragment, R.id.content);
        }
    }
//使用了ActivityUtils类中的addFragmentToActivity()方法来完成Fragment的添加。
    private DiariesFragment getDiariesFragment() {
        return (DiariesFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }
//方法，返回类型为DiariesFragment，方法名为getDiariesFragment()。
//调用getSupportFragmentManager()方法获取FragmentManager实例，然后调用findFragmentById()方法，传入参数R.id.content，来获取id为content的Fragment。
    private void initToolbar() {
        //设置顶部状态栏为透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }//这段代码的作用是初始化一个顶部工具栏，并将状态栏设置为透明。
    // 它找到了一个id为toolbar的控件，并将其设置为ActionBar，
    // 在顶部显示一个工具栏。
    // 这个工具栏可以包含一些常用的操作按钮，例如返回按钮、菜单按钮等。
//底部状态栏的点击
    private final BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.menu_diary:
                MeController.setToolbarVisibility(this);
                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new DiariesFragment(), R.id.content);
                break;
            case R.id.menu_me:
                findViewById(R.id.toolbar).setVisibility(View.GONE);
                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new MeFragment(), R.id.content);
                break;
            case R.id.menu_new:
                bottomNavigationView.setVisibility(View.GONE);
                MeController.setToolbarVisibility(this);
                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new AddDiaryFragment(), R.id.content);
                break;
        }
        return true;
    };
}