package com.example.myapplication

import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.fileUtils.FileUtils.Companion.copyFile
import com.example.myapplication.fileUtils.FileUtils.Companion.readFile

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloadsPath = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toPath()
            .resolve("Gadgetbridge.db")

        val localDownloadsPath = getDatabasePath("Gadgetbridge.db").toPath()
        copyFile(downloadsPath, localDownloadsPath)

        val fileContent = readFile(getDatabasePath("Gadgetbridge.db"))
        findViewById<TextView>(R.id.fileContentTextView).text = fileContent
    }
}
