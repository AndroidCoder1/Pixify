package liz.agyei.pixify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import liz.agyei.pixify.data.api.FlickrAPI;
import liz.agyei.pixify.data.models.Photo;
import liz.agyei.pixify.databinding.PhotoItemBinding;
import liz.agyei.pixify.utils.ClickListener;
import liz.agyei.pixify.viewmodel.MainActivityViewModel;


public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.ViewHolder> implements ClickListener {

    private List<Photo> photos;
    private Context context;
    private ViewModel model;

    public PhotoRecyclerViewAdapter(List<Photo> photos, Context context, ViewModel model) {
        this.photos = photos;
        this.context = context;
        this.model = model;
    }

    @Override
    public PhotoRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PhotoItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.photo_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.photoItemBinding.setPhoto(photo);
        holder.photoItemBinding.setModel((MainActivityViewModel) model);
        setPhotoURL(photo.getUrl(), holder.photoItemBinding.ivPhotoUrl);
        holder.bind(photo);
        holder.photoItemBinding.setClickListener(this);
    }

    public void setPhotoURL(String photoURL, ImageView imageView){
        if(!photoURL.trim().isEmpty())
            Glide.with(imageView.getContext())
                    .load(photoURL)
                    .into(imageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    @Override
    public void photoClicked(Photo photo, View view) {
        Bundle bundle = new Bundle();
        bundle.putString("photo_url", photo.getUrl());
        bundle.putString("photo_title", photo.getTitle());
        bundle.putString("photo_id", photo.getId());
        Intent intent = new Intent(context, PhotoDetailsActivity.class);
        intent.putExtra("photo", bundle);
        context.startActivity(intent);
    }

    @Override
    public void bookmarkClicked(MainActivityViewModel model, Photo photo) {
        model.toggleBookMark(photo);
        System.out.println("bookmark clicked"+ photo.getTitle());
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public PhotoItemBinding photoItemBinding;

        public ViewHolder(PhotoItemBinding photoItemBinding) {
            super(photoItemBinding.getRoot());
            this.photoItemBinding = photoItemBinding;
        }

        public void bind(Object obj) {
            photoItemBinding.setVariable(BR.photo, obj);
            photoItemBinding.executePendingBindings();
        }
    }

}


