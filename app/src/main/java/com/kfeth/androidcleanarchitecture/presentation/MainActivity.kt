package com.kfeth.androidcleanarchitecture.presentation

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kfeth.androidcleanarchitecture.R
import com.kfeth.androidcleanarchitecture.data.UserEntity
import com.kfeth.androidcleanarchitecture.util.Resource
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
        viewModel.resource.observe(this, ::handleResource)
    }

    private fun handleResource(resource: Resource<List<UserEntity>>) {
        Log.i("TAG", "resource: $resource - ${resource.data}")
        when (resource) {
            is Resource.Loading -> showLoading()
            is Resource.Error -> showError(resource.error)
            is Resource.Success -> showData(resource.data)
        }
    }

    private fun showLoading() {
        textView.text = "Loading..."
    }

    private fun showError(error: Throwable?) {
        textView.text = "Error: $error"
    }

    private fun showData(users: List<UserEntity>?) {
        textView.text = "Success: $users"
    }
}