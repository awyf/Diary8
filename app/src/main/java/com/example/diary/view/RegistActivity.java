package com.example.diary.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.diary.R;
import com.example.diary.utils.Util;

public class RegistActivity extends Activity {
    EditText name;
    EditText pwd;
    //声明数据库帮助器的对象
//    private UserDBHelper userDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        name=findViewById(R.id.ed_login_user);
        pwd=findViewById(R.id.ed_login_password);
        findViewById(R.id.bt_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText().toString())||
                        TextUtils.isEmpty(pwd.getText().toString())) {
                    showDialog1();
                    //Toast.makeText(RegistActivity.this, "用户，密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    showDialog();//why
                }
                Util.regist(RegistActivity.this, name.getText().toString(),pwd.getText().toString());

            }
        });
    }

    public void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("注册成功");
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    public void showDialog1(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("用户，密码不能为空");
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    @Override
    protected void onStop() {
        super.onStop();
//        userDBHelper.closeLink();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
