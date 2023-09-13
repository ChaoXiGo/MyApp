package com.example.myapp.activities

import android.widget.GridLayout
import android.widget.ImageView
import com.example.myapp.databinding.ActivityNews2Binding
import com.example.myapp.entity.NewsResponse.NewsEntity
import com.example.myapp.view.CircleTransformation
import com.squareup.picasso.Picasso


class NewsActivity2 : BaseActivity<ActivityNews2Binding>() {
    override fun initBinding(): ActivityNews2Binding {
        return ActivityNews2Binding.inflate(layoutInflater)
    }

    override fun initData() {
        val bundle = intent.extras
        if (bundle != null) {
            val entity = bundle.getSerializable("newsEntity") as NewsEntity
            initContent(entity)
        }
    }

    private fun initContent(entity: NewsEntity) {
        vb.tvTitle.text = entity.newsTitle

        Picasso.with(this).load(entity.headerUrl).transform(CircleTransformation()).into(vb.imgHeader)
        Picasso.with(this).load(entity.newsThumbList[0].thumbUrl).into(vb.imgCover1)
        Picasso.with(this).load(entity.newsThumbList[1].thumbUrl).into(vb.imgCover2)
        Picasso.with(this).load(entity.newsThumbList[2].thumbUrl).into(vb.imgCover3)

        vb.tvAuthor.text = entity.authorName
        vb.tvCreateTime.text = entity.releaseDate
        vb.desc.text = entity.newsTitle

        vb.tvComment.text = "评论 ${entity.commentCount}"
    }
}