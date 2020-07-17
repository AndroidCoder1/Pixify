package liz.agyei.pixify.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import liz.agyei.pixify.data.models.Photo
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class Utils {
    companion object{
        fun inputStreamToString(inputStream: InputStream) : String{
            val responseBodyReader =
                InputStreamReader(inputStream, StandardCharsets.UTF_8)
            val reader = BufferedReader(responseBodyReader)
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
            inputStream.close()
            return sb.toString();
        }

        fun getPhoto(id: String, title: String) : Photo {
            return Photo(id, title)
        }

        fun generatePhotoURL(farm: String, server: String, photoId: String, secret: String, format: String) : String{
            return "https://farm$farm.staticflickr.com/$server/"+photoId+"_"+secret+"_m.$format"
        }
        fun getPerPageLimit(context: Context) : Int{
            return androidx.preference.PreferenceManager.getDefaultSharedPreferences(context).getInt("seek_bar", 25)
        }

        fun hideKeyboardFrom(
            context: Context,
            view: View
        ) {
            val imm: InputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }
}