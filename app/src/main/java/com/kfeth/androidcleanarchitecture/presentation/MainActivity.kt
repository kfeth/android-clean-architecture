package com.kfeth.androidcleanarchitecture.presentation

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kfeth.androidcleanarchitecture.R
import com.kfeth.androidcleanarchitecture.domain.model.UserInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: UsersViewModel by viewModels()
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        subscribeToData()
    }

    private fun subscribeToData() {
        viewModel.viewState.observe(this, ::handleViewState)
    }

    private fun handleViewState(viewState: ViewState<List<UserInfo>>) {
        Log.d("KARL", "viewState: $viewState")
        when (viewState) {
            is Loading -> showLoading()
            is Success -> showUsersData(viewState.data)
            is Error -> handleError(viewState.error.localizedMessage)
            is NoInternetState -> handleNoInternet()
        }
    }

    private fun handleNoInternet() {
        textView.text = "No Internet!"
    }

    private fun handleError(error: String?) {
        textView.text = "Error: $error"
    }

    private fun showUsersData(users: List<UserInfo>) {
        textView.text = "Success: $users"
    }

    private fun showLoading() {
        textView.text = "Loading..."
    }
}