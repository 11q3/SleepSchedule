package com.example.myapplication.bluetooth

typealias BTDevice = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String
)
