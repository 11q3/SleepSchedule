package com.example.myapplication.util

import android.content.Context
import android.os.Environment
import com.example.myapplication.db.DatabaseConstants
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileUtils {
    companion object {
        private const val databaseName = DatabaseConstants.DATABASE_NAME
        fun exportDatabase(context: Context) {
            val databasePath = context.getDatabasePath(databaseName).toPath()
            val exportPath = Environment.getExternalStorageDirectory().toString() + "/" + databaseName

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
