package com.kfeth.template.features.breakingnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.template.data.Article
import com.kfeth.template.databinding.ItemArticleBinding
import com.kfeth.template.util.setImageUrl

class ArticleAdapter(private val onClick: (Article) -> Unit) :
    ListAdapter<Article, ArticleViewHolder>(ArticleDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleViewHolder.from(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.bind(getItem(position), onClick)
}

class ArticleViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article, click: (Article) -> Unit) {
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

object ArticleDiff : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Article, newItem: Article) =
        oldItem == newItem
}
