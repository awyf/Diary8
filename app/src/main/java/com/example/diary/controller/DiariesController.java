package com.example.diary.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.diaryApplication;
import com.example.diary.R;
import com.example.diary.model.Diary;
import com.example.diary.model.DiaryHelper;
import com.example.diary.utils.ActivityUtils;
import com.example.diary.view.AddDiaryFragment;
import com.example.diary.view.DiariesFragment;

import java.util.ArrayList;
import java.util.List;
//日记列表的逻辑控制代码
//
public class DiariesController {
    private Fragment mView;
    private DiariesAdapter mListAdapter;
    private DiaryHelper diaryHelper;
    public DiariesController(@NonNull DiariesFragment diariesFragment) {
        diaryHelper = DiaryHelper.getInstance(diaryApplication.get());
        mView = diariesFragment;
        mView.setHasOptionsMenu(true);
        initAdapter();
    }

    private void initAdapter() {
        mListAdapter = new DiariesAdapter(new ArrayList<Diary>());
        mListAdapter.setOnLongClickListener(new DiariesAdapter.OnLongClickListener<Diary>() {
            @Override
            public boolean onLongClick(View v, Diary data) {
                showDeleteDialog(data);
                return false;
            }
        });
        mListAdapter.setOnClickListener(new DiariesAdapter.OnClickListener<Diary>() {
            @Override
            public void onClick(View v, Diary data) {
                showInputDialog(data);
            }
        });
    }
//这段代码是一个Android应用程序中的Java代码，用于显示一个对话框，询问用户是否要删除一个日记。
// 如果用户点击“确定”按钮，它将从数据库中删除该日记，并显示一个新的对话框，
// 告诉用户删除成功。如果用户点击“取消”按钮，则不会执行任何操作。

    private void showDeleteDialog(Diary data) {
        new AlertDialog.Builder(mView.getContext()).setMessage("是否删除" + data.getTitle())
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                diaryHelper.delete(data.getId());
                                loadDiaries();
                                new AlertDialog.Builder(mView.getContext()).setMessage("删除成功！")
                                        .setPositiveButton("确定",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                })
                                        .show();
                            }
                        })
                .setNegativeButton("取消", null).show();
    }

    private void showDetailDiary(final Diary diary) {

    }
//修改弹窗

    private void showInputDialog(final Diary data) {
        final EditText editText = new EditText(mView.getContext());
        editText.setText(data.getDescription());
        new AlertDialog.Builder(mView.getContext()).setTitle(data.getTitle())
                .setView(editText)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                diaryHelper.modify(data.getId(), "", editText.getText().toString());
                                loadDiaries();

                                new AlertDialog.Builder(mView.getContext()).setMessage("修改成功！")
                                        .setPositiveButton("确定",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                })
                                        .show();
                            }
                        })
                .setNegativeButton("确定", null).show();
    }

    public void setDiariesList(RecyclerView recycleView) {
        recycleView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        recycleView.setAdapter(mListAdapter);
        recycleView.addItemDecoration(new DividerItemDecoration(mView.getContext(), DividerItemDecoration.VERTICAL));
        recycleView.setItemAnimator(new DefaultItemAnimator());
    }

    public void loadDiaries() {
        processDiaries(diaryHelper.query());
    }

    public void gotoWriteDiary(FragmentManager fragmentManager, Fragment fragment) {
        new AlertDialog.Builder(mView.getContext())
                .setMessage(diaryApplication.get().getString(R.string.alert))
                .setPositiveButton(diaryApplication.get().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityUtils.removeFragmentTOActivity(fragmentManager, fragment);
                                ActivityUtils.addFragmentToActivity(fragmentManager, new AddDiaryFragment(), R.id.content);
                            }
                        })
                .setNegativeButton(diaryApplication.get().getString(R.string.cancel), null).show();

    }

    private void showError() {
        showMessage(mView.getString(R.string.error));
    }

    private void processDiaries(List<Diary> diaryList) {
        mListAdapter.update(diaryList);
    }

    private void showMessage(String message) {
        Toast.makeText(mView.getContext(), message, Toast.LENGTH_SHORT).show();
    }



}
