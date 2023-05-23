package com.example.diary.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;


import com.example.diary.model.UserDBHelper;
import com.example.diary.model.UserInfo;

import java.util.List;

public class Util {
    //声明数据库帮助器的实例
    private static UserDBHelper userDBHelper;

    public Util(Context c) { //获得数据库帮助器的实例
        if (userDBHelper == null) {
            userDBHelper = UserDBHelper.getInstance(c, 1);
        }
    }
    private static void getInstance(Context c) { //获得数据库帮助器的实例
        if (userDBHelper == null) {
            userDBHelper = UserDBHelper.getInstance(c, 1);
        }
    }

    @SuppressLint("DefaultLocale")
    public static  boolean Login(Context c,String name,String pwd ) {
        getInstance(c);
        //打开数据库帮助器的读连接
        userDBHelper.openReadLink();
        // 执行数据库帮助器的查询操作
        List<UserInfo> userInfoList = userDBHelper.query("1=1");
        String desc = String.format("数据库查询到%d条记录，详情如下：", userInfoList.size());
        boolean isLogin=false;
        for (int i = 0; i < userInfoList.size(); i++) {
            UserInfo userInfo = userInfoList.get(i);
            desc = String.format("%s\n第%d条记录信息如下:", desc, i + 1);
            desc = String.format("%s\n　姓名为%s", desc, userInfo.name);
            desc = String.format("%s\n　年龄为%d", desc, userInfo.age);
            desc = String.format("%s\n　身高为%d", desc, userInfo.height);
            desc = String.format("%s\n　体重为%f", desc, userInfo.weight);
            desc = String.format("%s\n　婚否为%b", desc, userInfo.married);
            desc = String.format("%s\n　密码", desc, userInfo.password);
            if(!isLogin&&name.equals(userInfo.name)&&pwd.equals(userInfo.password)){
                isLogin=true;
            }
        }
        if (userInfoList.size() <= 0) {
            desc = "数据库查询到的记录为空";
        }
        userDBHelper.closeLink();
        return isLogin;
    }
    public static void log(Object o){
        Log.i("tt",String.valueOf(o));
    }

    public static void regist(Context c,String name, String pwd) {
        getInstance(c);
        userDBHelper.openReadLink();
        UserInfo userInfo = new UserInfo();
        userInfo.password = pwd;
        userInfo.name = name;
        userDBHelper.insert(userInfo);
        userDBHelper.closeLink();
        show(c,"注册成功");
    }
    public static List<UserInfo> getAll(Context c) {
        List<UserInfo> data;
        getInstance(c);
        userDBHelper.openReadLink();
        data=userDBHelper.query(null);
        userDBHelper.closeLink();
    return data;
    }
    public static void delete(Context c,String id) {
        getInstance(c);
        userDBHelper.openReadLink();
        userDBHelper.deleteById(id);
        userDBHelper.closeLink();
    }
    public static void show(Context c, String name) {
        Toast.makeText(c, name, Toast.LENGTH_SHORT).show();
    }
    public static  void showAlert(Context c,String msg,OnClick onClick){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("提示");
        builder.setMessage(msg);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);
        //设置正面按钮
        if(onClick!=null){
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(onClick!=null){
                        onClick.onDo();
                    }
                    dialog.dismiss();
                }
            });
        }

        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(c, "你点击了不是", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
            }
        });
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        //显示对话框
        dialog.show();
    }
}
