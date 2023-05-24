package com.example.diary.controller;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.oyoung.diary.YyApplication;
import com.oyoung.diary.R;
import com.oyoung.diary.model.DiaryHelper;
import com.oyoung.diary.utils.ActivityUtils;
import com.oyoung.diary.utils.Util;
import com.oyoung.diary.view.AddDiaryFragment;
import com.oyoung.diary.view.DiariesFragment;

public class AddDiaryController {
    private Fragment mView;
    private static final String TAG = "AddDiaryController";
    private DiaryHelper diaryHelper;

    public AddDiaryController(@NonNull AddDiaryFragment addDiaryFragment) {
        diaryHelper = DiaryHelper.getInstance(YyApplication.get());
        mView = addDiaryFragment;
        mView.setHasOptionsMenu(true);
    }

    public void addDiaryToRepository(String title, String desc,String time, String emo) {
        if (title.isEmpty() || desc.isEmpty()) {
            showMessage(YyApplication.get().getString(R.string.add_failed));
            return;
        }
        diaryHelper.insert(title, desc,time,emo);
        showMessage(YyApplication.get().getString(R.string.add_success));
    }

    private void showMessage(String message) {
        Toast.makeText(mView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void closeWriteDiary(FragmentManager fragmentManager, Fragment fragment) {
        ActivityUtils.removeFragmentTOActivity(fragmentManager, fragment);
        ActivityUtils.addFragmentToActivity(fragmentManager, new DiariesFragment(), R.id.content);
    }

    public void setNavigationVisibility() {
        View navigation_bottom = mView.getActivity().findViewById(R.id.navigation_bottom);
        if (navigation_bottom.getVisibility() != View.VISIBLE) {
            navigation_bottom.setVisibility(View.VISIBLE);
        }
    }

    public void changeFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        if (!view.requestFocus()) {
            Log.e(TAG, "changeFocus: Error!");
            return;
        }
        if (view instanceof EditText) {
            InputMethodManager imm = (InputMethodManager) YyApplication.get().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }


}
