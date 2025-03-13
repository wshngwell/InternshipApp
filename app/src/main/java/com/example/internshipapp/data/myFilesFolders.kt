package com.example.internshipapp.data

import android.os.Environment


object MyFilesFolders {
    val fileBaseFolderForPublicDownloading =
        "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/La/Test"

}