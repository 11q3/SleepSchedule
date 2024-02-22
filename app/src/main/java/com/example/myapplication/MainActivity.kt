package com.example.myapplication

import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.Home
import com.example.myapplication.fragments.Settings
import com.example.myapplication.util.FileUtils
import java.nio.file.Paths

class MainActivity : AppCompatActivity() {
    private lateinit var fileUtils: FileUtils.Companion

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        fileUtils = FileUtils.Companion

        val downloadsPath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
            .toPath()
            .resolve("Gadgetbridge.db")

        val localPath = getDatabasePath("Gadgetbridge.db").toPath()

        fileUtils.copyFile(downloadsPath, localPath)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.settings -> replaceFragment(Settings())
            else -> { }
        }
            true
        }

    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentsTransaction = fragmentManager.beginTransaction()
        fragmentsTransaction.replace(R.id.frame_layout, fragment)
        fragmentsTransaction.commit()
    }
}
