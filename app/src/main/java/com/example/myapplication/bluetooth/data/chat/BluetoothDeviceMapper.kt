package com.example.myapplication.bluetooth.data.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.myapplication.bluetooth.BTDevice


@SuppressLint("MissingPermission")
fun BluetoothDevice.toBTDevice(): BTDevice {
    return BTDevice(
        name = name,
        address = address
    )
}
