package com.example.memeshare20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), MemeClicked {

    private lateinit var mAdapter: MemeAdapter
    private lateinit var mlist: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView:RecyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = MemeAdapter(this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData(){

        val url = "https://meme-api.herokuapp.com/gimme/40"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val jsonArray = it.getJSONArray("memes")
                mlist = ArrayList<String>()
                for (i in 0 until jsonArray.length()){
                    val jsonObject = jsonArray.getJSONObject(i)
                    val link = jsonObject.getString("url")
                    mlist.add(link)
                }
                mAdapter.update(mlist)
            },
            {

            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun itemClicked(item: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Check out this cool MEME $item")
        val chooser = Intent.createChooser(intent, "Share this MEME using...")
        startActivity(chooser)
    }


    fun load(view: View) {
        fetchData()
    }
}