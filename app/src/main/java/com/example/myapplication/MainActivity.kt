package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Paths

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        copyFile()

        val fileContent = readFile(getDatabasePath("Gadgetbridge.db"))
        findViewById<TextView>(R.id.fileContentTextView).text = fileContent
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun copyFile() {
        val downloadsPath = Paths.get(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),
            "Gadgetbridge.db (5)"
        )

        try {
            val localPath = getDatabasePath("Gadgetbridge.db")

            val inputStream = FileInputStream(downloadsPath.toFile())

            val outputStream = FileOutputStream(localPath)

            inputStream.copyTo(outputStream)

            inputStream.close()
            outputStream.close()

            println("File copied successfully.")
        } catch (e: IOException) {
            println("Failed to copy file: ${e.message}")
        }
    }

    private fun readFile(filePath: File): String {
        if (!filePath.exists()) {
            return "File not found."
        }

        val database = SQLiteDatabase.openDatabase(filePath.path, null, SQLiteDatabase.OPEN_READONLY)

        val tableNames = mutableListOf<String>()
        val cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)
        while (cursor.moveToNext()) {
            val tableName = cursor.getString(0)
            if (tableName != null) {
                tableNames.add(tableName)
            }
        }
        cursor.close()

        val fileContent = StringBuilder()
        for (tableName in tableNames) {
            fileContent.append("Table: $tableName\n")
            val cursor2 = database.rawQuery("SELECT * FROM $tableName", null)
            val columnNames = cursor2.columnNames
            fileContent.append("Columns: ${columnNames.joinToString()}\n")
            if (cursor2.moveToFirst()) {
                do {
                    val rowValues = mutableListOf<String>()
                    for (columnName in columnNames) {
                        val columnIndex = cursor2.getColumnIndex(columnName)
                        if (columnIndex != -1) {
                            rowValues.add(cursor2.getString(columnIndex) ?: "null")
                        } else {
                            rowValues.add("")
                        }
                    }
                    fileContent.append("Row: ${rowValues.joinToString()}\n")
                } while (cursor2.moveToNext())
            } else {
                fileContent.append("Table is empty.\n")
            }
            cursor2.close()
        }

        database.close()

        return fileContent.toString()
    }
}