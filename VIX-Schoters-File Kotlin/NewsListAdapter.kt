package com.example.vix_schoters_newsapp

class NewsListAdapter : ListAdapter<Article, NewsViewHolder>(ArticleDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(article.urlToImage)
                .centerCrop()
                .into(ivArticleImage)

            tvTitle.text = article.title
            tvDescription.text = article.description
            tvSource.text = article.source.name
            tvPublishedAt.text = article.publishedAt

            setOnClickListener {
                val action =
                    NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(article)
                findNavController().navigate(action)
            }
        }
    }


}
class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article) {
        binding.tvTitle.text = article.title
        binding.tvDescription.text = article.description
        binding.tvAuthor.text = article.author ?: ""
        Glide.with(binding.root.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)
            .into(binding.ivThumbnail)
    }

}
class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

}