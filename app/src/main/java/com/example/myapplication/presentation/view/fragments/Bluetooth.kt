package com.example.myapplication.presentation.view.fragments

import com.example.myapplication.presentation.viewmodels.BluetoothViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.coroutineScope
import com.example.myapplication.R
import com.example.myapplication.domain.model.BTDevice
import com.example.myapplication.infrastructure.controller.AndroidBluetoothController
import com.example.myapplication.presentation.view.fragments.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



class Bluetooth : BaseFragment() {

    private lateinit var bluetoothViewModel: BluetoothViewModel
    private lateinit var startScanButton: Button
    private lateinit var stopScanButton: Button

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bluetoothController = context?.let { AndroidBluetoothController(it) }
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return (bluetoothController?.let { BluetoothViewModel(it) }
                    ?: throw IllegalArgumentException("bluetoothController must not be null")) as T
            }
        }

        bluetoothViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[BluetoothViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_bluetooth, container, false)

        startScanButton = view.findViewById(R.id.startScanButton)
        stopScanButton = view.findViewById(R.id.stopScanButton)

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bluetoothViewModel.state.collectLatest { state ->
                    displayDevices(view.findViewById(R.id.pairedDevicesRecyclerView), state.pairedDevices)
                    displayDevices(view.findViewById(R.id.scannedDevicesRecyclerView), state.scannedDevices)
                }
            }
        }

        startScanButton.setOnClickListener {
            bluetoothViewModel.startScan()
        }

        stopScanButton.setOnClickListener {
            bluetoothViewModel.stopScan()
        }

        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_STOP) {
                    bluetoothViewModel.stopScan()
                }
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                bluetoothViewModel.stopScan()
            }
        })

        return view
    }

    private fun displayDevices(listView: ListView, devices: List<BTDevice>) {
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1)
        devices.forEach { device ->
            val name = device.name ?: "Unknown"
                adapter.add(name)
        }
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener( ){ parent, view, position, id ->
            val selectedDevice = devices[position]
            bluetoothViewModel.startConnection(selectedDevice)
        }
    }
}