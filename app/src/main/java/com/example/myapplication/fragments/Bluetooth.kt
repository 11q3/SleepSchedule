package com.example.myapplication.fragments

import com.example.myapplication.bluetooth.presentation.BluetoothViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.bluetooth.BTDevice
import com.example.myapplication.bluetooth.data.chat.AndroidBluetoothController
import com.example.myapplication.fragments.base.BaseFragment


class Bluetooth : BaseFragment() {

    private lateinit var bluetoothViewModel: BluetoothViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bluetoothController = this.context?.let { AndroidBluetoothController(it) }

        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return (bluetoothController?.let { BluetoothViewModel(it) } ?: throw IllegalArgumentException("bluetoothController must not be null")) as T
            }
        }

        bluetoothViewModel = ViewModelProvider(this, viewModelFactory).get(BluetoothViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_bluetooth, container, false)

        bluetoothViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            displayDevices(view.findViewById(R.id.pairedDevicesRecyclerView), state.pairedDevices)
            displayDevices(view.findViewById(R.id.scannedDevicesRecyclerView), state.scannedDevices)
        })

        view.findViewById<Button>(R.id.startScanButton).setOnClickListener {
            bluetoothViewModel.startScan()
        }

        view.findViewById<Button>(R.id.stopScanButton).setOnClickListener {
            bluetoothViewModel.stopScan()
        }

        return view
    }

    private fun displayDevices(listView: ListView, devices: List<BTDevice>) {
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, devices.map { it.name })
        listView.adapter = adapter
    }
}