package com.example.myapplication.fileUtils

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.io.path.outputStream

class FileUtils {
    companion object {
        fun copyFile(downloadPath: Path, localPath: Path) {
            try {
                downloadPath.toFile().inputStream().use {
                        input -> localPath.outputStream().use {
                        output -> input.copyTo(output)
                    }
                }
            } catch (e: IOException) {
                println("Failed to copy file: ${e.message}")
            }
        }

        fun readFile(filePath: File): String {
            if (!filePath.exists()) { return "File not found." }

            val database = SQLiteDatabase.openDatabase(
                filePath.path, null, SQLiteDatabase.OPEN_READONLY)

            val tableName = "HUAMI_EXTENDED_ACTIVITY_SAMPLE"

            val sql = "SELECT TIMESTAMP, SLEEP FROM $tableName WHERE SLEEP > 0 ORDER BY TIMESTAMP DESC"

            val cursor2 = database.rawQuery(sql, null)

            return if (cursor2.moveToFirst()) {
                val timestampsList = getTimestampsList(cursor2)
                val sleepPeriods = getSleepPeriods(timestampsList)

                cursor2.close()
                database.close()

                sleepPeriods.toString()
            } else {
                cursor2.close()
                database.close()
                "No data found."
            }
        }

        private fun getTimestampsList(cursor2: Cursor): MutableList<Long> {
            val timestampsList = mutableListOf<Long>()

            val columnIndex = cursor2.getColumnIndex("TIMESTAMP")
            do {
                timestampsList.add(cursor2.getLong(columnIndex))
            } while (cursor2.moveToNext())

            return timestampsList
        }

        private fun getSleepPeriods(timestampsList: MutableList<Long>): MutableList<String> {
            val sleepPeriods = mutableListOf<String>()
            val sleepPeriod = mutableListOf<String>()

            var periodStart = timestampsList[0]
            var periodEnd:Long
            sleepPeriod.add(getFormattedDateTime(periodStart))


            for (i in 0..<timestampsList.size-1) {
                val current = timestampsList[i]
                val next = timestampsList[i+1]

                if(current-next>60) {
                    periodEnd = current
                    sleepPeriod.add(getFormattedDateTime(periodEnd))
                }

                if(sleepPeriod.size==2) {
                    sleepPeriods.add(sleepPeriod.toString())
                    sleepPeriod.clear()
                    periodStart = next
                    sleepPeriod.add(getFormattedDateTime(periodStart))
                }
            }

            return sleepPeriods
        }

        private fun getFormattedDateTime(timestamp: Long): String {
            val dateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            return dateFormat.format(timestamp * 1000)
        }
    }
}
