package liz.agyei.pixify.data.models

import androidx.room.Entity

@Entity(tableName = "Photo")
data class Photo (val id: String, val title: String, var url: String = "", var bookmark: Boolean = false){

    fun setURL(url: String){
        this.url = url
    }
}