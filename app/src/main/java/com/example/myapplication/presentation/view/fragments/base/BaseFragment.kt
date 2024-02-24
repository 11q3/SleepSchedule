package com.example.myapplication.presentation.view.fragments.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.myapplication.data.database.DatabaseManager
import com.example.myapplication.infrastructure.util.FileUtils

abstract class BaseFragment : Fragment() {

    private lateinit var fileUtils: FileUtils.Companion
    protected lateinit var databaseManager: DatabaseManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fileUtils = FileUtils
        databaseManager = DatabaseManager(requireContext())
    }
}