package com.example.myapplication.bluetooth.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bluetooth.BluetoothController
import com.example.myapplication.bluetooth.presentation.BluetoothUIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BluetoothViewModel(private val bluetoothController: BluetoothController) : ViewModel() {
    val state = MutableLiveData<BluetoothUIState>()

    init {
        getDevices()
    }

    private fun getDevices() {
        viewModelScope.launch {
            bluetoothController.scannedDevices.collect { scannedDevices ->
                bluetoothController.pairedDevices.collect { pairedDevices ->
                    state.value = BluetoothUIState(scannedDevices, pairedDevices)
                }
            }
        }
    }

    fun startScan() {
        bluetoothController.startDiscovery()
    }

    fun stopScan() {
        bluetoothController.stopDiscovery()
    }
}