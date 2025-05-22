package com.example.internshipapp.presentation.postsInXML

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.internshipapp.R
import com.example.internshipapp.databinding.ActivityPostsBinding
import com.example.internshipapp.livedata.TestLiveDate

class PostsActivity : AppCompatActivity(R.layout.activity_posts) {

    private val binding: ActivityPostsBinding by viewBinding(ActivityPostsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)

        /* TestLiveDate.testSetAndPostValue()
         TestLiveDate.mutableLiveDate.observe(this) {
             Log.e("LiveData", it.toString())
         }
         TestLiveDate.testMediatorLiveDate.observe(this){
             Log.e("MediatorLiveData", it)
         }
         TestLiveDate.testMap.observe(this){
             Log.e("LiveData", it.toString())
         }*/

        TestLiveDate.uploadData.observe(this) {
            Log.e("LiveData", it)
        }
        TestLiveDate.uploadData.observe(this) {
            Log.e("LiveData", it)
        }
        TestLiveDate.uploadData.observe(this) {
            Log.e("LiveData", it)
        }
        TestLiveDate.putUploadData("lalal")


    }
}