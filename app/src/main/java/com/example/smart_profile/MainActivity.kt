package com.example.smart_profile

import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
   // private lateinit var audioManager: AudioManager
   // private var receiver: SmsListener = SmsListener()
    //private val filter = IntentFilter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // val _btnStart = findViewById<Button>(R.id.StartBtn)
       // val _btnStop = findViewById<Button>(R.id.StopBtn)
        /*StartBtn.setOnClickListener()
        {
            filter.addAction("com.example.smart_profile")
            registerReceiver(receiver, filter);﻿
        }

        StopBtn.setOnClickListener {
            ﻿unregisterReceiver(receiver);﻿
        }*/
        StartBtn.setOnClickListener(this)
        StopBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.StartBtn ->
            {
                val service = Intent(this@MainActivity, MyService::class.java)
                startService(service)

              /*  filter.addAction("com.example.smart_profile")
                registerReceiver(receiver, filter)
                val intent = Intent(this@MainActivity, SmsListener::class.java)
                intent.putExtra("message", message)
                startService(intent)*/
               /* filter.addAction("com.example.smart_profile")
                registerReceiver(receiver, filter);﻿*/
            }
            R.id.StopBtn -> {
                val service = Intent(this@MainActivity, MyService::class.java)
                stopService(service)
              //  unregisterReceiver(receiver)
            }
        }

    }
}


