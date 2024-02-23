package com.example.myapplication.bluetooth.presentation

import com.example.myapplication.bluetooth.BTDevice

data class BluetoothUIState(
    val scannedDevices: List<BTDevice> = emptyList(),
    val pairedDevices: List<BTDevice> = emptyList()
)
