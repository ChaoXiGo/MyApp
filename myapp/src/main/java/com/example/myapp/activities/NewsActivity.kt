package com.example.myapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp.R
import com.example.myapp.databinding.ActivityNewsBinding
import com.example.myapp.entity.NewsResponse.NewsEntity
import com.example.myapp.entity.VideoResponse
import com.example.myapp.view.CircleTransformation
import com.squareup.picasso.Picasso

class NewsActivity : BaseActivity<ActivityNewsBinding>() {
    override fun initBinding(): ActivityNewsBinding {
        return ActivityNewsBinding.inflate(layoutInflater)
    }

    override fun initData() {
        val bundle = intent.extras
        if (bundle != null) {
            val entity = bundle.getSerializable("newsEntity") as NewsEntity
            initContent(entity)
        }

        vb.imgCover.setOnClickListener{
            showToast("放大图片")
        }
    }

    private fun initContent(entity: NewsEntity) {

        vb.tvTitle.text = entity.newsTitle

        Picasso.with(this).load(entity.headerUrl).transform(CircleTransformation()).into(vb.imgHeader)
        Picasso.with(this).load(entity.newsThumbList[0].thumbUrl).into(vb.imgCover)

        vb.tvAuthor.text = entity.authorName
        vb.tvCreateTime.text = entity.releaseDate
        vb.desc.text = entity.newsTitle

        vb.tvComment.text = "评论 ${entity.commentCount}"
    }

}