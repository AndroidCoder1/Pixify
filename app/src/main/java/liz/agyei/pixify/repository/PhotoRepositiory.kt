package liz.agyei.pixify.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Maybe
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import liz.agyei.pixify.data.PixifyDatabase
import liz.agyei.pixify.data.api.FlickrAPI
import liz.agyei.pixify.data.interfaces.PhotosDao
import liz.agyei.pixify.data.models.Photo


class PhotoRepository(var api: FlickrAPI, application: Application) {

    private val photoDao: PhotosDao


    fun getAllPhotos(tag: String, page: Int, perPage: Int): Observable<List<Photo>> {
        return api.getPhotosWithURL(tag, page, perPage)
    }

    fun isBookMarked(id: String) : Boolean{
        return photoDao.isBookmarked(id)
    }

    fun insert(photo: Photo) {
        Completable.fromAction { photoDao.insertPhoto(photo) }.subscribe()
    }

    fun delete(photo: Photo) {
        Completable.fromAction { photoDao.deletePhoto(photo) }.subscribe()
    }

    init {
        val database: PixifyDatabase = PixifyDatabase.getDatabase(application)
        photoDao = database.photosDao()
    }
}