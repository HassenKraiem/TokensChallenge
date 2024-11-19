package com.example.tokenschallenge.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.example.tokenschallenge.mainActivity")
class DataStoreModule

@Module
@ComponentScan("com.example.tokenschallenge.data")
class ApiModule

@Module
@ComponentScan("com.example.tokenschallenge.screen")
class ViewModelModelModule