package com.golyv.uicomponents.screens.error.model

enum class Error(val value: String) {
    PERMISSION_DENIED("permission denied"),
    INTERNET_CONNECTION("No internet connection"),
    GENERAL_ERROR("general error");

    companion object {
        fun fromValue(value: String): Error? {
            return entries.find { it.value == value }
        }
    }
}