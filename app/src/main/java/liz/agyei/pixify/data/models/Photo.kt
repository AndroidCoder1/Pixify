package liz.agyei.pixify.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Photo")
data class Photo (@PrimaryKey val id: String, val title: String, var url: String = ""){

    fun setURL(url: String){
        this.url = url
    }
}