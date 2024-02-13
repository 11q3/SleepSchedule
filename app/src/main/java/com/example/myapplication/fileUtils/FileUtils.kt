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
            if (!filePath.exists()) {
                return "File not found."
            }

            val database = SQLiteDatabase.openDatabase(
                filePath.path, null, SQLiteDatabase.OPEN_READONLY)

            val tableName = "HUAMI_EXTENDED_ACTIVITY_SAMPLE"

            val sql = "SELECT TIMESTAMP, SLEEP FROM $tableName ORDER BY TIMESTAMP DESC"

            val cursor2 = database.rawQuery(sql, null)

            return if (cursor2.moveToFirst()) {
                val timestampsList = getSleepPairsList(cursor2)
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

        private fun getSleepPairsList(cursor2: Cursor): MutableList<Pair<Long, Int>> {
            val sleepPairs = mutableListOf<Pair<Long, Int>>()

            val timestampIndex = cursor2.getColumnIndex("TIMESTAMP")
            val sleepIndex = cursor2.getColumnIndex("SLEEP")
            do {
                val timestamp = cursor2.getLong(timestampIndex)
                val sleep = cursor2.getInt(sleepIndex)
                sleepPairs.add(Pair(timestamp, sleep))
            } while (cursor2.moveToNext())

            return sleepPairs
        }

        private fun getSleepPeriods(sleepPairs: MutableList<Pair<Long, Int>>): String { //MutableList<Pair<Long, Long>>
            val sleepPeriods = mutableListOf<Pair<Long, Long>>()

            var periodStart: Long? = null
            var periodEnd: Long? = null

            for (i in sleepPairs.indices) {
                val (timestamp, sleepColumn) = sleepPairs[i]

                if (sleepColumn > 0 && periodStart == null) {
                    periodStart = timestamp
                }

                if (sleepColumn == 0 && periodStart != null) {
                    periodEnd = timestamp+60

                    sleepPeriods.add(Pair(periodStart, periodEnd))
                    periodStart = null
                }
            }

            return getFormattedDateTime(sleepPeriods)
        }

        private fun getFormattedDateTime(timestamps: MutableList<Pair<Long, Long>>): String {
            val dateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            val formattedTimestamps = mutableListOf<String>()

            for ((end, start) in timestamps) {
                val formattedStart = dateFormat.format(start * 1000)
                val formattedEnd = dateFormat.format(end * 1000)
                formattedTimestamps.add("$formattedStart - $formattedEnd\n")
            }

            return formattedTimestamps.joinToString("\n")
        }
    }
}
