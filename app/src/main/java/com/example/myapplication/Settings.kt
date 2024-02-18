package com.example.myapplication

import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.fileUtils.FileUtils
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Create a private property for FileUtils
    private lateinit var fileUtils: FileUtils.Companion

    private lateinit var timeOfLastExportTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Initialize FileUtils here
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}