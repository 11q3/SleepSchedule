package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.os.Environment.getStorageDirectory
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.db.DatabaseManager
import com.example.myapplication.fragments.base.BaseFragment
import com.example.myapplication.util.FileUtils
import java.nio.file.Paths

class Home : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val fileContentTextView = view.findViewById<TextView>(R.id.fileContentTextView)

        //val appContext = requireContext().applicationContext
        //val filePath = appContext.getDatabasePath("Gadgetbridge.db").toPath()

        val fileContent = databaseManager.readDatabase()
        fileContentTextView.text = fileContent


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Home().apply {
                arguments = Bundle().apply {

                }
            }
    }
}