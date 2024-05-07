package com.uteev.vkshop.presentation


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        val isConnected = networkInfo != null && networkInfo.isConnected

        if (isConnected) {
            Toast.makeText(context, "Подключение к интернету установлено", Toast.LENGTH_SHORT).show()
            updateData(context)
        } else {
            Toast.makeText(context, "Подключение к интернету потеряно", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateData(context: Context) {
        // Получаем доступ к экземпляру ProductListActivity и обновляем данные
        val intent = Intent(context, ProductListActivity::class.java)
        intent.action = "com.uteev.vkshop.presentation.UPDATE_DATA"
        context.sendBroadcast(intent)
    }
}
