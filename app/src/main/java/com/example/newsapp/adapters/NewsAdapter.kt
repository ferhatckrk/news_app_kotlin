package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }


    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_preview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage)
                .into(holder.itemView.findViewById(R.id.ivArticleImage))
            this.findViewById<TextView>(R.id.tvSource).text = article.source?.name
            this.findViewById<TextView>(R.id.tvTitle).text = article.title.toString()
            this.findViewById<TextView>(R.id.tvDescription).text = article.description.toString()
            this.findViewById<TextView>(R.id.tvPublishedAt).text = article.publishedAt.toString()
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }


    private var onItemClickListener: ((Article) -> Unit)? = null


    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }


}