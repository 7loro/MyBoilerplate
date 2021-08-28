package com.casper.myboilerplate.todo.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(val id: Int? = null, val title: String, val desc: String) : Parcelable
