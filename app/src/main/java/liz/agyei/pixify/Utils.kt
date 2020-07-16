package liz.agyei.pixify

import liz.agyei.pixify.data.Photo
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

        fun getPhoto(id: String, title: String) : Photo{
            return Photo(id, title)
        }
    }
}