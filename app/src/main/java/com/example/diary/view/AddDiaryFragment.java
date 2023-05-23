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

public class AddDiaryFragment extends Fragment implements View.OnClickListener {
    private AddDiaryController mController;
    private View edit_layout;
    private Button btn_confirm;
    private EditText edit_title;
    private EditText edit_desc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = new AddDiaryController(this);
    }

    private void initView(View view) {
        btn_confirm = view.findViewById(R.id.add_diary_confirm);
        btn_confirm.setOnClickListener(this);
        edit_title = view.findViewById(R.id.edit_add_title);
        edit_desc = view.findViewById(R.id.edit_add_desc);
        edit_layout = view.findViewById(R.id.edit_layout);
        edit_layout.setOnClickListener(this);
    }



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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_diary_confirm:
                mController.addDiaryToRepository(edit_title.getText().toString().trim(), edit_desc.getText().toString().trim());
                mController.setNavigationVisibility();
                mController.closeWriteDiary(getActivity().getSupportFragmentManager(), this);
                break;
            case R.id.edit_layout:
                mController.changeFocus(edit_desc);
                break;
        }
    }
}
