package com.kfeth.androidcleanarchitecture.features.breakingnews

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.androidcleanarchitecture.R
import com.kfeth.androidcleanarchitecture.data.ArticleEntity
import com.kfeth.androidcleanarchitecture.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val viewModel: BreakingNewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)

        val adapter = ArticleAdapter(click = { navigateToDetailsFragment(it) })
        recyclerView.adapter = adapter

        viewModel.resource.observe(viewLifecycleOwner, {
            Timber.i("$it: ${it.data?.size}")
            adapter.submitList(it.data)
            when (it) {
                is Resource.Loading -> showLoading()
                is Resource.Error -> showError(it.error)
                is Resource.Success -> hideLoading()
            }
        })
    }

    private fun showLoading() { progressBar.visibility = View.VISIBLE }

    private fun hideLoading() { progressBar.visibility = View.GONE }

    private fun showError(error: Throwable?) {
        hideLoading()
        // TODO SnackBar message
    }

    private fun navigateToDetailsFragment(articleEntity: ArticleEntity) {
        val action = BreakingNewsFragmentDirections.actionArticleDetailsFragment(articleEntity.title)
        findNavController().navigate(action)
    }
}