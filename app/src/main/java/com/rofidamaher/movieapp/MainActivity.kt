package com.rofidamaher.movieapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var arrayList:ArrayList<Data>? = null
    //private var gradView:GridView
    private var adapter:Adapter? = null
    lateinit var pDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url ="https://api.themoviedb.org/3/movie/popular?api_key=f55a1c7dc128bab3647c3f8abb0aa657"

        AsyncTaskHandler().execute(url)
    }


    inner class AsyncTaskHandler: AsyncTask<String, String, String>(), AdapterView.OnItemClickListener {

        override fun onPreExecute() {
            super.onPreExecute()
            pDialog= ProgressDialog(this@MainActivity)
            pDialog.setMessage("Please Wait")
            pDialog.setCancelable(false)
            pDialog.show()

        }

        override fun doInBackground(vararg url: String?): String {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            val res:String
            val connection=URL(url[0]).openConnection()as HttpURLConnection
            try{
                connection.connect()
                res=connection.inputStream.use { it.reader().use { reader->reader.readText() } }

            }
            finally {
                connection.disconnect()
            }
            return res
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(pDialog.isShowing())
                pDialog.dismiss()


            jsonResult(result)
        }

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            var items:Data = arrayList!!.get(position)
            Toast.makeText(applicationContext,items.title,Toast.LENGTH_LONG).show()

            val intent = Intent(this@MainActivity, single_movie_details::class.java)
            intent.putExtra("id",items.id)
            intent.putExtra("rating",items.rating)
            intent.putExtra("popularity",items.popularity)
            intent.putExtra("voteCount",items.voteCount)
            intent.putExtra("title",items.title)
            intent.putExtra("subtitle",items.subtitle)
            intent.putExtra("date",items.date)
            intent.putExtra("overview",items.overview)
            intent.putExtra("img_url",items.img_url)
            startActivity(intent)
        }

        fun jsonResult(jsonString: String?) {

            try {
                val jsonObject = JSONObject(jsonString)
                val jsonArray = JSONArray( jsonObject.getString("results"))

                arrayList = ArrayList()
                var i = 0
                while (i < jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    arrayList!!.add(
                        Data(
                            jsonObject.getInt("id"),
                            jsonObject.getInt("vote_average"),
                            jsonObject.getInt("popularity"),
                            jsonObject.getInt("vote_count"),
                            jsonObject.getString("title"),
                            jsonObject.getString("original_title"),
                            jsonObject.getString("release_date"),
                            jsonObject.getString("overview"),
                            jsonObject.getString("poster_path")

                        )
                    )
                    i++
                }

                adapter = Adapter(applicationContext, arrayList!!)
                rv_movie_list?.adapter = adapter
                rv_movie_list?.onItemClickListener = this
            }
            finally {

            }


        }

    }




}
