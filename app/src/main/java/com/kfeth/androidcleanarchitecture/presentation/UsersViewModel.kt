package com.kfeth.androidcleanarchitecture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.androidcleanarchitecture.domain.interaction.users.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) :
    ViewModel() {

    fun load() {
            viewModelScope.launch {
                delay(1000)
                getUsersUseCase.invoke()
            }
        }
    }