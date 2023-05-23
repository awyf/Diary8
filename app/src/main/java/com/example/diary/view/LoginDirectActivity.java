package com.example.diary.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diary.MainActivity;
import com.example.diary.R;
import com.example.diary.utils.FileUtils;
import com.example.diary.utils.Util;

public class LoginDirectActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_input_text;
    private Button btn_comeIn;
    private TextView tv_setPsw;
    private TextView userName;
    private static final String TAG = "Login2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_login);
        bindView();
    }

    private void bindView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        edit_input_text = findViewById(R.id.edit_login2_input_text);
        btn_comeIn = findViewById(R.id.btn_login2_comeIn);
        btn_comeIn.setOnClickListener(this);
        tv_setPsw = findViewById(R.id.tv_setPsw);
        tv_setPsw.setOnClickListener(this);
        userName= findViewById(R.id.edit_login2_input_text2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_setPsw:
                Intent setPsw_intent = new Intent(LoginDirectActivity.this, RegistActivity.class);
                startActivity(setPsw_intent);
//                overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
                break;
            case R.id.btn_login2_comeIn:
                String psw = edit_input_text.getText().toString().trim();
                String userNameStr = userName.getText().toString().trim();
                if (psw.isEmpty()) {
                    Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userNameStr.isEmpty()) {
                    Toast.makeText(this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean islogin= Util.Login(this,userNameStr,psw);
                if(islogin){
                    Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                    if("admin".equals(userNameStr)){
                        startActivity(new Intent(this, UserListActivity.class));
                    }else{
                        startActivity(new Intent(this, MainActivity.class));
                        LoginDirectActivity.this.finish();
                    }

                }else{
                    Util.show(this,"登陆失败");
                }
//                String readInfoByContext = FileUtils.readInfoByContext(this);
//                if (psw.equals(readInfoByContext)) {
//                    Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(this, MainActivity.class);
//                    startActivity(intent);
////                    overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
//                } else {
//                    Toast.makeText(this, "密码不正确!", Toast.LENGTH_SHORT).show();
//                }
                break;

        }
    }
}
