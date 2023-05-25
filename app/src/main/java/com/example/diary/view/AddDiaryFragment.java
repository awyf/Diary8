package com.example.diary.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem; 
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.diary.R;
import com.example.diary.controller.AddDiaryController;
import com.example.diary.utils.CollectionUtils;
import com.example.diary.utils.OnClick;
import com.example.diary.utils.Util;

//one

public class AddDiaryFragment extends Fragment implements View.OnClickListener {
    private AddDiaryController mController;
    private View edit_layout;
    private Button btn_confirm;
    private EditText edit_title;
    private EditText edit_desc;
    Spinner emo;
    //下拉列表控件
    TextView time;
    String timeStr="",emoStr="开心";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = new AddDiaryController(this);
    }

    private void initView(View view) {
        Date now=new Date();
        timeStr=now.getYear()+"年" +(now.getMonth()+1) + "月"+now.getDate()+ "日";
        btn_confirm = view.findViewById(R.id.add_diary_confirm);
        btn_confirm.setOnClickListener(this);
        edit_title = view.findViewById(R.id.edit_add_title);
        edit_desc = view.findViewById(R.id.edit_add_desc);
        edit_layout = view.findViewById(R.id.edit_layout);
        edit_layout.setOnClickListener(this);

        emo=view.findViewById(R.id.emo);
        time=view.findViewById(R.id.time);
        time.setOnClickListener(o->{
            show();
        });
        ArrayAdapter<CharSequence> job_adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_list, android.R.layout.simple_spinner_item);

        job_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emo.setAdapter(job_adapter);
        emo.setSelection(0);

        emo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                emo.setSelection(i);
                String item = (String) adapterView.getItemAtPosition(i);
                emoStr=item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                emo.setSelection(0);
            }
        });

    }

    private void show() {
        //获取当前年月日
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//当前年
        int month = calendar.get(Calendar.MONTH);//当前月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//当前日
        //new一个日期选择对话框的对象,并设置默认显示时间为当前的年月日时间.
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), mdateListener, year, month, day);
        dialog.show();
    }

    /**
     * 日期选择的回调监听
     */
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
            // TODO: 2017/8/17 这里有选择后的日期回调,根据具体要求写不同的代码,我就直接打印了
            timeStr=years+"年"  +monthOfYear+ "月"+dayOfMonth+ "日";
            time.setText(timeStr);
            Log.i("dd", "年" +years+ "月" +monthOfYear+ "日"+dayOfMonth);//这里月份是从0开始的,所以monthOfYear的值是0时就是1月.以此类推,加1就是实际月份了.
        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cancel, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cancel:
                mController.closeWriteDiary(getActivity().getSupportFragmentManager(), this);
                mController.setNavigationVisibility();
                return true;
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_diary, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //super 是 Java 中的一个关键字，指代父类，用于调用父类中的普通方法和构造方法
    //在 Java 中子类可以继承父类中所有可访问的数据域和方法，但不能继承父类中的构造方法，所以需要利用 super 来调用父类中的构造方法。

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_diary_confirm:
                if(edit_desc.getText().toString().trim().isEmpty()){
                    Util.showAlert(getActivity(), "内容不能为空", new OnClick() {
                        @Override
                        public void onDo() {

                        }
                    });
                    return;
                }
                else if (edit_title.getText().toString().trim().isEmpty()){
                    Util.showAlert(getActivity(), "标题不能为空", new OnClick() {
                        @Override
                        public void onDo() {

                        }
                    });
                    return;
                }
                else if (edit_title.getText().toString().trim().isEmpty()||edit_desc.getText().toString().trim().isEmpty()){
                    Util.showAlert(getActivity(), "请输入日记内容", new OnClick() {
                        @Override
                        public void onDo() {

                        }
                    });
                    return;
                }
                else {
                    Util.showAlert(getActivity(), "添加成功", new OnClick() {

                        public void onDo() {
                            mController.addDiaryToRepository(edit_title.getText().toString().trim(), edit_desc.getText().toString().trim(),timeStr.trim(),emoStr.trim());
                            mController.setNavigationVisibility();
                            mController.closeWriteDiary(getActivity().getSupportFragmentManager(), AddDiaryFragment.this);
                        }

                    });
                    break;
                }
            case R.id.edit_layout:
                mController.changeFocus(edit_desc);
                break;
        }
    }
}
