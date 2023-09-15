package com.example.myapp.activities

import cc.shinichi.library.ImagePreview
import com.example.myapp.databinding.ActivityNewsBinding
import com.example.myapp.entity.NewsResponse.NewsEntity
import com.example.myapp.view.CircleTransformation
import com.squareup.picasso.Picasso

class NewsActivity : BaseActivity<ActivityNewsBinding>() {
    override fun initBinding(): ActivityNewsBinding {
        return ActivityNewsBinding.inflate(layoutInflater)
    }

    lateinit var entity: NewsEntity
    override fun initData() {
        val bundle = intent.extras
        if (bundle != null) {
            entity = bundle.getSerializable("newsEntity") as NewsEntity
            initContent(entity)
        }
        vb.imgCover.setOnClickListener {
            ImagePreview
                .instance
                .setContext(this)
                .setIndex(0)
                .setImage(entity.newsThumbList[0].thumbUrl)
           .start()
        }
    }

    private fun initContent(entity: NewsEntity) {

        vb.tvTitle.text = entity.newsTitle

        Picasso.with(this).load(entity.headerUrl).transform(CircleTransformation())
            .into(vb.imgHeader)
        Picasso.with(this).load(entity.newsThumbList[0].thumbUrl).into(vb.imgCover)




        vb.tvAuthor.text = entity.authorName
        vb.tvCreateTime.text = entity.releaseDate
        vb.desc.text = entity.newsTitle

        vb.tvComment.text = "评论 ${entity.commentCount}"
    }

}