package com.example.myapplication

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloadsPath = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toPath()
            .resolve("Gadgetbridge.db")
        copyFile(downloadsPath)

        val fileContent = readFile(getDatabasePath("Gadgetbridge.db"))
        findViewById<TextView>(R.id.fileContentTextView).text = fileContent
    }

    private fun copyFile(downloadPath: Path) {
        try {
            val localPath = getDatabasePath("Gadgetbridge.db")
            downloadPath.toFile().inputStream().use {
                input -> localPath.outputStream().use {
                    output -> input.copyTo(output)
                }

            }
        } catch (e: IOException) {
            println("Failed to copy file: ${e.message}")
        }
    }

    private fun readFile(filePath: File): String {
        if (!filePath.exists()) { return "File not found." }

        val database = SQLiteDatabase.openDatabase(
            filePath.path, null, SQLiteDatabase.OPEN_READONLY)

        val tableName = "HUAMI_EXTENDED_ACTIVITY_SAMPLE"

        val fileContent = StringBuilder()
        fileContent.append("\nTable: $tableName\n\n")

        val sql = "SELECT TIMESTAMP, SLEEP FROM $tableName WHERE SLEEP > 0 ORDER BY TIMESTAMP DESC"

        val cursor2 = database.rawQuery(sql, null)
        val columnNames = cursor2.columnNames

        fileContent.append("Columns: ${columnNames.joinToString(",    ")}\n")

        if (!cursor2.moveToFirst()) {
            fileContent.append("Table is empty or no rows with sleep > 0.\n")
        }

        do {
            val rowValues = mutableListOf<String>()
            for (columnName in columnNames) {
                val columnIndex = cursor2.getColumnIndex(columnName)
                if (columnIndex != -1) {
                    if (columnName == "TIMESTAMP") {
                        val formattedTimestamp = getFormattedDateTime(cursor2.getLong(columnIndex))
                        rowValues.add(formattedTimestamp)
                    } else {
                        rowValues.add(cursor2.getString(columnIndex) ?: "null")
                    }
                } else {
                    rowValues.add("")
                }
            }
            fileContent.append("${rowValues.joinToString(",    ")} \n")
        } while (cursor2.moveToNext())

        cursor2.close()
        database.close()

        return fileContent.toString()
    }

    private fun getFormattedDateTime(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return dateFormat.format(timestamp * 1000)
    }
}