package com.jkuhail.android.memokeep.models

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MemoBook (
    @PrimaryKey
    var id: Int = 0,
    var name: String? = null,
    var date: String? = null,
) : RealmObject(), Parcelable {
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<MemoBook> {
            override fun createFromParcel(parcel: Parcel) = MemoBook(parcel)
            override fun newArray(size: Int) = arrayOfNulls<MemoBook>(size)
        }
    }

    private constructor(parcel: Parcel) : this(
            id = parcel.readInt(),
            name = parcel.readString(),
            date = parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(date)
    }

    override fun describeContents() = 0
}