package liz.agyei.pixify.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import liz.agyei.pixify.data.models.Photo;
import liz.agyei.pixify.repository.BookmarksRepositiory;


public class BookmarksViewModel extends AndroidViewModel {

    private BookmarksRepositiory repository;

    public BookmarksViewModel(Application application) {
        super(application);
        repository = new BookmarksRepositiory(application);
    }

    public List<Photo> getBookmarkedPhotos() { return repository.getBookmarkedPhotos(); }

    public void insert(Photo photo) { repository.insert(photo); }

    public void delete(Photo photo) { repository.delete(photo); }

}