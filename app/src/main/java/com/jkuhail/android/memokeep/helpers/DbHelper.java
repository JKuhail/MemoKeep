package com.jkuhail.android.memokeep.helpers;

import android.content.Context;
import android.util.Log;

import com.jkuhail.android.memokeep.models.Memo;
import com.jkuhail.android.memokeep.models.MemoBook;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Database class helper
 */
public class DbHelper {


    /**
     * Memo functions
     */

    public static void saveMemo(final Memo memo, final Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 -> realm1.copyToRealmOrUpdate(memo),
                () -> Log.i(context.toString(), "Saved successfully"),
                error -> Log.i("Realm Error", error.toString()));
    }

    public static void deleteMemo(final int id, final Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<Memo> rows = realm1.where(Memo.class).equalTo("id", id).findAll();
            rows.deleteAllFromRealm();
        });
    }

    public static ArrayList<Memo> retrieveMemos(Context context) {
        ArrayList<Memo> result = new ArrayList<>();
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<Memo> memos = realm1.where(Memo.class).sort("id", Sort.DESCENDING).findAll();
            result.addAll(memos);
        });
        return result;
    }

    public static ArrayList<Memo> retrieveStarredMemos(Context context) {
        ArrayList<Memo> result = new ArrayList<>();
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<Memo> memos = realm1.where(Memo.class).equalTo("importance", true).sort("id", Sort.DESCENDING).findAll();
            result.addAll(memos);
        });
        return result;
    }

    public static Memo findMemo(final int id, final Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Memo.class).equalTo("id", id).findFirst();
    }

    public static int incrementMemoId(Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(Memo.class).max("id");
        return currentIdNum != null ? currentIdNum.intValue() + 1 : 0;
    }


    /**
     * MemoBook functions
     */

    public static void saveMemoBook(final MemoBook memoBook, final Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 -> realm1.copyToRealmOrUpdate(memoBook),
                () -> Log.i(context.toString(), "Saved successfully"),
                error -> Log.i("Realm Error", error.toString()));
    }

    public static void deleteMemoBook(final int id, final Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<MemoBook> rows = realm1.where(MemoBook.class).equalTo("id", id).findAll();
            rows.deleteAllFromRealm();
        });
    }

    public static ArrayList<MemoBook> retrieveMemoBooks(Context context) {
        ArrayList<MemoBook> result = new ArrayList<>();
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<MemoBook> memoBooks = realm1.where(MemoBook.class).sort("id", Sort.DESCENDING).findAll();
            result.addAll(memoBooks);
        });
        return result;
    }

    public static MemoBook findMemoBook(final int id, final Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        return realm.where(MemoBook.class).equalTo("id", id).findFirst();
    }

    public static int incrementMemoBookId(Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(MemoBook.class).max("id");
        return currentIdNum != null ? currentIdNum.intValue() + 1 : 0;
    }
}
