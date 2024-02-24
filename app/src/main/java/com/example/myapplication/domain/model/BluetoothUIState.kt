package com.example.myapplication.domain.model

data class BluetoothUIState(
    val scannedDevices: List<BTDevice> = emptyList(),
    val pairedDevices: List<BTDevice> = emptyList()
)
