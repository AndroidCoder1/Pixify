package liz.agyei.pixify.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import liz.agyei.pixify.data.interfaces.PhotosDao
import liz.agyei.pixify.data.models.Photo

@Database(
        entities = [Photo::class],
        version = 1,
        exportSchema = false
)
abstract class PixifyDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao


    companion object {
        @Volatile
        private var INSTANCE: PixifyDatabase? = null

        fun getDatabase(context: Context): PixifyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PixifyDatabase::class.java,
                        "PixDB")
                        .allowMainThreadQueries()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}