package com.example.smart_profile

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.PowerManager



class MyService : Service() {
    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false
    private val filter = IntentFilter()
    private var m_ScreenOffReceiver: SmsListener = SmsListener()
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            m_ScreenOffReceiver = SmsListener()
             registerScreenOffReceiver()
        } else {
            stopForeground(true)
            stopSelf()
        }
        return START_STICKY
    }

    override fun onCreate() {
        var notification = createNotification()
        startForeground(1, notification)
        //m_ScreenOffReceiver = SmsListener()
        // registerScreenOffReceiver()
    }



    override fun onDestroy() {
        unregisterReceiver(m_ScreenOffReceiver)
        // m_ScreenOffReceiver = null
    }

    private fun registerScreenOffReceiver() {
        filter.addAction("com.example.smart_profile")
        registerReceiver(m_ScreenOffReceiver, filter)
        // val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
    }




    private fun createNotification(): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            notificationChannelId
        ) else Notification.Builder(this)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder
                .setContentTitle("Endless Service")
                .setContentText("This is your favorite endless service working")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Ticker text")
                .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
                .build()
        } else {
            TODO("VERSION.SDK_INT < JELLY_BEAN")
        }
    }
}