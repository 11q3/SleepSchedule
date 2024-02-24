package com.example.myapplication.domain.model

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult
    data class Error(val message:String): ConnectionResult
}