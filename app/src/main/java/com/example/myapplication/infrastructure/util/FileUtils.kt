package com.example.myapplication.infrastructure.util

import android.content.Context
import android.os.Environment
import com.example.myapplication.data.database.DatabaseConstants
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileUtils {
    companion object {
        private const val databaseName = DatabaseConstants.DATABASE_NAME
        fun exportDatabase(context: Context) {
            val databasePath = context.getDatabasePath(databaseName).toPath()
            val exportPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/" + databaseName

            try {
                val inputStream = File(exportPath).inputStream()
                val outputStream = FileOutputStream(databasePath.toFile().path)

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
