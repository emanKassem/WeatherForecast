package com.golyv.network.di

import com.golyv.network.util.ConnectivityMonitor
import com.golyv.network.util.NetworkManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkBinding {

    @Binds
    abstract fun bindsNetworkManager(
        connectivityMonitor: ConnectivityMonitor
    ): NetworkManager

}