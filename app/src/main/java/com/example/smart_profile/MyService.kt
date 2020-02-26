package com.example.smart_profile

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder


class MyService : Service() {
    private val filter = IntentFilter()
    private lateinit var m_ScreenOffReceiver: SmsListener
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        m_ScreenOffReceiver = SmsListener()
        registerScreenOffReceiver()
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

}