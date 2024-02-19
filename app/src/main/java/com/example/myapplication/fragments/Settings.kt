package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.fileUtils.FileUtils
import java.nio.file.Files
import java.nio.file.Paths

class Settings : Fragment() {

    private lateinit var fileUtils: FileUtils.Companion

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

        fileUtils = FileUtils.Companion
        timeOfLastExportTextView = view.findViewById(R.id.timeOfLastExport)

        val downloadsPath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
            .toPath()
            .resolve("Gadgetbridge.db")

        val appContext = requireContext().applicationContext

        val localPath = Paths.get(appContext.filesDir.path, "databases")
        fileUtils.copyFile(downloadsPath, localPath)
        val timeOfLastExport =  Files.getAttribute(localPath, "basic:creationTime")


        timeOfLastExportTextView.text = timeOfLastExport.toString()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Settings().apply {
                arguments = Bundle().apply {

                }
            }
    }
}