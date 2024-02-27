package com.example.myapplication.infrastructure.controller

import com.example.myapplication.domain.model.BTDevice
import com.example.myapplication.domain.model.ConnectionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController  {
    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BTDevice>>
    val pairedDevices: StateFlow<List<BTDevice>>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()
    fun startConnection(device: BTDevice): Flow<ConnectionResult>
    fun closeConnection()
    fun release()
}