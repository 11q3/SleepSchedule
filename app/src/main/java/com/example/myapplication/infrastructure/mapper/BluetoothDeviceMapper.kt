package com.example.myapplication.infrastructure.mapper

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.myapplication.domain.model.BTDevice


@SuppressLint("MissingPermission")
fun BluetoothDevice.toBTDevice(): BTDevice {
    return BTDevice(
        name = name,
        address = address
    )
}
