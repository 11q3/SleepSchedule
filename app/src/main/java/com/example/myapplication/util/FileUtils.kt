package com.example.myapplication.util

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import androidx.core.content.ContentProviderCompat.requireContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileUtils {
    companion object {
        fun exportDatabase(context: Context) {
            val databasePath = context.getDatabasePath("Gadgetbridge.db").toPath()
            val exportPath = Environment.getExternalStorageDirectory().toString() + "/" + "Gadgetbridge.db"

            try {
                val inputStream = File(databasePath.toFile().path).inputStream()
                val outputStream = FileOutputStream(exportPath)

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.flush()
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
