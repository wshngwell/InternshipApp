package com.example.internshipapp.data.feature13

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.internshipapp.MainActivity
import com.example.internshipapp.R
import com.example.internshipapp.domain.repositories.IMusicPlayerRepository
import com.example.internshipapp.myLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MusicForegroundService : Service() {

    private var job: Job? = null

    private val musicPlayerRepository: IMusicPlayerRepository by inject()


    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private val notificationBuilder by lazy {
        createNotfication(0, 0)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notificationBuilder)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job?.cancel()
        job = MainScope().launch {
            musicPlayerRepository.musicState.collect {
                createNotfication(it.currentPosition, it.musicDuration)
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun createNotfication(currentPosition: Long, musicDuration: Long): Notification {
        val notifIntent = Intent(this, MainActivity::class.java)
        notifIntent.setAction(Intent.ACTION_MAIN)
        notifIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) FLAG_IMMUTABLE else 0
        val pendingIntent =
            PendingIntent.getActivity(this, 120, notifIntent, flag)

        val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Service!")
            .setContentText(
                "Слушаем музыку \n " +
                        currentPosition.formatMinSec() + "/" +
                        musicDuration.formatMinSec()
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(icon)
            .setSilent(true)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(NOTIFICATION_ID, notification)

        return notification
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val CHANNEL_NAME = "CHANNEL_NAME"
        private const val NOTIFICATION_ID = 1


    }
}
