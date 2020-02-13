package ru.otus.demo.sharedpref

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

    }

    fun onShowShPref(view : View?) {
        val intent = Intent(this@MainActivity, SharedPrefActivity::class.java)
        startActivity(intent)
    }

    fun onShowEncShPref(view : View?) {
        val intent = Intent(this@MainActivity, EncSharedPrefActivity::class.java)
        startActivity(intent)
    }

    fun onShowFilesIo(view : View?) {
        val intent = Intent(this@MainActivity, IoFilesActivity::class.java)
        startActivity(intent)
    }

}
