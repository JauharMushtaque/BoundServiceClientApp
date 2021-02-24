package com.example.service.bound.clientapp

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    companion object val TAG = MainActivity.javaClass.simpleName
    private lateinit var recieveMessenger: Messenger
    private val serviceConnection =  object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            recieveMessenger=Messenger(service)
            recieveMessenger= Messenger(receiveHandler)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }
    }

     val receiveHandler= @SuppressLint("HandlerLeak")
    object :Handler(){
        override fun handleMessage(msg: Message) {
            Toast.makeText(this@MainActivity, msg.arg1, Toast.LENGTH_LONG).show()
            Log.d(TAG, "handleMessage: "+msg.data.getBundle("jauhar"))
            super.handleMessage(msg)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews():Unit{
        findViewById<Button>(R.id.remote_binding).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                intent= Intent()
                intent.setComponent(ComponentName("com.example.service.serverapp","com.example.service.serverapp.RemoteServiceByMessenger"))
                bindService(intent, serviceConnection, BIND_AUTO_CREATE)
            }
        })
    }
}