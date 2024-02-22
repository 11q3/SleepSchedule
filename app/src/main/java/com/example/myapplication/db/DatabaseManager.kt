package com.example.myapplication.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.Locale

class DatabaseManager(context: Context) {
    private val databaseName = DatabaseConstants.DATABASE_NAME
    private val sleepColumn = DatabaseConstants.SLEEP_COLUMN
    private val timestampColumn = DatabaseConstants.TIMESTAMP_COLUMN


    private val databasePath = context.getDatabasePath(
        databaseName).toPath()

    fun readDatabase(): String {
        if (!databasePath.toFile().exists()) {
            return "File not found."
        }

        val database = SQLiteDatabase.openDatabase(
            databasePath.toFile().path, null, SQLiteDatabase.OPEN_READONLY
        )

        val tableName = DatabaseConstants.TABLE_NAME

        val sql = "SELECT TIMESTAMP, SLEEP FROM $tableName ORDER BY TIMESTAMP DESC"

        val cursor2 = database.rawQuery(sql, null)

        return if (cursor2.moveToFirst()) {
            val sleepPairsList = getSleepPairsList(cursor2)
            val sleepPeriods = getSleepPeriods(sleepPairsList)

            cursor2.close()
            database.close()

            sleepPeriods
        } else {
            cursor2.close()
            database.close()
            "No data found."
        }
    }

    private fun getSleepPairsList(cursor: Cursor): MutableList<Pair<Long, Int>> {
        val sleepPairs = mutableListOf<Pair<Long, Int>>()

        val timestampIndex = cursor.getColumnIndex(timestampColumn)
        val sleepIndex = cursor.getColumnIndex(sleepColumn)

        while (cursor.moveToNext()) {
            val timestamp = cursor.getLong(timestampIndex)
            val sleep = cursor.getInt(sleepIndex)
            sleepPairs.add(Pair(timestamp, sleep))
        }

        return sleepPairs
    }

    private fun getSleepPeriods(sleepPairs: MutableList<Pair<Long, Int>>): String {
        val sleepPeriods = mutableListOf<Pair<Long, Long>>()

        var periodStart: Long? = null

        sleepPairs.forEachIndexed { _, (timestamp, sleepColumn) ->
            if (sleepColumn > 0 && periodStart == null) {
                periodStart = timestamp
            } else if (sleepColumn == 0 && periodStart != null
            ) {
                val periodEnd = timestamp + 60
                sleepPeriods.add(Pair(periodStart!!, periodEnd))
                periodStart = null
            }
        }

        val filteredSleepPeriods = getFilteredSleepPeriods(sleepPeriods)

        return getFormattedDateTime(filteredSleepPeriods)
        //return getFormattedDateTime(sleepPeriods)

        //TODO handle somehow cases when band isn`t on hand
    }

    private fun getFilteredSleepPeriods(sleepPeriods: MutableList<Pair<Long, Long>>): MutableList<Pair<Long, Long>> {
        val filteredSleepPeriods = mutableListOf<Pair<Long, Long>>()
        for (sleepPeriod in sleepPeriods) {
            val sleepPeriodDuration = sleepPeriod.first - sleepPeriod.second
            if (sleepPeriodDuration >= 20 * 60) {
                filteredSleepPeriods.add(sleepPeriod)
            }
        }
        return filteredSleepPeriods
    }

    private fun getFormattedDateTime(timestamps: MutableList<Pair<Long, Long>>): String {
        val dateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
        val formattedTimestamps = mutableListOf<String>()

        for ((periodEnd, periodStart) in timestamps) {
            val formattedStart = dateFormat.format(periodStart * 1000)
            val formattedEnd = dateFormat.format(periodEnd * 1000)

            formattedTimestamps.add("$formattedStart - $formattedEnd\n")
        }

        return formattedTimestamps.joinToString("\n")
    }

    fun getTimeOfLastExport(): String {
        return if (Files.exists(databasePath)) {
            Files.getAttribute(databasePath, "basic:creationTime").toString()
        } else {
            "File not found"
        }
    }
}


