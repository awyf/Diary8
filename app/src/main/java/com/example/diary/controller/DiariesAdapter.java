package com.example.diary.controller;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diary.model.Diary;

import java.util.List;
//更新
public class DiariesAdapter extends RecyclerView.Adapter<DiaryHolder> {
    private List<Diary> mDiaries;
    private OnLongClickListener<Diary> mOnLongClickListener;
    private OnClickListener<Diary> mOnClickListener;
    public DiariesAdapter(List<Diary> diaries) {
        mDiaries = diaries;
    }

    public void update(List<Diary> diaries) {
        mDiaries = diaries;
        notifyDataSetChanged();
    }
//这是一个RecyclerView的适配器类，用于将数据源中的Diary对象绑定到RecyclerView的ItemView上。
// 其中，mDiaries是数据源，mOnLongClickListener和mOnClickListener是长按和点击事件的监听器。
// update方法用于更新数据源并刷新RecyclerView。
    public void setOnLongClickListener(OnLongClickListener<Diary> onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }

    public void setOnClickListener(OnClickListener<Diary> onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public DiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiaryHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryHolder holder, int position) {
        final Diary diary = mDiaries.get(position);
        holder.onBindView(diary);
        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return mOnLongClickListener != null && mOnLongClickListener.onLongClick(view, diary);
            }
        });
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(view, diary);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiaries.size();
    }

    public interface OnLongClickListener<T> {
        boolean onLongClick(View v, T data);
    }

    public interface OnClickListener<T> {
        void onClick(View v, T data);
    }


}
