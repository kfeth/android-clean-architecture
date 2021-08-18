package com.kfeth.androidcleanarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.androidcleanarchitecture.domain.interaction.users.GetUsersUseCase
import com.kfeth.androidcleanarchitecture.domain.model.UserInfo
import com.kfeth.androidcleanarchitecture.domain.model.onFailure
import com.kfeth.androidcleanarchitecture.domain.model.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val getUsers: GetUsersUseCase) :
    ViewModel() {

    private val _viewState = MutableLiveData<ViewState<List<UserInfo>>>()
    val viewState: LiveData<ViewState<List<UserInfo>>>
        get() = _viewState

    init {
        executeUseCase {
            getUsers()
                .onSuccess { _viewState.value = Success(it) }
                .onFailure { _viewState.value = Error(it.throwable) }
        }
    }

    private fun executeUseCase(action: suspend () -> Unit) {
        _viewState.value = Loading()
        viewModelScope.launch(Dispatchers.Main) {
            action()
        }
    }
}