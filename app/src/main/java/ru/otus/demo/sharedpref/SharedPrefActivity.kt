package ru.otus.demo.sharedpref

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.content_sh_pref.*

class SharedPrefActivity : AppCompatActivity() {

    lateinit var userName: TextInputEditText
    lateinit var silentMode: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_sh_pref)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        userName = findViewById(R.id.form_user_name)
        silentMode = findViewById(R.id.form_silent_mode)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun saveValues(view: View?) {
        val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(PREF_USER_NAME, userName.text?.toString() ?: "")
        editor.putBoolean(PREF_SILENT_MODE, silentMode.isChecked)
        editor.putInt("ourIntKey", 222)
        editor.apply()

        val set = mutableSetOf<String>("one", "two", "five")
        editor.putStringSet("setKey", set)
        editor.commit()
    }

    fun loadValues(view: View?) {
        val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val text = prefs.getString(PREF_USER_NAME, "")
        val isSilent = prefs.getBoolean(PREF_SILENT_MODE, false)
        userName.setText(text)
        silentMode.isChecked = isSilent
    }

    fun clearValues(view: View?) {
        val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(PREF_USER_NAME)
        //OR clear all
        editor.clear()
        editor.apply()

        loadValues(null)
    }

    companion object {
        const val PREF_NAME = "PrefsName"
        const val ENC_PREF_NAME = "EncPrefsName"
        const val PREF_USER_NAME = "UserName"
        const val PREF_SILENT_MODE = "SilentMode"
    }

}
