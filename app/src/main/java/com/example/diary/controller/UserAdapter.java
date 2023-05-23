package com.example.diary.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.diary.R;
import com.example.diary.model.UserInfo;
import com.example.diary.utils.OnClick;
import com.example.diary.utils.Util;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final Context conext;
    private List<UserInfo> mUserInfoList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View UserInfoView;
        Button delete;
        TextView username;
        TextView pwd;

        public ViewHolder(@NonNull View view) {
            super(view);
            UserInfoView = view;
            delete = view.findViewById(R.id.delete);
            username = view.findViewById(R.id.username);
            pwd = view.findViewById(R.id.pwd);
        }
    }

    public UserAdapter(Context conext, List<UserInfo> mUserInfoList) {
        this.mUserInfoList = mUserInfoList;
        this.conext=conext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.UserInfoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = viewHolder.getAdapterPosition();
//                UserInfo UserInfo = mUserInfoList.get(position);
//                Toast.makeText(v.getContext(), "你点击了这个布局", Toast.LENGTH_SHORT).show();
//
//            }
//        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                UserInfo UserInfo = mUserInfoList.get(position);
                if("admin".equals(UserInfo.getName())){
                    Util.showAlert(conext, "不能删除管理员!", null);
                    return;
                }
                Util.showAlert(conext, "确定删除？", new OnClick() {
                    @Override
                    public void onDo() {
                        mUserInfoList.remove(position);
                        Util.delete(conext, UserInfo.getRowid() + "");
                        UserAdapter.this.notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "删除用户'" + UserInfo.getName() + "'成功", Toast.LENGTH_SHORT).show();
                    }
                });
               }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInfo UserInfo = mUserInfoList.get(position);
        holder.pwd.setText(UserInfo.getPassword());
        holder.username.setText(UserInfo.getName());
    }
    @Override
    public int getItemCount(){
        return mUserInfoList.size();
    }
}
