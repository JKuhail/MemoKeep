package com.jkuhail.android.memokeep.helpers

import android.content.Context
import android.util.Log
import com.jkuhail.android.memokeep.models.Memo
import com.jkuhail.android.memokeep.models.MemoBook
import io.realm.Realm
import io.realm.Sort
import java.util.*

/**
 * Database class helper
 */
object DbHelper {
    /**
     * Memo functions
     */
    @JvmStatic
    fun saveMemo(memo: Memo, context: Context) {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync({ realm1: Realm -> realm1.copyToRealmOrUpdate(memo) },
                { Log.i(context.toString(), "Saved successfully") }
        ) { error: Throwable -> Log.i("Realm Error", error.toString()) }
    }

    @JvmStatic
    fun deleteMemo(id: Int, context: Context) {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1: Realm ->
            val rows = realm1.where(Memo::class.java)
                    .equalTo("id", id)
                    .findAll()
            rows.deleteAllFromRealm()
        }
    }

    //for deleting memos after deleting a memoBook
    @JvmStatic
    fun deleteMemos(memoBookId: Int, context: Context) {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1: Realm ->
            val rows = realm1.where(Memo::class.java)
                    .equalTo("memoBookId", memoBookId)
                    .findAll()
            rows.deleteAllFromRealm()
        }
    }

    @JvmStatic
    fun retrieveMemos(context: Context): ArrayList<Memo> {
        val result = ArrayList<Memo>()
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1: Realm ->
            val memos = realm1.where(Memo::class.java)
                    .sort("id", Sort.DESCENDING)
                    .equalTo("isArchive", false)
                    .findAll()
            result.addAll(memos)
        }
        return result
    }

    @JvmStatic
    fun retrieveMemoBookMemos(context: Context, id: Int): ArrayList<Memo> {
        val result = ArrayList<Memo>()
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1: Realm ->
            val memos = realm1.where(Memo::class.java)
                    .sort("id", Sort.DESCENDING)
                    .equalTo("isArchive", false)
                    .equalTo("memoBookId", id)
                    .findAll()
            result.addAll(memos)
        }
        return result
    }

    @JvmStatic
    fun retrieveStarredMemos(context: Context): ArrayList<Memo> {
        val result = ArrayList<Memo>()
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1: Realm ->
            val memos = realm1.where(Memo::class.java)
                    .equalTo("isImportance", true)
                    .sort("id", Sort.DESCENDING)
                    .findAll()
            result.addAll(memos)
        }
        return result
    }

    fun findMemo(id: Int, context: Context): Memo? {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        return realm.where(Memo::class.java)
                .equalTo("id", id)
                .findFirst()
    }

    fun starredMemoExist(id: Int, context: Context): Boolean {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        val memo = realm.where(Memo::class.java)
                .equalTo("id", id)
                .equalTo("isImportance", true)
                .findFirst()
        return memo != null
    }

    @JvmStatic
    fun incrementMemoId(context: Context): Int {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        val currentIdNum = realm.where(Memo::class.java).max("id")
        return if (currentIdNum != null) currentIdNum.toInt() + 1 else 0
    }

    /**
     * MemoBook functions
     */
    @JvmStatic
    fun saveMemoBook(memoBook: MemoBook, context: Context) {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync({ realm1: Realm -> realm1.copyToRealmOrUpdate(memoBook) },
                { Log.i(context.toString(), "Saved successfully") }
        ) { error: Throwable -> Log.i("Realm Error", error.toString()) }
    }

    @JvmStatic
    fun deleteMemoBook(id: Int, context: Context) {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1: Realm ->
            val rows = realm1.where(MemoBook::class.java)
                    .equalTo("id", id)
                    .findAll()
            rows.deleteAllFromRealm()
        }
    }

    @JvmStatic
    fun retrieveMemoBooks(context: Context): ArrayList<MemoBook> {
        val result = ArrayList<MemoBook>()
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1: Realm ->
            val memoBooks = realm1.where(MemoBook::class.java)
                    .sort("id", Sort.DESCENDING)
                    .findAll()
            result.addAll(memoBooks)
        }
        return result
    }

    @JvmStatic
    fun findMemoBook(id: Int, context: Context): MemoBook? {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        return realm.where(MemoBook::class.java)
                .equalTo("id", id)
                .findFirst()
    }

    @JvmStatic
    fun incrementMemoBookId(context: Context): Int {
        Realm.init(context)
        val realm = Realm.getDefaultInstance()
        val currentIdNum = realm.where(MemoBook::class.java).max("id")
        return if (currentIdNum != null) currentIdNum.toInt() + 1 else 0
    }
}