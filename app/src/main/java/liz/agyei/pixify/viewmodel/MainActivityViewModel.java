package liz.agyei.pixify.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import liz.agyei.pixify.data.api.FlickrAPI;
import liz.agyei.pixify.data.models.Photo;
import liz.agyei.pixify.repository.PhotoRepository;


public class MainActivityViewModel extends AndroidViewModel {

    private PhotoRepository repository;

    public MutableLiveData<Boolean> _isProgressShowing;


    public MainActivityViewModel (FlickrAPI api, Application application) {
        super(application);
        repository = new PhotoRepository(api, application);
    }

    public LiveData<Boolean> isBookmarked(String id){
        return repository.isBookMarked(id);
    }

    public Observable<List<Photo>> getAllPhotos(String tag, int page, int perPage) { return repository.getAllPhotos(tag, page, perPage); }

    public LiveData<Boolean> isProgressShowing(){
        return _isProgressShowing;
    }

    public void toggleBookMark(Photo photo){
        if (isBookmarked(photo.getId()).getValue()) {
            delete(photo);
        } else {
            insert(photo);
        }
    }
    public void insert(Photo photo) { repository.insert(photo); }

    public void delete(Photo photo) { repository.delete(photo); }

}