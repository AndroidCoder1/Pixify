package liz.agyei.pixify.data.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import liz.agyei.pixify.Utils;
import liz.agyei.pixify.data.Photo;

public class JSONParser {
    public static List<Photo> parseJSONPhotos(InputStream responseBody) throws IOException, JSONException {
        List<Photo> photos = new ArrayList<>();
        String response = Utils.Companion.inputStreamToString(responseBody);
        JSONObject jObj = new JSONObject(response);
        if(jObj.has("photo")) {
            JSONArray jsonArray = jObj.getJSONArray("photo");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.has("id") ? jsonObject.getString("id") : "";
                String title = jsonObject.has("title") ? jsonObject.getString("title") : "";
                Photo photo = Utils.Companion.getPhoto(id, title);
                photos.add(photo);
            }
        }
        return photos;
    }

    public static Photo parseJSONPhoto(InputStream responseBody, Photo photo) throws IOException, JSONException {
        String response = Utils.Companion.inputStreamToString(responseBody);
        JSONObject jsonObject = new JSONObject(response);
        if(jsonObject.has("photo")){
            JSONObject photoJSON = jsonObject.getJSONObject("photo");
            if(photoJSON.has("urls")){
                JSONObject photoURLSJSON = jsonObject.getJSONObject("urls");
                if(photoURLSJSON.has("url")) {
                    String url = photoURLSJSON.getJSONArray("url").length() > 0 ? photoURLSJSON.getJSONArray("url").getJSONObject(0).getString("_content") : "";
                    photo.setURL(url);
                }
            }
        }
        return null;
    }
}
