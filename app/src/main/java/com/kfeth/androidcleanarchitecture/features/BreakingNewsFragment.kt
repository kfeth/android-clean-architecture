package com.kfeth.androidcleanarchitecture.features

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kfeth.androidcleanarchitecture.R
import com.kfeth.androidcleanarchitecture.data.ArticleEntity
import com.kfeth.androidcleanarchitecture.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val viewModel: BreakingNewsViewModel by viewModels()
    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView)
        subscribeToData()
    }

    private fun subscribeToData() {
        viewModel.resource.observe(viewLifecycleOwner, ::handleResource)
    }

    private fun handleResource(resource: Resource<List<ArticleEntity>>) {
        Log.i(javaClass.simpleName, "resource: $resource - ${resource.data?.size}")
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

    private fun showData(articles: List<ArticleEntity>?) {
        textView.text = articles?.joinToString("\n*") { it.title }
    }
}