package liz.agyei.pixify.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import liz.agyei.pixify.data.models.Photo

@Dao
interface PhotosDao {

    @Query("Select * from Photo")
    fun getPhotos() : List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Query("SELECT bookmark FROM Photo WHERE id == :id")
    fun isBookmarked(id: String): Boolean

    @Update
    fun updatePhoto(photo: Photo)

    @Delete
    fun deletePhoto(photo: Photo)
}