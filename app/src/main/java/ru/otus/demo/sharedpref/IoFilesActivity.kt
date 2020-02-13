package ru.otus.demo.sharedpref

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.content_io_files.*
import java.io.*


class IoFilesActivity : AppCompatActivity() {

    lateinit var userName: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_io_files)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        userName = findViewById(R.id.form_user_name)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun saveValues(view: View?) {
        writeToFile(userName.text?.toString() ?: "", this)
    }

    fun loadValues(view: View?) {

        userName.setText(readFromFile(this) ?: "")

    }


    private fun writeToFile(data: String, ctx: Context) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(ctx.openFileOutput("config.txt",
                    Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
            e.printStackTrace()
        }
    }

    private fun readFromFile(context: Context): String? {
        var ret = ""
        try {
            val inputStream: InputStream? = context.openFileInput("config.txt")
            inputStream?. run {
                val inputStreamReader = InputStreamReader(this)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also {
                        receiveString = it
                    } != null) {
                    stringBuilder.append(receiveString)
                }
                close()
                ret = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            Log.e("Exception", "File not found: $e")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.e("Exception", "Can not read file: $e")
            e.printStackTrace()
        }
        val f = File(filesDir, "foo.txt")
        return ret
    }

    //val f = File("/data/data/our.app.package.name/files/foo.txt") //Don't do that!

    //val f = File(filesDir, "foo.txt")

    fun saveShowDirectories(view: View) {
        val filesDirPath = filesDir.absolutePath
        val cacheDirPath = cacheDir.absolutePath
        val externalFilesPath = getExternalFilesDir(null)?.absolutePath
        val externalCachePath = externalCacheDir?.absolutePath
        Log.i("IoFilesActivity", "filesDirPath: [$filesDirPath]")
        Log.i("IoFilesActivity", "cacheDirPath: [$cacheDirPath]")
        Log.i("IoFilesActivity", "externalFilesPath: [$externalFilesPath]")
        Log.i("IoFilesActivity", "externalCachePath: [$externalCachePath]")

    }
}
