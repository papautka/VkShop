package com.uteev.vkshop.presentation.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uteev.vkshop.R

class NoConnectionActivity : AppCompatActivity() {
    private lateinit var buttonCheckConnection : Button

    private fun initView() {
        buttonCheckConnection = findViewById(R.id.bCheckConnect)
        buttonCheckConnection.setOnClickListener{
            if (isNetworkAvailable()) {
                val intent = Intent(this, ProductListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Подключение к интернету потеряно", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_connection_activity)
        initView()
    }

    companion object {
        fun newIntent(context : Context) : Intent {
            val intent = Intent(context, NoConnectionActivity::class.java)
            return intent
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}