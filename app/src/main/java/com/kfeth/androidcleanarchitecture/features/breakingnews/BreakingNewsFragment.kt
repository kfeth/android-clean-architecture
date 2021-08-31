package com.kfeth.androidcleanarchitecture.features.breakingnews

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kfeth.androidcleanarchitecture.R
import com.kfeth.androidcleanarchitecture.data.ArticleEntity
import com.kfeth.androidcleanarchitecture.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val viewModel: BreakingNewsViewModel by viewModels()
    private lateinit var textView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView)

        view.findViewById<Button>(R.id.navigate).setOnClickListener {
            navigateToDetailsFragment()
        }
        subscribeToData()
    }

    private fun subscribeToData() {
        viewModel.resource.observe(viewLifecycleOwner, ::handleResource)
    }

    private fun handleResource(resource: Resource<List<ArticleEntity>>) {
        Timber.i("$resource: ${resource.data?.size}")
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

    private fun navigateToDetailsFragment() {
        val sampleParam = "hello param"
        val action = BreakingNewsFragmentDirections.actionArticleDetailsFragment(sampleParam)
        findNavController().navigate(action)
    }
}