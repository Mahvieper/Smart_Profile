package com.example.smart_profile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.telephony.SmsMessage
import android.util.Log


class SmsListener : BroadcastReceiver() {
    private var mLastTimeReceived = System.currentTimeMillis()
    private lateinit var audioManager: AudioManager
    private var mListener: SmsListener? = null

    override fun onReceive(p0: Context, intent: Intent?) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - mLastTimeReceived > 200) {
            mLastTimeReceived = currentTimeMillis

            val pdus: Array<*>
            val msgs: Array<SmsMessage?>
            var msgFrom: String?
            var msgText: String?
            val strBuilder = StringBuilder()
            intent?.extras?.let {
                try {
                    pdus = it.get("pdus") as Array<*>
                    msgs = arrayOfNulls(pdus.size)
                    for (i in msgs.indices) {
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        strBuilder.append(msgs[i]?.messageBody)
                    }

                    msgText = strBuilder.toString()
                    msgFrom = msgs[0]?.originatingAddress

                    if (!msgFrom.isNullOrBlank() && !msgText.isNullOrBlank()) {
                        //Do something Here s
                        if(msgText!!.contains("Ring")) {
                            audioManager = p0.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                            audioManager.ringerMode = 2
                            Log.e("Ring","Goes in Ring")

                        } else if(msgText!!.contains("Silent")) {
                            audioManager = p0.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                            audioManager.ringerMode = 1 //= AudioManager.RINGER_MODE_SILENT
                            Log.e("Silent","Goes in Silent")
                            }
                    }
                } catch (e: Exception) {
                }
            }
        }
    }


}

