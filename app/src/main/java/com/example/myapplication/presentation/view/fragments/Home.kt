package com.example.myapplication.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.presentation.view.fragments.base.BaseFragment

class Home : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val fileContentTextView = view.findViewById<TextView>(R.id.fileContentTextView)

        val fileContent = databaseManager.readDatabase()
        fileContentTextView.text = fileContent

        return view
    }
}