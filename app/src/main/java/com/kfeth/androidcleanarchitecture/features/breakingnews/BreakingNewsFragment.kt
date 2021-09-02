package com.kfeth.androidcleanarchitecture.features.breakingnews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kfeth.androidcleanarchitecture.R
import com.kfeth.androidcleanarchitecture.data.Article
import com.kfeth.androidcleanarchitecture.databinding.FragmentBreakingNewsBinding
import com.kfeth.androidcleanarchitecture.util.Resource
import com.kfeth.androidcleanarchitecture.util.showSnackBarError
import com.kfeth.androidcleanarchitecture.util.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private val viewModel: BreakingNewsViewModel by viewModels()

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBreakingNewsBinding.bind(view)

        val adapter = ArticleAdapter(onClick = {
            navigateToDetailsFragment(it)
        })
        binding.apply {
            recyclerView.adapter = adapter
        }
        subscribeToData()
    }

    private fun subscribeToData() {
        viewModel.resource.observe(viewLifecycleOwner, {

            (binding.recyclerView.adapter as ArticleAdapter).submitList(it.data)
            binding.progressBar.toggleVisibility(it is Resource.Loading)

            if (it is Resource.Error) {
                showSnackBarError(it.error)
            }
        })
    }

    private fun navigateToDetailsFragment(article: Article) {
        val action =
            BreakingNewsFragmentDirections.actionDetailsFragment(article.title)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}