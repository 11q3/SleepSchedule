package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.fragments.base.BaseFragment

class Settings : BaseFragment() {

    private lateinit var timeOfLastExportTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        timeOfLastExportTextView = view.findViewById(R.id.timeOfLastExport)
        timeOfLastExportTextView.text = databaseManager.getTimeOfLastExport()

        return view
    }
}