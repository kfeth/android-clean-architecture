package com.kfeth.androidcleanarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kfeth.androidcleanarchitecture.data.UserEntity
import com.kfeth.androidcleanarchitecture.data.UsersRepository
import com.kfeth.androidcleanarchitecture.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(repository: UsersRepository) : ViewModel() {

    val resource: LiveData<Resource<List<UserEntity>>> = repository.getUsers().asLiveData()
}