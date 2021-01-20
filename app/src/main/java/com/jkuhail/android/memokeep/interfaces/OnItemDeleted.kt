package com.jkuhail.android.memokeep.interfaces

import android.view.View

interface OnItemDeleted {
    fun onItemDeleted(v: View?, position: Int, id: Int)
}