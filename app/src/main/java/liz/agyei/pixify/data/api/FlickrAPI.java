package liz.agyei.pixify.data.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import liz.agyei.pixify.BuildConfig;
import liz.agyei.pixify.data.Config;
import liz.agyei.pixify.data.Method;
import liz.agyei.pixify.data.models.Photo;

public class FlickrAPI {

    public Observable<InputStream> getHTTPResponse(URL url){
        return Observable.create(subscriber -> {
            try {
                URLConnection connection =  url.openConnection();
                subscriber.onNext(connection.getInputStream());
                subscriber.onComplete();
            }catch (Exception e){
                subscriber.onError(e);
            }
        });
    }

    public Observable<List<Photo>> getPhotosByTag(String tag, int page, int perPage) throws MalformedURLException, IOException {
        URL url = getURL(Method.PHOTOS_SEARCH, tag, page, perPage);
        return getHTTPResponse(url).map(JSONParser::parseJSONPhotos);
    }

    public Observable<String> getPhotoURL(String photoId) throws MalformedURLException, IOException {
        URL url = getURL(Method.GET_PHOTO_INFO, photoId, -1, 0);
        return getHTTPResponse(url).map(JSONParser::parseJSONPhoto);
    }

    public Observable<List<Photo>> getPhotosWithURL(String tag, int page, int perPage) throws IOException {
        return getPhotosByTag(tag, page, perPage)
                .flatMap((Function<List<Photo>, Observable<Photo>>) photoList -> Observable.fromIterable(photoList))
                .flatMap((Function<Photo, Observable<Photo>>) photo -> getPhotoURL(photo.getId()).flatMap((Function<String, Observable<Photo>>) s -> {
                    photo.setURL(s);
                    return Observable.just(photo);
                })).toList().toObservable();
    }

    private URL getURL(String method, String identifier, int page, int perPage) throws MalformedURLException {
        return new URL(String.format("%s?method=%s&api_key=%s&%s=%s&%sformat=json&nojsoncallback=1",
                Config.BASE_URL,
                method,
                BuildConfig.API_KEY,
                getSearchOrTagQuery(method.equals(Method.PHOTOS_SEARCH)),
                identifier,
                page == -1 ? "" : "page="+page+"&per_page="+perPage+"&"));
    }

    private String getSearchOrTagQuery(boolean isSearch){
        return isSearch ? "tags=" : "photo_id";
    }
}
