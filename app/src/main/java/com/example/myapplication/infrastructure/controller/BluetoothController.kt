package com.example.myapplication.infrastructure.controller

import com.example.myapplication.domain.model.BTDevice
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController  {
    val scannedDevices: StateFlow<List<BTDevice>>
    val pairedDevices: StateFlow<List<BTDevice>>

    fun startDiscovery()
    fun stopDiscovery()
    fun release()
}