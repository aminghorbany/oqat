package com.amin.oqatsharee.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData

class NetworkBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE"
    }

    val networkLiveData = MutableLiveData<Boolean>()


    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == ACTION_CONNECTIVITY_CHANGE) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                //connection available
                networkLiveData.postValue(true)

            } else {
                //connection lost
                networkLiveData.postValue(false)
            }
        }
    }
}
