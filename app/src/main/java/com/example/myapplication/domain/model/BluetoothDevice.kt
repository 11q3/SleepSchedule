package com.example.myapplication.domain.model


typealias BTDevice = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String
)