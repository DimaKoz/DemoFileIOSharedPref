package ru.otus.demo.sharedpref

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.content_sh_pref.*
import ru.otus.demo.sharedpref.SharedPrefActivity.Companion.ENC_PREF_NAME

class EncSharedPrefActivity : AppCompatActivity() {

    lateinit var userName: TextInputEditText
    lateinit var silentMode: CheckBox

    private var sharedPrf : SharedPreferences? = null

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
        val prefs = getSharedPref()

        // Step 3: Save data to the EncryptedSharedPreferences as usual
        val editor = prefs?.edit()
        editor?.putString(SharedPrefActivity.PREF_USER_NAME, userName.text?.toString() ?: "")
        editor?.putBoolean(SharedPrefActivity.PREF_SILENT_MODE, silentMode.isChecked)
        editor?.apply()
    }

    fun loadValues(view: View?) {
        val prefs = getSharedPref()

        // Step 3: Read data from EncryptedSharedPreferences as usual
        val text = prefs?.getString(SharedPrefActivity.PREF_USER_NAME, "") ?: ""
        val isSilent = prefs?.getBoolean(SharedPrefActivity.PREF_SILENT_MODE, false) ?: false
        userName.setText(text)
        silentMode.isChecked = isSilent
    }

    fun getSharedPref() : SharedPreferences? {
        if(sharedPrf == null) {

            // Step 1: Create or retrieve the Master Key for encryption/decryption
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

            // Step 2: Initialize/open an instance of EncryptedSharedPreferences
            val sharedPreferences = EncryptedSharedPreferences.create(
                ENC_PREF_NAME,
                masterKeyAlias,
                applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            sharedPrf = sharedPreferences
        }
        return sharedPrf
    }

    fun clearValues(view: View?) {
        val prefs = getSharedPreferences(SharedPrefActivity.PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(SharedPrefActivity.PREF_USER_NAME)
        //OR clear all
        editor.clear()
        editor.apply()

        loadValues(null)
    }

}
