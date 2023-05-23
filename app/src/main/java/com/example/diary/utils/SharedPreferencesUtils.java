package com.example.diary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.collection.SimpleArrayMap;

import com.example.diary.diaryApplication;

public final class SharedPreferencesUtils {
    private static final SimpleArrayMap<String, SharedPreferencesUtils> mCaches = new SimpleArrayMap<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferencesUtils(final String spName, final int mode) {
        mSharedPreferences = diaryApplication.get().getSharedPreferences(spName, mode);
    }

    public static SharedPreferencesUtils getInstance(String spName) {
        SharedPreferencesUtils utils = mCaches.get(spName);
        if (utils == null) {
            utils = new SharedPreferencesUtils(spName, Context.MODE_PRIVATE);
        }
        return utils;
    }

    public void put(final String key, final String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String get(final String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void remove(final String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
}
