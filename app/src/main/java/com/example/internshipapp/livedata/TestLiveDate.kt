package com.example.internshipapp.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object TestLiveDate {

    //setValue and PostValue//
    val mutableLiveDate = MutableLiveData<String>()

    fun testSetAndPostValue() {
        mutableLiveDate.value = "testSetAndPostValue  + mainThread"

        CoroutineScope(Dispatchers.IO).launch {
            mutableLiveDate.postValue("testSetAndPostValue + IO thread")
        }
    }

    //MediatorLiveData//
    val liveData1 = MutableLiveData("First")
    val liveData2 = MutableLiveData("Second")

    val testMediatorLiveDate = MediatorLiveData<String>().apply {
        addSource(liveData1) { value = "From source1: $it" }
        addSource(liveData2) { value = "From source2: $it" }
    }

    //map//
    val testMap = mutableLiveDate.map {
        it.length
    }


    fun changeValuesForLivedata() = CoroutineScope(Dispatchers.IO).launch {
        mutableLiveDate.postValue("2")
        delay(1000)
        mutableLiveDate.postValue("O")
        delay(1000)
        mutableLiveDate.postValue("0")
        delay(1000)
        mutableLiveDate.postValue("O")
        delay(1000)
        mutableLiveDate.postValue("1")
    }

    val uploadData = SingleLiveEvent<String>()
    fun putUploadData(uploadedData: String) {
        uploadData.value = uploadedData
    }

}