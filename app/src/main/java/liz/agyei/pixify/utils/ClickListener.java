package liz.agyei.pixify.utils;

import android.view.View;

import liz.agyei.pixify.data.models.Photo;
import liz.agyei.pixify.viewmodel.MainActivityViewModel;

public interface ClickListener {
    void photoClicked(Photo photo, View view);
    void bookmarkClicked(MainActivityViewModel model, Photo photo);
}
