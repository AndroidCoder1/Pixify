package liz.agyei.pixify.data.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import liz.agyei.pixify.BuildConfig;
import liz.agyei.pixify.data.Config;
import liz.agyei.pixify.data.Method;
import liz.agyei.pixify.data.models.Photo;

public class FlickrAPI {

    public Observable<InputStream> getHTTPResponse(URL url){
        return Observable.fromCallable(() -> (HttpsURLConnection) url.openConnection())
                .subscribeOn(Schedulers.io())
                .flatMap(connection -> {
                        if (connection.getResponseCode() == 200) {
                            InputStream inputStream = connection.getInputStream();
                            return Observable.just(inputStream);
                        }
                        else
                            return Observable.error(new RuntimeException("Bad response status " + connection.getResponseCode()));
                        },
                        Observable::error,
                        (Observable::empty))
                 .observeOn(Schedulers.computation());
    }

    public Observable<List<Photo>> getPhotosByTag(String tag, int page) throws MalformedURLException, IOException {
        URL url = getURL(Method.PHOTOS_SEARCH, tag, page);
        return getHTTPResponse(url).map(JSONParser::parseJSONPhotos);
    }

    public Observable<Photo> getPhotoWithURL(String tag, int page) throws MalformedURLException, IOException{
        return getPhotosByTag(tag, page)
                .flatMap(Observable::fromIterable)
                .map(photo -> {
                    URL url = getURL(Method.GET_PHOTO_INFO, photo.getId(), -1);
                    return getHTTPResponse(url)
                            .map(inputStream -> JSONParser.parseJSONPhoto(inputStream, photo));
                });
    }

    private URL getURL(String method, String identifier, int page) throws MalformedURLException {
        return new URL(String.format("%s?method=%s&api_key=%s&%s=%s&%sformat=json&nojsoncallback=1",
                Config.BASE_URL,
                method,
                BuildConfig.API_KEY,
                getSearchOrTagQuery(method.equals(Method.PHOTOS_SEARCH)),
                identifier,
                page == -1 ? "" : "page="+page+"&"));
    }

    private String getSearchOrTagQuery(boolean isSearch){
        return isSearch ? "tags=" : "photo_id=";
    }
}
