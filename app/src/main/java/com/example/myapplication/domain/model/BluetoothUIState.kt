package com.example.myapplication.domain.model

data class BluetoothUIState(
    val scannedDevices: List<BTDevice> = emptyList(),
    val pairedDevices: List<BTDevice> = emptyList(),
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null,
)
