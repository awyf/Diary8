package com.example.diary;

import android.app.Application;

import com.example.diary.utils.GlideUtils;

public class diaryApplication extends Application {
    private static diaryApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        GlideUtils.init(getApplicationContext());
        INSTANCE = this;
    }

    public static diaryApplication get() {
        return INSTANCE;
    }
}
