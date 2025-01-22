package com.vk.demoapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vk.demoapp.repository.MyRepository
import com.vk.demoapp.repository.ParentRepository

class ViewModelProvider(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository: ParentRepository = MyRepository()
        return  MainViewModel(repository) as T
    }
}