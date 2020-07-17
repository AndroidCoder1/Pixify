package liz.agyei.pixify.data.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import liz.agyei.pixify.data.models.Photo

@Dao
interface PhotosDao {

    @Query("Select * from Photo")
    fun getPhotos() : List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Query("SELECT EXISTS(SELECT * FROM Photo WHERE id == :id)")
    fun isBookmarked(id: String): LiveData<Boolean>

    @Update
    fun updatePhoto(photo: Photo)

    @Delete
    fun deletePhoto(photo: Photo)
}