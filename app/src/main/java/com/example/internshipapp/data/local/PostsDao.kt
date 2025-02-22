package com.example.internshipapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.internshipapp.data.local.dbModels.ConsumerDbModel
import com.example.internshipapp.data.local.dbModels.PostDbModel
import kotlinx.coroutines.flow.Flow


@Dao
interface PostsDao {
    @Query("SELECT * FROM postdbmodel_table ORDER BY timeOfInsertion")
    fun getPostsFromDb(): Flow<List<PostDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPostToFavourite(postDbModel: PostDbModel)

    @Query("DELETE FROM postdbmodel_table WHERE id =:postId ")
    suspend fun deletePostToFavourite(postId: Int)
}