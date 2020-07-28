package com.jkuhail.android.memokeep.helpers;

import android.content.Context;
import android.content.SharedPreferences;


public class SpHelper {

    public static void storeMemoId(Context context, int id){
        SharedPreferences sp = context.getSharedPreferences(Constants.SP_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constants.MEMO_ID, id);
        editor.apply();
    }

    public static void storeMemoBookId(Context context, int id){
        SharedPreferences sp = context.getSharedPreferences(Constants.SP_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constants.MEMO_BOOK_ID, id);
        editor.apply();
    }
}
