package com.rofidamaher.movieapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.movie_list_item.view.*

class Adapter (val context: Context, val list:ArrayList<Data>): BaseAdapter() {

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View = View.inflate(context,R.layout.activity_main , null)

        var listItem:Data = list.get(position)
        view.rv_movie_list.cv_movie_title.text = listItem.title
        view.rv_movie_list.cv_movie_release_date.text = listItem.date

        Picasso.get().load(listItem.img_url.toString()).into(view.cv_iv_movie_poster)
        return view
    }


}