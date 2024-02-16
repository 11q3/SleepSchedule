package com.example.myapplication

import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fileUtils.FileUtils.Companion.copyFile
import com.example.myapplication.fileUtils.FileUtils.Companion.readFile

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(home())
                R.id.settings -> replaceFragment(settings())
            else -> {
            }

        }
            true
        }

        val downloadsPath = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toPath()
            .resolve("Gadgetbridge.db")

        val localDownloadsPath = getDatabasePath("Gadgetbridge.db").toPath()
        copyFile(downloadsPath, localDownloadsPath)

        val fileContent = readFile(getDatabasePath("Gadgetbridge.db"))
        findViewById<TextView>(R.id.fileContentTextView).text = fileContent
    }

    private fun replaceFragment(fragment : Fragment): Boolean {
        val fragmentManager = supportFragmentManager
        val fragmentsTransaction = fragmentManager.beginTransaction()
        fragmentsTransaction.replace(R.id.fileContentTextView, fragment)
        fragmentsTransaction.commit()

        return true
    }
}
