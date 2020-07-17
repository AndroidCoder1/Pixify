package liz.agyei.pixify.data.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import liz.agyei.pixify.data.models.Photo;
import liz.agyei.pixify.utils.Utils;

public class JSONParser {
    public static List<Photo> parseJSONPhotos(InputStream responseBody) throws IOException, JSONException {
        List<Photo> photos = new ArrayList<>();
        String response = Utils.Companion.inputStreamToString(responseBody);
        System.out.println(response);
        JSONObject jsonObject = new JSONObject(response);
        if(jsonObject.has("photos")) {
            JSONObject photosJSON = jsonObject.getJSONObject("photos");
            if(photosJSON.has("photo")) {
                JSONArray jsonArray = photosJSON.getJSONArray("photo");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject photoJSON = jsonArray.getJSONObject(i);
                    String id = photoJSON.has("id") ? photoJSON.getString("id") : "";
                    String title = photoJSON.has("title") ? photoJSON.getString("title") : "";
                    Photo photo = Utils.Companion.getPhoto(id, title);
                    photos.add(photo);
                }
            }
        }
        return photos;
    }

    public static String parseJSONPhoto(InputStream responseBody) throws IOException, JSONException {
        String response = Utils.Companion.inputStreamToString(responseBody);
        JSONObject jsonObject = new JSONObject(response);
        if(jsonObject.has("photo")){
            JSONObject photoJSON = jsonObject.getJSONObject("photo");

            return Utils.Companion.generatePhotoURL(photoJSON.has("farm") ? photoJSON.getString("farm") : "",
                    photoJSON.has("server") ? photoJSON.getString("server")  : "",
                    photoJSON.has("id") ? photoJSON.getString("id") : "",
                    photoJSON.has("secret") ? photoJSON.getString("secret") : "",
                    photoJSON.has("originalformat") ? photoJSON.getString("originalformat") : "");
        }
        return "";
    }
}
