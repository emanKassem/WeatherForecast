package com.golyv.network.util

import kotlinx.coroutines.flow.Flow


interface NetworkManager {

    val hasInternet: Flow<Boolean>

}