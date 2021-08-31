package com.kfeth.androidcleanarchitecture.features.breakingnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kfeth.androidcleanarchitecture.R
import com.kfeth.androidcleanarchitecture.data.ArticleEntity

class ArticleAdapter(private val click: (ArticleEntity) -> Unit) :
    ListAdapter<ArticleEntity, ArticleViewHolder>(ArticlesDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleViewHolder.from(parent)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.bind(getItem(position), click)
}

class ArticleViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val root: View = itemView.findViewById(R.id.root)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val subtitle: TextView = itemView.findViewById(R.id.subtitle)

    fun bind(data: ArticleEntity, click: (ArticleEntity) -> Unit) {
        title.text = data.title
        subtitle.text = data.url
        root.setOnClickListener { click.invoke(data) }
    }
    companion object {
        fun from(parent: ViewGroup): ArticleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_article, parent, false)
            return ArticleViewHolder(view)
        }
    }
}

class ArticlesDiff : DiffUtil.ItemCallback<ArticleEntity>() {
    override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity) =
        oldItem == newItem
}