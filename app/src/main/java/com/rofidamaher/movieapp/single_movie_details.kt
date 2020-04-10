package com.rofidamaher.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_movie_details.*

class single_movie_details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie_details)

        var intent =intent

        Picasso.get().load(intent.getStringExtra("img_url").toString()).into(iv_movie_poster)
        movie_title.text = intent.getStringExtra("title")
        movie_tagline.text = intent.getStringExtra("subtitle")
        movie_release_date.text = intent.getStringExtra("date")
        movie_rating.text = intent.getStringExtra("rating")
        movie_Popularity.text = intent.getStringExtra("popularity")
        movie_vote_count.text = intent.getStringExtra("voteCount")
        movie_id.text = intent.getStringExtra("id")
        movie_overview.text = intent.getStringExtra("overview")+"....."



    }
}
