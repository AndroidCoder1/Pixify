package liz.agyei.pixify.repository

import android.app.Application
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import liz.agyei.pixify.data.PixifyDatabase
import liz.agyei.pixify.data.api.FlickrAPI
import liz.agyei.pixify.data.interfaces.PhotosDao
import liz.agyei.pixify.data.models.Photo


class BookmarksRepositiory(application: Application) {

    private val photoDao: PhotosDao

    fun getBookmarkedPhotos(): Observable<List<Photo>> {
        return Observable.just(photoDao.getPhotos())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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