package com.jkuhail.android.memokeep.models


import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Memo (
    @PrimaryKey
    var id: Int = 0,
    var title: String? = null,

    var content: String? = null,
    var date: String? = null,
    var memoBookId: Int = 0,
    var isImportance: Boolean = false,
    var isArchive: Boolean = false,
    var color: Int = 0,

) : RealmObject(), Parcelable {

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Memo> {
            override fun createFromParcel(parcel: Parcel) = Memo(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Memo>(size)
        }

        /**
         * The next two functions enable boolean parcelable without restrict the used api
         * (`writeBoolean & readBoolean` required minimum api 29)
         */
        fun Parcel.writeBolean(flag: Boolean) {
            when(flag) {
                true -> writeInt(1)
                false -> writeInt(0)
            }
        }

        fun Parcel.readBolean(): Boolean {
            return when(readInt()) {
                1 -> true
                else -> false
            }
        }
    }

    private constructor(parcel: Parcel) : this(
            id = parcel.readInt(),
            memoBookId = parcel.readInt(),
            title = parcel.readString(),
            content = parcel.readString(),
            date = parcel.readString(),
            isImportance = parcel.readBolean(),
            isArchive = parcel.readBolean(),
            color = parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(memoBookId)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(date)
        parcel.writeBolean(isImportance)
        parcel.writeBolean(isArchive)
        parcel.writeInt(color)
    }

    override fun describeContents() = 0


}