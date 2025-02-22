package com.example.internshipapp.data.local

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.example.internshipapp.data.local.dbModels.ConsumerDbModel
import com.example.internshipapp.data.local.dbModels.ConsumersDao
import com.example.internshipapp.data.local.dbModels.PostDbModel

@androidx.room.Database(
    entities = [PostDbModel::class, ConsumerDbModel::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 2,
            to = 3
        ),
        AutoMigration(
            from = 3,
            to = 4,
            spec = PostsDatabase.AutoMigrationFrom3To4::class
        )
    ]

)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun getPostsDao(): PostsDao

    abstract fun getConsumersDao(): ConsumersDao

    @DeleteColumn.Entries(
        DeleteColumn(
            tableName = "Consumer_table",
            columnName = "A"
        )
    )
    class AutoMigrationFrom3To4 : AutoMigrationSpec

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