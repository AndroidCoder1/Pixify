package liz.agyei.pixify.data.api;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import liz.agyei.pixify.data.models.Photo;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrRetrofitAPI {

    @GET("services/rest/")
    Flowable<List<Photo>> getPhotosByTag(@Query("method") String method,
                                         @Query("api_key") String apiKey,
                                         @Query("tags") String tags,
                                         @Query("format") String format,
                                         @Query("nojsoncallback") String noJSONCallback);

    @GET("services/rest/")
    Observable<List<Photo>> getPhotoWithURL(@Query("method") String method,
                                            @Query("api_key") String apiKey,
                                            @Query("photo_id") String photoID,
                                            @Query("format") String format,
                                            @Query("nojsoncallback") String noJSONCallback);
}
