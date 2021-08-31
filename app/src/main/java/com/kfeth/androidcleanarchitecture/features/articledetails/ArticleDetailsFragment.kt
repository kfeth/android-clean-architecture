package com.kfeth.androidcleanarchitecture.features.articledetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kfeth.androidcleanarchitecture.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {

    private val args: ArticleDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("$args")
    }
}