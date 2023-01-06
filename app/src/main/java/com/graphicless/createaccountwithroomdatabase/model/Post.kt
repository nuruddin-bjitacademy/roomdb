package com.graphicless.createaccountwithroomdatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "post_table")
data class Post(
    @PrimaryKey(autoGenerate = true)
     val id:Int,
     val userName: String,
     val post: String,
    var image: ByteArray?
):Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (userName != other.userName) return false
        if (post != other.post) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + userName.hashCode()
        result = 31 * result + post.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}