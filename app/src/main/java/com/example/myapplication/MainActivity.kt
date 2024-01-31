package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSIONS = 100
    private val PERMISSIONS_REQUIRED = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, REQUEST_CODE_PERMISSIONS)
        } else {
            copyFileFromExternalAppToLocalDirectory()
        }
    }

    private fun copyFileFromExternalAppToLocalDirectory(): Boolean {
        val externalFilePath = "/storage/emulated/0/Download/Gadgetbridge.db (5)"
        val externalFile = File(externalFilePath)
        if (!externalFile.exists()) {
            println("External file does not exist.")
            return false
        }

        val localDirectoryPath = filesDir.parentFile.path
        val localDirectory = File(localDirectoryPath)

        if (!localDirectory.exists()) {
            println("Local directory does not exist.")
            return false
        }

        val localFile = File(localDirectory, externalFile.name)
        return try {
            externalFile.copyTo(localFile, overwrite = true)
            println("File copied successfully.")
            true
        } catch (e: Exception) {
            println("Failed to copy file: ${e.message}")
            false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with your normal app logic
                copyFileFromExternalAppToLocalDirectory()
            } else {
                // Permissions denied, show a message and close the app
                Toast.makeText(this, "Permissions denied, closing the app", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}