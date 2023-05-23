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

    private void showDeleteDialog(Diary data) {
        new AlertDialog.Builder(mView.getContext()).setMessage(diaryApplication.get().getString(R.string.dialog_delete) + data.getTitle())
                .setPositiveButton(diaryApplication.get().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                diaryHelper.delete(data.getId());
                                loadDiaries();
                            }
                        })
                .setNegativeButton(diaryApplication.get().getString(R.string.cancel), null).show();
    }

    private void showDetailDiary(final Diary diary) {

    }


    private void showInputDialog(final Diary data) {
        final EditText editText = new EditText(mView.getContext());
        editText.setText(data.getDescription());
        new AlertDialog.Builder(mView.getContext()).setTitle(data.getTitle())
                .setView(editText)
                .setPositiveButton(diaryApplication.get().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                diaryHelper.modify(data.getId(), "", editText.getText().toString());
                                loadDiaries();
                            }
                        })
                .setNegativeButton(diaryApplication.get().getString(R.string.cancel), null).show();
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
