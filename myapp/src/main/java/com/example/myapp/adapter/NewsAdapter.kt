package com.example.myapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.entity.NewsResponse.NewsEntity
import com.example.myapp.linstener.OnItemClickListener
import com.example.myapp.view.CircleTransformation
import com.squareup.picasso.Picasso

class NewsAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mInfo: List<NewsEntity>

    lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    fun setInfo(info: List<NewsEntity>) {
        mInfo = info
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_news_one, parent, false)
                return ViewHolderOne(view)
            }

            2 -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_news_two, parent, false)
                return ViewHolderTwo(view)
            }

            else -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.item_news_three, parent, false)
                return ViewHolderThree(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mInfo != null) mInfo.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return mInfo[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsEntity = mInfo[position]
        when (getItemViewType(position)) {
            1 -> {
                val vh = holder as ViewHolderOne
                vh.title.text = newsEntity.newsTitle
                vh.author.text = newsEntity.authorName
                vh.comment.text = newsEntity.commentCount.toString() + "评论 "
                vh.time.text = newsEntity.releaseDate
                Picasso.with(context).load(newsEntity.headerUrl).transform(CircleTransformation()).into(vh.header)
                Picasso.with(context).load(newsEntity.newsThumbList[0].thumbUrl).into(vh.thumb)

                // 将对象通过 fragment 中的 onClick 点击事件传递
                vh.newsEntity = newsEntity
            }

            2 -> {
                val vh = holder as ViewHolderTwo
                vh.title.text = newsEntity.newsTitle
                vh.author.text = newsEntity.authorName
                vh.comment.text = newsEntity.commentCount.toString()
                vh.time.text = newsEntity.releaseDate
                Picasso.with(context).load(newsEntity.headerUrl).transform(CircleTransformation()).into(vh.header)
                Picasso.with(context).load(newsEntity.newsThumbList[0].thumbUrl).into(vh.pic1)
                Picasso.with(context).load(newsEntity.newsThumbList[1].thumbUrl).into(vh.pic2)
                Picasso.with(context).load(newsEntity.newsThumbList[2].thumbUrl).into(vh.pic3)

                // 将对象通过 fragment 中的 onClick 点击事件传递
                vh.newsEntity = newsEntity
            }

            else -> {
                val vh = holder as ViewHolderThree
                vh.title.text = newsEntity.newsTitle
                vh.author.text = newsEntity.authorName
                vh.comment.text = newsEntity.commentCount.toString() + "评论 "
                vh.time.text = newsEntity.releaseDate
                Picasso.with(context).load(newsEntity.headerUrl).into(vh.header)
                Picasso.with(context).load(newsEntity.newsThumbList[0].thumbUrl).into(vh.thumb)

                // 将对象通过 fragment 中的 onClick 点击事件传递
                vh.newsEntity = newsEntity
            }
        }
    }

    inner class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.title)
        var thumb = itemView.findViewById<ImageView>(R.id.thumb)

        var header = itemView.findViewById<ImageView>(R.id.header)
        var author = itemView.findViewById<TextView>(R.id.author)
        var comment = itemView.findViewById<TextView>(R.id.comment)
        var time = itemView.findViewById<TextView>(R.id.time)
        lateinit var newsEntity: NewsEntity

        init {
            itemView.setOnClickListener { mOnItemClickListener.onItemClick(newsEntity) }
        }
    }

    inner class ViewHolderTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.title)
        var header = itemView.findViewById<ImageView>(R.id.header)
        var author = itemView.findViewById<TextView>(R.id.author)
        var comment = itemView.findViewById<TextView>(R.id.comment)
        var time = itemView.findViewById<TextView>(R.id.time)
        var pic1 = itemView.findViewById<ImageView>(R.id.pic1)
        var pic2 = itemView.findViewById<ImageView>(R.id.pic2)
        var pic3 = itemView.findViewById<ImageView>(R.id.pic3)

        lateinit var newsEntity: NewsEntity

        init {
            itemView.setOnClickListener { mOnItemClickListener.onItemClick(newsEntity) }
        }

    }

    inner class ViewHolderThree(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.title)
        var thumb = itemView.findViewById<ImageView>(R.id.thumb)

        var header = itemView.findViewById<ImageView>(R.id.header)
        var author = itemView.findViewById<TextView>(R.id.author)
        var comment = itemView.findViewById<TextView>(R.id.comment)
        var time = itemView.findViewById<TextView>(R.id.time)

        lateinit var newsEntity: NewsEntity

        init {
            itemView.setOnClickListener { mOnItemClickListener.onItemClick(newsEntity) }
        }
    }
}