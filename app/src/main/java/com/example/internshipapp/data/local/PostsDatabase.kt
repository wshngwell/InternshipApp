package com.example.internshipapp.data.local

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.internshipapp.data.local.dbModels.PostDbModel

@androidx.room.Database(entities = [PostDbModel::class], version = 1, exportSchema = true)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun getPostsDao(): PostsDao

    companion object {

        private const val DB_NAME = "POSTS_DB"
        private var INSTANCE: PostsDatabase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): PostsDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val postsDatabase = Room.databaseBuilder(
                    context = application,
                    klass = PostsDatabase::class.java,
                    name = DB_NAME
                ).build()
                INSTANCE = postsDatabase
                return postsDatabase
            }
        }
    }
}