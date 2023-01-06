package com.graphicless.createaccountwithroomdatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "auth_table")
data class Authentication(
    @PrimaryKey
     val userName: String,
     val password: String
)
