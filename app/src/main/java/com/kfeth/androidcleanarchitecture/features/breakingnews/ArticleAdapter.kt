package com.kfeth.androidcleanarchitecture.features.breakingnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.androidcleanarchitecture.data.ArticleEntity
import com.kfeth.androidcleanarchitecture.databinding.ItemArticleBinding
import com.kfeth.androidcleanarchitecture.util.setImageUrl

class ArticleAdapter(private val onClick: (ArticleEntity) -> Unit) :
    ListAdapter<ArticleEntity, ArticleViewHolder>(ArticleEntityDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleViewHolder.from(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.bind(getItem(position), onClick)
}

class ArticleViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: ArticleEntity, click: (ArticleEntity) -> Unit) {
        binding.apply {
            title.text = article.title
            imageView.setImageUrl(article.imageUrl)
            root.setOnClickListener { click.invoke(article) }
        }
    }
    companion object {
        fun from(parent: ViewGroup): ArticleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemArticleBinding.inflate(layoutInflater, parent, false)
            return ArticleViewHolder(binding)
        }
    }
}

object ArticleEntityDiff : DiffUtil.ItemCallback<ArticleEntity>() {
    override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity) =
        oldItem == newItem
}