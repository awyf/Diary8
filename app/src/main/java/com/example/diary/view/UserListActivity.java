package com.example.diary.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.R;
import com.example.diary.controller.UserAdapter;
import com.example.diary.model.UserInfo;
import com.example.diary.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends Activity {
    RecyclerView recycleView;
    UserAdapter mAdapter;
    List<UserInfo> mUserInfoList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_diaries);
        findViewById(R.id.atitle).setVisibility(View.VISIBLE);
        findViewById(R.id.adminTab).setVisibility(View.VISIBLE);
        recycleView= findViewById(R.id.diaries_list);
        mUserInfoList=Util.getAll(this);
        mAdapter=new UserAdapter(this,mUserInfoList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        recycleView.setAdapter(mAdapter);
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycleView.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
//fhdg