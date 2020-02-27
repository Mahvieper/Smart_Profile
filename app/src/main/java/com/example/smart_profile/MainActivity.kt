package com.example.smart_profile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var isStop : Boolean = false
    val CHANNEL_ID = "exampleServiceChannel"
    // private lateinit var audioManager: AudioManager
    private var receiver: SmsListener = SmsListener()
    private val filter = IntentFilter()

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Example Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //createNotificationChannel();

        StartBtn.setOnClickListener(this)
        StopBtn.setOnClickListener(this)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        /* if(isStop) {}
         else {
             val service = Intent(this@MainActivity, MyService::class.java)
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 startForegroundService(service)
             }
         }*/
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.StartBtn ->
            {
                isStop = false
                val service = Intent(this@MainActivity, MyService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //filter.addAction("com.example.smart_profile")
                   // registerReceiver(receiver, filter)
                    startForegroundService(service)
                }

                /*  filter.addAction("com.example.smart_profile")
                  registerReceiver(receiver, filter)
                  val intent = Intent(this@MainActivity, SmsListener::class.java)
                  intent.putExtra("message", message)
                  startService(intent)*/
                /* filter.addAction("com.example.smart_profile")
                 registerReceiver(receiver, filter);﻿*/
            }
            R.id.StopBtn -> {
                isStop = true
                val service = Intent(this@MainActivity, MyService::class.java)
                stopService(service)
                //  unregisterReceiver(receiver)
            }
        }

    }
}


