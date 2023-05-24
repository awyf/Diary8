package com.example.diary.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.R;
import com.example.diary.controller.DiariesController;

public class DiariesFragment extends Fragment {
    private DiariesController mController;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = new DiariesController(this);
    }
//创建了一个DiariesController对象
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_diaries, container, false);
        mController.setDiariesList(root.findViewById(R.id.diaries_list));
        return root;
    }
//在onCreateView()方法中将日记列表传递给它
    @Override
    //在onResume()方法中，它调用DiariesController的loadDiaries()方法来加载日记列表
    public void onResume() {
        super.onResume();
        mController.loadDiaries();
    }
}
