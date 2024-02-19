package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.fileUtils.FileUtils

class Home : Fragment() {

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

        val filePath = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).resolve("Gadgetbridge.db")

        val fileContent = FileUtils.readFile(filePath)

        fileContentTextView.text = fileContent


        //TODO make this code to copy file, and only then to read

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