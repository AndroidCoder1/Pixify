package liz.agyei.pixify.data.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import liz.agyei.pixify.data.models.Photo

@Dao
interface PhotosDao {

    @Query("Select * from Photo")
    fun getPhotos() : List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Query("SELECT EXISTS(SELECT * FROM Photo WHERE id == :id)")
    fun isBookmarked(id: String): Boolean

    @Update
    fun updatePhoto(photo: Photo)

    @Delete
    fun deletePhoto(photo: Photo)
}