package com.graphicless.createaccountwithroomdatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val dob: String?,
    val title: String?,
    val workCompany: String?,
    val workPlace: String?,
    val personalWebsite: String?,
    val followers: Int?,
    val connections: Int?,
    var profilePic: ByteArray?,
    var coverPic: ByteArray?
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userName != other.userName) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (dob != other.dob) return false
        if (title != other.title) return false
        if (workCompany != other.workCompany) return false
        if (workPlace != other.workPlace) return false
        if (personalWebsite != other.personalWebsite) return false
        if (followers != other.followers) return false
        if (connections != other.connections) return false
        if (profilePic != null) {
            if (other.profilePic == null) return false
            if (!profilePic.contentEquals(other.profilePic)) return false
        } else if (other.profilePic != null) return false
        if (coverPic != null) {
            if (other.coverPic == null) return false
            if (!coverPic.contentEquals(other.coverPic)) return false
        } else if (other.coverPic != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userName.hashCode()
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (dob?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (workCompany?.hashCode() ?: 0)
        result = 31 * result + (workPlace?.hashCode() ?: 0)
        result = 31 * result + (personalWebsite?.hashCode() ?: 0)
        result = 31 * result + (followers ?: 0)
        result = 31 * result + (connections ?: 0)
        result = 31 * result + (profilePic?.contentHashCode() ?: 0)
        result = 31 * result + (coverPic?.contentHashCode() ?: 0)
        return result
    }
}
